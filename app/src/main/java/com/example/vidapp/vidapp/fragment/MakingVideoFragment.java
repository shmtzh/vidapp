package com.example.vidapp.vidapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Container;
import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.model.VideoModel;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MakingVideoFragment extends Fragment {
    private final String TAG = getClass().getCanonicalName();
    static ArrayList<String> paths = new ArrayList<>();
    static ArrayList<Movie> files = new ArrayList<>();
    String internalStorage;

    public static ArrayList<VideoModel> list = new ArrayList<>();

    public MakingVideoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_making_video, container, false);

        searchForVideoFiles();
        try {
            combineClips();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }


    public ArrayList<String> searchForVideoFiles() {
        internalStorage = System.getenv("EXTERNAL_STORAGE") + "/VidAppCuts";
        paths = getListFiles(new File(internalStorage));
        Log.d(TAG, String.valueOf(paths.size()));
        return paths;
    }


    private ArrayList<String> getListFiles(File parentDir) {
        ArrayList<String> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if (file.getName().endsWith(".mp4")) {
                    inFiles.add(file.getPath());
                }
            }
        }
        return inFiles;
    }

    protected void combineClips() throws IOException{

        for (int i = 0; i < paths.size(); i++) {
            Movie tm = MovieCreator.build(paths.get(i));
            files.add(tm);
        }

        List<Track> videoTracks = new LinkedList<Track>();
        List<Track> audioTracks = new LinkedList<Track>();

        for (Movie m : files) {
            for (Track t : m.getTracks()) {
                if (t.getHandler().equals("soun")) {
                    audioTracks.add(t);
                }
                if (t.getHandler().equals("vide")) {
                    videoTracks.add(t);
                }
            }
        }

        Movie result = new Movie();


        if (audioTracks.size() > 0) {
            Log.d(TAG, String.valueOf(audioTracks.size()));
            result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
        }
        if (videoTracks.size() > 0) {
            result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
        }

        Container out = new DefaultMp4Builder().build(result);

        FileChannel fc = new RandomAccessFile(String.format(internalStorage + "/output.mp4"), "rw").getChannel();
        out.writeContainer(fc);
        fc.close();







    }
}
