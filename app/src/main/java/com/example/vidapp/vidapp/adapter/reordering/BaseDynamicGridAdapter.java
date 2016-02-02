package com.example.vidapp.vidapp.adapter.reordering;

import android.content.Context;
import android.util.Log;

import com.example.vidapp.vidapp.model.VideoModel;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by shmtzh on 1/9/16.
 */
public abstract class BaseDynamicGridAdapter extends AbstractDynamicGridAdapter {

    private Context mContext;

    private ArrayList<VideoModel> mItems = new ArrayList<VideoModel>();
    private int mColumnCount;

    protected BaseDynamicGridAdapter(Context context, int columnCount) {
        this.mContext = context;
        this.mColumnCount = columnCount;
    }

    public BaseDynamicGridAdapter(Context context, ArrayList<VideoModel> items, int columnCount) {
        mContext = context;
        mColumnCount = columnCount;
        init(items);
    }

    private void init(ArrayList<VideoModel> items) {
        addAllStableId(items);
        this.mItems.addAll(items);
    }


    public void set(ArrayList<VideoModel> items) {
        clear();
        init(items);
        notifyDataSetChanged();
    }

    public void clear() {
        clearStableIdMap();
        mItems.clear();
        notifyDataSetChanged();
    }

    public void add(VideoModel item) {
        addStableId(item);
        mItems.add(item);
        notifyDataSetChanged();
    }

    public void add(int position, VideoModel item) {
        addStableId(item);
        mItems.add(position, item);
        notifyDataSetChanged();
    }

    public void add(ArrayList<VideoModel> items) {
        addAllStableId(items);
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }


    public void remove(Object item) {
        mItems.remove(item);
        removeStableID(item);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getColumnCount() {
        return mColumnCount;
    }

    public void setColumnCount(int columnCount) {
        this.mColumnCount = columnCount;
        notifyDataSetChanged();
    }

    @Override
    public void reorderItems(int originalPosition, int newPosition) {
        if (newPosition < getCount()) {
            DynamicGridUtils.swap(mItems, originalPosition, newPosition);
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean canReorder(int position) {
        return true;
    }

    public ArrayList<VideoModel> getItems() {
        return mItems;
    }

    protected Context getContext() {
        return mContext;
    }
}
