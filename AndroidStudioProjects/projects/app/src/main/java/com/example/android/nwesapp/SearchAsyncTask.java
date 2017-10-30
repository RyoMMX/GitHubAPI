package com.example.android.nwesapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchAsyncTask extends AsyncTaskLoader<String> {

    int page = 1;

    public int getPage() {
        return page;
    }

    public boolean nextPage() {
        if (page < 200) {
            page++;
            return true;
        }

        return false;
    }

    public boolean previousPage() {
        if (page > 1) {
            page--;
            return true;
        }

        return false;
    }

    public SearchAsyncTask(Context context, int page) {
        super(context);
        this.page = page;
    }

    @Override
    public String loadInBackground() {
        StringBuilder JsonData = new StringBuilder();
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {

            String urlString = "http://content.guardianapis.com/search?q=technology&show-fields=byline,thumbnail&page=" + page + "&page-size=30&api-key=ebce145d-a904-414c-b1b6-6d2ea4dfd612";
            Log.v("NETWORK_URL", urlString);

            URL url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");

            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                JsonData.append(line);
                line = reader.readLine();
            }
            Log.v("AsyncTask", "Connected" + httpURLConnection.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("AsyncTask", e.getMessage());
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return JsonData.toString();
    }
}