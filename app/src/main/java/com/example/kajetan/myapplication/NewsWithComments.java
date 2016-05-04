package com.example.kajetan.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsWithComments extends Fragment {
    private TextView tvText, tvAuthor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void onCreateSetView() {
        News news = (News)getArguments().getSerializable("news");
        //Intent intent = getActivity().getIntent();

        tvText = (TextView)getView().findViewById(R.id.recText);
        tvAuthor = (TextView)getView().findViewById(R.id.recAuthor);

        try {
            tvText.setText(news.text);
            tvAuthor.setText(news.author);
        } catch (NullPointerException e) {
            Log.d("LOG", e.toString());
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        onCreateSetView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_one_news, container, false);


        return view;
    }

    public static final NewsWithComments newInstance(News news)
    {
        NewsWithComments f = new NewsWithComments();
        Bundle bdl = new Bundle(1);
        bdl.putSerializable("news", news);
        f.setArguments(bdl);
        return f;
    }

}
