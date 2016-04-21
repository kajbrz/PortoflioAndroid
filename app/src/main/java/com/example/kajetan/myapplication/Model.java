package com.example.kajetan.myapplication;

/**
 * Created by Kajetan on 2016-04-21.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public interface Model {
    News[] readNews();
    void sendNews();
}
