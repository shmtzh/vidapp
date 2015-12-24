package com.example.vidapp.vidapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.vidapp.vidapp.R;
import com.example.vidapp.vidapp.fragment.ChooseClipFragment;
import com.example.vidapp.vidapp.fragment.HelpFragment;
import com.example.vidapp.vidapp.fragment.MyLibFragment;
import com.example.vidapp.vidapp.fragment.StartFragment;
import com.example.vidapp.vidapp.listener.CommunicationChannel;

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        }

        transaction.replace(R.id.container, fragment).addToBackStack("").commit();

    }
}

