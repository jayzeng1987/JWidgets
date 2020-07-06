package com.genvict.widgets;

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

    public JMenuButton setIconId(int id) {
        if (mIcon != null) {
            this.iconId = id;
            mIcon.setImageResource(iconId);
        }
        return this;
    }

    public JMenuButton setIconSize(int width, int height) {
        if (mIcon != null) {
            ViewGroup.LayoutParams lp = mIcon.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mIcon.setLayoutParams(lp);
        }
        return this;
    }

    public JMenuButton setIconClickEffect(boolean showEffect) {
        showIconClickEffect = showEffect;
        return this;
    }

    public JMenuButton setTitleMarginTop(int marginTop) {
        if (mTitle != null) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, marginTop, 0, 0);
            mTitle.setLayoutParams(lp);
        }
        return this;
    }

    public JMenuButton setTitle(String title) {
        if (mTitle != null) {
            this.title = title;
            mTitle.setText(this.title);
        }
        return this;
    }

    public JMenuButton setTitleTextColor(int color) {
        if (mTitle != null) {
            mTitle.setTextColor(color);
        }
        return this;
    }

    public JMenuButton setTitleTextSize(float size) {
        if (mTitle != null) {
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        return this;
    }

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

    final ColorMatrix colorMatrix = new ColorMatrix(new float[] { 0.5F, 0, 0, 0, 0, 0, 0.5F, 0, 0, 0, 0, 0, 0.5F, 0, 0, 0, 0, 0, 1, 0, });

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
