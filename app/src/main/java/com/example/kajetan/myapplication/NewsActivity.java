package com.example.kajetan.myapplication;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import android.support.v7.widget.RecyclerView;

public class NewsActivity extends AppCompatActivity {
    private static final Boolean DEBUG_TAG = true;
    private WebService wsNews;
    private WebView wvNews;
    private List<News> records;
    private RecyclerView rv;
    private ListNewsAdapter rvAdapter;

    TextView tvMy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_news);

        onCreateConfigureComponents();
        onCreateRecyclerVier();
    }

    private void onCreateRecyclerVier() {
        rv = (RecyclerView)(findViewById(R.id.recyclerView));

        rvAdapter = new ListNewsAdapter(NewsActivity.this, records);
        rv.setAdapter(rvAdapter);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL);
        rv.setLayoutManager(gridLayoutManager);
//
//        rv.setOnHoverListener(new View.OnHoverListener() {
//            @Override
//            public boolean onHover(View v, MotionEvent event) {
//
//            }
//        });
//                (AnimationUtils.loadAnimation(context, R.anim.showing_news);
    }


    protected void onResume(){
        super.onResume();

        ((WSNews)wsNews).readNews();
    }


    private void onCreateConfigureComponents()
    {
        records = Collections.synchronizedList(new ArrayList<News>());
        wsNews = new WSNews(records);
    }


    private class WSNews extends WebService{
        private List<News> records;
        public WSNews(List<News> records) {
            super("http://192.168.88.129/");
            this.records = records;
        }

        @Override
        public boolean testConnection() throws IOException {
            return false;
        }

        public void readNews() {
            new DownloadNews().doInBackground();
        }

        protected void sendJSON(String jsonObject) {

        }

        public void start() {

        }

        public void stop() {

        }

        private class DownloadNews extends AsyncTask<Void, Integer, Integer> {
            @Override
            protected Integer doInBackground(Void... params) {
                Log.d("LOG", "Inicjalizacja");
                String jsonString;
                URL url = null;
                HttpURLConnection conn = null;
                StringBuffer sb = new StringBuffer();

                Log.d("LOG", "Ustanowienie Polaczenia");
                try {
                    url = new URL(ip + "?News&mobile=-1");
                    //conn.setConnectTimeout(5000);
                    conn = (HttpURLConnection) url.openConnection();


                    Log.d("LOG", "Pobranie strumienia danych");
                    InputStream in = conn.getInputStream();
                    InputStreamReader isw = new InputStreamReader(in);

                    Log.d("LOG", "Zapis danych do bufora");
                    int data = isw.read();
                    while (data != -1) {
                        char current = (char) data;
                        data = isw.read();
                        sb.append(current);
                    }
                } catch (SocketTimeoutException e) {
                    Log.d("LOG", "Za długi czas połączenia");
                    records.clear();
                    records.add(new News("ERROR", "Nie można połączyć się z serwerem", "Lol"));
                    rvAdapter.notifyDataSetChanged();
                    return 1;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null)
                        conn.disconnect();
                }

                Log.d("LOG", "Dodanie Obiektow json do listy");
                jsonString = sb.toString();

                records.clear();
                List<News> list = JSONConverterAPI.decodeJson(jsonString);
                for (News n: list) {
                    records.add(n);
                    rvAdapter.notifyDataSetChanged();
                }

                return 0;
            }
        }
    }


}



