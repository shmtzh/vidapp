package com.example.vidapp.vidapp.adapter.choosing;

import android.app.Activity;
import android.view.ActionMode;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.vidapp.vidapp.R;

import java.lang.reflect.Method;


public class MultiChoiceAdapterHelper extends MultiChoiceAdapterHelperBase {

    Activity activity;
    ImageView imageView;

    protected MultiChoiceAdapterHelper(BaseAdapter owner, Activity activity) {
        super(owner);
        this.activity = activity;
    }

    @Override
    protected void startActionMode() {
    }

    @Override
    protected void finishActionMode() {
       imageView.setVisibility(View.GONE);
    }

    @Override
    protected void setActionModeTitle(String title) {
        imageView = (ImageView) activity.findViewById(R.id.done_button);
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    protected boolean isActionModeStarted() {
        return false;
    }

    @Override
    protected void clearActionMode() {
    }
}
