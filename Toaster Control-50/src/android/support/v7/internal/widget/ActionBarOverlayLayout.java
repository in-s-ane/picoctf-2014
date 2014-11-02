// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

// Referenced classes of package android.support.v7.internal.widget:
//            ActionBarContainer, ActionBarView

public class ActionBarOverlayLayout extends FrameLayout
{

    static final int mActionBarSizeAttr[];
    private ActionBar mActionBar;
    private View mActionBarBottom;
    private int mActionBarHeight;
    private View mActionBarTop;
    private ActionBarView mActionView;
    private ActionBarContainer mContainerView;
    private View mContent;
    private final Rect mZeroRect;

    public ActionBarOverlayLayout(Context context)
    {
        super(context);
        mZeroRect = new Rect(0, 0, 0, 0);
        init(context);
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mZeroRect = new Rect(0, 0, 0, 0);
        init(context);
    }

    private boolean applyInsets(View view, Rect rect, boolean flag, boolean flag1, boolean flag2, boolean flag3)
    {
        android.widget.FrameLayout.LayoutParams layoutparams = (android.widget.FrameLayout.LayoutParams)view.getLayoutParams();
        boolean flag4 = false;
        if (flag)
        {
            int i = layoutparams.leftMargin;
            int j = rect.left;
            flag4 = false;
            if (i != j)
            {
                flag4 = true;
                layoutparams.leftMargin = rect.left;
            }
        }
        if (flag1 && layoutparams.topMargin != rect.top)
        {
            flag4 = true;
            layoutparams.topMargin = rect.top;
        }
        if (flag3 && layoutparams.rightMargin != rect.right)
        {
            flag4 = true;
            layoutparams.rightMargin = rect.right;
        }
        if (flag2 && layoutparams.bottomMargin != rect.bottom)
        {
            flag4 = true;
            layoutparams.bottomMargin = rect.bottom;
        }
        return flag4;
    }

    private void init(Context context)
    {
        TypedArray typedarray = getContext().getTheme().obtainStyledAttributes(mActionBarSizeAttr);
        mActionBarHeight = typedarray.getDimensionPixelSize(0, 0);
        typedarray.recycle();
    }

    void pullChildren()
    {
        if (mContent == null)
        {
            mContent = findViewById(android.support.v7.appcompat.R.id.action_bar_activity_content);
            if (mContent == null)
            {
                mContent = findViewById(0x1020002);
            }
            mActionBarTop = findViewById(android.support.v7.appcompat.R.id.top_action_bar);
            mContainerView = (ActionBarContainer)findViewById(android.support.v7.appcompat.R.id.action_bar_container);
            mActionView = (ActionBarView)findViewById(android.support.v7.appcompat.R.id.action_bar);
            mActionBarBottom = findViewById(android.support.v7.appcompat.R.id.split_action_bar);
        }
    }

    public void setActionBar(ActionBar actionbar)
    {
        mActionBar = actionbar;
    }

    static 
    {
        int ai[] = new int[1];
        ai[0] = android.support.v7.appcompat.R.attr.actionBarSize;
        mActionBarSizeAttr = ai;
    }
}
