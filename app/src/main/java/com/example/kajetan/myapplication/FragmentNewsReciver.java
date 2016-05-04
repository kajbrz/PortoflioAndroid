package com.example.kajetan.myapplication;

import java.io.Serializable;

/**
 * Created by Kajetan on 2016-05-04.
 */
public interface FragmentNewsReciver extends Serializable{

    public void send(String from, News news);
}
