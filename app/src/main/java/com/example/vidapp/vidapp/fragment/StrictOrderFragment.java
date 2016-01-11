package com.example.vidapp.vidapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.adapter.choosing.MultipleSelectionGridAdapter;
import com.example.vidapp.vidapp.adapter.reordering.ReorderingAdapter;
import com.example.vidapp.vidapp.adapter.strict.StrictSelectionGridAdapter;
import com.example.vidapp.vidapp.listener.CommunicationChannel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class StrictOrderFragment extends Fragment implements View.OnClickListener {
    ArrayList<File> files = new ArrayList<>();
    boolean isRandom;
    CommunicationChannel mCommChListener;
    StrictSelectionGridAdapter adapter;
    ArrayList<Bitmap> thumbnails = new ArrayList<>();
    ImageView home, add, plus, makeMovie;

    public StrictOrderFragment() {
    }

    public StrictOrderFragment(ArrayList<File> files, boolean isRandom) {
        this.files = files;
        this.isRandom = isRandom;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strict_order, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.strict_grid_view);
        if (isRandom) randomFiles();
        else sortFiles();
        List<Bitmap> list = convertFileToBitMap(files);

        home = (ImageView) view.findViewById(R.id.home_button);
        add = (ImageView) view.findViewById(R.id.add_image);
        plus = (ImageView) view.findViewById(R.id.add_label);
        makeMovie = (ImageView) view.findViewById(R.id.make_movie);

        home.setOnClickListener(this);
        add.setOnClickListener(this);
        plus.setOnClickListener(this);
        makeMovie.setOnClickListener(this);


        adapter = new StrictSelectionGridAdapter(list, getActivity());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoDisplayerDialog dialog = new VideoDisplayerDialog(files.get(position).getPath());
                dialog.show(getFragmentManager(), "VideoDisplayerDialog");
            }
        });
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

    private void sortFiles() {
        Collections.sort(files);
    }

    public void randomFiles() {
        long seed = System.nanoTime();
        Collections.shuffle(files, new Random(seed));
        Collections.shuffle(files, new Random(seed));
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public ArrayList<Bitmap> convertFileToBitMap(ArrayList<File> files) {
        for (int i = 0; i < files.size(); i++) {
            thumbnails.add(ThumbnailUtils.createVideoThumbnail(files.get(i).getAbsolutePath(),
                    MediaStore.Images.Thumbnails.MINI_KIND));
        }
        return thumbnails;
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
                break;
        }
    }

    public void sendMessage(int id, ArrayList<File> files) {
        mCommChListener.setCommunication(id, files);
    }
}
