package com.genvict.widgets.jwaveloading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


public class JSinWaveView extends View {
    private Paint mWavePaint;
    private Path mWavePath;
    private int amplitude;

    private int mWidth;
    private int mHeight;

    private int mWaveHeight;
    private float mTheta = 0f;
    private int move = 0;

    public JSinWaveView(Context context) {
        this(context, null);
    }

    public JSinWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JSinWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取测量的宽高的size和mode
        final int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWavePath.reset();
        mWavePath.moveTo(0, 0);
        int index = 0;
        float endY = 0;
        while (index <= mWidth) {
            endY = (float) (Math.sin((float) index / (float) mWidth * 2f * Math.PI + mTheta)
                    * (float) amplitude + mWaveHeight);
            mWavePath.lineTo(index, endY);
            index++;
        }

        mWavePath.lineTo(index - 1, 0);
        mWavePath.close();

        canvas.drawPath(mWavePath, mWavePaint);

        mTheta += 0.5;
        if (mTheta >= 2f * Math.PI) {
            mTheta -= (2f * Math.PI);
        }

        if (mWaveHeight < mHeight / 2) {
            mWaveHeight++;
        }
        postInvalidateDelayed(80);


    }

    private void init(Context context, AttributeSet attrs) {

        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mWavePaint.setColor(Color.argb(255, 32, 202, 119));
        mWavePath = new Path();

        amplitude = 20;
        mWaveHeight = 0;
    }


}
