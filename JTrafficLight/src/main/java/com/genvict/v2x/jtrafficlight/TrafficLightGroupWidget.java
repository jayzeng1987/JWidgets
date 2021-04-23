package com.genvict.v2x.jtrafficlight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class TrafficLightGroupWidget extends LinearLayout {
    private Context mContext;
    private View mView;
    private TrafficLightWidget mIvTrafficLightRed;
    private TrafficLightWidget mIvTrafficLightYellow;
    private TrafficLightWidget mIvTrafficLightGreen;

    private boolean isRedFlash = false;
    private boolean isYellowFlash = false;
    private boolean isGreenFlash = false;

    public TrafficLightGroupWidget(Context context) {
        super(context);
    }

    public TrafficLightGroupWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TrafficLightGroupWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.layout_traffic_light, this, true);

        mIvTrafficLightRed = mView.findViewById(R.id.light_red);
        mIvTrafficLightYellow = mView.findViewById(R.id.light_yellow);
        mIvTrafficLightGreen = mView.findViewById(R.id.light_green);
    }

    public void start() {
        mIvTrafficLightRed.setIconId(R.mipmap.traffic_light_red);
        mIvTrafficLightRed.setCurLightStatus(TrafficLightWidget.LightStatus.BRIGHT);
        mIvTrafficLightRed.setDuration(5000);
        mIvTrafficLightRed.setLightCallback(new TrafficLightWidget.LightCallback() {
            @Override
            public void onFlashStop() {
                mIvTrafficLightGreen.start();
            }

            @Override
            public void onBrightStop() {
                if (isRedFlash) {
                    mIvTrafficLightRed.setCurLightStatus(TrafficLightWidget.LightStatus.FLASH);
                    mIvTrafficLightRed.setDuration(5000);
                    mIvTrafficLightRed.start();
                } else {
                    mIvTrafficLightGreen.start();
                }
            }
        });
        mIvTrafficLightRed.start();

        mIvTrafficLightYellow.setIconId(R.mipmap.traffic_light_yellow);
        mIvTrafficLightYellow.setCurLightStatus(TrafficLightWidget.LightStatus.FLASH);
        mIvTrafficLightYellow.setDuration(10000);
        mIvTrafficLightYellow.setLightCallback(new TrafficLightWidget.LightCallback() {
            @Override
            public void onFlashStop() {
                mIvTrafficLightRed.start();
            }

            @Override
            public void onBrightStop() {

            }
        });

        mIvTrafficLightGreen.setIconId(R.mipmap.traffic_light_green);
        mIvTrafficLightGreen.setCurLightStatus(TrafficLightWidget.LightStatus.BRIGHT);
        mIvTrafficLightGreen.setDuration(5000);
        mIvTrafficLightGreen.setLightCallback(new TrafficLightWidget.LightCallback() {
            @Override
            public void onFlashStop() {
                mIvTrafficLightYellow.start();
            }

            @Override
            public void onBrightStop() {
                if (isGreenFlash) {
                    mIvTrafficLightGreen.setCurLightStatus(TrafficLightWidget.LightStatus.FLASH);
                    mIvTrafficLightGreen.setDuration(5000);
                    mIvTrafficLightGreen.start();
                } else {
                    mIvTrafficLightYellow.start();
                }

            }
        });
    }

    public void stop() {
        mIvTrafficLightRed.stop();
        mIvTrafficLightGreen.stop();
        mIvTrafficLightYellow.stop();
    }
}
