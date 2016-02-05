package com.example.vidapp.vidapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.activity.StartActivity;
import com.example.vidapp.vidapp.listener.CommunicationChannel;


public class FinishedVideoDisplayingFragment extends Fragment {
    String path;
    ImageView dismissImageView;

    public FinishedVideoDisplayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_finished_video_displaying, container, false);
        path = StartActivity.getPath();
        dismissImageView = (ImageView) v.findViewById(R.id.dismiss_imageview);
        dismissImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(2);
            }
        });
        dismissImageView.bringToFront();
        final VideoView videoView = (VideoView) v.findViewById(R.id.video_container);
        videoView.setVideoPath(path);
        videoView.setMediaController(null);
        videoView.start();
        return v;
    }

    CommunicationChannel mCommChListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CommunicationChannel) {
            mCommChListener = (CommunicationChannel) context;
        } else {
            throw new ClassCastException();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void sendMessage(int id) {
        mCommChListener.setCommunication(id);
    }

}
