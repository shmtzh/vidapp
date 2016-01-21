package com.example.vidapp.vidapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.fragment.ChooseClipFragment;
import com.example.vidapp.vidapp.fragment.HelpFragment;
import com.example.vidapp.vidapp.fragment.MakingVideoFragment;
import com.example.vidapp.vidapp.fragment.MyLibFragment;
import com.example.vidapp.vidapp.fragment.OrderChoosingFragment;
import com.example.vidapp.vidapp.fragment.ReorderClipsFragment;
import com.example.vidapp.vidapp.fragment.SplashFragment;
import com.example.vidapp.vidapp.fragment.StartFragment;
import com.example.vidapp.vidapp.fragment.StrictOrderFragment;
import com.example.vidapp.vidapp.listener.CommunicationChannel;
import com.example.vidapp.vidapp.model.VideoModel;
import java.io.File;
import java.util.ArrayList;

public class StartActivity extends AppCompatActivity implements CommunicationChannel {

    static ArrayList<File> files;
    private final String TAG = getClass().getSimpleName();
    public static ArrayList<VideoModel> list = new ArrayList<>();
    public static ArrayList<VideoModel> selectedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        new FileScanning().execute();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new SplashFragment())
                .commit();
    }

    @Override
    public void setCommunication(int id) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        switch (id) {
            case 0:
                fragment = new ChooseClipFragment();
                break;
            case 1:
                fragment = new HelpFragment();
                break;
            case 2:
                fragment = new MyLibFragment();
                break;
            case 3:
                fragment = new StartFragment();
                break;
            case 4:
                fragment = new ReorderClipsFragment();
                break;
            case 5:
                fragment = new OrderChoosingFragment();
                break;
            case 6:
                fragment = new StrictOrderFragment(false);
                break;
            case 7:
                fragment = new StrictOrderFragment(true);
                break;
            case 8:
                fragment = new MakingVideoFragment();
                break;
        }

        transaction.replace(R.id.container, fragment).addToBackStack("").commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public static ArrayList<VideoModel> getList() {
        return list;
    }

    public static void setList(ArrayList<VideoModel> list) {
        StartActivity.list = list;
    }

    public static ArrayList<File> getFiles() {
        return files;
    }


    public static ArrayList<VideoModel> getSelectedFiles() {
        return selectedList;
    }


    public static void setSelectedFiles(ArrayList<VideoModel> selectedFiles) {
        StartActivity.selectedList = selectedFiles;
    }

    public static void setFiles(ArrayList<File> files) {
        StartActivity.files = files;
    }


    public ArrayList<File> searchForVideoFiles() {
        String internalStorage = System.getenv("EXTERNAL_STORAGE");
        files = getListFiles(new File(internalStorage));
        return files;
    }

    public ArrayList<VideoModel> convertFileToBitMap(ArrayList<File> files) {
        for (int i = 0; i < files.size(); i++) {

            list.add(i, new VideoModel(ThumbnailUtils.createVideoThumbnail(files.get(i).getAbsolutePath(),
                    MediaStore.Images.Thumbnails.MINI_KIND), files.get(i)));
        }
        return list;
    }


    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if (file.getName().endsWith(".mp4")) {
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }


    private class FileScanning extends AsyncTask<Void, Void, ArrayList<File>> {


        public FileScanning() {
        }

        @Override
        protected void onPostExecute(ArrayList<File> result) {
            super.onPostExecute(result);

            files = result;
            new BitmapScanning().execute();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<File> doInBackground(Void... arg0) {

            return searchForVideoFiles();

        }

    }




    private class BitmapScanning extends AsyncTask<Void, Void, ArrayList<VideoModel>> {

        public BitmapScanning() {
        }

        @Override
        protected void onPostExecute(ArrayList<VideoModel> result) {
            super.onPostExecute(result);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new StartFragment())
                    .commit();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<VideoModel> doInBackground(Void... arg0) {
            return convertFileToBitMap(files);
        }

    }


}

