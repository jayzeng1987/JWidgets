package com.genvict.widget;

import android.os.Bundle;

import com.genvict.v2x.jtrafficlight.TrafficLightWidget;

import androidx.appcompat.app.AppCompatActivity;

public class TrafficLightActivity extends AppCompatActivity {
    private TrafficLightWidget redLight, yellowLight, greenLight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_light);

        redLight = findViewById(R.id.light_red);
        redLight.setIconId(R.mipmap.traffic_light_red);
        redLight.setCurLightStatus(TrafficLightWidget.LightStatus.BRIGHT);
        redLight.setDuration(5000);
        redLight.setLightCallback(new TrafficLightWidget.LightCallback() {
            @Override
            public void onFlashStop() {
                greenLight.start();
            }

            @Override
            public void onBrightStop() {
                redLight.setCurLightStatus(TrafficLightWidget.LightStatus.FLASH);
                redLight.setDuration(5000);
                redLight.start();
            }
        });
        redLight.start();

        yellowLight = findViewById(R.id.light_yellow);
        yellowLight.setIconId(R.mipmap.traffic_light_yellow);
        yellowLight.setCurLightStatus(TrafficLightWidget.LightStatus.FLASH);
        yellowLight.setDuration(10000);
        yellowLight.setLightCallback(new TrafficLightWidget.LightCallback() {
            @Override
            public void onFlashStop() {

            }

            @Override
            public void onBrightStop() {

            }
        });

        greenLight = findViewById(R.id.light_green);
        greenLight.setIconId(R.mipmap.traffic_light_green);
        greenLight.setCurLightStatus(TrafficLightWidget.LightStatus.BRIGHT);
        greenLight.setDuration(5000);
        greenLight.setLightCallback(new TrafficLightWidget.LightCallback() {
            @Override
            public void onFlashStop() {
                yellowLight.start();
            }

            @Override
            public void onBrightStop() {
                greenLight.setCurLightStatus(TrafficLightWidget.LightStatus.FLASH);
                greenLight.setDuration(5000);
                greenLight.start();
            }
        });


    }

}