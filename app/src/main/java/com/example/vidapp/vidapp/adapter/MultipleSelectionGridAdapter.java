package com.example.vidapp.vidapp.adapter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vidapp.vidapp.R;

import java.util.List;

/**
  Created by shmtzh on 1/4/16.
 */
public class MultipleSelectionGridAdapter extends MultiChoiceBaseAdapter {
    private List<Bitmap> list;

    public MultipleSelectionGridAdapter(Bundle savedInstanceState, List<Bitmap> list) {
        super(savedInstanceState);
        this.list = list;
    }


    @Override
    protected View getViewImpl(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int layout = R.layout.item_checking;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
        }
//        ImageView imageView = (ImageView) convertView;
        ImageView imageView = (ImageView) convertView.findViewById(R.id.test);
        imageView.setImageBitmap(list.get(position));
        return imageView;

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
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//        if (item.getItemId() == R.id.) {
            Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();
            return true;
//        }
//        return false;
    }
}
