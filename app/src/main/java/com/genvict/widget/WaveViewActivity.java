package com.genvict.widget;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.genvict.widgets.jwaveloading.WaveViewBySinCos;

import androidx.appcompat.app.AppCompatActivity;

public class WaveViewActivity extends AppCompatActivity {
    private WaveViewBySinCos waveTest1;
    private WaveViewBySinCos waveTest2;
    private ImageView ivShip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_view);

        waveTest1 = findViewById(R.id.wave_test);
        waveTest2 = findViewById(R.id.wave_test2);
        waveTest1.startAnimation();
        waveTest2.startAnimation();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.ship_anim);
        ivShip = findViewById(R.id.iv_ship);
        ivShip.startAnimation(animation);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        waveTest1.stopAnimation();
        waveTest2.stopAnimation();
    }
}