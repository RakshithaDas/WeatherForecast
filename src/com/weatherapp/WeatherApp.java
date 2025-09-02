package com.weatherapp;

import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
    	

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter city name: ");
        String city = scanner.nextLine();
        scanner.close();

        System.out.println("Fetching real-time weather for: " + city);

        // Start Weather Thread with callback
        OpenWeatherService weatherThread = new OpenWeatherService(city, new WeatherCallback() {
            @Override
            public void onWeatherFetched(String[] weatherData) {
                if (weatherData[0].equals("Error")) {
                    System.out.println("Error fetching weather: " + weatherData[1]);
                } else {
                    System.out.println("Temperature: " + weatherData[0] + "°C");
                    System.out.println("Condition: " + weatherData[1]);
                }
            }
        });

        weatherThread.start();
    }
}
