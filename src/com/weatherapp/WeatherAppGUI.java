package com.weatherapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherAppGUI extends JFrame {
    private JTextField cityField;
    private JButton fetchButton;
    private JLabel tempLabel, conditionLabel, statusLabel;

    public WeatherAppGUI() {
        setTitle("Weather App");
        setSize(800, 600); // Increased height for better spacing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));
        setLocationRelativeTo(null);
        //setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centered layout with spacing


        // 🌸 Set Lavender Background
        Color lavender = new Color(230, 230, 250);
        Color deepLavender = new Color(186, 85, 211);
        Color softPurple = new Color(148, 0, 211);

        getContentPane().setBackground(lavender);

        // Define Font (Larger size)
        Font titleFont = new Font("Arial", Font.BOLD, 24);
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 36);
        Font statusFont=new Font("Arial", Font.BOLD, 36);
        Font cityFont=new Font("Arial", Font.BOLD, 26);


        // City Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(deepLavender);
        JLabel cityLabel = new JLabel("Enter City:");
        cityLabel.setFont(cityFont);
        inputPanel.add(cityLabel);
        
        cityField = new JTextField(20);
        cityField.setFont(labelFont);
        inputPanel.add(cityField);
        add(inputPanel);

        // Fetch Button
        fetchButton = new JButton("GET WEATHER");
        fetchButton.setBackground(softPurple);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setFont(buttonFont);
        fetchButton.setPreferredSize(new Dimension(500, 85)); // Width: 120px, Height: 30px

        fetchButton.addActionListener(new FetchWeatherListener());
        add(fetchButton);

        // Weather Info Labels
        tempLabel = new JLabel("Temperature: -- °C", SwingConstants.CENTER);
        tempLabel.setFont(titleFont);
        conditionLabel = new JLabel("Condition: --", SwingConstants.CENTER);
        conditionLabel.setFont(titleFont);
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(statusFont);

        // 🌸 Set Text Colors
        tempLabel.setForeground(softPurple);
        conditionLabel.setForeground(deepLavender);
        statusLabel.setForeground(Color.BLACK);

        add(tempLabel);
        add(conditionLabel);
        add(statusLabel);

        setVisible(true);
    }

    private class FetchWeatherListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String city = cityField.getText().trim();
            if (city.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a city name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            statusLabel.setText("Fetching weather...");

            new OpenWeatherService(city, new WeatherCallback() {
                @Override
                public void onWeatherFetched(String[] weatherData) {
                    SwingUtilities.invokeLater(() -> {
                        if (weatherData[0].equals("Error")) {
                        	tempLabel.setText("Temperature: -- °C");
                    
                            conditionLabel.setText("Condition: --");
                            
                            statusLabel.setText("Error: "+city+" not found ");
                        } else {
                            tempLabel.setText("Temperature: " + weatherData[0] + "°C");
                            conditionLabel.setText("Condition: " + weatherData[1]);
                            statusLabel.setText("Weather fetched successfully.");
                            
                        }
                    });
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeatherAppGUI::new);
    }
}

