package com.example.vidapp.vidapp.model;

import android.graphics.Bitmap;

import java.io.File;

/**
  Created by shmtzh on 1/14/16.
 */
public class VideoModel {
    private Bitmap bitmap;
    private File file;

    public VideoModel(Bitmap bitmap, File file) {
        this.bitmap = bitmap;
        this.file = file;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
