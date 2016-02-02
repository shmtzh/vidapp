package com.example.vidapp.vidapp.adapter.reordering;

/**
 * Created by shmtzh on 1/9/16.
 */
public interface DynamicGridAdapterInterface {

    void reorderItems(int originalPosition, int newPosition);

    int getColumnCount();

    boolean canReorder(int position);

}
