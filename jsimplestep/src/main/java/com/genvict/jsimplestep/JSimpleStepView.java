package com.genvict.jsimplestep;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class JSimpleStepView extends View {

    private int mStepItemSize = 0;
    private boolean mIsShowTitle = true;
    private Paint mStepCirclePaint;
    private Paint mStepNumPaint;
    private Paint mStepTitlePaint;
    private Paint mBaseStepLinePaint;
    private Paint mFinishStepLinePaint;
    private Paint mNextStepLinePaint;

    private int mStepCircleRadius = 35;
    private int mStepTitleHeight = 100;
    private int mStepNumTextSize = 35;
    private int mStepTitleTextSize = 30;
    private int mTitleTopSpace = 10;
    private List<String> mStepContentList;
    private int mCurrentStep = 1;
    private float mStepLineStrokeWidth = 5.0f;

    private int mUndoStepColorId = Color.GRAY;
    private int mFinishStepColorId = Color.BLACK;
    private int mFinishStepLineColorId = Color.BLACK;
    private int mBaseStepLineColorId = Color.GRAY;
    private int mNextStepLineColorId = Color.GRAY;
    private int mStepNumTextColorId = Color.WHITE;


    private int mViewWidth;
    private int mViewHeight;

    public JSimpleStepView(Context context) {
        this(context, null, 0);
    }

    public JSimpleStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JSimpleStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setStepContentList(List<String> list) {
        if (this.mStepContentList == null) {
            this.mStepContentList = new ArrayList<>(1);
        }
        this.mStepContentList.clear();
        this.mStepContentList.addAll(list);
        mStepItemSize = mStepContentList.size();
    }

    public void setCurrentStep(int step) {
        this.mCurrentStep = step;
        invalidate();
    }

    public int getCurrentStep() {
        return mCurrentStep;
    }

    public void setCircleRadius(int circleRadius) {
        this.mStepCircleRadius = circleRadius;
    }

    public void setStepNumTextSize(int stepNumTextSize) {
        this.mStepNumTextSize = stepNumTextSize;
    }

    public void setStepTitleTextSize(int stepTitleTextSize) {
        this.mStepTitleTextSize = stepTitleTextSize;
    }

    public void setTitleTopSpace(int titleTopSpace) {
        this.mTitleTopSpace = titleTopSpace;
    }

    public void setStepLineStrokeWidth(int width) {
        this.mStepLineStrokeWidth = width;
    }

    private void init() {
        initPaint();

    }

    private void initPaint() {
        //圆形背景
        mStepCirclePaint = new Paint();
        mStepCirclePaint.setStyle(Paint.Style.FILL);
        mStepCirclePaint.setAntiAlias(true);

        //步骤序号
        mStepNumPaint = new Paint();
        mStepNumPaint.setAntiAlias(true);
        mStepNumPaint.setColor(mStepNumTextColorId);
        mStepNumPaint.setTextAlign(Paint.Align.CENTER);
        mStepNumPaint.setTextSize(mStepNumTextSize);

        //步骤标题
        mStepTitlePaint = new Paint();
        mStepTitlePaint.setAntiAlias(true);
        mStepTitlePaint.setTextAlign(Paint.Align.CENTER);
        mStepTitlePaint.setTextSize(mStepTitleTextSize);

        //连接基线（圆点）
        mBaseStepLinePaint = new Paint();
        mBaseStepLinePaint.setAntiAlias(true);
        mBaseStepLinePaint.setStrokeWidth(mStepLineStrokeWidth);
        mBaseStepLinePaint.setColor(mBaseStepLineColorId);
        mBaseStepLinePaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.addCircle(0, 0, 3, Path.Direction.CW);
        mBaseStepLinePaint.setPathEffect(new PathDashPathEffect(path, 14, 0, PathDashPathEffect.Style.ROTATE));

        //已完成步骤连接线
        mFinishStepLinePaint = new Paint();
        mFinishStepLinePaint.setAntiAlias(true);
        mFinishStepLinePaint.setStrokeWidth(mStepLineStrokeWidth);
        mFinishStepLinePaint.setColor(mFinishStepLineColorId);

        //下一步骤连接线
        mNextStepLinePaint = new Paint();
        mNextStepLinePaint.setAntiAlias(true);
        mNextStepLinePaint.setStrokeWidth(mStepLineStrokeWidth);
        mNextStepLinePaint.setColor(mNextStepLineColorId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        //测量步骤标题高度
        mStepTitleHeight = mIsShowTitle ? measureTextHeight(mStepTitlePaint) : 0;
        //根据宽高模式测量控件宽高
        if (measureWidthMode == MeasureSpec.AT_MOST && measureHeightMode == MeasureSpec.AT_MOST) {
            //width: wrap_content 宽度需自行计算
            //height:wrap_content 高度需自行计算
            mViewWidth = mStepItemSize * mStepCircleRadius * 2;
            if (mIsShowTitle) {
                //则计算所有标题中最长的宽度，并按此宽度为标准计算所有标题的总宽度，该宽度与图形总宽度进行比较，取最大值
                int allTitleWidth = mStepItemSize * getMaxTextWidth(mStepContentList);
                mViewWidth = Math.max(mViewWidth, allTitleWidth);
            }
            mViewHeight = mIsShowTitle ? (mStepCircleRadius * 2 + mStepTitleHeight) : mStepCircleRadius * 2;
        } else if (measureWidthMode == MeasureSpec.EXACTLY && measureHeightMode == MeasureSpec.EXACTLY) {
            //width: match_parent 宽度为父控件测量建议值
            //height:match_parent 高度为父控件测量建议值
            mViewWidth = measureWidth;
            mViewHeight = measureHeight;
        } else if (measureWidthMode == MeasureSpec.AT_MOST && measureHeightMode == MeasureSpec.EXACTLY) {
            //width：wrap_content 宽度需自行计算
            //height：match_parent 高度为父控件测量建议值
            mViewWidth = mStepItemSize * mStepCircleRadius * 2;
            if (mIsShowTitle) {
                int allTitleWidth = mStepItemSize * getMaxTextWidth(mStepContentList);
                mViewWidth = Math.max(mViewWidth, allTitleWidth);
            }
            mViewHeight = measureHeight;
        } else {
            //width：match_parent 宽度为父控件测量建议值
            //height：wrap_content 高度需自行计算
            mViewWidth = measureWidth;
            mViewHeight = mIsShowTitle ? (mStepCircleRadius * 2 + mStepTitleHeight + mTitleTopSpace + 25) : mStepCircleRadius * 2;
        }

        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mStepContentList.size() == 0) {
            return;
        }
        //x,y：圆形中心坐标
        //lineStartX, lineStartY：连接线起点坐标
        //lineEndX, lineEndY：连接线终点坐标
        int x, y, lineStartX, lineStartY, lineEndX, lineEndY;
        //每个步骤项的宽度
        int stepItemWidth = mViewWidth / mStepItemSize;
        //标题Y坐标相对于圆形Y坐标的偏移量
        int titleYOffset = mStepCircleRadius + measureTextHeight(mStepTitlePaint) + mTitleTopSpace;
        //循环画图形、标题、连接线
        for (int i = 0; i < mStepItemSize; i++) {
            //计算每个圆形的中心点
            x = (stepItemWidth/2) * (2 * i + 1);
            y = (mViewHeight - mStepTitleHeight) / 2;
            //画圆形背景
            if (i < mCurrentStep) {
                //当前步骤或之前的步骤
                mStepCirclePaint.setColor(mFinishStepColorId);
                mStepTitlePaint.setColor(mFinishStepColorId);
            } else {
                //未完成的步骤
                mStepCirclePaint.setColor(mUndoStepColorId);
                mStepTitlePaint.setColor(mUndoStepColorId);
            }
            //画圆
            canvas.drawCircle(x, y, mStepCircleRadius, mStepCirclePaint);
            //画步骤序号
            canvas.drawText(String.valueOf(i + 1), x, y + getBaseY(mStepNumPaint), mStepNumPaint);
            //画步骤标题
            if (mIsShowTitle) {
                canvas.drawText(mStepContentList.get(i), x, y + titleYOffset, mStepTitlePaint);
            }
            //画连接线
            //计算连接线起始坐标
            lineStartX = x + mStepCircleRadius;
            lineStartY = y;
            //计算连接线终点坐标
            lineEndX = (stepItemWidth - 2 * mStepCircleRadius) + lineStartX;
            lineEndY = y;

            if (i < mStepItemSize - 1) {
                if (i < mCurrentStep - 1) {
                    //已完成步骤连接线
                    canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, mFinishStepLinePaint);
                } else if (i == mCurrentStep - 1) {
                    //下一步骤连接线
                    canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, mNextStepLinePaint);
                } else {
                    //禁止硬件加速，硬件加速导致无法显示虚线
                    setLayerType(LAYER_TYPE_SOFTWARE, null);
                    //未完成步骤连接线（点线）
                    canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, mBaseStepLinePaint);
                }
            }
        }

    }

    private int getBaseY(Paint paint) {
        if (paint != null) {
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();
            return ((fm.descent - fm.ascent) / 2- fm.descent);
        }
        return 0;
    }

    private int measureTextHeight(Paint paint){
        int height = 0;
        if(null == paint){
            return height;
        }
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        return fm.descent - fm.ascent;
    }

    private int measureTextWidth(Paint paint, String text) {
        int width = 0;
        if(null == paint){
            return width;
        }
        return (int) paint.measureText(text);
    }

    private int getMaxTextWidth(List<String> list) {
        int size = list.size();
        int maxWidth = 0;
        for (int i = 0; i < size; i++) {
            maxWidth = Math.max(maxWidth, measureTextWidth(mStepTitlePaint, list.get(i)));
        }
        return maxWidth;
    }

}
