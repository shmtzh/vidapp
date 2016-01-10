package com.example.vidapp.vidapp.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.vidapp.vidapp.R;

import java.io.File;
import java.util.ArrayList;


public class StrictOrderFragment extends Fragment {
    ArrayList<File> files;
    boolean isRandom;

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


        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
