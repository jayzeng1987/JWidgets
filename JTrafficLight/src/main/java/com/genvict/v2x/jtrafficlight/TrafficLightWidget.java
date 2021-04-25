package com.genvict.v2x.jtrafficlight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TrafficLightWidget extends LinearLayout {
    private final static String TAG = "genvict";
    private final static int MSG_START = 0x01;
    private final static int MSG_FINISH = 0x02;

    private Context mContext;
    private View mView;
    private ImageView mIvTrafficLightRed, mIvTrafficLightYellow, mIvTrafficLightGreen, mCurLightView;
    private int mRedLightIconId, mYellowLightIconId, mGreenLightIconId;

    private Handler mHandler;


    private volatile boolean mRunFlag = true;
    private volatile boolean mTimeoutFlag = true;
    private long mStartTime = 0;
    private long mCurrentTime = 0;
    private long mPassTime = 0;

    private LightDuration mLightDuration = new LightDuration();
    private int mCurLightDuration = 10000; //ms
    private AnimationDrawable mAnimation;

    private LightStatus mCurLightStatus = LightStatus.CLOSE;
    private LightStatus mNextLightStatus = LightStatus.CLOSE;

    public static final class LightDuration {
        public int redLightBright = 10000;
        public int redLightFlash = 10000;
        public int yellowLightBright = 10000;
        public int yellowLightFlash = 10000;
        public int greenLightBright = 10000;
        public int greenLightFlash = 10000;
    }

    public enum LightStatus {
        /**
         * 关闭
         */
        CLOSE,
        /**
         * 红灯亮
         */
        RED_BRIGHT,
        /**
         * 红灯闪
         */
        RED_FLASH,
        /**
         * 黄灯亮
         */
        YELLOW_BRIGHT,
        /**
         * 黄灯闪
         */
        YELLOW_FLASH,
        /**
         * 绿灯亮
         */
        GREEN_BRIGHT,
        /**
         * 绿灯闪
         */
        GREEN_FLASH,
    }


    public TrafficLightWidget(Context context) {
        this(context, null);
    }

    public TrafficLightWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrafficLightWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    /**
     * 初始化
     * @param context 上下文资源
     * @param attrs 属性集合
     */
    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.layout_traffic_light, this, true);
        mIvTrafficLightRed = mView.findViewById(R.id.iv_traffic_light_red);
        mIvTrafficLightYellow = mView.findViewById(R.id.iv_traffic_light_yellow);
        mIvTrafficLightGreen = mView.findViewById(R.id.iv_traffic_light_green);
        TypedArray ta =  mContext.obtainStyledAttributes(attrs, R.styleable.JTrafficLight);
        try {
            setRedLightIconId(ta.getResourceId(R.styleable.JTrafficLight_redLightIcon, R.mipmap.traffic_light_close));
            setYellowLightIconId(ta.getResourceId(R.styleable.JTrafficLight_yellowLightIcon, R.mipmap.traffic_light_close));
            setGreenLightIconId(ta.getResourceId(R.styleable.JTrafficLight_greenLightIcon, R.mipmap.traffic_light_close));
            int iconWidth = (int) ta.getDimension(R.styleable.JTrafficLight_iconWidth, getResources().getDimension(R.dimen.icon_default_width));
            int iconHeight = (int) ta.getDimension(R.styleable.JTrafficLight_iconHeight, getResources().getDimension(R.dimen.icon_default_Height));
            setLightSize(iconWidth, iconHeight);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_START:
                        mCurLightStatus = LightStatus.RED_BRIGHT;
                        startAnim(mCurLightStatus);
                        break;
                    case MSG_FINISH:
                        finishAnim();
                        break;
                    default:
                }
            }
        };
    }

    /**
     * 开始运行
     */
    public void start() {
        mCurLightStatus = LightStatus.RED_BRIGHT;
        startAnim(mCurLightStatus);
        new Thread(() -> {
            mRunFlag = true;
            while (mRunFlag) {
                mStartTime = System.currentTimeMillis();
                mTimeoutFlag = false;
                while(!mTimeoutFlag) {
                    mCurrentTime = System.currentTimeMillis();
                    mPassTime = mCurrentTime - mStartTime;
                    if (mPassTime >= mCurLightDuration) {
                        mTimeoutFlag = true;

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Message message = new Message();
                        message.what = MSG_FINISH;
                        mHandler.sendMessage(message);
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 停止运行
     */
    public void stop() {
        if (mAnimation != null) {
            mAnimation.stop();
        }
        mTimeoutFlag = true;
        mRunFlag = false;
        if (mIvTrafficLightRed != null) {
            mIvTrafficLightRed.setImageResource(R.mipmap.traffic_light_close);
        }
        if (mIvTrafficLightGreen != null) {
            mIvTrafficLightGreen.setImageResource(R.mipmap.traffic_light_close);
        }
        if (mIvTrafficLightYellow != null) {
            mIvTrafficLightYellow.setImageResource(R.mipmap.traffic_light_close);
        }
    }

    /**
     * 设置红灯图片资源ID
     * @param iconId 图片资源ID
     * @return TrafficLightWidget
     */
    public TrafficLightWidget setRedLightIconId(int iconId) {
        if (mIvTrafficLightRed != null && iconId > 0) {
            this.mRedLightIconId = iconId;
        }
        return this;
    }

    /**
     * 设置黄灯图片资源ID
     * @param iconId 图片资源ID
     * @return TrafficLightWidget
     */
    public TrafficLightWidget setYellowLightIconId(int iconId) {
        if (mIvTrafficLightYellow != null && iconId > 0) {
            this.mYellowLightIconId = iconId;
        }
        return this;
    }

    /**
     * 设置绿灯图片资源ID
     * @param iconId 图片资源ID
     * @return TrafficLightWidget
     */
    public TrafficLightWidget setGreenLightIconId(int iconId) {
        if (mIvTrafficLightGreen != null && iconId > 0) {
            this.mGreenLightIconId = iconId;
        }
        return this;
    }

    /**
     * 设置灯尺寸
     * @param width 宽度
     * @param height 高度
     * @return TrafficLightWidget
     */
    public TrafficLightWidget setLightSize(int width, int height) {
        if (mIvTrafficLightRed != null) {
            ViewGroup.LayoutParams lp = mIvTrafficLightRed.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mIvTrafficLightRed.setLayoutParams(lp);
        }
        if (mIvTrafficLightYellow != null) {
            ViewGroup.LayoutParams lp = mIvTrafficLightYellow.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mIvTrafficLightYellow.setLayoutParams(lp);
        }
        if (mIvTrafficLightGreen != null) {
            ViewGroup.LayoutParams lp = mIvTrafficLightGreen.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mIvTrafficLightGreen.setLayoutParams(lp);
        }
        return this;
    }

    /**
     * 设置各灯状态持续时长
     * @param lightDuration
     */
    public void setLightDuration(LightDuration lightDuration) {
        if (lightDuration != null) {
            mLightDuration.redLightBright = lightDuration.redLightBright;
            mLightDuration.redLightFlash = lightDuration.redLightFlash;
            mLightDuration.yellowLightBright = lightDuration.yellowLightBright;
            mLightDuration.yellowLightFlash = lightDuration.yellowLightFlash;
            mLightDuration.greenLightBright = lightDuration.greenLightBright;
            mLightDuration.greenLightFlash = lightDuration.greenLightFlash;
        }
    }

    /**
     * 设置不显示黄灯
     */
    public void setNoShowYellowLight() {
        if (mIvTrafficLightYellow != null) {
            mIvTrafficLightYellow.setVisibility(View.GONE);
            mLightDuration.yellowLightFlash = 0;
            mLightDuration.yellowLightBright = 0;
        }
    }

    /**
     * 获取当前灯的
     * @return
     */
    public LightStatus getCurLightStatus() {
        return mCurLightStatus;
    }

    /**
     * 根据灯状态执行动画
     * @param lightStatus 灯状态
     */
    private void startAnim(LightStatus lightStatus) {
        mAnimation = new AnimationDrawable();
        Drawable flashLightDrawable, closeLightDrawable;
        mCurLightStatus = lightStatus;
        switch (lightStatus) {
            case RED_FLASH:
                mCurLightDuration = mLightDuration.redLightFlash;
                mCurLightView = mIvTrafficLightRed;
                flashLightDrawable = getResources().getDrawable(mRedLightIconId);
                mAnimation.addFrame(flashLightDrawable, 500);
                closeLightDrawable = getResources().getDrawable(R.mipmap.traffic_light_close);
                mAnimation.addFrame(closeLightDrawable, 500);
                mAnimation.setOneShot(false);
                mIvTrafficLightRed.setImageDrawable(mAnimation);
                mAnimation.start();
                break;
            case RED_BRIGHT:
                mCurLightDuration = mLightDuration.redLightBright;
                mCurLightView = mIvTrafficLightRed;
                mIvTrafficLightRed.setImageResource(mRedLightIconId);
                invalidate();
                break;
            case YELLOW_FLASH:
                mCurLightDuration = mLightDuration.yellowLightFlash;
                mCurLightView = mIvTrafficLightYellow;
                flashLightDrawable = getResources().getDrawable(mYellowLightIconId);
                mAnimation.addFrame(flashLightDrawable, 500);
                closeLightDrawable = getResources().getDrawable(R.mipmap.traffic_light_close);
                mAnimation.addFrame(closeLightDrawable, 500);
                mAnimation.setOneShot(false);
                mIvTrafficLightYellow.setImageDrawable(mAnimation);
                mAnimation.start();
                break;
            case YELLOW_BRIGHT:
                mCurLightDuration = mLightDuration.yellowLightBright;
                mCurLightView = mIvTrafficLightYellow;
                mIvTrafficLightYellow.setImageResource(mYellowLightIconId);
                invalidate();
                break;
            case GREEN_FLASH:
                mCurLightDuration = mLightDuration.greenLightFlash;
                mCurLightView = mIvTrafficLightGreen;
                flashLightDrawable = getResources().getDrawable(mGreenLightIconId);
                mAnimation.addFrame(flashLightDrawable, 500);
                closeLightDrawable = getResources().getDrawable(R.mipmap.traffic_light_close);
                mAnimation.addFrame(closeLightDrawable, 500);
                mAnimation.setOneShot(false);
                mIvTrafficLightGreen.setImageDrawable(mAnimation);
                mAnimation.start();
                break;
            case GREEN_BRIGHT:
                mCurLightDuration = mLightDuration.greenLightBright;
                mCurLightView = mIvTrafficLightGreen;
                mIvTrafficLightGreen.setImageResource(mGreenLightIconId);
                invalidate();
                break;
            case CLOSE:
                break;
            default:
        }
        mAnimation.start();
    }

    /**
     * 动画结束
     */
    private void finishAnim() {
        Log.i(TAG, "finish, curLightStatus: " + mCurLightStatus);
        switch (mCurLightStatus) {
            case RED_FLASH:
                mNextLightStatus = LightStatus.GREEN_BRIGHT;
                break;
            case RED_BRIGHT:
                mNextLightStatus = LightStatus.RED_FLASH;
                break;
            case YELLOW_FLASH:
                mNextLightStatus = LightStatus.RED_BRIGHT;
                break;
            case YELLOW_BRIGHT:
                mNextLightStatus = LightStatus.YELLOW_FLASH;
                break;
            case GREEN_FLASH:
                mNextLightStatus = LightStatus.YELLOW_BRIGHT;
                break;
            case GREEN_BRIGHT:
                mNextLightStatus = LightStatus.GREEN_FLASH;
                break;
            default:
        }
        if (mAnimation != null && mAnimation.isRunning() && mCurLightView != null) {
            mAnimation.stop();
            mCurLightView.setImageResource(R.mipmap.traffic_light_close);
            invalidate();
        }
        //根据下一次灯状态执行动画
        startAnim(mNextLightStatus);
    }

}
