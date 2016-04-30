package com.example.kajetan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NewsWithComments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_with_comments);


        onCreateSetView();
    }

    private void onCreateSetView() {
        Intent intent = getIntent();
        ((TextView)findViewById(R.id.tvText)).setText(intent.getStringExtra("text"));
        ((TextView)findViewById(R.id.tvAuthor)).setText(intent.getStringExtra("author"));
    }
}
