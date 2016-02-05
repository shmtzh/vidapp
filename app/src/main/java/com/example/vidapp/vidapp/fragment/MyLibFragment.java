package com.example.vidapp.vidapp.fragment;

import android.content.Context;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.activity.StartActivity;
import com.example.vidapp.vidapp.adapter.finished.FinishedMoviesAdapter;
import com.example.vidapp.vidapp.dialog.VideoShowingDialog;
import com.example.vidapp.vidapp.listener.CommunicationChannel;
import com.example.vidapp.vidapp.model.VideoModel;

import java.io.File;
import java.util.ArrayList;


public class MyLibFragment extends Fragment implements View.OnClickListener {

    private String TAG = getClass().getSimpleName();
    public static FinishedMoviesAdapter adapter;
    ArrayList<File> files = new ArrayList<>();
    ArrayList<VideoModel> list = new ArrayList<>();
    ImageView goHome;
    public static ListView listView;
    CommunicationChannel mCommChListener;

    public MyLibFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_lib_fragmnet, container, false);
        goHome = (ImageView) view.findViewById(R.id.gohome);
        goHome.setOnClickListener(this);
        listView = (ListView) view.findViewById(R.id.listview_choosing);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        list = StartActivity.getLibModels();
        adapter = new FinishedMoviesAdapter(list, getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                VideoShowingDialog dialog = new VideoShowingDialog(list.get(position).getFile().getPath());
//                dialog.show(getFragmentManager(), "VideoDisplayerDialog");

                StartActivity.setPath(list.get(position).getFile().getPath());
                sendMessage(9);
            }
        });
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

    public void sendMessage(int id) {
        mCommChListener.setCommunication(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gohome:
                sendMessage(3);
                break;
        }
    }


}
