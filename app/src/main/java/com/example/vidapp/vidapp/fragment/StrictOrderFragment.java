package com.example.vidapp.vidapp.fragment;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.activity.StartActivity;
import com.example.vidapp.vidapp.adapter.strict.StrictSelectionGridAdapter;
import com.example.vidapp.vidapp.dialog.VideoDisplayerDialog;
import com.example.vidapp.vidapp.dialog.VideoShowingDialog;
import com.example.vidapp.vidapp.listener.CommunicationChannel;
import com.example.vidapp.vidapp.model.VideoModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class StrictOrderFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    boolean isRandom;
    CommunicationChannel mCommChListener;
    StrictSelectionGridAdapter adapter;
    ArrayList<VideoModel> list = new ArrayList<>();
    ImageView home, add, plus, makeMovie;
    GridView gridView;
    static ArrayList<File> files;

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
        } else {
            Collections.sort(list, new Comparator<VideoModel>() {
                @Override
                public int compare(VideoModel lhs, VideoModel rhs) {
                    if (((File) lhs.getFile()).lastModified() > ((File) rhs.getFile()).lastModified()) {
                        return -1;
                    } else if
                            (((File) lhs.getFile()).lastModified() > ((File) rhs.getFile()).lastModified()) {
                        return +1;
                    } else return 0;
                }
            });
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
                if (!checkDirectory()) StartActivity.isVertical = 0;
                VideoDisplayerDialog dialog = new VideoDisplayerDialog(list.get(position).getFile().getPath(), getActivity());
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
