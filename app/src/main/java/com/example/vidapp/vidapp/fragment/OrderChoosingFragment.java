package com.example.vidapp.vidapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.listener.CommunicationChannel;

import java.io.File;
import java.util.ArrayList;


public class OrderChoosingFragment extends Fragment implements View.OnClickListener {

    CommunicationChannel mCommChListener;
    ImageView viewCustom, viewRandom, viewDate;
    ImageView home;

    public OrderChoosingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_choosing, container, false);
        viewCustom = (ImageView) view.findViewById(R.id.button_custom);
        viewRandom = (ImageView) view.findViewById(R.id.button_random);
        viewDate = (ImageView) view.findViewById(R.id.button_date);
        home = (ImageView) view.findViewById(R.id.home_button);

        home.setOnClickListener(this);
        viewCustom.setOnClickListener(this);
        viewRandom.setOnClickListener(this);
        viewDate.setOnClickListener(this);

        return view;
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(final View v) {


                switch (v.getId()) {
                    case R.id.button_custom:
                        sendMessage(4);
                        break;
                    case R.id.button_date:
                        sendMessage(6);
                        break;
                    case R.id.button_random:
                        sendMessage(7);
                        break;
                    case R.id.home_button:
                        sendMessage(3);
                }


    }

    public void sendMessage(int id) {
        mCommChListener.setCommunication(id);
    }



}
