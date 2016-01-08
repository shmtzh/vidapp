package com.example.vidapp.vidapp.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.adapter.choosing.MultipleSelectionGridAdapter;
import com.example.vidapp.vidapp.listener.CommunicationChannel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ChooseClipFragment extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();

    CommunicationChannel mCommChListener;
    private OnFragmentInteractionListener mListener;
    ArrayList<File> files;
    ArrayList<File> selected_files = new ArrayList<>();
    ArrayList<Bitmap> thumbnails = new ArrayList<>();
    ImageView cancel;
    ImageView done;
    MultipleSelectionGridAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_choose_clip, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridview_choosing);
        List<Bitmap> list = convertFileToBitMap(searchForVideoFiles());
        cancel = (ImageView) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        done = (ImageView) view.findViewById(R.id.done_button);
        done.setOnClickListener(this);

        adapter = new MultipleSelectionGridAdapter(savedInstanceState, list, getActivity());

        adapter.setAdapterView(gridView);
        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), "Item click: " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public ArrayList<File> searchForVideoFiles() {
        final String dirPath = Environment.getRootDirectory().getParent() + "sdcard/DCIM";
        files = getListFiles(new File(dirPath));
        Log.d(TAG, String.valueOf(files.size()));
        return files;
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
            case R.id.cancel:
                sendMessage(3, null);
                break;
            case R.id.done_button:
                Log.d(TAG, String.valueOf(adapter.getCheckedItemCount()) + "is the number of selected items");
                Log.d(TAG, String.valueOf(adapter.getCheckedItems()));
                Set<Long> items = adapter.getCheckedItems();
                for (int i = 0; i < files.size(); i++) {
                    if (items.contains(Long.valueOf(i))) selected_files.add(files.get(i));
                }
                Log.d(TAG, String.valueOf(selected_files.size()) + " items in the selected array");
                sendMessage(4, selected_files);
                break;
        }
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
        mListener = null;
    }

    public void sendMessage(int id, ArrayList<File> files) {
        mCommChListener.setCommunication(id, files);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
