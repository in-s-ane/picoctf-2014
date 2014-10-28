// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.view.ActionMode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

// Referenced classes of package android.support.v7.internal.widget:
//            ActionBarView, ScrollingTabContainerView

public class ActionBarContainer extends FrameLayout
{

    private ActionBarView mActionBarView;
    private Drawable mBackground;
    private boolean mIsSplit;
    private boolean mIsStacked;
    private boolean mIsTransitioning;
    private Drawable mSplitBackground;
    private Drawable mStackedBackground;
    private View mTabContainer;

    public ActionBarContainer(Context context)
    {
        this(context, null);
    }

    public ActionBarContainer(Context context, AttributeSet attributeset)
    {
        boolean flag;
        flag = true;
        super(context, attributeset);
        setBackgroundDrawable(null);
        TypedArray typedarray = context.obtainStyledAttributes(attributeset, android.support.v7.appcompat.R.styleable.ActionBar);
        mBackground = typedarray.getDrawable(10);
        mStackedBackground = typedarray.getDrawable(11);
        if (getId() == android.support.v7.appcompat.R.id.split_action_bar)
        {
            mIsSplit = flag;
            mSplitBackground = typedarray.getDrawable(12);
        }
        typedarray.recycle();
        if (!mIsSplit) goto _L2; else goto _L1
_L1:
        if (mSplitBackground != null)
        {
            flag = false;
        }
_L4:
        setWillNotDraw(flag);
        return;
_L2:
        if (mBackground != null || mStackedBackground != null)
        {
            flag = false;
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    private void drawBackgroundDrawable(Drawable drawable, Canvas canvas)
    {
        Rect rect = drawable.getBounds();
        if ((drawable instanceof ColorDrawable) && !rect.isEmpty() && android.os.Build.VERSION.SDK_INT < 11)
        {
            canvas.save();
            canvas.clipRect(rect);
            drawable.draw(canvas);
            canvas.restore();
            return;
        } else
        {
            drawable.draw(canvas);
            return;
        }
    }

    protected void drawableStateChanged()
    {
        super.drawableStateChanged();
        if (mBackground != null && mBackground.isStateful())
        {
            mBackground.setState(getDrawableState());
        }
        if (mStackedBackground != null && mStackedBackground.isStateful())
        {
            mStackedBackground.setState(getDrawableState());
        }
        if (mSplitBackground != null && mSplitBackground.isStateful())
        {
            mSplitBackground.setState(getDrawableState());
        }
    }

    public View getTabContainer()
    {
        return mTabContainer;
    }

    public void onDraw(Canvas canvas)
    {
        if (getWidth() != 0 && getHeight() != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if (!mIsSplit)
        {
            break; /* Loop/switch isn't completed */
        }
        if (mSplitBackground != null)
        {
            drawBackgroundDrawable(mSplitBackground, canvas);
            return;
        }
        if (true) goto _L1; else goto _L3
_L3:
        if (mBackground != null)
        {
            drawBackgroundDrawable(mBackground, canvas);
        }
        if (mStackedBackground != null && mIsStacked)
        {
            drawBackgroundDrawable(mStackedBackground, canvas);
            return;
        }
        if (true) goto _L1; else goto _L4
_L4:
    }

    public void onFinishInflate()
    {
        super.onFinishInflate();
        mActionBarView = (ActionBarView)findViewById(android.support.v7.appcompat.R.id.action_bar);
    }

    public boolean onHoverEvent(MotionEvent motionevent)
    {
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionevent)
    {
        return mIsTransitioning || super.onInterceptTouchEvent(motionevent);
    }

    public void onLayout(boolean flag, int i, int j, int k, int l)
    {
        boolean flag1;
        int i1;
        int j1;
        View view;
        super.onLayout(flag, i, j, k, l);
        int k1;
        int l1;
        if (mTabContainer != null && mTabContainer.getVisibility() != 8)
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        if (mTabContainer == null || mTabContainer.getVisibility() == 8)
        {
            break MISSING_BLOCK_LABEL_157;
        }
        i1 = getMeasuredHeight();
        j1 = mTabContainer.getMeasuredHeight();
        if ((2 & mActionBarView.getDisplayOptions()) != 0)
        {
            break MISSING_BLOCK_LABEL_208;
        }
        k1 = getChildCount();
        l1 = 0;
        if (l1 >= k1)
        {
            break; /* Loop/switch isn't completed */
        }
        view = getChildAt(l1);
        if (view != mTabContainer && !mActionBarView.isCollapsed())
        {
            view.offsetTopAndBottom(j1);
        }
        l1++;
        if (true) goto _L2; else goto _L1
_L2:
        break MISSING_BLOCK_LABEL_88;
_L1:
        mTabContainer.layout(i, 0, k, j1);
_L3:
        boolean flag2;
        if (mIsSplit)
        {
            Drawable drawable1 = mSplitBackground;
            flag2 = false;
            if (drawable1 != null)
            {
                mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
                flag2 = true;
            }
        } else
        {
            Drawable drawable = mBackground;
            flag2 = false;
            if (drawable != null)
            {
                mBackground.setBounds(mActionBarView.getLeft(), mActionBarView.getTop(), mActionBarView.getRight(), mActionBarView.getBottom());
                flag2 = true;
            }
            boolean flag3;
            if (flag1 && mStackedBackground != null)
            {
                flag3 = true;
            } else
            {
                flag3 = false;
            }
            mIsStacked = flag3;
            if (flag3)
            {
                mStackedBackground.setBounds(mTabContainer.getLeft(), mTabContainer.getTop(), mTabContainer.getRight(), mTabContainer.getBottom());
                flag2 = true;
            }
        }
        if (flag2)
        {
            invalidate();
        }
        return;
        mTabContainer.layout(i, i1 - j1, k, i1);
          goto _L3
    }

    public void onMeasure(int i, int j)
    {
        super.onMeasure(i, j);
        if (mActionBarView != null)
        {
            android.widget.FrameLayout.LayoutParams layoutparams = (android.widget.FrameLayout.LayoutParams)mActionBarView.getLayoutParams();
            int k;
            if (mActionBarView.isCollapsed())
            {
                k = 0;
            } else
            {
                k = mActionBarView.getMeasuredHeight() + layoutparams.topMargin + layoutparams.bottomMargin;
            }
            if (mTabContainer != null && mTabContainer.getVisibility() != 8 && android.view.View.MeasureSpec.getMode(j) == 0x80000000)
            {
                int l = android.view.View.MeasureSpec.getSize(j);
                setMeasuredDimension(getMeasuredWidth(), Math.min(k + mTabContainer.getMeasuredHeight(), l));
                return;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        super.onTouchEvent(motionevent);
        return true;
    }

    public void setPrimaryBackground(Drawable drawable)
    {
        boolean flag;
        flag = true;
        if (mBackground != null)
        {
            mBackground.setCallback(null);
            unscheduleDrawable(mBackground);
        }
        mBackground = drawable;
        if (drawable != null)
        {
            drawable.setCallback(this);
            if (mActionBarView != null)
            {
                mBackground.setBounds(mActionBarView.getLeft(), mActionBarView.getTop(), mActionBarView.getRight(), mActionBarView.getBottom());
            }
        }
        if (!mIsSplit) goto _L2; else goto _L1
_L1:
        if (mSplitBackground != null)
        {
            flag = false;
        }
_L4:
        setWillNotDraw(flag);
        invalidate();
        return;
_L2:
        if (mBackground != null || mStackedBackground != null)
        {
            flag = false;
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public void setSplitBackground(Drawable drawable)
    {
        boolean flag;
        flag = true;
        if (mSplitBackground != null)
        {
            mSplitBackground.setCallback(null);
            unscheduleDrawable(mSplitBackground);
        }
        mSplitBackground = drawable;
        if (drawable != null)
        {
            drawable.setCallback(this);
            if (mIsSplit && mSplitBackground != null)
            {
                mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
        }
        if (!mIsSplit) goto _L2; else goto _L1
_L1:
        if (mSplitBackground != null)
        {
            flag = false;
        }
_L4:
        setWillNotDraw(flag);
        invalidate();
        return;
_L2:
        if (mBackground != null || mStackedBackground != null)
        {
            flag = false;
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public void setStackedBackground(Drawable drawable)
    {
        boolean flag;
        flag = true;
        if (mStackedBackground != null)
        {
            mStackedBackground.setCallback(null);
            unscheduleDrawable(mStackedBackground);
        }
        mStackedBackground = drawable;
        if (drawable != null)
        {
            drawable.setCallback(this);
            if (mIsStacked && mStackedBackground != null)
            {
                mStackedBackground.setBounds(mTabContainer.getLeft(), mTabContainer.getTop(), mTabContainer.getRight(), mTabContainer.getBottom());
            }
        }
        if (!mIsSplit) goto _L2; else goto _L1
_L1:
        if (mSplitBackground != null)
        {
            flag = false;
        }
_L4:
        setWillNotDraw(flag);
        invalidate();
        return;
_L2:
        if (mBackground != null || mStackedBackground != null)
        {
            flag = false;
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public void setTabContainer(ScrollingTabContainerView scrollingtabcontainerview)
    {
        if (mTabContainer != null)
        {
            removeView(mTabContainer);
        }
        mTabContainer = scrollingtabcontainerview;
        if (scrollingtabcontainerview != null)
        {
            addView(scrollingtabcontainerview);
            android.view.ViewGroup.LayoutParams layoutparams = scrollingtabcontainerview.getLayoutParams();
            layoutparams.width = -1;
            layoutparams.height = -2;
            scrollingtabcontainerview.setAllowCollapse(false);
        }
    }

    public void setTransitioning(boolean flag)
    {
        mIsTransitioning = flag;
        int i;
        if (flag)
        {
            i = 0x60000;
        } else
        {
            i = 0x40000;
        }
        setDescendantFocusability(i);
    }

    public void setVisibility(int i)
    {
        super.setVisibility(i);
        boolean flag;
        if (i == 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        if (mBackground != null)
        {
            mBackground.setVisible(flag, false);
        }
        if (mStackedBackground != null)
        {
            mStackedBackground.setVisible(flag, false);
        }
        if (mSplitBackground != null)
        {
            mSplitBackground.setVisible(flag, false);
        }
    }

    public ActionMode startActionModeForChild(View view, android.support.v7.view.ActionMode.Callback callback)
    {
        return null;
    }

    protected boolean verifyDrawable(Drawable drawable)
    {
        return drawable == mBackground && !mIsSplit || drawable == mStackedBackground && mIsStacked || drawable == mSplitBackground && mIsSplit || super.verifyDrawable(drawable);
    }
}
