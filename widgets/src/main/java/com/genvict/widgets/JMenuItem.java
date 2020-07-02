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
 * 自定义菜单图标
 * @author jayz
 */
public class JMenuItem extends LinearLayout {
    private Context mContext;
    private View mView;
    private ImageView mImageView;
    private TextView mTvTitle;

    private int iconId;
    private String title = "测试";
    private boolean showIconClickEffect = true;

    public JMenuItem(Context context) {
        this(context, null);
    }

    public JMenuItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JMenuItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public JMenuItem setIconId(int id) {
        this.iconId = id;
        mImageView.setImageResource(iconId);
        return this;
    }

    public JMenuItem setIconSize(int width, int height) {
        if (mImageView != null) {
            ViewGroup.LayoutParams lp = mImageView.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mImageView.setLayoutParams(lp);
        }
        return this;
    }

    public JMenuItem setIconClickEffect(boolean showEffect) {
        showIconClickEffect = showEffect;
        return this;
    }

    public JMenuItem setTitleMarginTop(int marginTop) {
        if (mTvTitle != null) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, marginTop, 0, 0);
            mTvTitle.setLayoutParams(lp);
        }
        return this;
    }

    public JMenuItem setTitle(String title) {
        if (mTvTitle != null) {
            this.title = title;
            mTvTitle.setText(this.title);
        }
        return this;
    }

    public JMenuItem setTitleTextColor(int color) {
        if (mTvTitle != null) {
            mTvTitle.setTextColor(color);
        }
        return this;
    }

    public JMenuItem setTitleTextSize(float size) {
        if (mTvTitle != null) {
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        return this;
    }

    public JMenuItem setTitleHideStatus(boolean hideStatus) {
        if (mTvTitle != null) {
            if (hideStatus) {
                mTvTitle.setVisibility(View.GONE);
            } else {
                mTvTitle.setVisibility(View.VISIBLE);
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
                mImageView.getDrawable().setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            } else {
                mImageView.getDrawable().setColorFilter(null);
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
        mView = inflater.inflate(R.layout.layout_menu_item, this, true);
        mImageView = mView.findViewById(R.id.img);
        mTvTitle = mView.findViewById(R.id.title);

        TypedArray ta =  mContext.obtainStyledAttributes(attrs, R.styleable.JMenuItem);
        try {
            setIconId(ta.getResourceId(R.styleable.JMenuItem_icon, R.mipmap.ic_launcher));
            int iconWidth = (int) ta.getDimension(R.styleable.JMenuItem_iconWidth, getResources().getDimension(R.dimen.icon_default_width));
            int iconHeight = (int) ta.getDimension(R.styleable.JMenuItem_iconHeight, getResources().getDimension(R.dimen.icon_default_Height));
            setIconSize(iconWidth, iconHeight);
            setIconClickEffect(ta.getBoolean(R.styleable.JMenuItem_iconClickEffect, true));
            setTitle(ta.getString(R.styleable.JMenuItem_title));
            setTitleTextColor(ta.getColor(R.styleable.JMenuItem_titleTextColor, getResources().getColor(R.color.colorPrimary)));
            setTitleTextSize(ta.getDimensionPixelSize(R.styleable.JMenuItem_titleTextSize, (int) getResources().getDimension(R.dimen.title_text_size)));
            setTitleMarginTop((int) ta.getDimension(R.styleable.JMenuItem_titleMarginTop, 0));
            setTitleHideStatus(ta.getBoolean(R.styleable.JMenuItem_titleHideStatus, false));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }
}
