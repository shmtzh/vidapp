package com.example.vidapp.vidapp.adapter.strict;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.vidapp.vidapp.R;

import java.util.ArrayList;
import java.util.List;

/**
  Created by shmtzh on 1/10/16.
 */
public class StrictSelectionGridAdapter extends BaseAdapter {
    List<Bitmap> list = new ArrayList<>();
    Context context;

    public StrictSelectionGridAdapter(List<Bitmap> list, FragmentActivity activity) {
        this.list = list;
        this.context = activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SteamyViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_reordering, null);
            holder = new SteamyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (SteamyViewHolder) convertView.getTag();
        }
        holder.build(position);
        return convertView;
    }


    private class SteamyViewHolder {
        private ImageView image;

        private SteamyViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.item_img);
        }

        void build(int position) {
            image.setImageBitmap(list.get(position));
        }
    }
}
