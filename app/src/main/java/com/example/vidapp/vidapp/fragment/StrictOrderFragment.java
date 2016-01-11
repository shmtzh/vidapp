package com.example.vidapp.vidapp.fragment;

import android.app.Activity;
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
import android.widget.Toast;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.adapter.choosing.MultipleSelectionGridAdapter;
import com.example.vidapp.vidapp.adapter.strict.StrictSelectionGridAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class StrictOrderFragment extends Fragment {
    ArrayList<File> files = new ArrayList<>();
    boolean isRandom;
    StrictSelectionGridAdapter adapter;
    ArrayList<Bitmap> thumbnails = new ArrayList<>();

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
        if (isRandom) randomFiles(); else sortFiles();
        List<Bitmap> list = convertFileToBitMap(files);

        adapter = new StrictSelectionGridAdapter(list, getActivity());
        gridView.setAdapter(adapter);

        return view;
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
}
