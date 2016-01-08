package com.example.vidapp.vidapp.adapter;

import android.app.Activity;
import android.content.Context;
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
    Activity activity;
    ImageView image;

    public MultipleSelectionGridAdapter(Bundle savedInstanceState, List<Bitmap> list, Activity activity) {
        super(savedInstanceState, activity);
        this.list = list;
        this.activity = activity;
    }


    @Override
    protected View getViewImpl(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int layout = R.layout.item_checking;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
        }

        image = (ImageView) activity.findViewById(R.id.done_button);
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
        image.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        super.onDestroyActionMode(mode);
        image.setVisibility(View.GONE);

    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return true;
    }
}
