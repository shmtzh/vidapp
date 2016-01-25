package com.example.vidapp.vidapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.activity.StartActivity;
import com.example.vidapp.vidapp.adapter.strict.StrictSelectionGridAdapter;
import com.example.vidapp.vidapp.dialog.VideoDisplayerDialog;
import com.example.vidapp.vidapp.listener.CommunicationChannel;
import com.example.vidapp.vidapp.model.VideoModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class StrictOrderFragment extends Fragment implements View.OnClickListener {
    boolean isRandom;
    CommunicationChannel mCommChListener;
    StrictSelectionGridAdapter adapter;
    ArrayList<VideoModel> list = new ArrayList<>();
    ImageView home, add, plus, makeMovie;
    GridView gridView;

    public StrictOrderFragment() {

    }

    public StrictOrderFragment(boolean isRandom) {
        this.isRandom = isRandom;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strict_order, container, false);

        list = StartActivity.selectedList;
        gridView = (GridView) view.findViewById(R.id.strict_grid_view);
        if (isRandom) {
            long seed = System.nanoTime();
            Collections.shuffle(list, new Random(seed));
            Collections.shuffle(list, new Random(seed));
        }

        home = (ImageView) view.findViewById(R.id.home_button);
        add = (ImageView) view.findViewById(R.id.add_image);
        plus = (ImageView) view.findViewById(R.id.add_label);
        makeMovie = (ImageView) view.findViewById(R.id.make_movie);

        add.setOnClickListener(this);
        plus.setOnClickListener(this);
        makeMovie.setOnClickListener(this);
        home.setOnClickListener(this);

        adapter = new StrictSelectionGridAdapter(list, getActivity());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoDisplayerDialog dialog = new VideoDisplayerDialog(list.get(position).getFile().getPath());
                dialog.show(getFragmentManager(), "VideoDisplayerDialog");
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
//        gridView.setAdapter(null);

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
                sendMessage(3);
                break;
            case R.id.add_image:
                sendMessage(0);
                break;
            case R.id.add_label:
                sendMessage(0);
                break;
            case R.id.make_movie:
                sendMessage(8);
                break;
        }
    }

    public void sendMessage(int id) {
        mCommChListener.setCommunication(id);
    }
}
