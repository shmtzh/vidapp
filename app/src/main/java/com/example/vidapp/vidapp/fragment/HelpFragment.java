package com.example.vidapp.vidapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vidapp.vidapp.R;


public class HelpFragment extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    ImageView homeButton;

    public interface CommunicationChannel
    {
        public void setCommunication(int id);
    }

    CommunicationChannel mCommChListner = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);

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
