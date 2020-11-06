package com.genvict.widgets.jwaveloading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

public class JWaveLoadingView extends View {
    private Paint mWavePaint;
//    private Path mWavePath;

    private int mHeight;
    private int mWaveWidth;
    private int mWaveHeight;
    private volatile int mBaseLine;

    private ValueAnimator mValueAnimator;
    private float offsetX;

    public JWaveLoadingView(Context context) {
        this(context, null);
    }

    public JWaveLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JWaveLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public synchronized void setProgress(int progress) {
        mBaseLine = mBaseLine - progress;
        Log.i("genvict", "setProgress: " + progress);
        Log.i("genvict", "mBaseLine: " + mBaseLine);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWaveWidth = getWidth();
        mWaveHeight = 100;
        mBaseLine = getHeight() / 2;
        Log.i("genvict", "onSizeChanged: " + mBaseLine);
        mHeight = getHeight() / 2;

        mValueAnimator = ValueAnimator.ofFloat(mWaveWidth);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offsetX = (float)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mValueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(getPath(), mWavePaint);
    }

    private void init(Context context, AttributeSet attrs) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mWavePaint.setColor(Color.argb(200, 2, 169, 244));
    }

    private Path getPath() {
        Log.i("genvict", "getPath: mBaseLine:" + mBaseLine);
        int itemWidth = mWaveWidth/2;//半个波长
        Path mWavePath = new Path();
        mWavePath.moveTo(-itemWidth * 3, mBaseLine);

        for (int i = -3; i < 2; i++) {
            int startX = i * itemWidth;
            mWavePath.quadTo(
                    startX + itemWidth/2 + offsetX,//控制点的X,（起始点X + itemWidth/2 + offset)
                    getWaveHeigh( i ),//控制点的Y
                    startX + itemWidth + offsetX,//结束点的X
                    mBaseLine//结束点的Y
            );//只需要处理完半个波长，剩下的有for循环自已就添加了。
        }
        mWavePath.lineTo(mWaveWidth, getHeight());
        mWavePath.lineTo(0, getHeight());
        mWavePath.close();

        return mWavePath;
    }

    //奇数峰值是正的，偶数峰值是负数
    private int getWaveHeigh(int num){
        if(num % 2 == 0){
            return mBaseLine + mWaveHeight;
        }
        return mBaseLine - mWaveHeight;
    }
}
