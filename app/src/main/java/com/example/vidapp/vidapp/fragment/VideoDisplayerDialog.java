package com.example.vidapp.vidapp.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.vidapp.vidapp.R;

/**
 * Created by shmtzh on 1/11/16.
 */
public class VideoDisplayerDialog extends DialogFragment implements View.OnClickListener {

    private  final String LOG_TAG = getClass().getSimpleName();
String path;

    public VideoDisplayerDialog(String path) {
        this.path = path;
    }

    public VideoDisplayerDialog() {

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.video_dialog, null);


        VideoView videoView = (VideoView) v.findViewById(R.id.video_container);

        videoView.setVideoPath(path);

        videoView.setMediaController(new MediaController(getActivity()));
        videoView.requestFocus(0);
        videoView.start();
        return v;
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
}
