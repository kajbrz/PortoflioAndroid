package com.example.kajetan.myapplication;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Kajetan on 2016-04-21.
 */
abstract public class WebService {
    protected String ip;

    public WebService(String ip)
    {
        this.ip = ip;
    }

    public abstract boolean testConnection() throws IOException;
}
