package com.example.vidapp.vidapp.fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.listener.CommunicationChannel;

import java.io.File;
import java.util.ArrayList;


public class StartFragment extends Fragment implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private final String TAG = getClass().getSimpleName();
    ImageView startImageView, helpImageView, mylibImageView;

    CommunicationChannel mCommChListener = null;

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
                checkPermission();
                Log.d(TAG, "onClick");
                break;

            case R.id.help_image_view:
                sendMessage(1);
                break;

            case R.id.mylib_image_view:
                sendMessage(2);
                break;

        }
    }

    public void checkPermission()
    {

        if (isReadPermissionGranted()) {
            sendMessage(0);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                todo
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {

                if (isReadPermissionGranted()){
                    sendMessage(0);
                }else{
                    Toast.makeText(getActivity(), "deny", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            }
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if(context instanceof CommunicationChannel)
        {
            mCommChListener = (CommunicationChannel) context;
        }
        else
        {
            throw new ClassCastException();
        }

    }

    boolean isReadPermissionGranted() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    public void sendMessage(int id)
    {
        mCommChListener.setCommunication(id);
    }


}


