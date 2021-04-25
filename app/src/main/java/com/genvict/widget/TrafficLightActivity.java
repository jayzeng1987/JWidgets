package com.genvict.widget;

import android.os.Bundle;
import android.widget.Button;

import com.genvict.v2x.jtrafficlight.TrafficLightWidget;

import androidx.appcompat.app.AppCompatActivity;

public class TrafficLightActivity extends AppCompatActivity {
    private TrafficLightWidget trafficLightWidget;
    private Button btnStart, btnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_light);

        trafficLightWidget = findViewById(R.id.light_red);
        trafficLightWidget.setRedLightIconId(R.mipmap.traffic_light_red);
        trafficLightWidget.setYellowLightIconId(R.mipmap.traffic_light_yellow);
        trafficLightWidget.setGreenLightIconId(R.mipmap.traffic_light_green);

        TrafficLightWidget.LightDuration lightDuration = new TrafficLightWidget.LightDuration();
        lightDuration.redLightBright = 5000;
        lightDuration.redLightFlash = 0;
        lightDuration.yellowLightBright = 0;
        lightDuration.yellowLightFlash = 5000;
        lightDuration.greenLightBright = 5000;
        lightDuration.greenLightFlash = 5000;
        trafficLightWidget.setLightDuration(lightDuration);

        btnStart = findViewById(R.id.btn_light_start);
        btnStart.setOnClickListener(view -> {
            trafficLightWidget.start();
        });
        btnStop = findViewById(R.id.btn_light_stop);
        btnStop.setOnClickListener(view -> {
            trafficLightWidget.stop();
        });

    }

}