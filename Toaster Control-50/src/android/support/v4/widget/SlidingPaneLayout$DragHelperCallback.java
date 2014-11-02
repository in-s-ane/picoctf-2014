// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.widget;

import android.view.View;

// Referenced classes of package android.support.v4.widget:
//            SlidingPaneLayout, ViewDragHelper

private class <init> extends <init>
{

    final SlidingPaneLayout this$0;

    public int clampViewPositionHorizontal(View view, int i, int j)
    {
        <init> <init>1 = (<init>)SlidingPaneLayout.access$400(SlidingPaneLayout.this).getLayoutParams();
        if (SlidingPaneLayout.access$700(SlidingPaneLayout.this))
        {
            int i1 = getWidth() - (getPaddingRight() + <init>1.argin + SlidingPaneLayout.access$400(SlidingPaneLayout.this).getWidth());
            int j1 = i1 - SlidingPaneLayout.access$800(SlidingPaneLayout.this);
            return Math.max(Math.min(i, i1), j1);
        } else
        {
            int k = getPaddingLeft() + <init>1.rgin;
            int l = k + SlidingPaneLayout.access$800(SlidingPaneLayout.this);
            return Math.min(Math.max(i, k), l);
        }
    }

    public int clampViewPositionVertical(View view, int i, int j)
    {
        return view.getTop();
    }

    public int getViewHorizontalDragRange(View view)
    {
        return SlidingPaneLayout.access$800(SlidingPaneLayout.this);
    }

    public void onEdgeDragStarted(int i, int j)
    {
        SlidingPaneLayout.access$200(SlidingPaneLayout.this).captureChildView(SlidingPaneLayout.access$400(SlidingPaneLayout.this), j);
    }

    public void onViewCaptured(View view, int i)
    {
        setAllChildrenVisible();
    }

    public void onViewDragStateChanged(int i)
    {
label0:
        {
            if (SlidingPaneLayout.access$200(SlidingPaneLayout.this).getViewDragState() == 0)
            {
                if (SlidingPaneLayout.access$300(SlidingPaneLayout.this) != 0.0F)
                {
                    break label0;
                }
                updateObscuredViewsVisibility(SlidingPaneLayout.access$400(SlidingPaneLayout.this));
                dispatchOnPanelClosed(SlidingPaneLayout.access$400(SlidingPaneLayout.this));
                SlidingPaneLayout.access$502(SlidingPaneLayout.this, false);
            }
            return;
        }
        dispatchOnPanelOpened(SlidingPaneLayout.access$400(SlidingPaneLayout.this));
        SlidingPaneLayout.access$502(SlidingPaneLayout.this, true);
    }

    public void onViewPositionChanged(View view, int i, int j, int k, int l)
    {
        SlidingPaneLayout.access$600(SlidingPaneLayout.this, i);
        invalidate();
    }

    public void onViewReleased(View view, float f, float f1)
    {
        this._cls0 _lcls0 = (this._cls0)view.getLayoutParams();
        if (!SlidingPaneLayout.access$700(SlidingPaneLayout.this)) goto _L2; else goto _L1
_L1:
        int i;
        int j = getPaddingRight() + _lcls0.argin;
        if (f < 0.0F || f == 0.0F && SlidingPaneLayout.access$300(SlidingPaneLayout.this) > 0.5F)
        {
            j += SlidingPaneLayout.access$800(SlidingPaneLayout.this);
        }
        int k = SlidingPaneLayout.access$400(SlidingPaneLayout.this).getWidth();
        i = getWidth() - j - k;
_L4:
        SlidingPaneLayout.access$200(SlidingPaneLayout.this).settleCapturedViewAt(i, view.getTop());
        invalidate();
        return;
_L2:
        i = getPaddingLeft() + _lcls0.rgin;
        if (f > 0.0F || f == 0.0F && SlidingPaneLayout.access$300(SlidingPaneLayout.this) > 0.5F)
        {
            i += SlidingPaneLayout.access$800(SlidingPaneLayout.this);
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public boolean tryCaptureView(View view, int i)
    {
        if (SlidingPaneLayout.access$100(SlidingPaneLayout.this))
        {
            return false;
        } else
        {
            return ((this._cls0)view.getLayoutParams()).ble;
        }
    }

    private ()
    {
        this$0 = SlidingPaneLayout.this;
        super();
    }

    this._cls0(this._cls0 _pcls0)
    {
        this();
    }
}
