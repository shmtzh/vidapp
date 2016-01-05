package com.example.vidapp.vidapp.adapter;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.Set;

/**
  Created by shmtzh on 1/4/16.
 */
public interface MultiChoiceAdapter {
    void setAdapterView(AdapterView<? super BaseAdapter> adapterView);
    void setOnItemClickListener(AdapterView.OnItemClickListener listener);
    void save(Bundle outState);
    void setItemChecked(long position, boolean checked);
    Set<Long> getCheckedItems();
    int getCheckedItemCount();
    boolean isChecked(long position);
    void setItemClickInActionModePolicy(ItemClickInActionModePolicy policy);
    ItemClickInActionModePolicy getItemClickInActionModePolicy();
    boolean isItemCheckable(int position);
    String getActionModeTitle(int count);
}