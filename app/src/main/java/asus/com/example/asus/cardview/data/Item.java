package asus.com.example.asus.cardview.data;

import org.json.JSONObject;

/**
 * Created by ASUS on 1.05.2018.
 */


public class Item implements JSONPopulator {

    private Condition condition;

    public Condition getCondition() {
        return condition;

    }

    @Override
    public void poupulate(JSONObject data) {

        condition = new Condition();
        condition.poupulate(data.optJSONObject("condition"));

    }
}

