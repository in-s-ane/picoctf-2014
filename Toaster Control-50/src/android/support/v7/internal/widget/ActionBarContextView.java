// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.internal.view.menu.ActionMenuPresenter;
import android.support.v7.internal.view.menu.ActionMenuView;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.view.ActionMode;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

// Referenced classes of package android.support.v7.internal.widget:
//            AbsActionBarView, ActionBarContainer

public class ActionBarContextView extends AbsActionBarView
{

    private static final String TAG = "ActionBarContextView";
    private View mClose;
    private View mCustomView;
    private Drawable mSplitBackground;
    private CharSequence mSubtitle;
    private int mSubtitleStyleRes;
    private TextView mSubtitleView;
    private CharSequence mTitle;
    private LinearLayout mTitleLayout;
    private boolean mTitleOptional;
    private int mTitleStyleRes;
    private TextView mTitleView;

    public ActionBarContextView(Context context)
    {
        this(context, null);
    }

    public ActionBarContextView(Context context, AttributeSet attributeset)
    {
        this(context, attributeset, android.support.v7.appcompat.R.attr.actionModeStyle);
    }

    public ActionBarContextView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        TypedArray typedarray = context.obtainStyledAttributes(attributeset, android.support.v7.appcompat.R.styleable.ActionMode, i, 0);
        setBackgroundDrawable(typedarray.getDrawable(3));
        mTitleStyleRes = typedarray.getResourceId(1, 0);
        mSubtitleStyleRes = typedarray.getResourceId(2, 0);
        mContentHeight = typedarray.getLayoutDimension(0, 0);
        mSplitBackground = typedarray.getDrawable(4);
        typedarray.recycle();
    }

    private void initTitle()
    {
        byte byte0 = 8;
        if (mTitleLayout == null)
        {
            LayoutInflater.from(getContext()).inflate(android.support.v7.appcompat.R.layout.abc_action_bar_title_item, this);
            mTitleLayout = (LinearLayout)getChildAt(-1 + getChildCount());
            mTitleView = (TextView)mTitleLayout.findViewById(android.support.v7.appcompat.R.id.action_bar_title);
            mSubtitleView = (TextView)mTitleLayout.findViewById(android.support.v7.appcompat.R.id.action_bar_subtitle);
            if (mTitleStyleRes != 0)
            {
                mTitleView.setTextAppearance(getContext(), mTitleStyleRes);
            }
            if (mSubtitleStyleRes != 0)
            {
                mSubtitleView.setTextAppearance(getContext(), mSubtitleStyleRes);
            }
        }
        mTitleView.setText(mTitle);
        mSubtitleView.setText(mSubtitle);
        boolean flag;
        boolean flag1;
        TextView textview;
        int i;
        LinearLayout linearlayout;
        if (!TextUtils.isEmpty(mTitle))
        {
            flag = true;
        } else
        {
            flag = false;
        }
        if (!TextUtils.isEmpty(mSubtitle))
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        textview = mSubtitleView;
        if (flag1)
        {
            i = 0;
        } else
        {
            i = byte0;
        }
        textview.setVisibility(i);
        linearlayout = mTitleLayout;
        if (flag || flag1)
        {
            byte0 = 0;
        }
        linearlayout.setVisibility(byte0);
        if (mTitleLayout.getParent() == null)
        {
            addView(mTitleLayout);
        }
    }

    public volatile void animateToVisibility(int i)
    {
        super.animateToVisibility(i);
    }

    public void closeMode()
    {
        if (mClose == null)
        {
            killMode();
        }
    }

    public volatile void dismissPopupMenus()
    {
        super.dismissPopupMenus();
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams()
    {
        return new android.view.ViewGroup.MarginLayoutParams(-1, -2);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeset)
    {
        return new android.view.ViewGroup.MarginLayoutParams(getContext(), attributeset);
    }

    public volatile int getAnimatedVisibility()
    {
        return super.getAnimatedVisibility();
    }

    public volatile int getContentHeight()
    {
        return super.getContentHeight();
    }

    public CharSequence getSubtitle()
    {
        return mSubtitle;
    }

    public CharSequence getTitle()
    {
        return mTitle;
    }

    public boolean hideOverflowMenu()
    {
        if (mActionMenuPresenter != null)
        {
            return mActionMenuPresenter.hideOverflowMenu();
        } else
        {
            return false;
        }
    }

    public void initForMode(final ActionMode mode)
    {
        MenuBuilder menubuilder;
        android.view.ViewGroup.LayoutParams layoutparams;
        if (mClose == null)
        {
            mClose = LayoutInflater.from(getContext()).inflate(android.support.v7.appcompat.R.layout.abc_action_mode_close_item, this, false);
            addView(mClose);
        } else
        if (mClose.getParent() == null)
        {
            addView(mClose);
        }
        mClose.findViewById(android.support.v7.appcompat.R.id.action_mode_close_button).setOnClickListener(new android.view.View.OnClickListener() {

            final ActionBarContextView this$0;
            final ActionMode val$mode;

            public void onClick(View view)
            {
                mode.finish();
            }

            
            {
                this$0 = ActionBarContextView.this;
                mode = actionmode;
                super();
            }
        });
        menubuilder = (MenuBuilder)mode.getMenu();
        if (mActionMenuPresenter != null)
        {
            mActionMenuPresenter.dismissPopupMenus();
        }
        mActionMenuPresenter = new ActionMenuPresenter(getContext());
        mActionMenuPresenter.setReserveOverflow(true);
        layoutparams = new android.view.ViewGroup.LayoutParams(-2, -1);
        if (!mSplitActionBar)
        {
            menubuilder.addMenuPresenter(mActionMenuPresenter);
            mMenuView = (ActionMenuView)mActionMenuPresenter.getMenuView(this);
            mMenuView.setBackgroundDrawable(null);
            addView(mMenuView, layoutparams);
            return;
        } else
        {
            mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
            mActionMenuPresenter.setItemLimit(0x7fffffff);
            layoutparams.width = -1;
            layoutparams.height = mContentHeight;
            menubuilder.addMenuPresenter(mActionMenuPresenter);
            mMenuView = (ActionMenuView)mActionMenuPresenter.getMenuView(this);
            mMenuView.setBackgroundDrawable(mSplitBackground);
            mSplitView.addView(mMenuView, layoutparams);
            return;
        }
    }

    public boolean isOverflowMenuShowing()
    {
        if (mActionMenuPresenter != null)
        {
            return mActionMenuPresenter.isOverflowMenuShowing();
        } else
        {
            return false;
        }
    }

    public volatile boolean isOverflowReserved()
    {
        return super.isOverflowReserved();
    }

    public boolean isTitleOptional()
    {
        return mTitleOptional;
    }

    public void killMode()
    {
        removeAllViews();
        if (mSplitView != null)
        {
            mSplitView.removeView(mMenuView);
        }
        mCustomView = null;
        mMenuView = null;
    }

    public void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        if (mActionMenuPresenter != null)
        {
            mActionMenuPresenter.hideOverflowMenu();
            mActionMenuPresenter.hideSubMenus();
        }
    }

    protected void onLayout(boolean flag, int i, int j, int k, int l)
    {
        int i1 = getPaddingLeft();
        int j1 = getPaddingTop();
        int k1 = l - j - getPaddingTop() - getPaddingBottom();
        if (mClose != null && mClose.getVisibility() != 8)
        {
            android.view.ViewGroup.MarginLayoutParams marginlayoutparams = (android.view.ViewGroup.MarginLayoutParams)mClose.getLayoutParams();
            int i2 = i1 + marginlayoutparams.leftMargin;
            i1 = i2 + positionChild(mClose, i2, j1, k1) + marginlayoutparams.rightMargin;
        }
        if (mTitleLayout != null && mCustomView == null && mTitleLayout.getVisibility() != 8)
        {
            i1 += positionChild(mTitleLayout, i1, j1, k1);
        }
        if (mCustomView != null)
        {
            int _tmp = i1 + positionChild(mCustomView, i1, j1, k1);
        }
        int l1 = k - i - getPaddingRight();
        if (mMenuView != null)
        {
            int _tmp1 = l1 - positionChildInverse(mMenuView, l1, j1, k1);
        }
    }

    protected void onMeasure(int i, int j)
    {
        if (android.view.View.MeasureSpec.getMode(i) != 0x40000000)
        {
            throw new IllegalStateException((new StringBuilder()).append(getClass().getSimpleName()).append(" can only be used ").append("with android:layout_width=\"FILL_PARENT\" (or fill_parent)").toString());
        }
        if (android.view.View.MeasureSpec.getMode(j) == 0)
        {
            throw new IllegalStateException((new StringBuilder()).append(getClass().getSimpleName()).append(" can only be used ").append("with android:layout_height=\"wrap_content\"").toString());
        }
        int k = android.view.View.MeasureSpec.getSize(i);
        int l;
        int i1;
        int j1;
        int k1;
        int l1;
        if (mContentHeight > 0)
        {
            l = mContentHeight;
        } else
        {
            l = android.view.View.MeasureSpec.getSize(j);
        }
        i1 = getPaddingTop() + getPaddingBottom();
        j1 = k - getPaddingLeft() - getPaddingRight();
        k1 = l - i1;
        l1 = android.view.View.MeasureSpec.makeMeasureSpec(k1, 0x80000000);
        if (mClose != null)
        {
            int l4 = measureChildView(mClose, j1, l1, 0);
            android.view.ViewGroup.MarginLayoutParams marginlayoutparams = (android.view.ViewGroup.MarginLayoutParams)mClose.getLayoutParams();
            j1 = l4 - (marginlayoutparams.leftMargin + marginlayoutparams.rightMargin);
        }
        if (mMenuView != null && mMenuView.getParent() == this)
        {
            j1 = measureChildView(mMenuView, j1, l1, 0);
        }
        if (mTitleLayout != null && mCustomView == null)
        {
            if (mTitleOptional)
            {
                int i4 = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
                mTitleLayout.measure(i4, l1);
                int j4 = mTitleLayout.getMeasuredWidth();
                int j2;
                int k2;
                int l2;
                android.view.ViewGroup.LayoutParams layoutparams;
                boolean flag;
                LinearLayout linearlayout;
                int k4;
                if (j4 <= j1)
                {
                    flag = true;
                } else
                {
                    flag = false;
                }
                if (flag)
                {
                    j1 -= j4;
                }
                linearlayout = mTitleLayout;
                if (flag)
                {
                    k4 = 0;
                } else
                {
                    k4 = 8;
                }
                linearlayout.setVisibility(k4);
            } else
            {
                j1 = measureChildView(mTitleLayout, j1, l1, 0);
            }
        }
        if (mCustomView != null)
        {
            layoutparams = mCustomView.getLayoutParams();
            int i3;
            int j3;
            int k3;
            int l3;
            if (layoutparams.width != -2)
            {
                i3 = 0x40000000;
            } else
            {
                i3 = 0x80000000;
            }
            if (layoutparams.width >= 0)
            {
                j3 = Math.min(layoutparams.width, j1);
            } else
            {
                j3 = j1;
            }
            if (layoutparams.height != -2)
            {
                k3 = 0x40000000;
            } else
            {
                k3 = 0x80000000;
            }
            if (layoutparams.height >= 0)
            {
                l3 = Math.min(layoutparams.height, k1);
            } else
            {
                l3 = k1;
            }
            mCustomView.measure(android.view.View.MeasureSpec.makeMeasureSpec(j3, i3), android.view.View.MeasureSpec.makeMeasureSpec(l3, k3));
        }
        if (mContentHeight <= 0)
        {
            int i2 = 0;
            j2 = getChildCount();
            for (k2 = 0; k2 < j2; k2++)
            {
                l2 = i1 + getChildAt(k2).getMeasuredHeight();
                if (l2 > i2)
                {
                    i2 = l2;
                }
            }

            setMeasuredDimension(k, i2);
            return;
        } else
        {
            setMeasuredDimension(k, l);
            return;
        }
    }

    public volatile void postShowOverflowMenu()
    {
        super.postShowOverflowMenu();
    }

    public void setContentHeight(int i)
    {
        mContentHeight = i;
    }

    public void setCustomView(View view)
    {
        if (mCustomView != null)
        {
            removeView(mCustomView);
        }
        mCustomView = view;
        if (mTitleLayout != null)
        {
            removeView(mTitleLayout);
            mTitleLayout = null;
        }
        if (view != null)
        {
            addView(view);
        }
        requestLayout();
    }

    public void setSplitActionBar(boolean flag)
    {
        if (mSplitActionBar != flag)
        {
            if (mActionMenuPresenter != null)
            {
                android.view.ViewGroup.LayoutParams layoutparams = new android.view.ViewGroup.LayoutParams(-2, -1);
                if (!flag)
                {
                    mMenuView = (ActionMenuView)mActionMenuPresenter.getMenuView(this);
                    mMenuView.setBackgroundDrawable(null);
                    ViewGroup viewgroup1 = (ViewGroup)mMenuView.getParent();
                    if (viewgroup1 != null)
                    {
                        viewgroup1.removeView(mMenuView);
                    }
                    addView(mMenuView, layoutparams);
                } else
                {
                    mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                    mActionMenuPresenter.setItemLimit(0x7fffffff);
                    layoutparams.width = -1;
                    layoutparams.height = mContentHeight;
                    mMenuView = (ActionMenuView)mActionMenuPresenter.getMenuView(this);
                    mMenuView.setBackgroundDrawable(mSplitBackground);
                    ViewGroup viewgroup = (ViewGroup)mMenuView.getParent();
                    if (viewgroup != null)
                    {
                        viewgroup.removeView(mMenuView);
                    }
                    mSplitView.addView(mMenuView, layoutparams);
                }
            }
            super.setSplitActionBar(flag);
        }
    }

    public volatile void setSplitView(ActionBarContainer actionbarcontainer)
    {
        super.setSplitView(actionbarcontainer);
    }

    public volatile void setSplitWhenNarrow(boolean flag)
    {
        super.setSplitWhenNarrow(flag);
    }

    public void setSubtitle(CharSequence charsequence)
    {
        mSubtitle = charsequence;
        initTitle();
    }

    public void setTitle(CharSequence charsequence)
    {
        mTitle = charsequence;
        initTitle();
    }

    public void setTitleOptional(boolean flag)
    {
        if (flag != mTitleOptional)
        {
            requestLayout();
        }
        mTitleOptional = flag;
    }

    public volatile void setVisibility(int i)
    {
        super.setVisibility(i);
    }

    public boolean showOverflowMenu()
    {
        if (mActionMenuPresenter != null)
        {
            return mActionMenuPresenter.showOverflowMenu();
        } else
        {
            return false;
        }
    }
}
