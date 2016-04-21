package com.example.kajetan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NewsActivity extends AppCompatActivity {
    private WebService wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        configure();
    }

    private void configure()
    {
        wb = new myWebService("localhost", "3306", "Blog");
        setOnButtonTestListener();
    }

    private void setOnButtonTestListener() {
        Button btn = (Button)findViewById(R.id.btnTest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wb.testConnection())
                    Toast.makeText(NewsActivity.this, "Istnieje polaczenie", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(NewsActivity.this, "Brak polaczenia", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class myWebService extends WebService
    {
        public myWebService(String ip, String port, String databaseName) {
            super(ip, port, databaseName);
        }

        @Override
        public boolean testConnection() {
            return false;
        }

        @Override
        public News[] readNews() {
            return new News[0];
        }

        @Override
        public void sendNews() {

        }
    }
}



