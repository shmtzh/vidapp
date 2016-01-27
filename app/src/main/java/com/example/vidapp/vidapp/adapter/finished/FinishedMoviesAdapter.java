package com.example.vidapp.vidapp.adapter.finished;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shmtzh on 1/25/16.
 */
public class FinishedMoviesAdapter extends BaseAdapter {
    List<VideoModel> list = new ArrayList<>();
    Context context;

    public FinishedMoviesAdapter(List<VideoModel> list, FragmentActivity activity) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mylib, null);
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
        private TextView text;

        private SteamyViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.mylib_thumbnail);
            text = (TextView) view.findViewById(R.id.mylib_name);
        }


        void build(int position) {
            image.setImageBitmap(list.get(position).getBitmap());
            text.setText(list.get(position).getFile().getName());
        }

    }
}
