package com.example.vidapp.vidapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

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
    View view;


    public ChoosingClipAdapter(Context c, ArrayList<Bitmap> list) {
        mContext = c;
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tables, null);
        }

        view = convertView;
        ImageView imageView = (ImageView) convertView.findViewById(R.id.video_thumbnail);
        imageView.setImageBitmap(list.get(position));
        final ImageView selection = (ImageView) convertView.findViewById(R.id.selection);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
                v.setTag(1);
            }
        });

        return convertView;
    }


//    @Override
//    public void onClick(View v) {
//        Toast.makeText(mContext, "^___^", Toast.LENGTH_LONG).show();
//        ImageView imageView = (ImageView) view.findViewById(R.id.selection);
//        imageView.setVisibility(View.VISIBLE);
//    }
}
