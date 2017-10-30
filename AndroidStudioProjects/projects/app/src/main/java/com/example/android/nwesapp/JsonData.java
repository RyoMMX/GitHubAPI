package com.example.android.nwesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonData {
    private String json;

    private void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    private int total;

    private ArrayList<Result> resultArrayList = new ArrayList<>();

    public ArrayList<Result> getResultArrayList() {
        ArrayList<Result> outputResult = new ArrayList<>(resultArrayList);
        return outputResult;
    }

    public JsonData(String json) {
        this.json = json;
        parseJson();
    }

    private void parseJson() {
        try {
            //root
            JSONObject root = new JSONObject(json);

            //response
            JSONObject response = root.getJSONObject("response");
            getResponseData(response);

            if (response.has("results")) {
                JSONArray results = response.getJSONArray("results");
                getResultsData(results);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseData(JSONObject response) throws JSONException {
        if (response.has("total")) {
            setTotal(response.getInt("total"));
        }
    }

    private void getResultsData(JSONArray results) throws JSONException {
        Result resultData;
        JSONObject resultJson;
        JSONObject fields;
        for (int i = 0; i < results.length(); i++) {
            resultData = new Result();
            resultJson = results.getJSONObject(i);

            if (resultJson.has("type")) {
                resultData.setType(resultJson.getString("type"));
            }

            if (resultJson.has("webPublicationDate")) {
                resultData.setWebPublicationDate(resultJson.getString("webPublicationDate"));

            }
            if (resultJson.has("webTitle")) {
                resultData.setWebTitle(resultJson.getString("webTitle"));
            }
            if (resultJson.has("webUrl")) {
                resultData.setWebUrl(resultJson.getString("webUrl"));
            }

            if (resultJson.has("fields")) {
                fields = resultJson.getJSONObject("fields");

                if (fields.has("byline")) {
                    resultData.setByline(fields.getString("byline"));
                }

                if (fields.has("thumbnail")) {
                    resultData.setThumbnail(fields.getString("thumbnail"));

                }
            }

            resultArrayList.add(resultData);
        }
    }
}