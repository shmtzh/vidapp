package com.example.vidapp.vidapp.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.adapter.ChoosingClipAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ChooseClipFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private OnFragmentInteractionListener mListener;

    public ChooseClipFragment() {
    }

    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_clip, container, false);
        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new ChoosingClipAdapter(getActivity(), searchForVideoFiles()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public ArrayList<File> searchForVideoFiles() {
        final String dirPath = Environment.getRootDirectory().getParent() + "sdcard";
        Log.d(TAG, Environment.getExternalStorageState());
        Log.d(TAG, dirPath);
        File dir = new File(dirPath);
        boolean states = dir.canRead();
        boolean is = dir.isDirectory();
        boolean es = dir.exists();
        ArrayList<File> files = getListFiles(new File(dirPath));
        Log.d(TAG, String.valueOf(files.size()));

        return files;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
