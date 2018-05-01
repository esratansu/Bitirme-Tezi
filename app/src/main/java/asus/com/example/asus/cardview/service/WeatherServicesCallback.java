package asus.com.example.asus.cardview.service;

import asus.com.example.asus.cardview.data.Channel;

/**
 * Created by ASUS on 1.05.2018.
 */

public interface WeatherServicesCallback {
    void servicesSuccess(Channel channel);

    void servicesFailure(Exception exception);
}
