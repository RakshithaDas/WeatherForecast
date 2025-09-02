package com.weatherapp;

// Thread class for fetching weather data
public abstract class WeatherFetcher extends Thread {
    private String city;
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCity() {
        return city;
    }
    
    // Abstract method (Will be implemented in OpenWeatherService)
    public abstract void run();
}
