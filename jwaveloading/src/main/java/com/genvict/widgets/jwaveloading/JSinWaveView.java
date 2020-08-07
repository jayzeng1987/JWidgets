package com.genvict.widgets.jwaveloading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


public class JSinWaveView extends View {
    private Paint mCirclePaint;
    private Paint mOutCirclePaint;
    private Paint mProgressPaint;
    private Paint mWavePaint;
    private Path mWavePath;


    private int mWidth;
    private int mHeight;

    private int mCircleX;
    private int mCircleY;
    private int mCircleRadius;

    private int mWaveHeight;
    private int mAmplitude = 20;
    private float mTheta = 0f;

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

        mCircleX = mWidth / 2;
        mCircleY = mHeight / 4;
        mCircleRadius = mWidth / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画圆
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.drawCircle(mCircleX, mCircleY, mCircleRadius + 20, mOutCirclePaint);
        canvas.drawCircle(mCircleX, mCircleY, mCircleRadius, mCirclePaint);

        int startY = mCircleY - mCircleRadius;
        mWavePath.reset();
        mWavePath.moveTo(0, startY);
        int index = 0;
        float endY = 0;
        while (index <= mWidth) {
            endY = (float) (Math.sin((float) index / (float) mWidth * 5f * Math.PI + mTheta)
                    * (float) mAmplitude + mWaveHeight);
            mWavePath.lineTo(index, endY + startY);
            index++;
        }

        mWavePath.lineTo(index - 1, startY);
        mWavePath.close();
//        canvas.save();
        Path pc = new Path();
        pc.addCircle(mCircleX, mCircleY, mCircleRadius, Path.Direction.CCW);
        pc.close();
        //切割画布
        canvas.clipPath(pc, Region.Op.INTERSECT);

        canvas.drawPath(mWavePath, mWavePaint);
//        canvas.restore();

        mTheta += 0.3;
        if (mTheta >= 2f * Math.PI) {
            mTheta -= (2f * Math.PI);
        }

        if (mWaveHeight < 2 * mCircleRadius) {
            mWaveHeight += 2;

            canvas.drawText(mWaveHeight + "", mCircleX, mCircleY, mProgressPaint);
        } else {
            mWaveHeight = 0;
        }
        postInvalidateDelayed(50);


    }

    private void init(Context context, AttributeSet attrs) {
        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
//        mWavePaint.setColor(Color.argb(255, 32, 202, 119));
        mWavePaint.setColor(Color.argb(200, 2, 169, 244));


        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.GRAY);

        mOutCirclePaint = new Paint();
        mOutCirclePaint.setAntiAlias(true);
        mOutCirclePaint.setColor(Color.LTGRAY);

        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setTextAlign(Paint.Align.CENTER);
        mProgressPaint.setTextSize(60);
        mProgressPaint.setColor(Color.WHITE);

        mWavePath = new Path();
    }


}
