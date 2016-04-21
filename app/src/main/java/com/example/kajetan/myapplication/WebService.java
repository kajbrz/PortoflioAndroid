package com.example.kajetan.myapplication;

/**
 * Created by Kajetan on 2016-04-21.
 */
abstract public class WebService implements Model {
    protected String ip;
    protected String port;
    protected String databaseName;

    public WebService(String ip, String port, String databaseName)
    {
        this.ip = ip;
        this.port = port;
        this.databaseName = databaseName;
    }

    public abstract boolean testConnection();
}
