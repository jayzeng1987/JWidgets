package com.genvict.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.genvict.widgets.jwaveloading.JSinWaveView;

import androidx.appcompat.app.AppCompatActivity;

public class WaveActivity extends AppCompatActivity {
    private JSinWaveView waveLoading;
    private Button btnUpdateProgress;
    private int progress;
    private SeekBar seekBar;

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
                        for (int i = 40; i <= 100; i++) {
                            waveLoading.setProgress(i);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
//                        waveLoading.setProgress(50);
                    }
                }).start();
//                progress = (int) (Math.random()*100);
//                waveLoading.setProgress(progress);
            }
        });

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveLoading.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
