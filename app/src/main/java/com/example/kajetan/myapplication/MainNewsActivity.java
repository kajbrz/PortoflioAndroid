package com.example.kajetan.myapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Created by Kajetan on 2016-05-04.
 */
public class MainNewsActivity extends FragmentActivity implements FragmentNewsReciver {
    private Toolbar myToolbar;
    //private android.support.v4.app.Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            setContentView(R.layout.activity_news_main);
        } catch (Exception e) {
            Log.d("LOG", e.toString());
        }

        onCreateConfigureTooblar();
        try {
            onCreateSetFragments();
        } catch (Exception e) {
            Log.d("LOG", e.toString());
        }

    }

    private void onCreateSetFragments() {
        try {
            FragmentManager fg = getSupportFragmentManager();
            FragmentTransaction ft = fg.beginTransaction();
            NewsActivity frag = NewsActivity.newInstance(this);
            ft = ft.add(R.id.firstFragment, frag, "first");
            ft.commit();
        } catch (Exception e) {
            Log.d("LOG", e.toString());
        }
    }

    private void onCreateConfigureTooblar() {
        myToolbar = (Toolbar)findViewById(R.id.myToolbar);

        myToolbar.setTitle(R.string.test);
    }

    @Override
    public void send(String from, News news) {
        Log.d("LOG", news.toString());
        try {
            FragmentManager fg = getSupportFragmentManager();
            FragmentTransaction ft = fg.beginTransaction();
            NewsWithComments frag = NewsWithComments.newInstance(news);
            ft.addToBackStack("first");
            ft = ft.replace(R.id.firstFragment, frag, "second");
            ft.commit();
        } catch (Exception e) {
            Log.d("LOG", e.toString());
        }
    }
}
