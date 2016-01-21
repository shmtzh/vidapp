package com.example.vidapp.vidapp.model;

/**
 * Created by shmtzh on 1/19/16.
 */
public class CutModel {
    private String start;
    private String end;
    private String path;

    public CutModel(String start, String end, String path) {
        this.start = start;
        this.end = end;
        this.path = path;
    }

    public CutModel() {
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
