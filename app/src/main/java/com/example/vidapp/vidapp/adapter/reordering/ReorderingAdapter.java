package com.example.vidapp.vidapp.adapter.reordering;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vidapp.vidapp.R;

import java.util.ArrayList;
import java.util.List;

/**
  Created by shmtzh on 1/9/16.
 */
public class ReorderingAdapter extends BaseDynamicGridAdapter {
List<Bitmap> items = new ArrayList<>();

    public ReorderingAdapter(Context context, List<Bitmap> items, int columnCount) {
        super(context, items, columnCount);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheeseViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_reordering, null);
            holder = new CheeseViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CheeseViewHolder) convertView.getTag();
        }
        holder.build(position);
        return convertView;
    }

    private class CheeseViewHolder {
        private ImageView image;

        private CheeseViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.item_img);
        }

        void build(int position) {
//            image.setImageResource(R.drawable.plus);
        image.setImageBitmap(items.get(position));
        }
    }
}
