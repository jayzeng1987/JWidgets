package com.genvict.widget;

import android.os.Bundle;

import com.genvict.widgets.jwaveloading.WaveViewBySinCos;

import androidx.appcompat.app.AppCompatActivity;

public class WaveViewActivity extends AppCompatActivity {
    private WaveViewBySinCos waveTest1;
    private WaveViewBySinCos waveTest2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_view);

        waveTest1 = findViewById(R.id.wave_test);
        waveTest2 = findViewById(R.id.wave_test2);
        waveTest1.startAnimation();
        waveTest2.startAnimation();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        waveTest1.stopAnimation();
        waveTest2.stopAnimation();
    }
}