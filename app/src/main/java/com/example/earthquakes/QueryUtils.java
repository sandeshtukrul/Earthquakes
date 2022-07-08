package com.example.earthquakes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private QueryUtils(){}

    public static List<Earthquake> parseUrl(String url){
        URL link = BuildUrl(url);
        String jsonData = "";
        try {
            jsonData = makeHttpRequest(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseJsonData(jsonData);
    }

    private static URL BuildUrl(String url){
        URL url1 = null;
        try {
            url1 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url1;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String json;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            json = readFromStream(inputStream);

        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }

            if (inputStream != null){
                inputStream.close();
            }
        }

        return json;
    }

    private static String readFromStream(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        try {
            String output = reader.readLine();
            while (output != null){
                builder.append(output);
                output = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private static List<Earthquake> parseJsonData(String jsonData){

        List<Earthquake> earthquakeArrayList = new ArrayList<>();

        if (jsonData == null){
            return null;
        }

        try {
            JSONObject object = new JSONObject(jsonData);
            JSONArray array = object.getJSONArray("features");

            for (int i = 0; i < array.length(); i++) {

                JSONObject object1 = array.getJSONObject(i);
                JSONObject properties = object1.getJSONObject("properties");

                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");

                Earthquake earthquake = new Earthquake(mag, time, place, url);
                earthquakeArrayList.add(earthquake);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return earthquakeArrayList;
    }
}
