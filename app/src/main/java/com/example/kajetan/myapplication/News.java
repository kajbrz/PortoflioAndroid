package com.example.kajetan.myapplication;

import java.io.Serializable;

/**
 * Created by Kajetan on 2016-04-21.
 */
public class News implements Serializable {
    public String date;
    public String text;
    public String author;
    int imageType;

    public News(String date, String text, String author){
        this.date = date;
        this.text = text;
        this.author = author;
        imageType = 0;
    }

    @Override
    public String toString()
    {
        return "[" + date + "||" + text + "||" + author + "]";
    }
}
