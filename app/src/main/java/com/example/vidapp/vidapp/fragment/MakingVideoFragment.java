package com.example.vidapp.vidapp.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.TrackBox;
import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.activity.StartActivity;
import com.example.vidapp.vidapp.adapter.finished.FinishedMoviesAdapter;
import com.example.vidapp.vidapp.listener.CommunicationChannel;
import com.example.vidapp.vidapp.model.VideoModel;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Mp4TrackImpl;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.util.Matrix;
import com.googlecode.mp4parser.util.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MakingVideoFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getCanonicalName();
    static ArrayList<String> paths = new ArrayList<>();
    static ArrayList<Movie> files = new ArrayList<>();
    String internalStorage;
    CommunicationChannel mCommChListener;
    EditText nameText;
    String nameCred;
    private boolean isLeftLandscape = false, isRightLandscape = false;

    public MakingVideoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_making_video, container, false);
        nameText = (EditText) view.findViewById(R.id.new_video_name);
        nameText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            return true;
                    }
                }
                return true;
            }
        });
        Button saveButton = (Button) view.findViewById(R.id.save);
        ImageView homeButton = (ImageView) view.findViewById(R.id.home_image_view);
        saveButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);
        searchForVideoFiles();
        try {
            combineClips();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        return view;
    }


    public ArrayList<String> searchForVideoFiles() {
        internalStorage = System.getenv("EXTERNAL_STORAGE") + "/VidAppCuts";
        paths = getListFiles(new File(internalStorage));
        Log.d(TAG, String.valueOf(paths.size()) + "paths");
        return paths;
    }


    private ArrayList<String> getListFiles(File parentDir) {
        ArrayList<String> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if (file.getName().endsWith(".mp4")) {
                    inFiles.add(file.getPath());
                }
            }
        }
        return inFiles;
    }

    protected void combineClips() throws IOException {

        MediaMetadataRetriever m = new MediaMetadataRetriever();


        for (int i = 0; i < paths.size(); i++) {
            m.setDataSource(paths.get(i));
            if (Build.VERSION.SDK_INT >= 17) {
                String s = m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
                if (Integer.valueOf(s) == 0) {
                    isLeftLandscape = true;
                }
                if (Integer.valueOf(s) == 180) {
                    isRightLandscape = true;
                }
            }
        }

        if (isLeftLandscape && isRightLandscape) {
            for (int i = 0; i < paths.size(); i++) {
                m.setDataSource(paths.get(i));
                if (Build.VERSION.SDK_INT >= 17) {
                    String s = m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
                    if (Integer.valueOf(s) == 180)
                        rotate(paths.get(i));
                }
            }
        }


        for (int i = 0; i < paths.size(); i++) {
            Movie tm = MovieCreator.build(paths.get(i));
            Log.d(TAG, files.size() + "files");
            files.add(tm);
        }
        List<Track> videoTracks = new ArrayList<Track>();
        List<Track> audioTracks = new ArrayList<Track>();
        for (Movie movie : files) {
            for (Track t : movie.getTracks()) {
                if (t.getHandler().equals("soun")) {
                    audioTracks.add(t);
                }
                if (t.getHandler().equals("vide")) {
                    videoTracks.add(t);
                }
            }
        }
        Movie result = new Movie();

        if (audioTracks.size() > 0) {
            Log.d(TAG, String.valueOf(audioTracks.size()) + "audi");
            result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
        }
        if (videoTracks.size() > 0) {
            Log.d(TAG, String.valueOf(audioTracks.size()) + "vide");
            result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
        }
        Container out = new DefaultMp4Builder().build(result);
        String folder_main = "VidApp";
        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }
        FileChannel fc = new RandomAccessFile(String.format(System.getenv("EXTERNAL_STORAGE") + "/VidApp" + "/output.mp4"), "rw").getChannel();
        out.writeContainer(fc);

        fc.close();
        while (!audioTracks.isEmpty()) {
            audioTracks.remove(0);
        }
        while (!videoTracks.isEmpty()) {
            videoTracks.remove(0);
        }
        while (!files.isEmpty()) {
            files.remove(0);
        }


    }

    public void convertFileToBitMap(File file) {
        Log.d(TAG, file.getAbsolutePath());
        StartActivity.libModels.add(
                new VideoModel(
                       ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(),
                               MediaStore.Images.Thumbnails.MICRO_KIND), file));
     }


    public void rotate(String path) throws IOException {

        IsoFile isoFile = new IsoFile(path);
        Movie m = new Movie();

        List<TrackBox> trackBoxes = isoFile.getMovieBox().getBoxes(
                TrackBox.class);

        for (TrackBox trackBox : trackBoxes) {

            trackBox.getTrackHeaderBox().setMatrix(Matrix.ROTATE_0);
            m.addTrack(new Mp4TrackImpl(trackBox));
        }


        Container out = new DefaultMp4Builder().build(m);
        String folder_main = "VidApp";
        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }
        FileChannel fc = new RandomAccessFile(String.format(System.getenv("EXTERNAL_STORAGE") + "/VidApp" + "/output.mp4"), "rw").getChannel();
        out.writeContainer(fc);
        fc.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                nameCred = nameText.getText().toString();
                setFileName();
                deleteTempFiles();
                sendMessage(2);
                break;
            case R.id.home_image_view:
                sendMessage(3);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CommunicationChannel) {
            mCommChListener = (CommunicationChannel) context;
        } else {
            throw new ClassCastException();
        }
    }

    public void deleteTempFiles() {
        File dir = new File(System.getenv("EXTERNAL_STORAGE") + "/VidAppCuts/");
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }

    public void sendMessage(int id) {
        mCommChListener.setCommunication(id);
    }


    private void setFileName() {
        String sdcard = System.getenv("EXTERNAL_STORAGE") + "/VidApp/";
        File from = new File(sdcard, "output.mp4");
        nameCred = nameCred.replaceAll(" ", "_");
        File to = new File(sdcard, nameCred + ".mp4");
        from.renameTo(to);
        convertFileToBitMap(to);

    }

}
