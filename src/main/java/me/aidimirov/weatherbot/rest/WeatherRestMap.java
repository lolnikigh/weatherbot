package me.aidimirov.weatherbot.rest;

import me.aidimirov.weatherbot.config.OpenweatherConfig;
import me.aidimirov.weatherbot.config.TelegramBotConfig;
import me.aidimirov.weatherbot.payload.WeatherNowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class WeatherRestMap {

    @Autowired private RestTemplate restTemplate;
    @Autowired private OpenweatherConfig openweatherConfig;


    public WeatherNowResponse weatherNow(String city) {
        try {
            return restTemplate.getForObject(openweatherConfig.getUrl()
                    .replace("{city}", city), WeatherNowResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isCityExists(String city) throws IOException {
        var url = new URL(openweatherConfig.getUrl().replace("{city}", city));
        var connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
    }
}
