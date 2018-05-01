package asus.com.example.asus.cardview.data;

import org.json.JSONObject;

/**
 * Created by ASUS on 1.05.2018.
 */


public class Units implements JSONPopulator {

    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void poupulate(JSONObject data) {

        temperature = data.optString("temperature");

    }
}


