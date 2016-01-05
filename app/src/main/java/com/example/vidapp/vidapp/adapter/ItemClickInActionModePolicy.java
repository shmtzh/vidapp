package com.example.vidapp.vidapp.adapter;

/**
 * Created by shmtzh on 1/4/16.
 */
public enum ItemClickInActionModePolicy {
    /**
     * Changes the selection state of the clicked item, just as if it had been
     * long clicked. This is what the native MULTICHOICE_MODAL mode of List
     * does, and what almost every app does
     */
    SELECT,
    /**
     * Opens the clicked item, just as if it had been clicked outside of the
     * action mode. This is what the Gmail app does
     */
    OPEN
}