package com.example.vidapp.vidapp.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.adapter.reordering.DynamicGridView;
import com.example.vidapp.vidapp.adapter.reordering.ReorderingAdapter;
import com.example.vidapp.vidapp.listener.CommunicationChannel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ReorderClipsFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();

    CommunicationChannel mCommChListener;
    ImageView home, add, plus, makeMovie;
    ArrayList<Bitmap> thumbnails = new ArrayList<>();
    ArrayList<File> files = new ArrayList<>();
    ReorderingAdapter adapter;
    DynamicGridView gridView;

    public ReorderClipsFragment() {
    }

    public ReorderClipsFragment(ArrayList<File> files) {
        this.files = files;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reorder_clips, container, false);

       gridView = (DynamicGridView) view.findViewById(R.id.dynamicgridview);
        List<Bitmap> list = convertFileToBitMap(files);

        adapter = new ReorderingAdapter(getActivity(), list, list.size());
        gridView.setAdapter(adapter);
        gridView.startEditMode();

        home = (ImageView) view.findViewById(R.id.home_button);
        add = (ImageView) view.findViewById(R.id.add_image);
        plus = (ImageView) view.findViewById(R.id.add_label);
        makeMovie = (ImageView) view.findViewById(R.id.make_movie);

        home.setOnClickListener(this);
        add.setOnClickListener(this);
        plus.setOnClickListener(this);
        makeMovie.setOnClickListener(this);

        return view;
    }

    public ArrayList<Bitmap> convertFileToBitMap(ArrayList<File> files) {
        for (int i = 0; i < files.size(); i++) {
            thumbnails.add(ThumbnailUtils.createVideoThumbnail(files.get(i).getAbsolutePath(),
                    MediaStore.Images.Thumbnails.MINI_KIND));
        }
        return thumbnails;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_button:
                sendMessage(3, null);
                break;
            case R.id.add_image:
                sendMessage(0, null);
                break;
            case R.id.add_label:
                sendMessage(0, null);
                break;
            case R.id.make_movie:
                sendMessage(5, null);
                gridView.stopEditMode();
                break;
        }
    }

    public void sendMessage(int id, ArrayList<File> files) {
        mCommChListener.setCommunication(id, files);
    }
}
