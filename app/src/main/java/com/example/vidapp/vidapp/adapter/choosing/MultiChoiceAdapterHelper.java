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
        try {
            Activity activity = (Activity) adapterView.getContext();
            Method method = activity.getClass().getMethod("startActionMode", ActionMode.Callback.class);
//            actionMode = (ActionMode) method.invoke(activity, owner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void finishActionMode() {
//        if (actionMode != null) {
//            actionMode.finish();
//        }
        imageView.setVisibility(View.GONE);


    }

    @Override
    protected void setActionModeTitle(String title) {
//        actionMode.setTitle(title);
        imageView = (ImageView) activity.findViewById(R.id.done_button);
        imageView.setVisibility(View.VISIBLE);

    }

    @Override
    protected boolean isActionModeStarted() {
//        return actionMode != null;
        imageView.setVisibility(View.GONE);

        return false;
    }

    @Override
    protected void clearActionMode() {
//        actionMode = null;
    }
}
