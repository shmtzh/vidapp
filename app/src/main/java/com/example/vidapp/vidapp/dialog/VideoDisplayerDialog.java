package com.example.vidapp.vidapp.dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.model.CutModel;
import com.example.vidapp.vidapp.utils.TrimVideoUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shmtzh on 1/11/16.
 */
public class VideoDisplayerDialog extends DialogFragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private final String LOG_TAG = getClass().getSimpleName();
    String path;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    int timeInMilliseconds = 0;
    int timeSwapBuff = 0;
    int updatedTime = 0;
    int secs, mins, milliseconds;
    ArrayList<CutModel> toCutModel = new ArrayList<>();

    private VideoView mVideoView;
    private Context mContext;
    private Uri mUri;
    private final Handler mHandler = new Handler();
    public ProgressDialog mProgress;
    private int mTrimStartTime = 0;
    private int mTrimEndTime = 0;
    private String mSaveFileName = null;
    private static final String TIME_STAMP_NAME = "'TRIM'_yyyyMMdd_HHmmss";
    private File mSrcFile = null;
    private File mDstFile = null;
    private File mSaveDirectory = null;
    // For showing the result.
    private String saveFolderName = null;


    public VideoDisplayerDialog(String path) {
        this.path = path;
    }

    public VideoDisplayerDialog() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.video_dialog, null);


        final VideoView videoView = (VideoView) v.findViewById(R.id.video_container);
        videoView.setVideoPath(path);
        videoView.setMediaController(null);
        videoView.start();
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);


        videoView.setOnTouchListener(new View.OnTouchListener() {


            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CutModel model = new CutModel();
                model.setPath(path);
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {

                    model.setStart(String.valueOf(timeInMilliseconds));
                    Log.d(TAG, String.valueOf(timeInMilliseconds));
                    videoView.setBackground(getResources().getDrawable(R.drawable.help_back));
                    mTrimStartTime = timeInMilliseconds;

                } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {

                    videoView.setBackground(null);
                    Log.d(TAG, String.valueOf(timeInMilliseconds));
                    mTrimEndTime = timeInMilliseconds;
                    model.setEnd(String.valueOf(timeInMilliseconds));

                    toCutModel.add(model);
                    trimVideo();
                }
                return true;
            }
        });


        return v;
    }

    private void trimVideo() {

        mSaveDirectory = getSaveDirectory();
        saveFolderName = mSaveDirectory.getName();

        mSaveFileName = new SimpleDateFormat(TIME_STAMP_NAME).format(
                new Date(System.currentTimeMillis()));
        mDstFile = new File(mSaveDirectory, mSaveFileName + ".mp4");
        mSrcFile = new File(path);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TrimVideoUtils.startTrim(mSrcFile, mDstFile, mTrimStartTime, mTrimEndTime);
                    insertContent(mDstFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private File getSaveDirectory() {
        String folder_main = "VidAppCuts";

        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }
        return f;
    }

    @Override
    public void onStop() {
        super.onStop();
        timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
        Log.d(TAG, String.valueOf(toCutModel.size()));
        Log.d(TAG, toCutModel.toString());

    }

    public void onClick(View v) {
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }


    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }

    private Uri insertContent(File file) {
        long nowInMs = System.currentTimeMillis();
        long nowInSec = nowInMs / 1000;
        final ContentValues values = new ContentValues(12);
        values.put(MediaStore.Video.Media.TITLE, mSaveFileName);
        values.put(MediaStore.Video.Media.DISPLAY_NAME, file.getName());
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.Media.DATE_TAKEN, nowInMs);
        values.put(MediaStore.Video.Media.DATE_MODIFIED, nowInSec);
        values.put(MediaStore.Video.Media.DATE_ADDED, nowInSec);
        values.put(MediaStore.Video.Media.DATA, file.getAbsolutePath());
        values.put(MediaStore.Video.Media.SIZE, file.length());
        // Copy the data taken and location info from src.
        String[] projection = new String[]{
                MediaStore.Video.VideoColumns.DATE_TAKEN,
                MediaStore.Video.VideoColumns.LATITUDE,
                MediaStore.Video.VideoColumns.LONGITUDE,
                MediaStore.Video.VideoColumns.RESOLUTION,
        };
        // Copy some info from the source file.
        querySource(projection, new ContentResolverQueryCallback() {
            @Override
            public void onCursorResult(Cursor cursor) {
                long timeTaken = cursor.getLong(0);
                if (timeTaken > 0) {
                    values.put(MediaStore.Video.Media.DATE_TAKEN, timeTaken);
                }
                double latitude = cursor.getDouble(1);
                double longitude = cursor.getDouble(2);
                // TODO: Change || to && after the default location issue is
                // fixed.
                if ((latitude != 0f) || (longitude != 0f)) {
                    values.put(MediaStore.Video.Media.LATITUDE, latitude);
                    values.put(MediaStore.Video.Media.LONGITUDE, longitude);
                }
                values.put(MediaStore.Video.Media.RESOLUTION, cursor.getString(3));
            }
        });
        return getActivity().getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
    }

    private interface ContentResolverQueryCallback {
        void onCursorResult(Cursor cursor);
    }

    private void querySource(String[] projection, ContentResolverQueryCallback callback) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(mUri, projection, null, null, null);
            if ((cursor != null) && cursor.moveToNext()) {
                callback.onCursorResult(cursor);
            }
        } catch (Exception e) {
            // Ignore error for lacking the data column from the source.
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = (int) (SystemClock.uptimeMillis() - startTime);
            updatedTime = timeSwapBuff + timeInMilliseconds;
//            secs = (int) (updatedTime / 1000);
//            mins = secs / 60;
//            secs = secs % 60;
//            milliseconds = (int) (updatedTime % 1000);
            customHandler.postDelayed(this, 0);
        }
    };

}
