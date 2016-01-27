package com.example.vidapp.vidapp.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.listener.CommunicationChannel;

import java.io.File;
import java.util.ArrayList;


public class HelpFragment extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    ImageView homeButton;
VideoView helpVideo;

    CommunicationChannel mCommChListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help, container, false);

//        helpVideo = (VideoView) view.findViewById(R.id.home_video_view);
//        helpVideo.setMediaController(null);
//        helpVideo.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.help_video));  //Don't put extension
//        helpVideo.requestFocus(0);
//        helpVideo.start();
        homeButton = (ImageView) view.findViewById(R.id.home_image_view);
        homeButton.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

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

    public void sendMessage(int id) {
        mCommChListener.setCommunication(id);
    }


}
