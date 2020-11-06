package com.genvict.jsimplestep;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

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
     * 步骤状态枚举
     */
    private enum StepStatus {
        /**
         * 已完成
         */
        FINISH,
        /**
         * 当前正在执行
         */
        DOING,
        /**
         * 未完成
         */
        UNDO
    }

    /**
     * 步骤描述信息
     */
    private class StepDescription {
        /**
         * 图形中心点x坐标
         */
        public int centerX;
        /**
         * 图形中心点y坐标
         */
        public int centerY;
        /**
         * 圆形背景半径
         */
        public int radius;
        /**
         * 状态图形路径
         */
        public Path statusGraphPath = new Path();
        /**
         * 状态线图形路径
         */
        public Path statusLinePath = new Path();
        /**
         * 步骤状态
         */
        public StepStatus status;
        /**
         * 连接线起点x坐标
         */
        public int lineStartX;
        /**
         * 连接线起点y坐标
         */
        public int lineStartY;
        /**
         * 连接线终点x坐标
         */
        public int lineEndX;
        /**
         * 连接线终点y坐标
         */
        public int lineEndY;
    }

    /**
     * 步骤描述信息列表
     */
    private List<StepDescription> mStepDescriptionList = new ArrayList<>();
    /**
     * 标题显示Y轴偏移量
     */
    private int mTitleYOffset;
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
     * 步骤状态图形画笔
     */
    private Paint mStepStatusGraphPaint;
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
     * 连接基线宽度
     */
    private float mBaseStepLineStrokeWidth;
    /**
     * 未完成步骤颜色
     */
    private int mUndoStepColorId;
    /**
     * 当前步骤颜色
     */
    private int mCurrentStepColorId;
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
    /**
     * 步骤状态图形类型 1：序号 2：图形
     */
    private int mStepStatusGraphType;
    /**
     * 步骤状态图形动画
     */
    private ValueAnimator mStatusGraphAnimator;
    /**
     * 状态线动画
     */
    private ValueAnimator mStatusLineAnimator;
    /**
     * 测量路径
     */
    private PathMeasure mStatusGraphMeasure = new PathMeasure();
    /**
     * 测量路径
     */
    private PathMeasure mStatusLineMeasure = new PathMeasure();
    /**
     * 当前正在绘制的图形路径
     */
    private Path mStatusGraphSegPath = new Path();
    /**
     * 当前正在绘制的图形路径
     */
    private Path mStatusLineSegPath = new Path();
    /**
     * 图形动画进度
     */
    private float mStatusGraphProgress;
    /**
     * 状态线动画进度
     */
    private float mStatusLineProgress;
    /**
     * 总进度值
     */
    private static float mTotalProgress = 1.0f;

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
        //初始化动画
        initAnimator();
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
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
        int size = mStepDescriptionList.size();
        for (int i = 0; i < size; i++) {
            StepDescription sd = mStepDescriptionList.get(i);
            initGraphPath(sd, i, mCurrentStepNum);
        }
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
     * 连接基线宽度
     * @param width 宽度
     * @return JSimpleStepView
     */
    public JSimpleStepView setBaseStepLineStrokeWidth(int width) {
        this.mBaseStepLineStrokeWidth = width;
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
     * 设置当前步骤颜色
     * @param colorId 颜色资源ID
     * @return JSimpleStepView
     */
    public JSimpleStepView setCurrentStepColorId(int colorId) {
        this.mCurrentStepColorId = colorId;
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

    /**
     * 设置步骤状态图形类型
     * @param type 步骤状态图形类型 0：序号 1：图形
     * @return JSimpleStepView
     */
    public JSimpleStepView setStepStatusGraphType(int type) {
        if (type < 1 || type > 2) {
            this.mStepStatusGraphType = 1;
        } else {
            this.mStepStatusGraphType = type;
        }
        return this;
    }

    /**
     * 初始化属性参数
     * @param context
     * @param attrs
     */
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
            setBaseStepLineStrokeWidth((int) ta.getDimension(R.styleable.JSimpleStepView_baseStepLineStrokeWidth, getResources().getDimension(R.dimen.baseStepLineDefStrokeWidth)));

            setUndoStepColorId(ta.getColor(R.styleable.JSimpleStepView_undoStepColor, getResources().getColor(R.color.undoStepDefColor)));
            setCurrentStepColorId(ta.getColor(R.styleable.JSimpleStepView_currentStepColor, getResources().getColor(R.color.currentStepDefColor)));
            setFinishStepColorId(ta.getColor(R.styleable.JSimpleStepView_finishStepColor, getResources().getColor(R.color.finishStepDefColor)));
            setFinishStepLineColorId(ta.getColor(R.styleable.JSimpleStepView_finishStepLineColor, getResources().getColor(R.color.finishStepLineDefColor)));
            setBaseStepLineColorId(ta.getColor(R.styleable.JSimpleStepView_baseStepLineColor, getResources().getColor(R.color.baseStepLineDefColor)));
            setNextStepLineColorId(ta.getColor(R.styleable.JSimpleStepView_nextStepLineColor, getResources().getColor(R.color.nextStepLineDefColor)));
            setStepNumTextColorId(ta.getColor(R.styleable.JSimpleStepView_stepNumTextColor, getResources().getColor(R.color.stepNumTextDefColor)));

            setStepStatusGraphType(ta.getInt(R.styleable.JSimpleStepView_stepStatusGraphType, 1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }

    /**
     * 初始化画笔
     */
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
        mBaseStepLinePaint.setStrokeWidth(mBaseStepLineStrokeWidth);
        mBaseStepLinePaint.setColor(mBaseStepLineColorId);
        mBaseStepLinePaint.setStyle(Paint.Style.STROKE);
        mBaseStepLinePaint.setPathEffect(new DashPathEffect(new float[]{8, 7}, 0));
//        Path path = new Path();
//        path.addCircle(0, 0, 3, Path.Direction.CW);
//        mBaseStepLinePaint.setPathEffect(new PathDashPathEffect(path, 14, 0, PathDashPathEffect.Style.ROTATE));

        //已完成步骤连接线
        mFinishStepLinePaint = new Paint();
        mFinishStepLinePaint.setAntiAlias(true);
        mFinishStepLinePaint.setStrokeWidth(mStepLineStrokeWidth);
        mFinishStepLinePaint.setColor(mFinishStepLineColorId);

        //下一步骤连接线
        mNextStepLinePaint = new Paint();
        mNextStepLinePaint.setStyle(Paint.Style.STROKE);
        mNextStepLinePaint.setAntiAlias(true);
        mNextStepLinePaint.setStrokeWidth(mStepLineStrokeWidth);
        mNextStepLinePaint.setColor(mNextStepLineColorId);
        mNextStepLinePaint.setStrokeCap(Paint.Cap.ROUND);

        //步骤图形状态画笔
        mStepStatusGraphPaint = new Paint();
        mStepStatusGraphPaint.setStyle(Paint.Style.STROKE);
        mStepStatusGraphPaint.setAntiAlias(true);
        mStepStatusGraphPaint.setStrokeWidth(mStepLineStrokeWidth);
        mStepStatusGraphPaint.setColor(Color.WHITE);
        mStepStatusGraphPaint.setStrokeCap(Paint.Cap.ROUND);
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //标题Y坐标相对于圆形Y坐标的偏移量
        mTitleYOffset = mStepCircleRadius + measureTextHeight(mStepTitlePaint) + mStepTitleTopSpace;

        //每个步骤项的宽度
        int stepItemWidth = mViewWidth / mStepItemSize;
        //保存每个步骤圆形图形的x，y中心点、半径和步骤状态
        for (int i = 0; i < mStepItemSize; i++) {
            StepDescription sd = new StepDescription();
            sd.centerX = (stepItemWidth/2) * (2 * i + 1);
            sd.centerY = (mViewHeight - mStepTitleHeight) / 2;
            //计算连接线起始坐标
            sd.lineStartX = sd.centerX + mStepCircleRadius;
            sd.lineStartY = sd.centerY;
            //计算连接线终点坐标
            sd.lineEndX = (stepItemWidth - 2 * mStepCircleRadius) + sd.lineStartX;
            sd.lineEndY = sd.lineStartY;
            initGraphPath(sd, i, mCurrentStepNum);
            mStepDescriptionList.add(sd);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mStatusGraphAnimator.cancel();
        mStatusLineAnimator.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mStepItemSize == 0) {
            return;
        }
        int circleRadius = mStepCircleRadius;
        for (int i = 0; i < mStepDescriptionList.size(); i++) {
            StepDescription sd = mStepDescriptionList.get(i);
            switch (sd.status) {
                case FINISH:
                    mStepCirclePaint.setColor(mFinishStepColorId);
                    circleRadius = mStepCircleRadius;
                    break;
                case DOING:
                    mStepCirclePaint.setColor(mCurrentStepColorId);
                    circleRadius = (int) (mStepCircleRadius * 1.2);
                    break;
                case UNDO:
                    mStepCirclePaint.setColor(mUndoStepColorId);
                    circleRadius = mStepCircleRadius;
                    break;
            }

            //画连接线
            drawLinkLine(canvas, sd, i, mCurrentStepNum);
            //画圆
            canvas.drawCircle(sd.centerX, sd.centerY, circleRadius, mStepCirclePaint);
            if (mStepStatusGraphType == 1) {
                //画步骤序号
                canvas.drawText(String.valueOf(i + 1), sd.centerX, sd.centerY + getVerticalCenterOffset(mStepNumPaint), mStepNumPaint);
            } else if (mStepStatusGraphType == 2) {
                //画图形
                drawStepGraph(canvas, sd);
            }
            //画步骤标题
            if (mIsShowTitle) {
                canvas.drawText(mStepContentList.get(i), sd.centerX, sd.centerY + mTitleYOffset, mStepTitlePaint);
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

    private void initAnimator() {
        mStatusGraphAnimator = ValueAnimator.ofFloat(0, mTotalProgress);
        mStatusGraphAnimator.setDuration(900);
        mStatusGraphAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStatusGraphProgress = (float) animation.getAnimatedValue();
//                mStatusLineProgress = 0;
                Log.i("genvict", "onAnimationUpdate: mStatusGraphProgress" + mStatusGraphProgress);
                invalidate();
            }
        });
        mStatusGraphAnimator.setInterpolator(new DecelerateInterpolator());
        mStatusGraphAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mStatusLineAnimator.isStarted()) {
                    mStatusLineAnimator.cancel();
                }
                mStatusLineAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mStatusLineAnimator.cancel();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mStatusLineAnimator = ValueAnimator.ofFloat(0, mTotalProgress);
        mStatusLineAnimator.setDuration(900);
        mStatusLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStatusLineProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    /**
     * 初始化图形路径
     * @param sd 步骤描述
     * @param index 步骤索引
     * @param currentStepNum 当前步骤
     */
    private void initGraphPath(StepDescription sd, int index, int currentStepNum) {
        int tmp = index + 1;
        if (tmp < currentStepNum) {
            sd.radius = mStepCircleRadius;
            sd.status = StepStatus.FINISH;
            int p1x = (int) (sd.centerX - 0.8 * sd.radius / 2), p1y = sd.centerY;
            int p2x = sd.centerX, p2y = (int) (sd.centerY + 0.7 * sd.radius / 2);
            int p3x = sd.centerX + sd.radius / 2, p3y = sd.centerY - sd.radius / 2;

            Path path = new Path();
            path.moveTo(p1x, p1y);
            path.lineTo(p2x, p2y);
            path.lineTo(p3x, p3y);
            sd.statusGraphPath.set(path);

        } else if (tmp == mCurrentStepNum) {
            sd.radius = (int) (mStepCircleRadius * 1.2);
            sd.status = StepStatus.DOING;
            int p1x = (int) (sd.centerX - 0.8 * sd.radius / 2), p1y = sd.centerY;
            int p2x = sd.centerX, p2y = (int) (sd.centerY + 0.7 * sd.radius / 2);
            int p3x = sd.centerX + sd.radius / 2, p3y = sd.centerY - sd.radius / 2;

            Path path = new Path();
            path.moveTo(p1x, p1y);
            path.lineTo(p2x, p2y);
            path.lineTo(p3x, p3y);
            sd.statusGraphPath.set(path);
            mStatusGraphMeasure.setPath(sd.statusGraphPath, false);

            Path linePath = new Path();
            linePath.moveTo(sd.lineStartX, sd.lineStartY);
            int targetEndX = (sd.lineEndX - sd.lineStartX) /2 + sd.lineStartX;
            linePath.lineTo(targetEndX, sd.lineEndY);
            sd.statusLinePath.set(linePath);
            mStatusLineMeasure.setPath(linePath, false);

            if (mStepStatusGraphType == 1) {
                if (mStatusLineAnimator.isStarted()) {
                    mStatusLineAnimator.cancel();
                }
                mStatusLineAnimator.start();
            } else {
                if (mStatusGraphAnimator.isStarted()) {
                    mStatusGraphAnimator.cancel();
                }
                mStatusGraphAnimator.start();
            }

        } else {
            sd.radius = mStepCircleRadius;
            sd.status = StepStatus.UNDO;
            int p1x = sd.centerX, p1y = sd.centerY - sd.radius * 1/2;
            int p2x = sd.centerX, p2y = sd.centerY + sd.radius * 1/6;
            int p3x = sd.centerX, p3y = sd.centerY + sd.radius * 1/2;

            Path path=new Path();
            path.moveTo(p1x, p1y);
            path.lineTo(p2x, p2y);
            path.moveTo(p3x, p3y);
            path.lineTo(p3x, p3y + 1);
            sd.statusGraphPath.set(path);
        }
    }

    /**
     * 画步骤图形
     * @param canvas 画布
     * @param sd 步骤描述
     */
    private void drawStepGraph(Canvas canvas, StepDescription sd) {
        if (sd.status == StepStatus.DOING) {
            mStatusGraphSegPath.reset();
            mStatusGraphMeasure.getSegment(0, mStatusGraphProgress * mStatusGraphMeasure.getLength(), mStatusGraphSegPath, true);
            canvas.drawPath(mStatusGraphSegPath, mStepStatusGraphPaint);
        } else {
            canvas.drawPath(sd.statusGraphPath, mStepStatusGraphPaint);
        }
    }

    /**
     * 画连接线
     * @param canvas 画布
     * @param sd 步骤描述
     * @param index 步骤索引
     * @param currentStepNum 当前步骤
     */
    private void drawLinkLine(Canvas canvas, StepDescription sd, int index, int currentStepNum) {
        if (index < mStepItemSize - 1) {
            //未完成步骤连接线（点线）
            canvas.drawLine(sd.lineStartX, sd.lineStartY, sd.lineEndX, sd.lineEndY, mBaseStepLinePaint);

            if (index < currentStepNum - 1) {
                //已完成步骤连接线
                canvas.drawLine(sd.lineStartX, sd.lineStartY, sd.lineEndX, sd.lineEndY, mFinishStepLinePaint);
            } else if (index == currentStepNum - 1) {
                //下一步骤连接线，画一半实线
//                int targetEndX = (sd.lineEndX - sd.lineStartX) /2 + sd.lineStartX;
//                canvas.drawLine(sd.lineStartX, sd.lineStartY, targetEndX, sd.lineEndY, mNextStepLinePaint);
                if (mStatusGraphProgress == mTotalProgress || mStepStatusGraphType == 1) {
                    //当mStepStatusGraphType为显示序号类型时，无须等待mStatusGraphProgress到1，直接处理mStatusLineProgress
                    //当mStepStatusGraphType为显示图形时，mStatusGraphProgress完成后才会执行mStatusLineProgress的处理
                    mStatusLineSegPath.reset();
                    mStatusLineMeasure.getSegment(0, mStatusLineProgress * mStatusLineMeasure.getLength(), mStatusLineSegPath, true);
                    canvas.drawPath(mStatusLineSegPath, mNextStepLinePaint);
                }
            }
        }
    }
}
