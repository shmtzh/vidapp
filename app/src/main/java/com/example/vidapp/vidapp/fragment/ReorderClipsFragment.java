package com.example.vidapp.vidapp.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
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
import android.widget.Toast;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.activity.StartActivity;
import com.example.vidapp.vidapp.adapter.reordering.DynamicGridView;
import com.example.vidapp.vidapp.adapter.reordering.ReorderingAdapter;
import com.example.vidapp.vidapp.dialog.VideoDisplayerDialog;
import com.example.vidapp.vidapp.dialog.VideoShowingDialog;
import com.example.vidapp.vidapp.listener.CommunicationChannel;
import com.example.vidapp.vidapp.model.VideoModel;

import java.io.File;
import java.util.ArrayList;


public class ReorderClipsFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    CommunicationChannel mCommChListener;
    ImageView home, add, plus, makeMovie, done, start;
    ArrayList<VideoModel> list = new ArrayList<>();
    ReorderingAdapter adapter;
    DynamicGridView gridView;
    static ArrayList<File> files;

    public ReorderClipsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reorder_clips, container, false);
        list = StartActivity.getSelectedFiles();
        gridView = (DynamicGridView) view.findViewById(R.id.dynamicgridview);
        adapter = new ReorderingAdapter(getActivity(), list, list.size());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!checkDirectory()) StartActivity.isVertical = 0;
                VideoDisplayerDialog dialog = new VideoDisplayerDialog(list.get(position).getFile().getPath(), getActivity());
                WindowManager.LayoutParams a = dialog.getActivity().getWindow().getAttributes();
                a.dimAmount = 0;
                dialog.getActivity().getWindow().setAttributes(a);
                dialog.show(getFragmentManager(), "VideoDisplayerDialog");
            }
        });
        home = (ImageView) view.findViewById(R.id.home_button);
        add = (ImageView) view.findViewById(R.id.add_image);
        plus = (ImageView) view.findViewById(R.id.add_label);
        makeMovie = (ImageView) view.findViewById(R.id.make_movie);
        done = (ImageView) view.findViewById(R.id.done_button);
        start = (ImageView) view.findViewById(R.id.start_button);
        home.setOnClickListener(this);
        add.setOnClickListener(this);
        plus.setOnClickListener(this);
        makeMovie.setOnClickListener(this);
        start.setOnClickListener(this);
        done.setOnClickListener(this);
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


    private boolean checkDirectory() {
        searchForVideoFiles();
        return files != null;
    }

    public ArrayList<File> searchForVideoFiles() {
        String internalStorage = System.getenv("EXTERNAL_STORAGE") + "/VidAppCuts/";
        files = getListFiles(new File(internalStorage));
        return files;
    }

    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] inDirectory = parentDir.listFiles();
        try {
            if (inDirectory.length == 0) {
                Log.d(TAG, "null");
                return null;
            } else {
                Log.d(TAG, "not null");
                for (File file : inDirectory) {
                    if (file.isDirectory()) {
                        inFiles.addAll(getListFiles(file));
                    } else {
                        if (file.getName().endsWith(".mp4")) {
                            inFiles.add(file);
                        }
                    }
                }
                return inFiles;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_button:
                gridView.stopEditMode();
                start.setVisibility(View.VISIBLE);
                done.setVisibility(View.GONE);
                break;
            case R.id.start_button:
                gridView.startEditMode();
                start.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                break;
            case R.id.home_button:
                sendMessage(3);
                break;
            case R.id.add_image:
                sendMessage(0);
                break;
            case R.id.add_label:
                sendMessage(0);
                break;
            case R.id.make_movie:
                if (checkDirectory()) sendMessage(8);
                else
                    Toast.makeText(getActivity(), "Edit any video before making movie", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void sendMessage(int id) {
        mCommChListener.setCommunication(id);
    }
}
