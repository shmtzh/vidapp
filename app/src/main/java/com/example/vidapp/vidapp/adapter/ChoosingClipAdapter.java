package com.example.vidapp.vidapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.vidapp.vidapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
  Created by shmtzh on 12/27/15.
 */
public class ChoosingClipAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Bitmap> list;

    public ChoosingClipAdapter(Context c, ArrayList<Bitmap> list) {
        mContext = c;
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tables, null);
        }


        ImageView imageView = (ImageView) convertView.findViewById(R.id.ColPhoto);
        imageView.setImageBitmap(list.get(position

        ));


        return imageView;
    }


}
