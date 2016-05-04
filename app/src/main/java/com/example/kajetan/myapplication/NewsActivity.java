package com.example.kajetan.myapplication;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsActivity extends Fragment {
    private WebService wsNews;
    private List<News> records;
    private RecyclerView rv;
    private ListNewsAdapter rvAdapter;
    private FragmentNewsReciver fragmentNewsReciver;

    public NewsActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentNewsReciver = (FragmentNewsReciver)getArguments().getSerializable("interface");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_news, container, false);

        return view;
    }


    public static final NewsActivity newInstance(FragmentNewsReciver fnr)
    {
        NewsActivity f = new NewsActivity();
        Bundle bdl = new Bundle(1);
        bdl.putSerializable("interface", fnr);
        f.setArguments(bdl);
        return f;
    }


    private void onCreateRecyclerVier() {
        rv = (RecyclerView)(getActivity().findViewById(R.id.recyclerView));

        rvAdapter = new ListNewsAdapter(getActivity(), records, fragmentNewsReciver);
        rv.setAdapter(rvAdapter);

        StaggeredGridLayoutManager gridLayoutManager =
            new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL);
        rv.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onResume(){
        super.onResume();

        onCreateConfigureComponents();
        onCreateRecyclerVier();
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



