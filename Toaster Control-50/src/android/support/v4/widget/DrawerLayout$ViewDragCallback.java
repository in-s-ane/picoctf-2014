// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.widget;

import android.view.View;

// Referenced classes of package android.support.v4.widget:
//            DrawerLayout, ViewDragHelper

private class mAbsGravity extends mAbsGravity
{

    private final int mAbsGravity;
    private ViewDragHelper mDragger;
    private final Runnable mPeekRunnable = new Runnable() {

        final DrawerLayout.ViewDragCallback this$1;

        public void run()
        {
            peekDrawer();
        }

            
            {
                this$1 = DrawerLayout.ViewDragCallback.this;
                super();
            }
    };
    final DrawerLayout this$0;

    private void closeOtherDrawer()
    {
        byte byte0 = 3;
        if (mAbsGravity == byte0)
        {
            byte0 = 5;
        }
        View view = findDrawerWithGravity(byte0);
        if (view != null)
        {
            closeDrawer(view);
        }
    }

    private void peekDrawer()
    {
        int i = mDragger.getEdgeSize();
        boolean flag;
        View view;
        int j;
        if (mAbsGravity == 3)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        if (flag)
        {
            view = findDrawerWithGravity(3);
            int k = 0;
            if (view != null)
            {
                k = -view.getWidth();
            }
            j = k + i;
        } else
        {
            view = findDrawerWithGravity(5);
            j = getWidth() - i;
        }
        if (view != null && (flag && view.getLeft() < j || !flag && view.getLeft() > j) && getDrawerLockMode(view) == 0)
        {
            vity vity = ()view.getLayoutParams();
            mDragger.smoothSlideViewTo(view, j, view.getTop());
            vity.eking = true;
            invalidate();
            closeOtherDrawer();
            cancelChildViewTouch();
        }
    }

    public int clampViewPositionHorizontal(View view, int i, int j)
    {
        if (checkDrawerViewAbsoluteGravity(view, 3))
        {
            return Math.max(-view.getWidth(), Math.min(i, 0));
        } else
        {
            int k = getWidth();
            return Math.max(k - view.getWidth(), Math.min(i, k));
        }
    }

    public int clampViewPositionVertical(View view, int i, int j)
    {
        return view.getTop();
    }

    public int getViewHorizontalDragRange(View view)
    {
        return view.getWidth();
    }

    public void onEdgeDragStarted(int i, int j)
    {
        View view;
        if ((i & 1) == 1)
        {
            view = findDrawerWithGravity(3);
        } else
        {
            view = findDrawerWithGravity(5);
        }
        if (view != null && getDrawerLockMode(view) == 0)
        {
            mDragger.captureChildView(view, j);
        }
    }

    public boolean onEdgeLock(int i)
    {
        return false;
    }

    public void onEdgeTouched(int i, int j)
    {
        postDelayed(mPeekRunnable, 160L);
    }

    public void onViewCaptured(View view, int i)
    {
        ((mPeekRunnable)view.getLayoutParams()).eking = false;
        closeOtherDrawer();
    }

    public void onViewDragStateChanged(int i)
    {
        updateDrawerState(mAbsGravity, i, mDragger.getCapturedView());
    }

    public void onViewPositionChanged(View view, int i, int j, int k, int l)
    {
        int i1 = view.getWidth();
        float f;
        byte byte0;
        if (checkDrawerViewAbsoluteGravity(view, 3))
        {
            f = (float)(i1 + i) / (float)i1;
        } else
        {
            f = (float)(getWidth() - i) / (float)i1;
        }
        setDrawerViewOffset(view, f);
        if (f == 0.0F)
        {
            byte0 = 4;
        } else
        {
            byte0 = 0;
        }
        view.setVisibility(byte0);
        invalidate();
    }

    public void onViewReleased(View view, float f, float f1)
    {
        float f2 = getDrawerViewOffset(view);
        int i = view.getWidth();
        int k;
        if (checkDrawerViewAbsoluteGravity(view, 3))
        {
            if (f > 0.0F || f == 0.0F && f2 > 0.5F)
            {
                k = 0;
            } else
            {
                k = -i;
            }
        } else
        {
            int j = getWidth();
            if (f < 0.0F || f == 0.0F && f2 > 0.5F)
            {
                k = j - i;
            } else
            {
                k = j;
            }
        }
        mDragger.settleCapturedViewAt(k, view.getTop());
        invalidate();
    }

    public void removeCallbacks()
    {
        DrawerLayout.this.removeCallbacks(mPeekRunnable);
    }

    public void setDragger(ViewDragHelper viewdraghelper)
    {
        mDragger = viewdraghelper;
    }

    public boolean tryCaptureView(View view, int i)
    {
        return isDrawerView(view) && checkDrawerViewAbsoluteGravity(view, mAbsGravity) && getDrawerLockMode(view) == 0;
    }


    public _cls1.this._cls1(int i)
    {
        this$0 = DrawerLayout.this;
        super();
        mAbsGravity = i;
    }
}
