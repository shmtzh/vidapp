package com.example.vidapp.vidapp.adapter;

import android.app.Activity;
import android.view.ActionMode;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.vidapp.vidapp.R;

import java.lang.reflect.Method;

/**
  Created by shmtzh on 1/4/16.
 */
public class MultiChoiceAdapterHelper extends MultiChoiceAdapterHelperBase {

//    private ActionMode actionMode;
    ImageView imageView;

    protected MultiChoiceAdapterHelper(BaseAdapter owner) {
        super(owner);
    }

    @Override
    protected void startActionMode() {
        try {
            Activity activity = (Activity) adapterView.getContext();
            imageView = (ImageView) activity.findViewById(R.id.choose_clips);
            imageView.setVisibility(View.GONE);

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
    }

    @Override
    protected void setActionModeTitle(String title) {
//        actionMode.setTitle(title);
    }

    @Override
    protected boolean isActionModeStarted() {
//        return actionMode != null;
    return false;
    }

    @Override
    protected void clearActionMode() {
//        actionMode = null;
    }
}
