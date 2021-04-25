package com.genvict.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.genvict.widgets.jwaveloading.JWaveLoadingView;

import androidx.appcompat.app.AppCompatActivity;

public class WaveLoadingActivity extends AppCompatActivity {
    private Button btnStart;
    private JWaveLoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_loading);

        loadingView = findViewById(R.id.wave_loading);

        btnStart = findViewById(R.id.btn_light_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 100; i++) {
                            loadingView.setProgress(i);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

    }
}
