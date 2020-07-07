package com.genvict.widgets.jmb;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * 自定义菜单按钮
 * @author jayz
 */
public class JMenuButton extends LinearLayout {
    private Context mContext;
    private View mView;
    private ImageView mIcon;
    private TextView mTitle;

    private int iconId;
    private String title = "测试";
    private boolean showIconClickEffect = true;
    final private ColorMatrix colorMatrix = new ColorMatrix(
            new float[] { 0.5F, 0, 0, 0, 0, 0, 0.5F, 0, 0, 0, 0, 0, 0.5F, 0, 0, 0, 0, 0, 1, 0, });

    public JMenuButton(Context context) {
        this(context, null);
    }

    public JMenuButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JMenuButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        if (showIconClickEffect) {
            if (pressed) {
                mIcon.getDrawable().setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            } else {
                mIcon.getDrawable().setColorFilter(null);
            }
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    /**
     * 设置主图标资源ID
     * @param iconId 图片资源ID
     * @return JMenuButton
     */
    public JMenuButton setIconId(int iconId) {
        if (mIcon != null) {
            this.iconId = iconId;
            mIcon.setImageResource(this.iconId);
        }
        return this;
    }

    /**
     * 设置主图标大小
     * @param width 宽度，默认64dp
     * @param height 高度，默认64dp
     * @return JMenuButton
     */
    public JMenuButton setIconSize(int width, int height) {
        if (mIcon != null) {
            ViewGroup.LayoutParams lp = mIcon.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mIcon.setLayoutParams(lp);
        }
        return this;
    }

    /**
     * 设置是否启用图标点击效果
     * @param showEffect true：启用，false：不启用，默认true
     * @return JMenuButton
     */
    public JMenuButton setIconClickEffect(boolean showEffect) {
        showIconClickEffect = showEffect;
        return this;
    }

    /**
     * 设置标题MarginTop
     * @param marginTop 边距值
     * @return JMenuButton
     */
    public JMenuButton setTitleMarginTop(int marginTop) {
        if (mTitle != null) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, marginTop, 0, 0);
            mTitle.setLayoutParams(lp);
        }
        return this;
    }

    /**
     * 设置标题
     * @param title 标题
     * @return JMenuButton
     */
    public JMenuButton setTitle(String title) {
        if (mTitle != null) {
            this.title = title;
            mTitle.setText(this.title);
        }
        return this;
    }

    /**
     * 设置标题文字颜色
     * @param colorId 颜色资源ID
     * @return JMenuButton
     */
    public JMenuButton setTitleTextColor(int colorId) {
        if (mTitle != null) {
            mTitle.setTextColor(colorId);
        }
        return this;
    }

    /**
     * 设置标题文字大小
     * @param size 文字大小
     * @return JMenuButton
     */
    public JMenuButton setTitleTextSize(float size) {
        if (mTitle != null) {
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        return this;
    }

    /**
     * 设置是否显示标题
     * @param isShow true：显示，false：不显示，默认true
     * @return JMenuButton
     */
    public JMenuButton setShowTitle(boolean isShow) {
        if (mTitle != null) {
            if (isShow) {
                mTitle.setVisibility(View.VISIBLE);
            } else {
                mTitle.setVisibility(View.GONE);
            }
        }
        return this;
    }

    /**
     * 初始化
     * @param context 上下文资源
     * @param attrs 属性集合
     */
    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.layout_menu_button, this, true);
        mIcon = mView.findViewById(R.id.img);
        mTitle = mView.findViewById(R.id.title);

        TypedArray ta =  mContext.obtainStyledAttributes(attrs, R.styleable.JMenuButton);
        try {
            setIconId(ta.getResourceId(R.styleable.JMenuButton_icon, R.mipmap.ic_launcher));
            int iconWidth = (int) ta.getDimension(R.styleable.JMenuButton_iconWidth, getResources().getDimension(R.dimen.icon_default_width));
            int iconHeight = (int) ta.getDimension(R.styleable.JMenuButton_iconHeight, getResources().getDimension(R.dimen.icon_default_Height));
            setIconSize(iconWidth, iconHeight);
            setIconClickEffect(ta.getBoolean(R.styleable.JMenuButton_iconClickEffect, true));
            setTitle(ta.getString(R.styleable.JMenuButton_title));
            setTitleTextColor(ta.getColor(R.styleable.JMenuButton_titleTextColor, getResources().getColor(R.color.colorPrimary)));
            setTitleTextSize(ta.getDimensionPixelSize(R.styleable.JMenuButton_titleTextSize, (int) getResources().getDimension(R.dimen.title_text_size)));
            setTitleMarginTop((int) ta.getDimension(R.styleable.JMenuButton_titleMarginTop, 0));
            setShowTitle(ta.getBoolean(R.styleable.JMenuButton_showTitle, true));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }
}
