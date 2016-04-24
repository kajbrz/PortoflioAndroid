package com.example.kajetan.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kajetan on 2016-04-24.
 */
public class JSONConverterAPI {
    public static ArrayList<News> decodeJson(String jsonObject)
    {
        ArrayList<News> alListOfNews = new ArrayList<News>();
        try {
            JSONObject jsMy = new JSONObject(jsonObject);

            JSONArray jsonArray = jsMy.getJSONArray("messages");
            for (int i = 0; i < jsonArray.length(); i++)
            {
                try {
                    JSONObject jsObj = jsonArray.getJSONObject(i);
                    alListOfNews.add(i, new News(
                            jsObj.getString("date"),
                            jsObj.getString("text"),
                            jsObj.getString("author")
                    ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return alListOfNews;
    }

}
