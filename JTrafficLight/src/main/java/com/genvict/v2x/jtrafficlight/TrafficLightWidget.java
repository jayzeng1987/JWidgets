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
    private Context mContext;
    private View mView;
    private ImageView mIvTrafficLight;
    private int mIconId;
    private int mDuration = 10;
    private LightStatus curLightStatus = LightStatus.CLOSE;
    private boolean flashFlag = false;
    final private Handler handler;

    public enum LightStatus {
        CLOSE,
        BRIGHT,
        FLASH,
    }

    public interface LightCallback {
        void onFlashStop();
        void onBrightStop();
    }

    private final static int MSG_START = 0x01;
    private final static int MSG_STOP = 0x02;

    public TrafficLightWidget(Context context) {
        this(context, null);
    }

    public TrafficLightWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrafficLightWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_START:
                        if (animation != null) {
                            animation.start();
                        }
                        break;
                    case MSG_STOP:
                        stop();
                        break;
                    default:
                }
            }
        };
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
        mIvTrafficLight = mView.findViewById(R.id.iv_traffic_light);
        TypedArray ta =  mContext.obtainStyledAttributes(attrs, R.styleable.JTrafficLight);
        try {
            setIconId(ta.getResourceId(R.styleable.JTrafficLight_icon, R.mipmap.traffic_light_close));
            int iconWidth = (int) ta.getDimension(R.styleable.JTrafficLight_iconWidth, getResources().getDimension(R.dimen.icon_default_width));
            int iconHeight = (int) ta.getDimension(R.styleable.JTrafficLight_iconHeight, getResources().getDimension(R.dimen.icon_default_Height));
            setIconSize(iconWidth, iconHeight);
            setMargin((int) ta.getDimension(R.styleable.JTrafficLight_iconMargin, 0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }

    /**
     * 设置图标资源ID
     * @param iconId 图片资源ID
     * @return TrafficLightWidget
     */
    public TrafficLightWidget setIconId(int iconId) {
        if (mIvTrafficLight != null && iconId > 0) {
            this.mIconId = iconId;
//            mIvTrafficLight.setImageResource(mIconId);
        }
        return this;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 设置图标大小
     * @param width 宽度，默认96dp
     * @param height 高度，默认96dp
     * @return JMenuButton
     */
    public TrafficLightWidget setIconSize(int width, int height) {
        if (mIvTrafficLight != null) {
            ViewGroup.LayoutParams lp = mIvTrafficLight.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mIvTrafficLight.setLayoutParams(lp);
        }
        return this;
    }

    /**
     * 设置图标Margin
     * @param margin 边距值
     * @return TrafficLightWidget
     */
    public TrafficLightWidget setMargin(int margin) {
        if (mIvTrafficLight != null) {
            LayoutParams lp = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.setMargins(margin, margin, margin, margin);
            mIvTrafficLight.setLayoutParams(lp);
        }
        return this;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setCurLightStatus(LightStatus status) {
        this.curLightStatus = status;
    }

    private LightCallback callback;
    public void setLightCallback(LightCallback callback) {
        this.callback = callback;
    }

    private volatile boolean runFlag = true;
    private long startTime = 0;
    private long currentTime = 0;
    private long passTime = 0;
    private AnimationDrawable animation;
    public void start(){
        animation = new AnimationDrawable();
        switch (curLightStatus) {
            case FLASH:
                Drawable flashLightDrawable = getResources().getDrawable(mIconId);
                animation.addFrame(flashLightDrawable, 1000);
                Drawable closeLightDrawable = getResources().getDrawable(R.mipmap.traffic_light_close);
                animation.addFrame(closeLightDrawable, 500);
                animation.setOneShot(false);
                mIvTrafficLight.setImageDrawable(animation);
                animation.start();
                break;
            case BRIGHT:
//                Drawable brightLightDrawable = getResources().getDrawable(mIconId);
//                animation.addFrame(brightLightDrawable, 1000);
                mIvTrafficLight.setImageResource(mIconId);
                invalidate();
                break;
            case CLOSE:
                break;
            default:
        }



        startTime = System.currentTimeMillis();
        new Thread(() -> {
            runFlag = true;
            while (runFlag) {
                currentTime = System.currentTimeMillis();
                passTime = currentTime - startTime;
                if (passTime >= mDuration) {
                    runFlag = false;
                    Message message = new Message();
                    message.what = MSG_STOP;
                    handler.sendMessage(message);
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public void stop(){
        if (animation != null) {
            animation.stop();
        }
        runFlag = false;
        mIvTrafficLight.setImageResource(R.mipmap.traffic_light_close);
        Log.i("genvict", "stop !!!!");
        if (callback != null) {
            switch (curLightStatus) {
                case BRIGHT:
                    callback.onBrightStop();
                    break;
                case FLASH:
                    callback.onFlashStop();
                    break;
                default:
            }
        }
    }

}
