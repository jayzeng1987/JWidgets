package com.genvict.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 菜单项控件
 * @author jayz
 */
public class JMenuItem extends LinearLayout {
    private Context mContext;
    private View mView;
    private View mDivideLine;
    private View mDivideArea;
    private LinearLayout mMainView;
    private ImageView mIcon;
    private ImageView mIconUnread;
    private ImageView mIconArrow;
    private TextView mMainTitile;
    private TextView mSubTitile;
    private int mPressColor;
    private int mNormalColor;

    public JMenuItem(@NonNull Context context) {
        this(context, null);
    }

    public JMenuItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JMenuItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public JMenuItem setIconId(int iconId) {
        if (mIcon != null) {
            mIcon.setImageResource(iconId);
        }
        return this;
    }

    public JMenuItem setIconSize(int width, int height) {
        if (mIcon != null) {
            ViewGroup.LayoutParams lp = mIcon.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mIcon.setLayoutParams(lp);
        }
        return this;
    }

    public JMenuItem setMainTitle(String title) {
        if (mMainTitile != null) {
            mMainTitile.setText(title);
        }
        return this;
    }

    public JMenuItem setSubTitle(String title) {
        if (mSubTitile != null) {
            mSubTitile.setText(title);
        }
        return this;
    }

    public JMenuItem setShowSubTitle(boolean isShow) {
        if (mSubTitile != null) {
            mSubTitile.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public JMenuItem setShowDivideLine(boolean isShow) {
        if (mDivideLine != null) {
            mDivideLine.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public JMenuItem setShowDivideArea(boolean isShow) {
        if (mDivideArea != null) {
            mDivideArea.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public JMenuItem setShowUnreadIcon(boolean isShow) {
        if (mIconUnread != null) {
            mIconUnread.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public JMenuItem setShowArrowIcon(boolean isShow) {
        if (mIconArrow != null) {
            mIconArrow.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public JMenuItem setDivideLineBgColor(int colorId) {
        if (mDivideLine != null) {
            mDivideLine.setBackgroundColor(colorId);
        }
        return this;
    }

    public JMenuItem setDivideAreaBgColor(int colorId) {
        if (mDivideArea != null) {
            mDivideArea.setBackgroundColor(colorId);
        }
        return this;
    }

    public JMenuItem setTitleTextColor(int colorId) {
        if (mMainTitile != null && mSubTitile != null) {
            mMainTitile.setTextColor(colorId);
            mSubTitile.setTextColor(colorId);
        }
        return this;
    }

    public JMenuItem setTitleTextSize(float size) {
        if (mMainTitile != null && mSubTitile != null) {
            mMainTitile.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            mSubTitile.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        return this;
    }

    public JMenuItem setItemPressedColor(int pressedColor) {
        return this.setItemSelectorColor(pressedColor, mNormalColor);
    }

    public JMenuItem setItemNormalColor(int normalColor) {
        return this.setItemSelectorColor(mPressColor, normalColor);
    }

    public JMenuItem setItemSelectorColor(int pressedColor, int normalColor) {
        if (mMainView != null) {
            GradientDrawable pressed = new GradientDrawable();
            pressed.setColor(pressedColor);
            GradientDrawable normal = new GradientDrawable();
            normal.setColor(normalColor);
            StateListDrawable bg = new StateListDrawable();
            bg.addState(new int[]{android.R.attr.state_pressed}, pressed);
            bg.addState(new int[]{}, normal);
            mMainView.setBackground(bg);
        }
        return this;
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.layout_menu_item, this, true);
        mMainView = mView.findViewById(R.id.ll_main);
        mDivideLine = mView.findViewById(R.id.view_divide_line);
        mDivideArea = mView.findViewById(R.id.view_divide_area);
        mIcon = mView.findViewById(R.id.icon_main);
        mIconUnread = mView.findViewById(R.id.icon_unread);
        mIconArrow = mView.findViewById(R.id.icon_arrow);

        mMainTitile = mView.findViewById(R.id.tv_main_title);
        mSubTitile = mView.findViewById(R.id.tv_sub_title);

        TypedArray ta =  mContext.obtainStyledAttributes(attrs, R.styleable.JMenuItem);
        try {
            setIconId(ta.getResourceId(R.styleable.JMenuItem_icon, R.mipmap.ic_launcher));
            int iconWidth = (int) ta.getDimension(R.styleable.JMenuItem_iconWidth, getResources().getDimension(R.dimen.JMenuItemIconDefaultWidth));
            int iconHeight = (int) ta.getDimension(R.styleable.JMenuItem_iconHeight, getResources().getDimension(R.dimen.JMenuItemIconDefaultHeight));
            setIconSize(iconWidth, iconHeight);

            setMainTitle(ta.getString(R.styleable.JMenuItem_mainTitle));
            setSubTitle(ta.getString(R.styleable.JMenuItem_subTitle));
            setTitleTextColor(ta.getColor(R.styleable.JMenuItem_titleTextColor, getResources().getColor(R.color.JMenuItemTextDefaultColor)));
            setTitleTextSize(ta.getDimensionPixelSize(R.styleable.JMenuItem_titleTextSize, (int) getResources().getDimension(R.dimen.JMenuItemTitleTextSize)));

            setShowSubTitle(ta.getBoolean(R.styleable.JMenuItem_showSubTitle, false));
            setShowDivideLine(ta.getBoolean(R.styleable.JMenuItem_showDivideLine, false));
            setShowDivideArea(ta.getBoolean(R.styleable.JMenuItem_showDivideArea, false));
            setShowUnreadIcon(ta.getBoolean(R.styleable.JMenuItem_showUnreadIcon, false));
            setShowArrowIcon(ta.getBoolean(R.styleable.JMenuItem_showArrowIcon, true));

            mPressColor = ta.getColor(R.styleable.JMenuItem_pressedBgColor, getResources().getColor(R.color.JMenuItemClickBgColor));
            mNormalColor = ta.getColor(R.styleable.JMenuItem_normalBgColor, getResources().getColor(R.color.JMenuItemBgColorWhite));
            setItemSelectorColor(mPressColor, mNormalColor);
            setDivideLineBgColor(ta.getColor(R.styleable.JMenuItem_divideLineBgColor, getResources().getColor(R.color.JMenuItemDivideLineColor)));
            setDivideAreaBgColor(ta.getColor(R.styleable.JMenuItem_divideAreaBgColor, getResources().getColor(R.color.JMenuItemDivideAreaColor)));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }
}
