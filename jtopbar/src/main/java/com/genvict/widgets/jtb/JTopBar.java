package com.genvict.widgets.jtb;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 自定义顶部标题栏
 * @author jayz
 */
public class JTopBar extends ConstraintLayout {
    private Context mContext;
    private View mRootView;
    private ConstraintLayout mWrapView;
    private LinearLayout mLeftItem;
    private LinearLayout mRightItem;

    private ImageView mLeftIcon;
    private ImageView mRightIcon;
    private TextView mTvLeftTitle;
    private TextView mTvMainTitle;
    private TextView mTvSubTitle;

    private boolean mLeftItemIsShow;
    private boolean mRightItemIsShow;

    final private ColorMatrix colorMatrix = new ColorMatrix(
            new float[] { 0.5F, 0, 0, 0, 0, 0, 0.5F, 0, 0, 0, 0, 0, 0.5F, 0, 0, 0, 0, 0, 1, 0, });

    @Override
    public void dispatchSetSelected(boolean selected) {
        super.dispatchSetSelected(selected);
    }

    public JTopBar(Context context) {
        this(context, null);
    }

    public JTopBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JTopBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    /**
     * 设置左侧图标
     * @param iconId 图标资源ID
     * @return JTopBar
     */
    public JTopBar setLeftIconId(int iconId) {
        if (mLeftIcon != null) {
            mLeftIcon.setImageResource(iconId);
        }
        return this;
    }

    /**
     * 设置右侧图标
     * @param iconId 图标资源ID
     * @return JTopBar
     */
    public JTopBar setRightIconId(int iconId) {
        if (mRightIcon != null) {
            mRightIcon.setImageResource(iconId);
        }
        return this;
    }

    /**
     * 设置左侧图标尺寸
     * @param width 宽度
     * @param height 高度
     * @return JTopBar
     */
    public JTopBar setLeftIconSize(int width, int height) {
        if (mLeftIcon != null) {
            ViewGroup.LayoutParams lp = mLeftIcon.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mLeftIcon.setLayoutParams(lp);
        }
        return this;
    }

    /**
     * 设置右侧图标尺寸
     * @param width 宽度
     * @param height 高度
     * @return JTopBar
     */
    public JTopBar setRightIconSize(int width, int height) {
        if (mRightIcon != null) {
            ViewGroup.LayoutParams lp = mRightIcon.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mRightIcon.setLayoutParams(lp);
        }
        return this;
    }

    /**
     * 设置左侧标题
     * @param title 标题
     * @return JTopBar
     */
    public JTopBar setLeftTitle(String title) {
        if (mTvLeftTitle != null) {
            mTvLeftTitle.setText(title);
        }
        return this;
    }

    /**
     * 设置中间主标题
     * @param title 标题
     * @return JTopBar
     */
    public JTopBar setMainTitle(String title) {
        if (mTvMainTitle != null) {
            mTvMainTitle.setText(title);
        }
        return this;
    }

    /**
     * 设置中间子标题
     * @param title 标题
     * @return JTopBar
     */
    public JTopBar setSubTitle(String title) {
        if (mTvSubTitle != null) {
            mTvSubTitle.setText(title);
        }
        return this;
    }

    /**
     * 设置标题文字颜色
     * @param colorId 颜色资源ID
     * @return JTopBar
     */
    public JTopBar setTitleTextColor(int colorId) {
        if (mTvLeftTitle != null && mTvMainTitle != null && mTvSubTitle != null) {
            mTvLeftTitle.setTextColor(colorId);
            mTvSubTitle.setTextColor(colorId);
            mTvMainTitle.setTextColor(colorId);
        }
        return this;
    }

    /**
     * 设置左标题文字大小
     * @param size 大小
     * @return JTopBar
     */
    public JTopBar setLeftTitleTextSize(int size) {
        if (mTvLeftTitle != null) {
            mTvLeftTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        return this;
    }

    /**
     * 设置中间主标题文字大小
     * @param size 大小
     * @return JTopBar
     */
    public JTopBar setMainTitleTextSize(int size) {
        if (mTvMainTitle != null) {
            mTvMainTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        return this;
    }

    /**
     * 设置中间子标题文字大小
     * @param size 大小
     * @return JTopBar
     */
    public JTopBar setSubTitleTextSize(int size) {
        if (mTvSubTitle != null) {
            mTvSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        return this;
    }

    /**
     * 设置是否显示左标题
     * @param isShow true：显示，false：不显示（默认false）
     * @return JTopBar
     */
    public JTopBar setShowLeftTitle(boolean isShow) {
        if (mTvLeftTitle != null) {
            mTvLeftTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    /**
     * 设置是否显示左边图标和标题
     * @param isShow true：显示，false：不显示（默认true）
     * @return JTopBar
     */
    public JTopBar setShowLeftItem(boolean isShow) {
        if (mLeftItem != null) {
            mLeftItemIsShow = isShow;
            mLeftItem.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    /**
     * 设置是否显示右图标
     * @param isShow true：显示，false：不显示（默认false）
     * @return JTopBar
     */
    public JTopBar setShowRightItem(boolean isShow) {
        if (mRightItem != null) {
            mRightItemIsShow = isShow;
            mRightItem.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    /**
     * 设置是否显示中间子标题
     * @param isShow true：显示，false：不显示（默认false）
     * @return JTopBar
     */
    public JTopBar setShowSubTitle(boolean isShow) {
        if (mTvSubTitle != null) {
            mTvSubTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    /**
     * 设置标题栏高度
     * @param height 高度
     * @return JTopBar
     */
    public JTopBar setTopBarHeight(int height) {
        if (mWrapView != null) {
            ViewGroup.LayoutParams lp = mWrapView.getLayoutParams();
            lp.height = height;
            mWrapView.setLayoutParams(lp);
        }
        return this;
    }

    /**
     * 设置标题栏背景色
     * @param colorId 颜色资源ID
     * @return JTopBar
     */
    public JTopBar setTopBarBgColor(int colorId) {
        if (mWrapView != null) {
            mWrapView.setBackgroundColor(colorId);
        }
        return this;
    }

    /**
     * 设置左图标点击事件
     * @param listener OnClickListener点击事件
     * @return JTopBar
     */
    public JTopBar setLeftIconClickListener(View.OnClickListener listener) {
        if (mLeftIcon != null && mLeftItemIsShow) {
            mLeftIcon.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 设置右图标点击事件
     * @param listener OnClickListener点击事件
     * @return JTopBar
     */
    public JTopBar setRightIconClickListener(View.OnClickListener listener) {
        if (mRightIcon != null && mRightItemIsShow) {
            mRightIcon.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 初始化
     * @param context 上下文资源
     * @param attrs 属性集合
     */
    private void init(Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = inflater.inflate(R.layout.layout_topbar, this, true);
        mWrapView = mRootView.findViewById(R.id.view_wrap);
        mLeftItem = mRootView.findViewById(R.id.ll_left);
        mRightItem = mRootView.findViewById(R.id.ll_right);
        mLeftIcon = mRootView.findViewById(R.id.icon_left);
        mRightIcon = mRootView.findViewById(R.id.icon_right);
        mTvLeftTitle = mRootView.findViewById(R.id.tv_left_title);
        mTvMainTitle = mRootView.findViewById(R.id.tv_main_title);
        mTvSubTitle = mRootView.findViewById(R.id.tv_sub_title);

        TypedArray ta =  mContext.obtainStyledAttributes(attrs, R.styleable.JTopBar);
        try {
            setLeftIconId(ta.getResourceId(R.styleable.JTopBar_leftIconId, R.mipmap.back));
            int iconWidth = (int) ta.getDimension(R.styleable.JTopBar_leftIconWidth, getResources().getDimension(R.dimen.JTopBarIconDefWidth));
            int iconHeight = (int) ta.getDimension(R.styleable.JTopBar_leftIconHeight, getResources().getDimension(R.dimen.JTopBarIconDefHeight));
            setLeftIconSize(iconWidth, iconHeight);

            setRightIconId(ta.getResourceId(R.styleable.JTopBar_rightIconId, R.mipmap.more));
            iconWidth = (int) ta.getDimension(R.styleable.JTopBar_rightIconWidth, getResources().getDimension(R.dimen.JTopBarIconDefWidth));
            iconHeight = (int) ta.getDimension(R.styleable.JTopBar_rightIconHeight, getResources().getDimension(R.dimen.JTopBarIconDefHeight));
            setRightIconSize(iconWidth, iconHeight);

            setLeftTitle(ta.getString(R.styleable.JTopBar_leftTitle));
            setMainTitle(ta.getString(R.styleable.JTopBar_mainTitle));
            setSubTitle(ta.getString(R.styleable.JTopBar_subTitle));

            setTitleTextColor(ta.getColor(R.styleable.JTopBar_textColor, getResources().getColor(R.color.JTopBarDefTextColor)));
            setLeftTitleTextSize(ta.getDimensionPixelSize(R.styleable.JTopBar_leftTitleTextSize, (int) getResources().getDimension(R.dimen.JTopBarLeftTitleDefTextSize)));
            setMainTitleTextSize(ta.getDimensionPixelSize(R.styleable.JTopBar_mainTitleTextSize, (int) getResources().getDimension(R.dimen.JTopBarMainTitleDefTextSize)));
            setSubTitleTextSize(ta.getDimensionPixelSize(R.styleable.JTopBar_leftTitleTextSize, (int) getResources().getDimension(R.dimen.JTopBarLeftTitleDefTextSize)));

            setShowLeftTitle(ta.getBoolean(R.styleable.JTopBar_showLeftTitle, false));
            setShowLeftItem(ta.getBoolean(R.styleable.JTopBar_showLeftItem, true));
            setShowRightItem(ta.getBoolean(R.styleable.JTopBar_showRightItem, false));
            setShowSubTitle(ta.getBoolean(R.styleable.JTopBar_showSubTitle, false));

            int topbarHeight = (int) ta.getDimension(R.styleable.JTopBar_topbarHeight, getResources().getDimension(R.dimen.JTopBarDefHeight));
            setTopBarHeight(topbarHeight);

            setTopBarBgColor(ta.getColor(R.styleable.JTopBar_topbarBgColor, getResources().getColor(R.color.JTopBarDefBgColor)));

            setItemClickEffect(mLeftIcon);
            setItemClickEffect(mRightIcon);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }

    }

    private void setItemClickEffect(final ImageView view) {
        if (view != null) {
            view.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            view.getDrawable().setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                            break;
                        case MotionEvent.ACTION_MOVE:
                        case MotionEvent.ACTION_UP:
                            view.getDrawable().setColorFilter(null);
                            break;
                        default:
                    }
                    return false;
                }
            });
        }
    }
}
