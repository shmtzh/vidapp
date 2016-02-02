package com.example.vidapp.vidapp.adapter.reordering;

import android.graphics.Bitmap;
import android.view.View;

import com.example.vidapp.vidapp.model.VideoModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shmtzh on 1/9/16.
 */
public class DynamicGridUtils {

    public static void reorder(ArrayList<VideoModel> list, int indexFrom, int indexTwo) {
        VideoModel obj = list.remove(indexFrom);
        list.add(indexTwo, obj);
    }

    public static void swap(ArrayList<VideoModel> list, int firstIndex, int secondIndex) {
        Bitmap firstBitmap = list.get(firstIndex).getBitmap();
        Bitmap secondBitmap = list.get(secondIndex).getBitmap();
        File firstFile = list.get(firstIndex).getFile();
        File secondFile = list.get(secondIndex).getFile();
        list.get(firstIndex).setBitmap(secondBitmap);
        list.get(secondIndex).setBitmap(firstBitmap);
        list.get(firstIndex).setFile(secondFile);
        list.get(secondIndex).setFile(firstFile);
    }

    public static float getViewX(View view) {
        return Math.abs((view.getRight() - view.getLeft()) / 2);
    }

    public static float getViewY(View view) {
        return Math.abs((view.getBottom() - view.getTop()) / 2);
    }

}
