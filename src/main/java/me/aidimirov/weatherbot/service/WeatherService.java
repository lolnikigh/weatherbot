package me.aidimirov.weatherbot.service;

import me.aidimirov.weatherbot.payload.WeatherNowResponse;
import me.aidimirov.weatherbot.rest.WeatherRestMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {

    @Autowired private WeatherRestMap weatherRestMap;

    public WeatherNowResponse weatherNow(String city) {
        return weatherRestMap.weatherNow(city);
    }

    public boolean isCityExists(String city) throws IOException {
        return weatherRestMap.isCityExists(city);
    }
}
