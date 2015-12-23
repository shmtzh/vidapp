package com.example.vidapp.vidapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vidapp.vidapp.R;


public class StartFragment extends Fragment implements View.OnClickListener {


    private final String TAG = getClass().getSimpleName();
    ImageView startImageView, helpImageView, mylibImageView;

    CommunicationChannel mCommChListner = null;

    public interface CommunicationChannel
    {
        public void setCommunication(int id);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        startImageView = (ImageView) view.findViewById(R.id.start_image_view);
        helpImageView = (ImageView) view.findViewById(R.id.help_image_view);
        mylibImageView = (ImageView) view.findViewById(R.id.mylib_image_view);

        startImageView.setOnClickListener(this);
        helpImageView.setOnClickListener(this);
        mylibImageView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.start_image_view:
                sendMessage(0);
                break;

            case R.id.help_image_view:
                sendMessage(1);
                break;

            case R.id.mylib_image_view:
                sendMessage(2);
                break;

        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if(context instanceof CommunicationChannel)
        {
            mCommChListner = (CommunicationChannel) context;
        }
        else
        {
            throw new ClassCastException();
        }

    }

    public void sendMessage(int id)
    {
        mCommChListner.setCommunication(id);
    }


}


