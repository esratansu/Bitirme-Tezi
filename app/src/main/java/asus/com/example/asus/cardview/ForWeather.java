package asus.com.example.asus.cardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import asus.com.example.asus.cardview.data.Channel;
import asus.com.example.asus.cardview.data.Item;
import asus.com.example.asus.cardview.service.WeatherServicesCallback;
import asus.com.example.asus.cardview.service.YahooWeatherServices;

public class ForWeather extends AppCompatActivity implements WeatherServicesCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private EditText editText;
    private EditText editText2;
    Button button;


    private YahooWeatherServices services;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_weather);

        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        services = new YahooWeatherServices(this);
        dialog = new ProgressDialog(this);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setMessage("Yükleniyor...");
                dialog.show();
                //services.refresWeather("UK, United States");
                services.refresWeather(editText.getText() + ", " + editText2.getText());

            }
        });

        /*services = new YahooWeatherServices(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Yükleniyor...");
        dialog.show();
        //services.refresWeather("UK, United States");
        services.refresWeather("UK, United States");*/


    }

    @Override
    public void servicesSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);
        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(services.getLocation());
    }

    @Override
    public void servicesFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
