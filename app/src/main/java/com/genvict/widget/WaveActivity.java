package com.genvict.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.genvict.widgets.jwaveloading.JSinWaveView;

import androidx.appcompat.app.AppCompatActivity;

public class WaveActivity extends AppCompatActivity {
    private JSinWaveView waveLoading;
    private Button btnUpdateProgress;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);

        waveLoading = findViewById(R.id.wave_loading);
        btnUpdateProgress = findViewById(R.id.btn_update_progress);
        btnUpdateProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 100; i++) {
                            waveLoading.setProgress(i);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
//                progress = (int) (Math.random()*100);
//                waveLoading.setProgress(progress);
            }
        });

    }
}
