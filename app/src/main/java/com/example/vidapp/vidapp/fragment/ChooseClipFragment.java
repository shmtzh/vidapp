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
import com.example.vidapp.vidapp.activity.StartActivity;
import com.example.vidapp.vidapp.adapter.choosing.MultipleSelectionGridAdapter;
import com.example.vidapp.vidapp.listener.CommunicationChannel;
import com.example.vidapp.vidapp.model.VideoModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ChooseClipFragment extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();

    CommunicationChannel mCommChListener;
    //    ArrayList<File> files;
    ArrayList<VideoModel> list = new ArrayList<>();
    //    ArrayList<Bitmap> thumbnails = new ArrayList<>();
    ImageView cancel;
    ImageView done;
    MultipleSelectionGridAdapter adapter;

    public ChooseClipFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_clip, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridview_choosing);

        cancel = (ImageView) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        done = (ImageView) view.findViewById(R.id.done_button);
        done.setOnClickListener(this);
        list = StartActivity.getList();
        adapter = new MultipleSelectionGridAdapter(savedInstanceState, list, getActivity());

        adapter.setAdapterView(gridView);
        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), "Item click: " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });

        StartActivity.selectedList.clear();

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                sendMessage(3);
                break;
            case R.id.done_button:
                Log.d(TAG, String.valueOf(adapter.getCheckedItemCount()) + "is the number of selected items");
                Log.d(TAG, String.valueOf(adapter.getCheckedItems()));
                Set<Long> items = adapter.getCheckedItems();
                for (int i = 0; i < list.size(); i++) {
                    if (items.contains(Long.valueOf(i))) StartActivity.selectedList.add(list.get(i));
                }
                Log.d(TAG, String.valueOf(StartActivity.selectedList.size()) + " items in the selected array");
                sendMessage(5);
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


    public void sendMessage(int id) {
        mCommChListener.setCommunication(id);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
