package com.example.vidapp.vidapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vidapp.vidapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shmtzh on 12/27/15.
 */
public class ChoosingClipAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Bitmap> list;
    View view;


    public ChoosingClipAdapter(Context c, ArrayList<Bitmap> list) {
        mContext = c;
        this.list = list;
    }

    static class ViewHolder {
        ImageView bitmap;
        ImageView selection;
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

//        GridObject object = myObjects.get(position);
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tables, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.bitmap = (ImageView) convertView.findViewById(R.id.video_thumbnail);
        holder.bitmap.setImageBitmap(list.get(position));



        return convertView;
    }

}
