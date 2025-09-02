package com.weatherapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class OpenWeatherService extends Thread {
    private String city;
    private WeatherCallback callback; // Callback to update the UI
    private static final String API_KEY = "27ec712f5bf7e56dcbe4fc1fdd8f9e8c"; 

    public OpenWeatherService(String city, WeatherCallback callback) {
        this.city = city;
        this.callback = callback;
    }

    @Override
    public void run() {
        String[] weatherData = fetchWeather();
        if (callback != null) {
            callback.onWeatherFetched(weatherData);
        }
    }

    public String[] fetchWeather() {
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&units=metric&appid=" + API_KEY;

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                return new String[]{"Error", "HTTP " + responseCode};
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            reader.close();

            JSONObject weatherJson = new JSONObject(jsonResponse.toString());
            double temp = weatherJson.getJSONObject("main").getDouble("temp");
            String description = weatherJson.getJSONArray("weather").getJSONObject(0).getString("description");

            return new String[]{String.valueOf(temp), description};

        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error", "Failed to fetch"};
        }
    }
}
