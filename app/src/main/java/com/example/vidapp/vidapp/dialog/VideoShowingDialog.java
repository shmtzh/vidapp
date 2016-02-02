package com.example.vidapp.vidapp.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.vidapp.vidapp.R;

/**
 * Created by shmtzh on 1/26/16.
 */
public class VideoShowingDialog extends DialogFragment {

    String path;
    ImageView dismissImageView;

    public VideoShowingDialog(String path) {
        this.path = path;
    }

    public VideoShowingDialog() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams a = getDialog().getWindow().getAttributes();
        a.dimAmount = 0;
        getDialog().getWindow().setAttributes(a);

        View v = inflater.inflate(R.layout.video_dialog, null);
        dismissImageView = (ImageView) v.findViewById(R.id.dismiss_imageview);
        dismissImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        dismissImageView.bringToFront();
        final VideoView videoView = (VideoView) v.findViewById(R.id.video_container);
        videoView.setVideoPath(path);
        videoView.setMediaController(null);
        videoView.start();

        return v;
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

}