// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import java.lang.annotation.Annotation;
import java.util.List;

// Referenced classes of package android.support.v4.widget:
//            ViewDragHelper

public class DrawerLayout extends ViewGroup
{
    class AccessibilityDelegate extends AccessibilityDelegateCompat
    {

        private final Rect mTmpRect = new Rect();
        final DrawerLayout this$0;

        private void addChildrenForAccessibility(AccessibilityNodeInfoCompat accessibilitynodeinfocompat, ViewGroup viewgroup)
        {
            int i = viewgroup.getChildCount();
            for (int j = 0; j < i; j++)
            {
                View view = viewgroup.getChildAt(j);
                if (DrawerLayout.includeChildForAccessibilitiy(view))
                {
                    accessibilitynodeinfocompat.addChild(view);
                }
            }

        }

        private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat accessibilitynodeinfocompat, AccessibilityNodeInfoCompat accessibilitynodeinfocompat1)
        {
            Rect rect = mTmpRect;
            accessibilitynodeinfocompat1.getBoundsInParent(rect);
            accessibilitynodeinfocompat.setBoundsInParent(rect);
            accessibilitynodeinfocompat1.getBoundsInScreen(rect);
            accessibilitynodeinfocompat.setBoundsInScreen(rect);
            accessibilitynodeinfocompat.setVisibleToUser(accessibilitynodeinfocompat1.isVisibleToUser());
            accessibilitynodeinfocompat.setPackageName(accessibilitynodeinfocompat1.getPackageName());
            accessibilitynodeinfocompat.setClassName(accessibilitynodeinfocompat1.getClassName());
            accessibilitynodeinfocompat.setContentDescription(accessibilitynodeinfocompat1.getContentDescription());
            accessibilitynodeinfocompat.setEnabled(accessibilitynodeinfocompat1.isEnabled());
            accessibilitynodeinfocompat.setClickable(accessibilitynodeinfocompat1.isClickable());
            accessibilitynodeinfocompat.setFocusable(accessibilitynodeinfocompat1.isFocusable());
            accessibilitynodeinfocompat.setFocused(accessibilitynodeinfocompat1.isFocused());
            accessibilitynodeinfocompat.setAccessibilityFocused(accessibilitynodeinfocompat1.isAccessibilityFocused());
            accessibilitynodeinfocompat.setSelected(accessibilitynodeinfocompat1.isSelected());
            accessibilitynodeinfocompat.setLongClickable(accessibilitynodeinfocompat1.isLongClickable());
            accessibilitynodeinfocompat.addAction(accessibilitynodeinfocompat1.getActions());
        }

        public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityevent)
        {
            if (accessibilityevent.getEventType() == 32)
            {
                List list = accessibilityevent.getText();
                View view1 = findVisibleDrawer();
                if (view1 != null)
                {
                    int i = getDrawerViewAbsoluteGravity(view1);
                    CharSequence charsequence = getDrawerTitle(i);
                    if (charsequence != null)
                    {
                        list.add(charsequence);
                    }
                }
                return true;
            } else
            {
                return super.dispatchPopulateAccessibilityEvent(view, accessibilityevent);
            }
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityevent)
        {
            super.onInitializeAccessibilityEvent(view, accessibilityevent);
            accessibilityevent.setClassName(android/support/v4/widget/DrawerLayout.getName());
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilitynodeinfocompat)
        {
            AccessibilityNodeInfoCompat accessibilitynodeinfocompat1 = AccessibilityNodeInfoCompat.obtain(accessibilitynodeinfocompat);
            super.onInitializeAccessibilityNodeInfo(view, accessibilitynodeinfocompat1);
            accessibilitynodeinfocompat.setClassName(android/support/v4/widget/DrawerLayout.getName());
            accessibilitynodeinfocompat.setSource(view);
            android.view.ViewParent viewparent = ViewCompat.getParentForAccessibility(view);
            if (viewparent instanceof View)
            {
                accessibilitynodeinfocompat.setParent((View)viewparent);
            }
            copyNodeInfoNoChildren(accessibilitynodeinfocompat, accessibilitynodeinfocompat1);
            accessibilitynodeinfocompat1.recycle();
            addChildrenForAccessibility(accessibilitynodeinfocompat, (ViewGroup)view);
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup viewgroup, View view, AccessibilityEvent accessibilityevent)
        {
            if (DrawerLayout.includeChildForAccessibilitiy(view))
            {
                return super.onRequestSendAccessibilityEvent(viewgroup, view, accessibilityevent);
            } else
            {
                return false;
            }
        }

        AccessibilityDelegate()
        {
            this$0 = DrawerLayout.this;
            super();
        }
    }

    final class ChildAccessibilityDelegate extends AccessibilityDelegateCompat
    {

        final DrawerLayout this$0;

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilitynodeinfocompat)
        {
            super.onInitializeAccessibilityNodeInfo(view, accessibilitynodeinfocompat);
            if (!DrawerLayout.includeChildForAccessibilitiy(view))
            {
                accessibilitynodeinfocompat.setParent(null);
            }
        }

        ChildAccessibilityDelegate()
        {
            this$0 = DrawerLayout.this;
            super();
        }
    }

    public static interface DrawerListener
    {

        public abstract void onDrawerClosed(View view);

        public abstract void onDrawerOpened(View view);

        public abstract void onDrawerSlide(View view, float f);

        public abstract void onDrawerStateChanged(int i);
    }

    private static interface EdgeGravity
        extends Annotation
    {
    }

    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams
    {

        public int gravity;
        boolean isPeeking;
        boolean knownOpen;
        float onScreen;

        public LayoutParams(int i, int j)
        {
            super(i, j);
            gravity = 0;
        }

        public LayoutParams(int i, int j, int k)
        {
            this(i, j);
            gravity = k;
        }

        public LayoutParams(Context context, AttributeSet attributeset)
        {
            super(context, attributeset);
            gravity = 0;
            TypedArray typedarray = context.obtainStyledAttributes(attributeset, DrawerLayout.LAYOUT_ATTRS);
            gravity = typedarray.getInt(0, 0);
            typedarray.recycle();
        }

        public LayoutParams(LayoutParams layoutparams)
        {
            super(layoutparams);
            gravity = 0;
            gravity = layoutparams.gravity;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutparams)
        {
            super(layoutparams);
            gravity = 0;
        }

        public LayoutParams(android.view.ViewGroup.MarginLayoutParams marginlayoutparams)
        {
            super(marginlayoutparams);
            gravity = 0;
        }
    }

    private static interface LockMode
        extends Annotation
    {
    }

    protected static class SavedState extends android.view.View.BaseSavedState
    {

        public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

            public SavedState createFromParcel(Parcel parcel)
            {
                return new SavedState(parcel);
            }

            public volatile Object createFromParcel(Parcel parcel)
            {
                return createFromParcel(parcel);
            }

            public SavedState[] newArray(int i)
            {
                return new SavedState[i];
            }

            public volatile Object[] newArray(int i)
            {
                return newArray(i);
            }

        };
        int lockModeLeft;
        int lockModeRight;
        int openDrawerGravity;

        public void writeToParcel(Parcel parcel, int i)
        {
            super.writeToParcel(parcel, i);
            parcel.writeInt(openDrawerGravity);
        }


        public SavedState(Parcel parcel)
        {
            super(parcel);
            openDrawerGravity = 0;
            lockModeLeft = 0;
            lockModeRight = 0;
            openDrawerGravity = parcel.readInt();
        }

        public SavedState(Parcelable parcelable)
        {
            super(parcelable);
            openDrawerGravity = 0;
            lockModeLeft = 0;
            lockModeRight = 0;
        }
    }

    public static abstract class SimpleDrawerListener
        implements DrawerListener
    {

        public void onDrawerClosed(View view)
        {
        }

        public void onDrawerOpened(View view)
        {
        }

        public void onDrawerSlide(View view, float f)
        {
        }

        public void onDrawerStateChanged(int i)
        {
        }

        public SimpleDrawerListener()
        {
        }
    }

    private static interface State
        extends Annotation
    {
    }

    private class ViewDragCallback extends ViewDragHelper.Callback
    {

        private final int mAbsGravity;
        private ViewDragHelper mDragger;
        private final Runnable mPeekRunnable = new _cls1();
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
                LayoutParams layoutparams = (LayoutParams)view.getLayoutParams();
                mDragger.smoothSlideViewTo(view, j, view.getTop());
                layoutparams.isPeeking = true;
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
            ((LayoutParams)view.getLayoutParams()).isPeeking = false;
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


        public ViewDragCallback(int i)
        {
            this$0 = DrawerLayout.this;
            super();
            mAbsGravity = i;
        }
    }


    private static final boolean ALLOW_EDGE_LOCK = false;
    private static final boolean CHILDREN_DISALLOW_INTERCEPT = true;
    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;
    private static final int LAYOUT_ATTRS[] = {
        0x10100b3
    };
    public static final int LOCK_MODE_LOCKED_CLOSED = 1;
    public static final int LOCK_MODE_LOCKED_OPEN = 2;
    public static final int LOCK_MODE_UNLOCKED = 0;
    private static final int MIN_DRAWER_MARGIN = 64;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final int PEEK_DELAY = 160;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "DrawerLayout";
    private static final float TOUCH_SLOP_SENSITIVITY = 1F;
    private final ChildAccessibilityDelegate mChildAccessibilityDelegate;
    private boolean mChildrenCanceledTouch;
    private boolean mDisallowInterceptRequested;
    private int mDrawerState;
    private boolean mFirstLayout;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private final ViewDragCallback mLeftCallback;
    private final ViewDragHelper mLeftDragger;
    private DrawerListener mListener;
    private int mLockModeLeft;
    private int mLockModeRight;
    private int mMinDrawerMargin;
    private final ViewDragCallback mRightCallback;
    private final ViewDragHelper mRightDragger;
    private int mScrimColor;
    private float mScrimOpacity;
    private Paint mScrimPaint;
    private Drawable mShadowLeft;
    private Drawable mShadowRight;
    private CharSequence mTitleLeft;
    private CharSequence mTitleRight;

    public DrawerLayout(Context context)
    {
        this(context, null);
    }

    public DrawerLayout(Context context, AttributeSet attributeset)
    {
        this(context, attributeset, 0);
    }

    public DrawerLayout(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        mChildAccessibilityDelegate = new ChildAccessibilityDelegate();
        mScrimColor = 0x99000000;
        mScrimPaint = new Paint();
        mFirstLayout = true;
        float f = getResources().getDisplayMetrics().density;
        mMinDrawerMargin = (int)(0.5F + 64F * f);
        float f1 = 400F * f;
        mLeftCallback = new ViewDragCallback(3);
        mRightCallback = new ViewDragCallback(5);
        mLeftDragger = ViewDragHelper.create(this, 1.0F, mLeftCallback);
        mLeftDragger.setEdgeTrackingEnabled(1);
        mLeftDragger.setMinVelocity(f1);
        mLeftCallback.setDragger(mLeftDragger);
        mRightDragger = ViewDragHelper.create(this, 1.0F, mRightCallback);
        mRightDragger.setEdgeTrackingEnabled(2);
        mRightDragger.setMinVelocity(f1);
        mRightCallback.setDragger(mRightDragger);
        setFocusableInTouchMode(true);
        ViewCompat.setImportantForAccessibility(this, 1);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
        ViewGroupCompat.setMotionEventSplittingEnabled(this, false);
    }

    private View findVisibleDrawer()
    {
        int i = getChildCount();
        for (int j = 0; j < i; j++)
        {
            View view = getChildAt(j);
            if (isDrawerView(view) && isDrawerVisible(view))
            {
                return view;
            }
        }

        return null;
    }

    static String gravityToString(int i)
    {
        if ((i & 3) == 3)
        {
            return "LEFT";
        }
        if ((i & 5) == 5)
        {
            return "RIGHT";
        } else
        {
            return Integer.toHexString(i);
        }
    }

    private static boolean hasOpaqueBackground(View view)
    {
        Drawable drawable = view.getBackground();
        boolean flag = false;
        if (drawable != null)
        {
            int i = drawable.getOpacity();
            flag = false;
            if (i == -1)
            {
                flag = true;
            }
        }
        return flag;
    }

    private boolean hasPeekingDrawer()
    {
        int i = getChildCount();
        for (int j = 0; j < i; j++)
        {
            if (((LayoutParams)getChildAt(j).getLayoutParams()).isPeeking)
            {
                return true;
            }
        }

        return false;
    }

    private boolean hasVisibleDrawer()
    {
        return findVisibleDrawer() != null;
    }

    private static boolean includeChildForAccessibilitiy(View view)
    {
        return ViewCompat.getImportantForAccessibility(view) != 4 && ViewCompat.getImportantForAccessibility(view) != 2;
    }

    public void addView(View view, int i, android.view.ViewGroup.LayoutParams layoutparams)
    {
        if (i > 0 || i < 0 && getChildCount() > 0)
        {
            ViewCompat.setImportantForAccessibility(view, 4);
            ViewCompat.setAccessibilityDelegate(view, mChildAccessibilityDelegate);
        } else
        {
            ViewCompat.setImportantForAccessibility(view, 1);
        }
        super.addView(view, i, layoutparams);
    }

    void cancelChildViewTouch()
    {
        if (!mChildrenCanceledTouch)
        {
            long l = SystemClock.uptimeMillis();
            MotionEvent motionevent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
            int i = getChildCount();
            for (int j = 0; j < i; j++)
            {
                getChildAt(j).dispatchTouchEvent(motionevent);
            }

            motionevent.recycle();
            mChildrenCanceledTouch = true;
        }
    }

    boolean checkDrawerViewAbsoluteGravity(View view, int i)
    {
        return (i & getDrawerViewAbsoluteGravity(view)) == i;
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutparams)
    {
        return (layoutparams instanceof LayoutParams) && super.checkLayoutParams(layoutparams);
    }

    public void closeDrawer(int i)
    {
        View view = findDrawerWithGravity(i);
        if (view == null)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("No drawer view found with gravity ").append(gravityToString(i)).toString());
        } else
        {
            closeDrawer(view);
            return;
        }
    }

    public void closeDrawer(View view)
    {
        if (!isDrawerView(view))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("View ").append(view).append(" is not a sliding drawer").toString());
        }
        if (mFirstLayout)
        {
            LayoutParams layoutparams = (LayoutParams)view.getLayoutParams();
            layoutparams.onScreen = 0.0F;
            layoutparams.knownOpen = false;
        } else
        if (checkDrawerViewAbsoluteGravity(view, 3))
        {
            mLeftDragger.smoothSlideViewTo(view, -view.getWidth(), view.getTop());
        } else
        {
            mRightDragger.smoothSlideViewTo(view, getWidth(), view.getTop());
        }
        invalidate();
    }

    public void closeDrawers()
    {
        closeDrawers(false);
    }

    void closeDrawers(boolean flag)
    {
        boolean flag1 = false;
        int i = getChildCount();
        int j = 0;
        while (j < i) 
        {
            View view = getChildAt(j);
            LayoutParams layoutparams = (LayoutParams)view.getLayoutParams();
            if (isDrawerView(view) && (!flag || layoutparams.isPeeking))
            {
                int k = view.getWidth();
                if (checkDrawerViewAbsoluteGravity(view, 3))
                {
                    flag1 |= mLeftDragger.smoothSlideViewTo(view, -k, view.getTop());
                } else
                {
                    flag1 |= mRightDragger.smoothSlideViewTo(view, getWidth(), view.getTop());
                }
                layoutparams.isPeeking = false;
            }
            j++;
        }
        mLeftCallback.removeCallbacks();
        mRightCallback.removeCallbacks();
        if (flag1)
        {
            invalidate();
        }
    }

    public void computeScroll()
    {
        int i = getChildCount();
        float f = 0.0F;
        for (int j = 0; j < i; j++)
        {
            f = Math.max(f, ((LayoutParams)getChildAt(j).getLayoutParams()).onScreen);
        }

        mScrimOpacity = f;
        if (mLeftDragger.continueSettling(true) | mRightDragger.continueSettling(true))
        {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    void dispatchOnDrawerClosed(View view)
    {
        LayoutParams layoutparams = (LayoutParams)view.getLayoutParams();
        if (layoutparams.knownOpen)
        {
            layoutparams.knownOpen = false;
            if (mListener != null)
            {
                mListener.onDrawerClosed(view);
            }
            View view1 = getChildAt(0);
            if (view1 != null)
            {
                ViewCompat.setImportantForAccessibility(view1, 1);
            }
            ViewCompat.setImportantForAccessibility(view, 4);
            if (hasWindowFocus())
            {
                View view2 = getRootView();
                if (view2 != null)
                {
                    view2.sendAccessibilityEvent(32);
                }
            }
        }
    }

    void dispatchOnDrawerOpened(View view)
    {
        LayoutParams layoutparams = (LayoutParams)view.getLayoutParams();
        if (!layoutparams.knownOpen)
        {
            layoutparams.knownOpen = true;
            if (mListener != null)
            {
                mListener.onDrawerOpened(view);
            }
            View view1 = getChildAt(0);
            if (view1 != null)
            {
                ViewCompat.setImportantForAccessibility(view1, 4);
            }
            ViewCompat.setImportantForAccessibility(view, 1);
            sendAccessibilityEvent(32);
            view.requestFocus();
        }
    }

    void dispatchOnDrawerSlide(View view, float f)
    {
        if (mListener != null)
        {
            mListener.onDrawerSlide(view, f);
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long l)
    {
        int i = getHeight();
        boolean flag = isContentView(view);
        int j = getWidth();
        int k = canvas.save();
        int i1 = 0;
        if (flag)
        {
            int j3 = getChildCount();
            int k3 = 0;
            while (k3 < j3) 
            {
                View view1 = getChildAt(k3);
                if (view1 != view && view1.getVisibility() == 0 && hasOpaqueBackground(view1) && isDrawerView(view1) && view1.getHeight() >= i)
                {
                    if (checkDrawerViewAbsoluteGravity(view1, 3))
                    {
                        int i4 = view1.getRight();
                        if (i4 > i1)
                        {
                            i1 = i4;
                        }
                    } else
                    {
                        int l3 = view1.getLeft();
                        if (l3 < j)
                        {
                            j = l3;
                        }
                    }
                }
                k3++;
            }
            canvas.clipRect(i1, 0, j, getHeight());
        }
        boolean flag1 = super.drawChild(canvas, view, l);
        canvas.restoreToCount(k);
        if (mScrimOpacity > 0.0F && flag)
        {
            int i3 = (int)((float)((0xff000000 & mScrimColor) >>> 24) * mScrimOpacity) << 24 | 0xffffff & mScrimColor;
            mScrimPaint.setColor(i3);
            canvas.drawRect(i1, 0.0F, j, getHeight(), mScrimPaint);
        } else
        {
            if (mShadowLeft != null && checkDrawerViewAbsoluteGravity(view, 3))
            {
                int j2 = mShadowLeft.getIntrinsicWidth();
                int k2 = view.getRight();
                int l2 = mLeftDragger.getEdgeSize();
                float f1 = Math.max(0.0F, Math.min((float)k2 / (float)l2, 1.0F));
                mShadowLeft.setBounds(k2, view.getTop(), k2 + j2, view.getBottom());
                mShadowLeft.setAlpha((int)(255F * f1));
                mShadowLeft.draw(canvas);
                return flag1;
            }
            if (mShadowRight != null && checkDrawerViewAbsoluteGravity(view, 5))
            {
                int j1 = mShadowRight.getIntrinsicWidth();
                int k1 = view.getLeft();
                int l1 = getWidth() - k1;
                int i2 = mRightDragger.getEdgeSize();
                float f = Math.max(0.0F, Math.min((float)l1 / (float)i2, 1.0F));
                mShadowRight.setBounds(k1 - j1, view.getTop(), k1, view.getBottom());
                mShadowRight.setAlpha((int)(255F * f));
                mShadowRight.draw(canvas);
                return flag1;
            }
        }
        return flag1;
    }

    View findDrawerWithGravity(int i)
    {
        int j = 7 & GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        int k = getChildCount();
        for (int l = 0; l < k; l++)
        {
            View view = getChildAt(l);
            if ((7 & getDrawerViewAbsoluteGravity(view)) == j)
            {
                return view;
            }
        }

        return null;
    }

    View findOpenDrawer()
    {
        int i = getChildCount();
        for (int j = 0; j < i; j++)
        {
            View view = getChildAt(j);
            if (((LayoutParams)view.getLayoutParams()).knownOpen)
            {
                return view;
            }
        }

        return null;
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams()
    {
        return new LayoutParams(-1, -1);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeset)
    {
        return new LayoutParams(getContext(), attributeset);
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutparams)
    {
        if (layoutparams instanceof LayoutParams)
        {
            return new LayoutParams((LayoutParams)layoutparams);
        }
        if (layoutparams instanceof android.view.ViewGroup.MarginLayoutParams)
        {
            return new LayoutParams((android.view.ViewGroup.MarginLayoutParams)layoutparams);
        } else
        {
            return new LayoutParams(layoutparams);
        }
    }

    public int getDrawerLockMode(int i)
    {
        int j = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        if (j == 3)
        {
            return mLockModeLeft;
        }
        if (j == 5)
        {
            return mLockModeRight;
        } else
        {
            return 0;
        }
    }

    public int getDrawerLockMode(View view)
    {
        int i = getDrawerViewAbsoluteGravity(view);
        if (i == 3)
        {
            return mLockModeLeft;
        }
        if (i == 5)
        {
            return mLockModeRight;
        } else
        {
            return 0;
        }
    }

    public CharSequence getDrawerTitle(int i)
    {
        int j = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        if (j == 3)
        {
            return mTitleLeft;
        }
        if (j == 5)
        {
            return mTitleRight;
        } else
        {
            return null;
        }
    }

    int getDrawerViewAbsoluteGravity(View view)
    {
        return GravityCompat.getAbsoluteGravity(((LayoutParams)view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(this));
    }

    float getDrawerViewOffset(View view)
    {
        return ((LayoutParams)view.getLayoutParams()).onScreen;
    }

    boolean isContentView(View view)
    {
        return ((LayoutParams)view.getLayoutParams()).gravity == 0;
    }

    public boolean isDrawerOpen(int i)
    {
        View view = findDrawerWithGravity(i);
        if (view != null)
        {
            return isDrawerOpen(view);
        } else
        {
            return false;
        }
    }

    public boolean isDrawerOpen(View view)
    {
        if (!isDrawerView(view))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("View ").append(view).append(" is not a drawer").toString());
        } else
        {
            return ((LayoutParams)view.getLayoutParams()).knownOpen;
        }
    }

    boolean isDrawerView(View view)
    {
        return (7 & GravityCompat.getAbsoluteGravity(((LayoutParams)view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(view))) != 0;
    }

    public boolean isDrawerVisible(int i)
    {
        View view = findDrawerWithGravity(i);
        if (view != null)
        {
            return isDrawerVisible(view);
        } else
        {
            return false;
        }
    }

    public boolean isDrawerVisible(View view)
    {
        if (!isDrawerView(view))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("View ").append(view).append(" is not a drawer").toString());
        }
        return ((LayoutParams)view.getLayoutParams()).onScreen > 0.0F;
    }

    void moveDrawerToOffset(View view, float f)
    {
        float f1 = getDrawerViewOffset(view);
        int i = view.getWidth();
        int j = (int)(f1 * (float)i);
        int k = (int)(f * (float)i) - j;
        if (!checkDrawerViewAbsoluteGravity(view, 3))
        {
            k = -k;
        }
        view.offsetLeftAndRight(k);
        setDrawerViewOffset(view, f);
    }

    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        mFirstLayout = true;
    }

    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mFirstLayout = true;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionevent)
    {
        int i;
        boolean flag;
        boolean flag1;
        i = MotionEventCompat.getActionMasked(motionevent);
        flag = mLeftDragger.shouldInterceptTouchEvent(motionevent) | mRightDragger.shouldInterceptTouchEvent(motionevent);
        flag1 = false;
        i;
        JVM INSTR tableswitch 0 3: default 56
    //                   0 92
    //                   1 213
    //                   2 175
    //                   3 213;
           goto _L1 _L2 _L3 _L4 _L3
_L1:
        if (flag || flag1 || hasPeekingDrawer()) goto _L6; else goto _L5
_L5:
        boolean flag2;
        boolean flag3;
        flag3 = mChildrenCanceledTouch;
        flag2 = false;
        if (!flag3) goto _L7; else goto _L6
_L6:
        flag2 = true;
_L7:
        return flag2;
_L2:
        float f = motionevent.getX();
        float f1 = motionevent.getY();
        mInitialMotionX = f;
        mInitialMotionY = f1;
        int j = mScrimOpacity != 0.0F;
        flag1 = false;
        if (j > 0)
        {
            boolean flag5 = isContentView(mLeftDragger.findTopChildUnder((int)f, (int)f1));
            flag1 = false;
            if (flag5)
            {
                flag1 = true;
            }
        }
        mDisallowInterceptRequested = false;
        mChildrenCanceledTouch = false;
        continue; /* Loop/switch isn't completed */
_L4:
        boolean flag4 = mLeftDragger.checkTouchSlop(3);
        flag1 = false;
        if (flag4)
        {
            mLeftCallback.removeCallbacks();
            mRightCallback.removeCallbacks();
            flag1 = false;
        }
        continue; /* Loop/switch isn't completed */
_L3:
        closeDrawers(true);
        mDisallowInterceptRequested = false;
        mChildrenCanceledTouch = false;
        flag1 = false;
        if (true) goto _L1; else goto _L8
_L8:
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        if (i == 4 && hasVisibleDrawer())
        {
            KeyEventCompat.startTracking(keyevent);
            return true;
        } else
        {
            return super.onKeyDown(i, keyevent);
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyevent)
    {
        if (i == 4)
        {
            View view = findVisibleDrawer();
            if (view != null && getDrawerLockMode(view) == 0)
            {
                closeDrawers();
            }
            return view != null;
        } else
        {
            return super.onKeyUp(i, keyevent);
        }
    }

    protected void onLayout(boolean flag, int i, int j, int k, int l)
    {
        int i1;
        int j1;
        int k1;
        mInLayout = true;
        i1 = k - i;
        j1 = getChildCount();
        k1 = 0;
_L2:
        View view;
        if (k1 >= j1)
        {
            break MISSING_BLOCK_LABEL_454;
        }
        view = getChildAt(k1);
        if (view.getVisibility() != 8)
        {
            break; /* Loop/switch isn't completed */
        }
_L3:
        k1++;
        if (true) goto _L2; else goto _L1
_L1:
        LayoutParams layoutparams;
label0:
        {
            layoutparams = (LayoutParams)view.getLayoutParams();
            if (!isContentView(view))
            {
                break label0;
            }
            view.layout(layoutparams.leftMargin, layoutparams.topMargin, layoutparams.leftMargin + view.getMeasuredWidth(), layoutparams.topMargin + view.getMeasuredHeight());
        }
          goto _L3
        int l1;
        int i2;
        int j2;
        l1 = view.getMeasuredWidth();
        i2 = view.getMeasuredHeight();
        float f;
        boolean flag1;
        if (checkDrawerViewAbsoluteGravity(view, 3))
        {
            j2 = -l1 + (int)((float)l1 * layoutparams.onScreen);
            f = (float)(l1 + j2) / (float)l1;
        } else
        {
            j2 = i1 - (int)((float)l1 * layoutparams.onScreen);
            f = (float)(i1 - j2) / (float)l1;
        }
        if (f != layoutparams.onScreen)
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        0x70 & layoutparams.gravity;
        JVM INSTR lookupswitch 2: default 212
    //                   16: 362
    //                   80: 319;
           goto _L4 _L5 _L6
_L5:
        break MISSING_BLOCK_LABEL_362;
_L4:
        view.layout(j2, layoutparams.topMargin, j2 + l1, i2 + layoutparams.topMargin);
_L7:
        if (flag1)
        {
            setDrawerViewOffset(view, f);
        }
        int k2;
        int l2;
        int i3;
        int j3;
        if (layoutparams.onScreen > 0.0F)
        {
            i3 = 0;
        } else
        {
            i3 = 4;
        }
        if (view.getVisibility() != i3)
        {
            view.setVisibility(i3);
        }
          goto _L3
_L6:
        j3 = l - j;
        view.layout(j2, j3 - layoutparams.bottomMargin - view.getMeasuredHeight(), j2 + l1, j3 - layoutparams.bottomMargin);
          goto _L7
        k2 = l - j;
        l2 = (k2 - i2) / 2;
        if (l2 < layoutparams.topMargin)
        {
            l2 = layoutparams.topMargin;
        } else
        if (l2 + i2 > k2 - layoutparams.bottomMargin)
        {
            l2 = k2 - layoutparams.bottomMargin - i2;
        }
        view.layout(j2, l2, j2 + l1, l2 + i2);
          goto _L7
        mInLayout = false;
        mFirstLayout = false;
        return;
          goto _L3
    }

    protected void onMeasure(int i, int j)
    {
        int k;
        int l;
        int i1;
        int j1;
        k = android.view.View.MeasureSpec.getMode(i);
        l = android.view.View.MeasureSpec.getMode(j);
        i1 = android.view.View.MeasureSpec.getSize(i);
        j1 = android.view.View.MeasureSpec.getSize(j);
        if (k != 0x40000000 || l != 0x40000000)
        {
            if (!isInEditMode())
            {
                break MISSING_BLOCK_LABEL_133;
            }
            break MISSING_BLOCK_LABEL_45;
        }
_L1:
        setMeasuredDimension(i1, j1);
        int k1 = getChildCount();
        int l1 = 0;
        while (l1 < k1) 
        {
            View view = getChildAt(l1);
            if (view.getVisibility() != 8)
            {
                LayoutParams layoutparams = (LayoutParams)view.getLayoutParams();
                if (isContentView(view))
                {
                    view.measure(android.view.View.MeasureSpec.makeMeasureSpec(i1 - layoutparams.leftMargin - layoutparams.rightMargin, 0x40000000), android.view.View.MeasureSpec.makeMeasureSpec(j1 - layoutparams.topMargin - layoutparams.bottomMargin, 0x40000000));
                } else
                if (isDrawerView(view))
                {
                    int i2 = 7 & getDrawerViewAbsoluteGravity(view);
                    if ((0 & i2) != 0)
                    {
                        throw new IllegalStateException((new StringBuilder()).append("Child drawer has absolute gravity ").append(gravityToString(i2)).append(" but this ").append("DrawerLayout").append(" already has a ").append("drawer view along that edge").toString());
                    }
                    view.measure(getChildMeasureSpec(i, mMinDrawerMargin + layoutparams.leftMargin + layoutparams.rightMargin, layoutparams.width), getChildMeasureSpec(j, layoutparams.topMargin + layoutparams.bottomMargin, layoutparams.height));
                } else
                {
                    throw new IllegalStateException((new StringBuilder()).append("Child ").append(view).append(" at index ").append(l1).append(" does not have a valid layout_gravity - must be Gravity.LEFT, ").append("Gravity.RIGHT or Gravity.NO_GRAVITY").toString());
                }
            }
            l1++;
        }
        break MISSING_BLOCK_LABEL_398;
        if (k != 0x80000000 && k == 0)
        {
            i1 = 300;
        }
        if (l != 0x80000000 && l == 0)
        {
            j1 = 300;
        }
          goto _L1
        throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
    }

    protected void onRestoreInstanceState(Parcelable parcelable)
    {
        SavedState savedstate = (SavedState)parcelable;
        super.onRestoreInstanceState(savedstate.getSuperState());
        if (savedstate.openDrawerGravity != 0)
        {
            View view = findDrawerWithGravity(savedstate.openDrawerGravity);
            if (view != null)
            {
                openDrawer(view);
            }
        }
        setDrawerLockMode(savedstate.lockModeLeft, 3);
        setDrawerLockMode(savedstate.lockModeRight, 5);
    }

    protected Parcelable onSaveInstanceState()
    {
        SavedState savedstate;
        int i;
        int j;
        savedstate = new SavedState(super.onSaveInstanceState());
        i = getChildCount();
        j = 0;
_L2:
        View view;
        if (j >= i)
        {
            break MISSING_BLOCK_LABEL_73;
        }
        view = getChildAt(j);
        if (isDrawerView(view))
        {
            break; /* Loop/switch isn't completed */
        }
_L4:
        j++;
        LayoutParams layoutparams;
        if (true) goto _L2; else goto _L1
_L1:
        if (!(layoutparams = (LayoutParams)view.getLayoutParams()).knownOpen) goto _L4; else goto _L3
_L3:
        savedstate.openDrawerGravity = layoutparams.gravity;
        savedstate.lockModeLeft = mLockModeLeft;
        savedstate.lockModeRight = mLockModeRight;
        return savedstate;
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        mLeftDragger.processTouchEvent(motionevent);
        mRightDragger.processTouchEvent(motionevent);
        switch (0xff & motionevent.getAction())
        {
        case 2: // '\002'
        default:
            return true;

        case 0: // '\0'
            float f4 = motionevent.getX();
            float f5 = motionevent.getY();
            mInitialMotionX = f4;
            mInitialMotionY = f5;
            mDisallowInterceptRequested = false;
            mChildrenCanceledTouch = false;
            return true;

        case 1: // '\001'
            float f = motionevent.getX();
            float f1 = motionevent.getY();
            boolean flag = true;
            View view = mLeftDragger.findTopChildUnder((int)f, (int)f1);
            if (view != null && isContentView(view))
            {
                float f2 = f - mInitialMotionX;
                float f3 = f1 - mInitialMotionY;
                int i = mLeftDragger.getTouchSlop();
                if (f2 * f2 + f3 * f3 < (float)(i * i))
                {
                    View view1 = findOpenDrawer();
                    if (view1 != null)
                    {
                        if (getDrawerLockMode(view1) == 2)
                        {
                            flag = true;
                        } else
                        {
                            flag = false;
                        }
                    }
                }
            }
            closeDrawers(flag);
            mDisallowInterceptRequested = false;
            return true;

        case 3: // '\003'
            closeDrawers(true);
            mDisallowInterceptRequested = false;
            mChildrenCanceledTouch = false;
            return true;
        }
    }

    public void openDrawer(int i)
    {
        View view = findDrawerWithGravity(i);
        if (view == null)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("No drawer view found with gravity ").append(gravityToString(i)).toString());
        } else
        {
            openDrawer(view);
            return;
        }
    }

    public void openDrawer(View view)
    {
        if (!isDrawerView(view))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("View ").append(view).append(" is not a sliding drawer").toString());
        }
        if (mFirstLayout)
        {
            LayoutParams layoutparams = (LayoutParams)view.getLayoutParams();
            layoutparams.onScreen = 1.0F;
            layoutparams.knownOpen = true;
        } else
        if (checkDrawerViewAbsoluteGravity(view, 3))
        {
            mLeftDragger.smoothSlideViewTo(view, 0, view.getTop());
        } else
        {
            mRightDragger.smoothSlideViewTo(view, getWidth() - view.getWidth(), view.getTop());
        }
        invalidate();
    }

    public void requestDisallowInterceptTouchEvent(boolean flag)
    {
        super.requestDisallowInterceptTouchEvent(flag);
        mDisallowInterceptRequested = flag;
        if (flag)
        {
            closeDrawers(true);
        }
    }

    public void requestLayout()
    {
        if (!mInLayout)
        {
            super.requestLayout();
        }
    }

    public void setDrawerListener(DrawerListener drawerlistener)
    {
        mListener = drawerlistener;
    }

    public void setDrawerLockMode(int i)
    {
        setDrawerLockMode(i, 3);
        setDrawerLockMode(i, 5);
    }

    public void setDrawerLockMode(int i, int j)
    {
        int k;
        k = GravityCompat.getAbsoluteGravity(j, ViewCompat.getLayoutDirection(this));
        if (k == 3)
        {
            mLockModeLeft = i;
        } else
        if (k == 5)
        {
            mLockModeRight = i;
        }
        if (i != 0)
        {
            ViewDragHelper viewdraghelper;
            if (k == 3)
            {
                viewdraghelper = mLeftDragger;
            } else
            {
                viewdraghelper = mRightDragger;
            }
            viewdraghelper.cancel();
        }
        i;
        JVM INSTR tableswitch 1 2: default 64
    //                   1 106
    //                   2 87;
           goto _L1 _L2 _L3
_L1:
        View view1;
        return;
_L3:
        View view;
        if ((view1 = findDrawerWithGravity(k)) != null)
        {
            openDrawer(view1);
            return;
        }
        continue; /* Loop/switch isn't completed */
_L2:
        if ((view = findDrawerWithGravity(k)) != null)
        {
            closeDrawer(view);
            return;
        }
        if (true) goto _L1; else goto _L4
_L4:
    }

    public void setDrawerLockMode(int i, View view)
    {
        if (!isDrawerView(view))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("View ").append(view).append(" is not a ").append("drawer with appropriate layout_gravity").toString());
        } else
        {
            setDrawerLockMode(i, ((LayoutParams)view.getLayoutParams()).gravity);
            return;
        }
    }

    public void setDrawerShadow(int i, int j)
    {
        setDrawerShadow(getResources().getDrawable(i), j);
    }

    public void setDrawerShadow(Drawable drawable, int i)
    {
        int j = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        if ((j & 3) == 3)
        {
            mShadowLeft = drawable;
            invalidate();
        }
        if ((j & 5) == 5)
        {
            mShadowRight = drawable;
            invalidate();
        }
    }

    public void setDrawerTitle(int i, CharSequence charsequence)
    {
        int j = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        if (j == 3)
        {
            mTitleLeft = charsequence;
        } else
        if (j == 5)
        {
            mTitleRight = charsequence;
            return;
        }
    }

    void setDrawerViewOffset(View view, float f)
    {
        LayoutParams layoutparams = (LayoutParams)view.getLayoutParams();
        if (f == layoutparams.onScreen)
        {
            return;
        } else
        {
            layoutparams.onScreen = f;
            dispatchOnDrawerSlide(view, f);
            return;
        }
    }

    public void setScrimColor(int i)
    {
        mScrimColor = i;
        invalidate();
    }

    void updateDrawerState(int i, int j, View view)
    {
        LayoutParams layoutparams;
        int k = mLeftDragger.getViewDragState();
        int l = mRightDragger.getViewDragState();
        int i1;
        if (k == 1 || l == 1)
        {
            i1 = 1;
        } else
        if (k == 2 || l == 2)
        {
            i1 = 2;
        } else
        {
            i1 = 0;
        }
        if (view == null || j != 0) goto _L2; else goto _L1
_L1:
        layoutparams = (LayoutParams)view.getLayoutParams();
        if (layoutparams.onScreen != 0.0F) goto _L4; else goto _L3
_L3:
        dispatchOnDrawerClosed(view);
_L2:
        if (i1 != mDrawerState)
        {
            mDrawerState = i1;
            if (mListener != null)
            {
                mListener.onDrawerStateChanged(i1);
            }
        }
        return;
_L4:
        if (layoutparams.onScreen == 1.0F)
        {
            dispatchOnDrawerOpened(view);
        }
        if (true) goto _L2; else goto _L5
_L5:
    }





    // Unreferenced inner class android/support/v4/widget/DrawerLayout$ViewDragCallback$1

/* anonymous class */
    class ViewDragCallback._cls1
        implements Runnable
    {

        final ViewDragCallback this$1;

        public void run()
        {
            peekDrawer();
        }

            
            {
                this$1 = ViewDragCallback.this;
                super();
            }
    }

}
