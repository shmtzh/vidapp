package com.example.vidapp.vidapp.fragment;

import android.content.Context;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.adapter.finished.FinishedMoviesAdapter;
import com.example.vidapp.vidapp.dialog.VideoShowingDialog;
import com.example.vidapp.vidapp.listener.CommunicationChannel;
import com.example.vidapp.vidapp.model.VideoModel;

import java.io.File;
import java.util.ArrayList;


public class MyLibFragment extends Fragment implements View.OnClickListener {

    FinishedMoviesAdapter adapter;
    ArrayList<File> files = new ArrayList<>();
    ArrayList<VideoModel> list = new ArrayList<>();
    ImageView goHome;
    ListView listView;
    CommunicationChannel mCommChListener;

    public MyLibFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_lib_fragmnet, container, false);

        convertFileToBitMap(searchForVideoFiles());

        goHome = (ImageView) view.findViewById(R.id.gohome);
        goHome.setOnClickListener(this);

        listView = (ListView) view.findViewById(R.id.listview_choosing);
        adapter = new FinishedMoviesAdapter(list, getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoShowingDialog dialog = new VideoShowingDialog(list.get(position).getFile().getPath());
                dialog.show(getFragmentManager(), "VideoDisplayerDialog");
            }
        });

        return view;
    }

    public ArrayList<File> searchForVideoFiles() {
        String internalStorage = System.getenv("EXTERNAL_STORAGE") + "/VidApp";
        files = getListFiles(new File(internalStorage));
        return files;
    }

    public ArrayList<VideoModel> convertFileToBitMap(ArrayList<File> files) {
        if (files == null) return null;
        for (int i = 0; i < files.size(); i++) {
            list.add(i, new VideoModel(ThumbnailUtils.createVideoThumbnail(files.get(i).getAbsolutePath(),
                    MediaStore.Images.Thumbnails.MINI_KIND), files.get(i)));
        }
        return list;
    }


    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        if (files == null) return null;
        for (File file : files) {
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
