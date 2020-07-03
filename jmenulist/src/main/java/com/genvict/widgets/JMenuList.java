package com.genvict.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 菜单列表控件
 * @author jayz
 */
public class JMenuList extends FrameLayout {
    private Context mContext;
    private View mView;
    private View mDivideLine;
    private View mDivideArea;
    private LinearLayout mMainView;
    private LinearLayout mItemView;
    private ImageView mIcon;
    private ImageView mIconUnread;
    private ImageView mIconArrow;
    private TextView mMainTitile;
    private TextView mSubTitile;

    public JMenuList(@NonNull Context context) {
        this(context, null);
    }

    public JMenuList(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JMenuList(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public JMenuList setIconId(int iconId) {
        if (mIcon != null) {
            mIcon.setImageResource(iconId);
        }
        return this;
    }

    public JMenuList setIconSize(int width, int height) {
        if (mIcon != null) {
            ViewGroup.LayoutParams lp = mIcon.getLayoutParams();
            lp.width = width;
            lp.height = height;
            mIcon.setLayoutParams(lp);
        }
        return this;
    }

    public JMenuList setMainTitle(String title) {
        if (mMainTitile != null) {
            mMainTitile.setText(title);
        }
        return this;
    }

    public JMenuList setSubTitle(String title) {
        if (mSubTitile != null) {
            mSubTitile.setText(title);
        }
        return this;
    }

    public JMenuList setShowSubTitle(boolean isShow) {
        if (mSubTitile != null) {
            mSubTitile.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public JMenuList setShowDivideLine(boolean isShow) {
        if (mDivideLine != null) {
            mDivideLine.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public JMenuList setShowDivideArea(boolean isShow) {
        if (mDivideArea != null) {
            mDivideArea.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public JMenuList setShowUnreadIcon(boolean isShow) {
        if (mIconUnread != null) {
            mIconUnread.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public JMenuList setShowArrowIcon(boolean isShow) {
        if (mIconArrow != null) {
            mIconArrow.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public JMenuList setBgColor(int colorId) {
        if (mMainView != null && mItemView != null) {
            mMainView.setBackgroundColor(colorId);
            mItemView.setBackgroundColor(colorId);
        }
        return this;
    }

    public JMenuList setDivideLineBgColor(int colorId) {
        if (mDivideLine != null) {
            mDivideLine.setBackgroundColor(colorId);
        }
        return this;
    }

    public JMenuList setDivideAreaBgColor(int colorId) {
        if (mDivideArea != null) {
            mDivideArea.setBackgroundColor(colorId);
        }
        return this;
    }

    public JMenuList setTitleTextColor(int colorId) {
        if (mMainTitile != null && mSubTitile != null) {
            mMainTitile.setTextColor(colorId);
            mSubTitile.setTextColor(colorId);
        }
        return this;
    }

    public JMenuList setTitleTextSize(float size) {
        if (mMainTitile != null && mSubTitile != null) {
            mMainTitile.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            mSubTitile.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        return this;
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.layout_menu_list, this, true);
        mMainView = mView.findViewById(R.id.ll_main);
        mItemView = mView.findViewById(R.id.ll_item);
        mDivideLine = mView.findViewById(R.id.view_divide_line);
        mDivideArea = mView.findViewById(R.id.view_divide_area);
        mIcon = mView.findViewById(R.id.icon_main);
        mIconUnread = mView.findViewById(R.id.icon_unread);
        mIconArrow = mView.findViewById(R.id.icon_arrow);

        mMainTitile = mView.findViewById(R.id.tv_main_title);
        mSubTitile = mView.findViewById(R.id.tv_sub_title);

        TypedArray ta =  mContext.obtainStyledAttributes(attrs, R.styleable.JMenuList);
        try {
            setIconId(ta.getResourceId(R.styleable.JMenuList_icon, R.mipmap.ic_launcher));
            int iconWidth = (int) ta.getDimension(R.styleable.JMenuList_iconWidth, getResources().getDimension(R.dimen.JMenuListIconDefaultWidth));
            int iconHeight = (int) ta.getDimension(R.styleable.JMenuList_iconHeight, getResources().getDimension(R.dimen.JMenuListIconDefaultHeight));
            setIconSize(iconWidth, iconHeight);

            setMainTitle(ta.getString(R.styleable.JMenuList_mainTitle));
            setSubTitle(ta.getString(R.styleable.JMenuList_subTitle));
            setTitleTextColor(ta.getColor(R.styleable.JMenuList_titleTextColor, getResources().getColor(R.color.JMenuListTextDefaultColor)));
            setTitleTextSize(ta.getDimensionPixelSize(R.styleable.JMenuList_titleTextSize, (int) getResources().getDimension(R.dimen.JMenuListTitleTextSize)));

            setShowSubTitle(ta.getBoolean(R.styleable.JMenuList_showSubTitle, false));
            setShowDivideLine(ta.getBoolean(R.styleable.JMenuList_showDivideLine, false));
            setShowDivideArea(ta.getBoolean(R.styleable.JMenuList_showDivideArea, false));
            setShowUnreadIcon(ta.getBoolean(R.styleable.JMenuList_showUnreadIcon, false));
            setShowArrowIcon(ta.getBoolean(R.styleable.JMenuList_showArrowIcon, true));

            setBgColor(ta.getColor(R.styleable.JMenuList_bgColor, getResources().getColor(R.color.JMenuListBgColorWhite)));
            setDivideLineBgColor(ta.getColor(R.styleable.JMenuList_divideLineBgColor, getResources().getColor(R.color.JMenuListDivideLineColor)));
            setDivideAreaBgColor(ta.getColor(R.styleable.JMenuList_divideAreaBgColor, getResources().getColor(R.color.JMenuListDivideAreaColor)));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }
}
