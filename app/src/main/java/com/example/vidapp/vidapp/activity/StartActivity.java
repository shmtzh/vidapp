package com.example.vidapp.vidapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.fragment.ChooseClipFragment;
import com.example.vidapp.vidapp.fragment.HelpFragment;
import com.example.vidapp.vidapp.fragment.MyLibFragment;
import com.example.vidapp.vidapp.fragment.OrderChoosingFragment;
import com.example.vidapp.vidapp.fragment.ReorderClipsFragment;
import com.example.vidapp.vidapp.fragment.StartFragment;
import com.example.vidapp.vidapp.listener.CommunicationChannel;

import java.io.File;
import java.util.ArrayList;

public class StartActivity extends AppCompatActivity implements CommunicationChannel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new StartFragment())
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setCommunication(int id, ArrayList<File> files) {


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
                fragment = new ReorderClipsFragment(files);
                break;
            case 5:
                fragment = new OrderChoosingFragment();
                break;
        }

        transaction.replace(R.id.container, fragment).addToBackStack("").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();


    }
}

