package com.genvict.jsimplestep;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * 自定义简单步骤指示器控件
 * @author jayz
 */
public class JSimpleStepView extends View {
    private Context mContext;

    /**
     * 控件视图宽度
     */
    private int mViewWidth;
    /**
     * 控件视图高度
     */
    private int mViewHeight;
    /**
     * 步骤数，当设置了步骤标题内容列表后，该值等于步骤标题内容列表的长度
     */
    private int mStepItemSize;
    /**
     * 是否显示步骤标题 true：显示，false：不显示（默认true）
     */
    private boolean mIsShowTitle;
    /**
     * 步骤标题内容列表
     */
    private List<String> mStepContentList;
    /**
     * 当前步骤序号
     */
    private int mCurrentStepNum = 1;

    /**
     * 圆形画笔
     */
    private Paint mStepCirclePaint;
    /**
     * 步骤序号画笔
     */
    private Paint mStepNumPaint;
    /**
     * 步骤标题画笔
     */
    private Paint mStepTitlePaint;
    /**
     * 步骤连接线画笔
     */
    private Paint mBaseStepLinePaint;
    /**
     * 完成的步骤连接线画笔
     */
    private Paint mFinishStepLinePaint;
    /**
     * 下一步步骤连接线画笔
     */
    private Paint mNextStepLinePaint;

    /**
     * 圆的半径
     */
    private int mStepCircleRadius;
    /**
     * 步骤标题高度
     */
    private int mStepTitleHeight;
    /**
     * 步骤序号文字大小
     */
    private int mStepNumTextSize;
    /**
     * 步骤标题文字大小
     */
    private int mStepTitleTextSize;
    /**
     * 步骤标题顶部空白间隔
     */
    private int mStepTitleTopSpace;
    /**
     * 步骤连接线宽度
     */
    private float mStepLineStrokeWidth;
    /**
     * 未完成步骤颜色
     */
    private int mUndoStepColorId;
    /**
     * 已完成步骤颜色
     */
    private int mFinishStepColorId;
    /**
     * 已完成步骤连接线颜色
     */
    private int mFinishStepLineColorId;
    /**
     * 基础连接线颜色
     */
    private int mBaseStepLineColorId;
    /**
     * 下一步连接线颜色
     */
    private int mNextStepLineColorId;
    /**
     * 步骤序号文字颜色
     */
    private int mStepNumTextColorId;

    public JSimpleStepView(Context context) {
        this(context, null, 0);
    }

    public JSimpleStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JSimpleStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaint();
    }

    /**
     * 设置步骤数，当调用了setStepContentList设置步骤内容后，该方法无效，StepItemSize值等于StepContentList的大小
     * @param size 数量
     * @return JSimpleStepView
     */
    public JSimpleStepView setStepItemSize(int size) {
        if (mStepContentList != null && mStepContentList.size() > 0) {
            return this;
        }
        this.mStepItemSize = size;
        return this;
    }

    /**
     * 设置步骤内容列表
     * @param list 内容列表
     * @return JSimpleStepView
     */
    public JSimpleStepView setStepContentList(List<String> list) {
        if (this.mStepContentList == null) {
            this.mStepContentList = new ArrayList<>(1);
        }
        this.mStepContentList.clear();
        this.mStepContentList.addAll(list);
        mStepItemSize = mStepContentList.size();
        return this;
    }

    /**
     * 是否显示步骤标题
     * @param isShowTitle true：显示，false：不显示
     * @return JSimpleStepView
     */
    public JSimpleStepView setIsShowTitle(boolean isShowTitle) {
        this.mIsShowTitle = isShowTitle;
        invalidate();
        return this;
    }

    /**
     * 设置当前步骤
     * @param stepNum 步骤序号
     * @return JSimpleStepView
     */
    public JSimpleStepView setCurrentStepNum(int stepNum) {
        this.mCurrentStepNum = stepNum;
        invalidate();
        return this;
    }

    /**
     * 获取当前步骤序号
     * @return 步骤序号
     */
    public int getCurrentStepNum() {
        return mCurrentStepNum;
    }

    /**
     * 设置圆的半径
     * @param circleRadius 圆的半径
     * @return JSimpleStepView
     */
    public JSimpleStepView setCircleRadius(int circleRadius) {
        this.mStepCircleRadius = circleRadius;
        return this;
    }

    /**
     * 设置步骤序号文字大小
     * @param textSize 文字大小
     * @return JSimpleStepView
     */
    public JSimpleStepView setStepNumTextSize(int textSize) {
        this.mStepNumTextSize = textSize;
        return this;
    }

    /**
     * 设置步骤标题文字大小
     * @param textSize 文字大小
     * @return JSimpleStepView
     */
    public JSimpleStepView setStepTitleTextSize(int textSize) {
        this.mStepTitleTextSize = textSize;
        return this;
    }

    /**
     * 设置标题顶部空白间隔
     * @param titleTopSpace 空白间隔
     * @return JSimpleStepView
     */
    public JSimpleStepView setTitleTopSpace(int titleTopSpace) {
        this.mStepTitleTopSpace = titleTopSpace;
        return this;
    }

    /**
     * 步骤连接线宽度
     * @param width 宽度
     * @return JSimpleStepView
     */
    public JSimpleStepView setStepLineStrokeWidth(int width) {
        this.mStepLineStrokeWidth = width;
        return this;
    }

    /**
     * 设置未完成步骤颜色
     * @param colorId 颜色资源ID
     * @return JSimpleStepView
     */
    public JSimpleStepView setUndoStepColorId(int colorId) {
        this.mUndoStepColorId = colorId;
        return this;
    }

    /**
     * 设置已完成步骤颜色
     * @param colorId 颜色资源ID
     * @return JSimpleStepView
     */
    public JSimpleStepView setFinishStepColorId(int colorId) {
        this.mFinishStepColorId = colorId;
        return this;
    }

    /**
     * 设置未完成步骤连接线颜色
     * @param colorId 颜色资源ID
     * @return JSimpleStepView
     */
    public JSimpleStepView setFinishStepLineColorId(int colorId) {
        this.mFinishStepLineColorId = colorId;
        return this;
    }

    /**
     * 设置基础连接线颜色
     * @param colorId 颜色资源ID
     * @return JSimpleStepView
     */
    public JSimpleStepView setBaseStepLineColorId(int colorId) {
        this.mBaseStepLineColorId = colorId;
        return this;
    }

    /**
     * 设置下一步骤连接线颜色
     * @param colorId 颜色资源ID
     * @return JSimpleStepView
     */
    public JSimpleStepView setNextStepLineColorId(int colorId) {
        this.mNextStepLineColorId = colorId;
        return this;
    }

    /**
     * 设置步骤序号文字颜色
     * @param colorId 颜色资源ID
     * @return JSimpleStepView
     */
    public JSimpleStepView setStepNumTextColorId(int colorId) {
        this.mStepNumTextColorId = colorId;
        return this;
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.JSimpleStepView);
        try {
            setIsShowTitle(ta.getBoolean(R.styleable.JSimpleStepView_isShowTitle, true));
            setCircleRadius((int) ta.getDimension(R.styleable.JSimpleStepView_stepCircleRadius, getResources().getDimension(R.dimen.stepCircleDefRadius)));
            setStepNumTextSize((int) ta.getDimension(R.styleable.JSimpleStepView_stepNumTextSize, getResources().getDimension(R.dimen.stepNumDefTextSize)));
            setStepTitleTextSize((int) ta.getDimension(R.styleable.JSimpleStepView_stepTitleTextSize, getResources().getDimension(R.dimen.stepTitleDefTextSize)));
            setTitleTopSpace((int) ta.getDimension(R.styleable.JSimpleStepView_stepTitleTopSpace, getResources().getDimension(R.dimen.stepTitleDefTopSpace)));
            setStepLineStrokeWidth((int) ta.getDimension(R.styleable.JSimpleStepView_stepLineStrokeWidth, getResources().getDimension(R.dimen.stepLineDefStrokeWidth)));

            setUndoStepColorId(ta.getColor(R.styleable.JSimpleStepView_undoStepColor, getResources().getColor(R.color.undoStepDefColor)));
            setFinishStepColorId(ta.getColor(R.styleable.JSimpleStepView_finishStepColor, getResources().getColor(R.color.finishStepDefColor)));
            setFinishStepLineColorId(ta.getColor(R.styleable.JSimpleStepView_finishStepLineColor, getResources().getColor(R.color.finishStepLineDefColor)));
            setBaseStepLineColorId(ta.getColor(R.styleable.JSimpleStepView_baseStepLineColor, getResources().getColor(R.color.baseStepLineDefColor)));
            setNextStepLineColorId(ta.getColor(R.styleable.JSimpleStepView_nextStepLineColor, getResources().getColor(R.color.nextStepLineDefColor)));
            setStepNumTextColorId(ta.getColor(R.styleable.JSimpleStepView_stepNumTextColor, getResources().getColor(R.color.stepNumTextDefColor)));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
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
        //获取测量的宽高的size和mode
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
            mViewHeight = mIsShowTitle ? (mStepCircleRadius * 2 + mStepTitleHeight + mStepTitleTopSpace + 25) : mStepCircleRadius * 2;
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
        if (mStepItemSize == 0) {
            return;
        }
        //x,y：圆形中心坐标
        //lineStartX, lineStartY：连接线起点坐标
        //lineEndX, lineEndY：连接线终点坐标
        int x, y, lineStartX, lineStartY, lineEndX, lineEndY;
        //每个步骤项的宽度
        int stepItemWidth = mViewWidth / mStepItemSize;
        //标题Y坐标相对于圆形Y坐标的偏移量
        int titleYOffset = mStepCircleRadius + measureTextHeight(mStepTitlePaint) + mStepTitleTopSpace;
        //循环画图形、标题、连接线
        for (int i = 0; i < mStepItemSize; i++) {
            //计算每个圆形的中心点
            x = (stepItemWidth/2) * (2 * i + 1);
            y = (mViewHeight - mStepTitleHeight) / 2;

            //画连接线
            //计算连接线起始坐标
            lineStartX = x + mStepCircleRadius;
            lineStartY = y;
            //计算连接线终点坐标
            lineEndX = (stepItemWidth - 2 * mStepCircleRadius) + lineStartX;
            lineEndY = y;

            if (i < mStepItemSize - 1) {
                //禁止硬件加速，硬件加速导致无法显示虚线
                setLayerType(LAYER_TYPE_SOFTWARE, null);
                //未完成步骤连接线（点线）
                canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, mBaseStepLinePaint);

                if (i < mCurrentStepNum - 1) {
                    //已完成步骤连接线
                    canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, mFinishStepLinePaint);
                } else if (i == mCurrentStepNum - 1) {
                    //下一步骤连接线，画一半实线
                    int targetEndX = (lineEndX - lineStartX) /2 + lineStartX;
                    canvas.drawLine(lineStartX, lineStartY, targetEndX, lineEndY, mNextStepLinePaint);
                }
            }

            //画圆形背景
            if (i < mCurrentStepNum) {
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
            canvas.drawText(String.valueOf(i + 1), x, y + getVerticalCenterOffset(mStepNumPaint), mStepNumPaint);
            //画步骤标题
            if (mIsShowTitle) {
                canvas.drawText(mStepContentList.get(i), x, y + titleYOffset, mStepTitlePaint);
            }
        }
    }

    /**
     * 获取文字垂直居中便宜量
     * @param paint 文字画笔
     * @return 偏移量
     */
    private int getVerticalCenterOffset(Paint paint) {
        if (paint != null) {
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();
            return ((fm.descent - fm.ascent) / 2- fm.descent);
        }
        return 0;
    }

    /**
     * 测量文字高度
     * @param paint 文字画笔
     * @return 文字高度
     */
    private int measureTextHeight(Paint paint){
        int height = 0;
        if(null == paint){
            return height;
        }
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        return fm.descent - fm.ascent;
    }

    /**
     * 测量文字宽度
     * @param paint 文字画笔
     * @param text 文字内容
     * @return 文字宽度
     */
    private int measureTextWidth(Paint paint, String text) {
        int width = 0;
        if(null == paint){
            return width;
        }
        return (int) paint.measureText(text);
    }

    /**
     * 获取文字列表中最大的宽度
     * @param list 文字类别
     * @return 最大宽度
     */
    private int getMaxTextWidth(List<String> list) {
        int size = list.size();
        int maxWidth = 0;
        for (int i = 0; i < size; i++) {
            maxWidth = Math.max(maxWidth, measureTextWidth(mStepTitlePaint, list.get(i)));
        }
        return maxWidth;
    }

}
