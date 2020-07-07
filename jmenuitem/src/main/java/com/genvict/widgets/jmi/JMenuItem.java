package com.genvict.widgets.jmi;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
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
    private int mPressColorId;
    private int mNormalColorId;

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

    /**
     * 设置主图标资源ID
     * @param iconId 图片资源ID
     * @return JMenuItem
     */
    public JMenuItem setIconId(int iconId) {
        if (mIcon != null) {
            mIcon.setImageResource(iconId);
        }
        return this;
    }

    /**
     * 设置主图标大小
     * @param width 宽度，默认24dp
     * @param height 高度，默认24dp
     * @return JMenuItem
     */
    public JMenuItem setIconSize(int width, int height) {
        if (mIcon != null) {
            ViewGroup.LayoutParams lp = mIcon.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mIcon.setLayoutParams(lp);
        }
        return this;
    }

    /**
     * 设置主标题
     * @param title 标题内容
     * @return JMenuItem
     */
    public JMenuItem setMainTitle(String title) {
        if (mMainTitile != null) {
            mMainTitile.setText(title);
        }
        return this;
    }

    /**
     * 设置子标题
     * @param title 标题内容
     * @return JMenuItem
     */
    public JMenuItem setSubTitle(String title) {
        if (mSubTitile != null) {
            mSubTitile.setText(title);
        }
        return this;
    }

    /**
     * 设置是否显示子标题
     * @param isShow true：显示，false：不显示，默认为false
     * @return JMenuItem
     */
    public JMenuItem setShowSubTitle(boolean isShow) {
        if (mSubTitile != null) {
            mSubTitile.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    /**
     * 设置是否显示分隔线
     * @param isShow true：显示，false：不显示，默认为false
     * @return JMenuItem
     */
    public JMenuItem setShowDivideLine(boolean isShow) {
        if (mDivideLine != null) {
            mDivideLine.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    /**
     * 设置是否显示分隔栏
     * @param isShow true：显示，false：不显示，默认为false
     * @return JMenuItem
     */
    public JMenuItem setShowDivideArea(boolean isShow) {
        if (mDivideArea != null) {
            mDivideArea.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    /**
     * 设置是否显示未读信息图标
     * @param isShow true：显示，false：不显示，默认为false
     * @return JMenuItem
     */
    public JMenuItem setShowUnreadIcon(boolean isShow) {
        if (mIconUnread != null) {
            mIconUnread.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
        }
        return this;
    }

    /**
     * 设置是否显示箭头图标
     * @param isShow isShow true：显示，false：不显示，默认为true
     * @return JMenuItem
     */
    public JMenuItem setShowArrowIcon(boolean isShow) {
        if (mIconArrow != null) {
            mIconArrow.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
        }
        return this;
    }

    /**
     * 设置分隔线背景颜色
     * @param colorId 颜色资源ID
     * @return JMenuItem
     */
    public JMenuItem setDivideLineBgColor(int colorId) {
        if (mDivideLine != null) {
            mDivideLine.setBackgroundColor(colorId);
        }
        return this;
    }

    /**
     * 设置分隔栏背景颜色
     * @param colorId 颜色资源ID
     * @return JMenuItem
     */
    public JMenuItem setDivideAreaBgColor(int colorId) {
        if (mDivideArea != null) {
            mDivideArea.setBackgroundColor(colorId);
        }
        return this;
    }

    /**
     * 设置标题文字颜色
     * @param colorId 颜色资源ID
     * @return JMenuItem
     */
    public JMenuItem setTitleTextColor(int colorId) {
        if (mMainTitile != null && mSubTitile != null) {
            mMainTitile.setTextColor(colorId);
            mSubTitile.setTextColor(colorId);
        }
        return this;
    }

    /**
     * 设置标题文字大小
     * @param size 大小，默认12sp
     * @return JMenuItem
     */
    public JMenuItem setTitleTextSize(float size) {
        if (mMainTitile != null && mSubTitile != null) {
            mMainTitile.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            mSubTitile.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        return this;
    }

    /**
     * 设置Item点击状态背景颜色
     * @param pressedColorId 颜色资源ID
     * @return JMenuItem
     */
    public JMenuItem setItemPressedColor(int pressedColorId) {
        return this.setItemSelectorColor(pressedColorId, mNormalColorId);
    }

    /**
     * 设置Item正常状态背景颜色
     * @param normalColorId 颜色资源ID
     * @return JMenuItem
     */
    public JMenuItem setItemNormalColor(int normalColorId) {
        return this.setItemSelectorColor(mPressColorId, normalColorId);
    }

    /**
     * 设置Item选择器背景颜色
     * @param pressedColorId 点击状态背景颜色资源ID
     * @param normalColorId 正常状态背景颜色资源ID
     * @return JMenuItem
     */
    public JMenuItem setItemSelectorColor(int pressedColorId, int normalColorId) {
        if (mMainView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            GradientDrawable pressed = new GradientDrawable();
            pressed.setColor(pressedColorId);
            GradientDrawable normal = new GradientDrawable();
            normal.setColor(normalColorId);
            StateListDrawable bg = new StateListDrawable();
            bg.addState(new int[]{android.R.attr.state_pressed}, pressed);
            bg.addState(new int[]{}, normal);
            mMainView.setBackground(bg);
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

            mPressColorId = ta.getColor(R.styleable.JMenuItem_pressedBgColor, getResources().getColor(R.color.JMenuItemClickBgColor));
            mNormalColorId = ta.getColor(R.styleable.JMenuItem_normalBgColor, getResources().getColor(R.color.JMenuItemBgColorWhite));
            setItemSelectorColor(mPressColorId, mNormalColorId);
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
