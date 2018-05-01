package asus.com.example.asus.cardview.service;

import android.net.Uri;
import android.os.AsyncTask;

import asus.com.example.asus.cardview.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class YahooWeatherServices {
    private WeatherServicesCallback callback;
    private String location;
    private Exception error;

    public YahooWeatherServices(WeatherServicesCallback callback) {
        this.callback = callback;
    }

    public String getLocation() {
        return location;
    }

    public void refresWeather(String l) {
        this.location = l;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... stringss) {
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")and u='c'", stringss[0]);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();

                    InputStream inputStrem = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStrem));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);

                    }
                    return result.toString();

                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if (s == null && error == null) {
                    callback.servicesFailure(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);
                    JSONObject queryResults = data.optJSONObject("query");
                    int count = queryResults.optInt("count");

                    if (count == 0) {
                        callback.servicesFailure(new LocationWeatherException("İçin hava tahmini bulunamadı" + location));
                        return;
                    }
                    Channel channel = new Channel();
                    channel.poupulate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    callback.servicesSuccess(channel);

                } catch (JSONException e) {
                    callback.servicesFailure(e);
                }
            }
        }.execute(location);
    }

    public class LocationWeatherException extends Exception {
        public LocationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }
}
