// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

public class ListPopupWindow
{
    private static class DropDownListView extends ListView
    {

        public static final int INVALID_POSITION = -1;
        static final int NO_POSITION = -1;
        private static final String TAG = "ListPopupWindow.DropDownListView";
        private boolean mHijackFocus;
        private boolean mListSelectionHidden;

        private int lookForSelectablePosition(int i, boolean flag)
        {
            ListAdapter listadapter = getAdapter();
            if (listadapter != null && !isInTouchMode()) goto _L2; else goto _L1
_L1:
            return -1;
_L2:
            int j;
            int k;
            j = listadapter.getCount();
            if (getAdapter().areAllItemsEnabled())
            {
                continue; /* Loop/switch isn't completed */
            }
            if (flag)
            {
                for (k = Math.max(0, i); k < j && !listadapter.isEnabled(k); k++) { }
            } else
            {
                for (k = Math.min(i, j - 1); k >= 0 && !listadapter.isEnabled(k); k--) { }
            }
            if (k < 0 || k >= j) goto _L1; else goto _L3
_L3:
            return k;
            if (i < 0 || i >= j) goto _L1; else goto _L4
_L4:
            return i;
        }

        public boolean hasFocus()
        {
            return mHijackFocus || super.hasFocus();
        }

        public boolean hasWindowFocus()
        {
            return mHijackFocus || super.hasWindowFocus();
        }

        public boolean isFocused()
        {
            return mHijackFocus || super.isFocused();
        }

        public boolean isInTouchMode()
        {
            return mHijackFocus && mListSelectionHidden || super.isInTouchMode();
        }

        final int measureHeightOfChildrenCompat(int i, int j, int k, int l, int i1)
        {
            int j1;
            int k1;
            int l1;
            Drawable drawable;
            ListAdapter listadapter;
            j1 = getListPaddingTop();
            k1 = getListPaddingBottom();
            getListPaddingLeft();
            getListPaddingRight();
            l1 = getDividerHeight();
            drawable = getDivider();
            listadapter = getAdapter();
            if (listadapter != null) goto _L2; else goto _L1
_L1:
            int k2 = j1 + k1;
_L4:
            return k2;
_L2:
            int i2 = j1 + k1;
            int j2;
            View view;
            int l2;
            int i3;
            if (l1 > 0 && drawable != null)
            {
                j2 = l1;
            } else
            {
                j2 = 0;
            }
            k2 = 0;
            view = null;
            l2 = 0;
            i3 = listadapter.getCount();
            for (int j3 = 0; j3 < i3; j3++)
            {
                int k3 = listadapter.getItemViewType(j3);
                if (k3 != l2)
                {
                    view = null;
                    l2 = k3;
                }
                view = listadapter.getView(j3, view, this);
                android.view.ViewGroup.LayoutParams layoutparams = view.getLayoutParams();
                int l3;
                if (layoutparams != null && layoutparams.height > 0)
                {
                    l3 = android.view.View.MeasureSpec.makeMeasureSpec(layoutparams.height, 0x40000000);
                } else
                {
                    l3 = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
                }
                view.measure(i, l3);
                if (j3 > 0)
                {
                    i2 += j2;
                }
                i2 += view.getMeasuredHeight();
                if (i2 >= l)
                {
                    if (i1 < 0 || j3 <= i1 || k2 <= 0 || i2 == l)
                    {
                        return l;
                    }
                    continue; /* Loop/switch isn't completed */
                }
                if (i1 >= 0 && j3 >= i1)
                {
                    k2 = i2;
                }
            }

            return i2;
            if (true) goto _L4; else goto _L3
_L3:
        }


/*
        static boolean access$502(DropDownListView dropdownlistview, boolean flag)
        {
            dropdownlistview.mListSelectionHidden = flag;
            return flag;
        }

*/


        public DropDownListView(Context context, boolean flag)
        {
            super(context, null, android.support.v7.appcompat.R.attr.dropDownListViewStyle);
            mHijackFocus = flag;
            setCacheColorHint(0);
        }
    }

    private class ListSelectorHider
        implements Runnable
    {

        final ListPopupWindow this$0;

        public void run()
        {
            clearListSelection();
        }

        private ListSelectorHider()
        {
            this$0 = ListPopupWindow.this;
            super();
        }

    }

    private class PopupDataSetObserver extends DataSetObserver
    {

        final ListPopupWindow this$0;

        public void onChanged()
        {
            if (isShowing())
            {
                show();
            }
        }

        public void onInvalidated()
        {
            dismiss();
        }

        private PopupDataSetObserver()
        {
            this$0 = ListPopupWindow.this;
            super();
        }

    }

    private class PopupScrollListener
        implements android.widget.AbsListView.OnScrollListener
    {

        final ListPopupWindow this$0;

        public void onScroll(AbsListView abslistview, int i, int j, int k)
        {
        }

        public void onScrollStateChanged(AbsListView abslistview, int i)
        {
            if (i == 1 && !isInputMethodNotNeeded() && mPopup.getContentView() != null)
            {
                mHandler.removeCallbacks(mResizePopupRunnable);
                mResizePopupRunnable.run();
            }
        }

        private PopupScrollListener()
        {
            this$0 = ListPopupWindow.this;
            super();
        }

    }

    private class PopupTouchInterceptor
        implements android.view.View.OnTouchListener
    {

        final ListPopupWindow this$0;

        public boolean onTouch(View view, MotionEvent motionevent)
        {
            int i;
            int j;
            int k;
            i = motionevent.getAction();
            j = (int)motionevent.getX();
            k = (int)motionevent.getY();
            if (i != 0 || mPopup == null || !mPopup.isShowing() || j < 0 || j >= mPopup.getWidth() || k < 0 || k >= mPopup.getHeight()) goto _L2; else goto _L1
_L1:
            mHandler.postDelayed(mResizePopupRunnable, 250L);
_L4:
            return false;
_L2:
            if (i == 1)
            {
                mHandler.removeCallbacks(mResizePopupRunnable);
            }
            if (true) goto _L4; else goto _L3
_L3:
        }

        private PopupTouchInterceptor()
        {
            this$0 = ListPopupWindow.this;
            super();
        }

    }

    private class ResizePopupRunnable
        implements Runnable
    {

        final ListPopupWindow this$0;

        public void run()
        {
            if (mDropDownList != null && mDropDownList.getCount() > mDropDownList.getChildCount() && mDropDownList.getChildCount() <= mListItemExpandMaximum)
            {
                mPopup.setInputMethodMode(2);
                show();
            }
        }

        private ResizePopupRunnable()
        {
            this$0 = ListPopupWindow.this;
            super();
        }

    }


    private static final boolean DEBUG = false;
    private static final int EXPAND_LIST_TIMEOUT = 250;
    public static final int FILL_PARENT = -1;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    private static final String TAG = "ListPopupWindow";
    public static final int WRAP_CONTENT = -2;
    private ListAdapter mAdapter;
    private Context mContext;
    private boolean mDropDownAlwaysVisible;
    private View mDropDownAnchorView;
    private int mDropDownHeight;
    private int mDropDownHorizontalOffset;
    private DropDownListView mDropDownList;
    private Drawable mDropDownListHighlight;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;
    private int mDropDownWidth;
    private boolean mForceIgnoreOutsideTouch;
    private Handler mHandler;
    private final ListSelectorHider mHideSelector;
    private android.widget.AdapterView.OnItemClickListener mItemClickListener;
    private android.widget.AdapterView.OnItemSelectedListener mItemSelectedListener;
    private int mLayoutDirection;
    int mListItemExpandMaximum;
    private boolean mModal;
    private DataSetObserver mObserver;
    private PopupWindow mPopup;
    private int mPromptPosition;
    private View mPromptView;
    private final ResizePopupRunnable mResizePopupRunnable;
    private final PopupScrollListener mScrollListener;
    private Runnable mShowDropDownRunnable;
    private Rect mTempRect;
    private final PopupTouchInterceptor mTouchInterceptor;

    public ListPopupWindow(Context context)
    {
        this(context, null, android.support.v7.appcompat.R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(Context context, AttributeSet attributeset)
    {
        this(context, attributeset, android.support.v7.appcompat.R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(Context context, AttributeSet attributeset, int i)
    {
        mDropDownHeight = -2;
        mDropDownWidth = -2;
        mDropDownAlwaysVisible = false;
        mForceIgnoreOutsideTouch = false;
        mListItemExpandMaximum = 0x7fffffff;
        mPromptPosition = 0;
        mResizePopupRunnable = new ResizePopupRunnable();
        mTouchInterceptor = new PopupTouchInterceptor();
        mScrollListener = new PopupScrollListener();
        mHideSelector = new ListSelectorHider();
        mHandler = new Handler();
        mTempRect = new Rect();
        mContext = context;
        mPopup = new PopupWindow(context, attributeset, i);
        mPopup.setInputMethodMode(1);
        java.util.Locale _tmp = mContext.getResources().getConfiguration().locale;
    }

    private int buildDropDown()
    {
        if (mDropDownList != null) goto _L2; else goto _L1
_L1:
        int i;
        int j;
        int k;
        Object obj;
        View view1;
        LinearLayout linearlayout;
        android.widget.LinearLayout.LayoutParams layoutparams1;
        Context context = mContext;
        mShowDropDownRunnable = new Runnable() {

            final ListPopupWindow this$0;

            public void run()
            {
                View view2 = getAnchorView();
                if (view2 != null && view2.getWindowToken() != null)
                {
                    show();
                }
            }

            
            {
                this$0 = ListPopupWindow.this;
                super();
            }
        };
        Drawable drawable;
        boolean flag1;
        android.widget.LinearLayout.LayoutParams layoutparams2;
        if (!mModal)
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        mDropDownList = new DropDownListView(context, flag1);
        if (mDropDownListHighlight != null)
        {
            mDropDownList.setSelector(mDropDownListHighlight);
        }
        mDropDownList.setAdapter(mAdapter);
        mDropDownList.setOnItemClickListener(mItemClickListener);
        mDropDownList.setFocusable(true);
        mDropDownList.setFocusableInTouchMode(true);
        mDropDownList.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {

            final ListPopupWindow this$0;

            public void onItemSelected(AdapterView adapterview, View view2, int j1, long l1)
            {
                if (j1 != -1)
                {
                    DropDownListView dropdownlistview = mDropDownList;
                    if (dropdownlistview != null)
                    {
                        dropdownlistview.mListSelectionHidden = false;
                    }
                }
            }

            public void onNothingSelected(AdapterView adapterview)
            {
            }

            
            {
                this$0 = ListPopupWindow.this;
                super();
            }
        });
        mDropDownList.setOnScrollListener(mScrollListener);
        if (mItemSelectedListener != null)
        {
            mDropDownList.setOnItemSelectedListener(mItemSelectedListener);
        }
        obj = mDropDownList;
        view1 = mPromptView;
        i = 0;
        if (view1 == null) goto _L4; else goto _L3
_L3:
        linearlayout = new LinearLayout(context);
        linearlayout.setOrientation(1);
        layoutparams1 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
        mPromptPosition;
        JVM INSTR tableswitch 0 1: default 224
    //                   0 445
    //                   1 426;
           goto _L5 _L6 _L7
_L5:
        Log.e("ListPopupWindow", (new StringBuilder()).append("Invalid hint position ").append(mPromptPosition).toString());
_L16:
        view1.measure(android.view.View.MeasureSpec.makeMeasureSpec(mDropDownWidth, 0x80000000), 0);
        layoutparams2 = (android.widget.LinearLayout.LayoutParams)view1.getLayoutParams();
        i = view1.getMeasuredHeight() + layoutparams2.topMargin + layoutparams2.bottomMargin;
        obj = linearlayout;
_L4:
        mPopup.setContentView(((View) (obj)));
_L9:
        drawable = mPopup.getBackground();
        View view;
        android.widget.LinearLayout.LayoutParams layoutparams;
        boolean flag;
        if (drawable != null)
        {
            drawable.getPadding(mTempRect);
            j = mTempRect.top + mTempRect.bottom;
            if (!mDropDownVerticalOffsetSet)
            {
                mDropDownVerticalOffset = -mTempRect.top;
            }
        } else
        {
            mTempRect.setEmpty();
            j = 0;
        }
        if (mPopup.getInputMethodMode() == 2)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        k = getMaxAvailableHeight(getAnchorView(), mDropDownVerticalOffset, flag);
        if (mDropDownAlwaysVisible || mDropDownHeight == -1)
        {
            return k + j;
        }
          goto _L8
_L7:
        linearlayout.addView(((View) (obj)), layoutparams1);
        linearlayout.addView(view1);
        continue; /* Loop/switch isn't completed */
_L6:
        linearlayout.addView(view1);
        linearlayout.addView(((View) (obj)), layoutparams1);
        continue; /* Loop/switch isn't completed */
_L2:
        (ViewGroup)mPopup.getContentView();
        view = mPromptView;
        i = 0;
        if (view != null)
        {
            layoutparams = (android.widget.LinearLayout.LayoutParams)view.getLayoutParams();
            i = view.getMeasuredHeight() + layoutparams.topMargin + layoutparams.bottomMargin;
        }
          goto _L9
_L8:
        mDropDownWidth;
        JVM INSTR tableswitch -2 -1: default 560
    //                   -2 605
    //                   -1 645;
           goto _L10 _L11 _L12
_L10:
        int l = android.view.View.MeasureSpec.makeMeasureSpec(mDropDownWidth, 0x40000000);
_L14:
        int i1 = mDropDownList.measureHeightOfChildrenCompat(l, 0, -1, k - i, -1);
        if (i1 > 0)
        {
            i += j;
        }
        return i1 + i;
_L11:
        l = android.view.View.MeasureSpec.makeMeasureSpec(mContext.getResources().getDisplayMetrics().widthPixels - (mTempRect.left + mTempRect.right), 0x80000000);
        continue; /* Loop/switch isn't completed */
_L12:
        l = android.view.View.MeasureSpec.makeMeasureSpec(mContext.getResources().getDisplayMetrics().widthPixels - (mTempRect.left + mTempRect.right), 0x40000000);
        if (true) goto _L14; else goto _L13
_L13:
        if (true) goto _L16; else goto _L15
_L15:
    }

    private void removePromptView()
    {
        if (mPromptView != null)
        {
            android.view.ViewParent viewparent = mPromptView.getParent();
            if (viewparent instanceof ViewGroup)
            {
                ((ViewGroup)viewparent).removeView(mPromptView);
            }
        }
    }

    public void clearListSelection()
    {
        DropDownListView dropdownlistview = mDropDownList;
        if (dropdownlistview != null)
        {
            dropdownlistview.mListSelectionHidden = true;
            dropdownlistview.requestLayout();
        }
    }

    public void dismiss()
    {
        mPopup.dismiss();
        removePromptView();
        mPopup.setContentView(null);
        mDropDownList = null;
        mHandler.removeCallbacks(mResizePopupRunnable);
    }

    public View getAnchorView()
    {
        return mDropDownAnchorView;
    }

    public int getAnimationStyle()
    {
        return mPopup.getAnimationStyle();
    }

    public Drawable getBackground()
    {
        return mPopup.getBackground();
    }

    public int getHeight()
    {
        return mDropDownHeight;
    }

    public int getHorizontalOffset()
    {
        return mDropDownHorizontalOffset;
    }

    public int getInputMethodMode()
    {
        return mPopup.getInputMethodMode();
    }

    public ListView getListView()
    {
        return mDropDownList;
    }

    public int getMaxAvailableHeight(View view, int i, boolean flag)
    {
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int ai[] = new int[2];
        view.getLocationOnScreen(ai);
        int j = rect.bottom;
        if (flag)
        {
            j = view.getContext().getResources().getDisplayMetrics().heightPixels;
        }
        int k = Math.max(j - (ai[1] + view.getHeight()) - i, i + (ai[1] - rect.top));
        if (mPopup.getBackground() != null)
        {
            mPopup.getBackground().getPadding(mTempRect);
            k -= mTempRect.top + mTempRect.bottom;
        }
        return k;
    }

    public int getPromptPosition()
    {
        return mPromptPosition;
    }

    public Object getSelectedItem()
    {
        if (!isShowing())
        {
            return null;
        } else
        {
            return mDropDownList.getSelectedItem();
        }
    }

    public long getSelectedItemId()
    {
        if (!isShowing())
        {
            return 0x8000000000000000L;
        } else
        {
            return mDropDownList.getSelectedItemId();
        }
    }

    public int getSelectedItemPosition()
    {
        if (!isShowing())
        {
            return -1;
        } else
        {
            return mDropDownList.getSelectedItemPosition();
        }
    }

    public View getSelectedView()
    {
        if (!isShowing())
        {
            return null;
        } else
        {
            return mDropDownList.getSelectedView();
        }
    }

    public int getSoftInputMode()
    {
        return mPopup.getSoftInputMode();
    }

    public int getVerticalOffset()
    {
        if (!mDropDownVerticalOffsetSet)
        {
            return 0;
        } else
        {
            return mDropDownVerticalOffset;
        }
    }

    public int getWidth()
    {
        return mDropDownWidth;
    }

    public boolean isDropDownAlwaysVisible()
    {
        return mDropDownAlwaysVisible;
    }

    public boolean isInputMethodNotNeeded()
    {
        return mPopup.getInputMethodMode() == 2;
    }

    public boolean isModal()
    {
        return mModal;
    }

    public boolean isShowing()
    {
        return mPopup.isShowing();
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        if (!isShowing() || i == 62 || mDropDownList.getSelectedItemPosition() < 0 && (i == 66 || i == 23)) goto _L2; else goto _L1
_L1:
        int j;
        boolean flag;
        int k;
        int l;
        j = mDropDownList.getSelectedItemPosition();
        ListAdapter listadapter;
        if (!mPopup.isAboveAnchor())
        {
            flag = true;
        } else
        {
            flag = false;
        }
        listadapter = mAdapter;
        k = 0x7fffffff;
        l = 0x80000000;
        if (listadapter != null)
        {
            boolean flag1 = listadapter.areAllItemsEnabled();
            if (flag1)
            {
                k = 0;
            } else
            {
                k = mDropDownList.lookForSelectablePosition(0, true);
            }
            if (flag1)
            {
                l = -1 + listadapter.getCount();
            } else
            {
                l = mDropDownList.lookForSelectablePosition(-1 + listadapter.getCount(), false);
            }
        }
        if ((!flag || i != 19 || j > k) && (flag || i != 20 || j < l)) goto _L4; else goto _L3
_L3:
        clearListSelection();
        mPopup.setInputMethodMode(1);
        show();
_L7:
        return true;
_L4:
        mDropDownList.mListSelectionHidden = false;
        if (!mDropDownList.onKeyDown(i, keyevent))
        {
            break; /* Loop/switch isn't completed */
        }
        mPopup.setInputMethodMode(2);
        mDropDownList.requestFocusFromTouch();
        show();
        i;
        JVM INSTR lookupswitch 4: default 288
    //                   19: 159
    //                   20: 159
    //                   23: 159
    //                   66: 159;
           goto _L2 _L5 _L5 _L5 _L5
_L5:
        if (true) goto _L7; else goto _L6
_L2:
        return false;
_L6:
        if (!flag || i != 20)
        {
            continue; /* Loop/switch isn't completed */
        }
        if (j != l) goto _L2; else goto _L8
_L8:
        return true;
        if (flag || i != 19 || j != k) goto _L2; else goto _L9
_L9:
        return true;
    }

    public boolean onKeyUp(int i, KeyEvent keyevent)
    {
        if (!isShowing() || mDropDownList.getSelectedItemPosition() < 0) goto _L2; else goto _L1
_L1:
        boolean flag = mDropDownList.onKeyUp(i, keyevent);
        if (!flag) goto _L4; else goto _L3
_L3:
        i;
        JVM INSTR lookupswitch 2: default 60
    //                   23: 62
    //                   66: 62;
           goto _L4 _L5 _L5
_L4:
        return flag;
_L5:
        dismiss();
        return flag;
_L2:
        return false;
    }

    public boolean performItemClick(int i)
    {
        if (isShowing())
        {
            if (mItemClickListener != null)
            {
                DropDownListView dropdownlistview = mDropDownList;
                View view = dropdownlistview.getChildAt(i - dropdownlistview.getFirstVisiblePosition());
                ListAdapter listadapter = dropdownlistview.getAdapter();
                mItemClickListener.onItemClick(dropdownlistview, view, i, listadapter.getItemId(i));
            }
            return true;
        } else
        {
            return false;
        }
    }

    public void postShow()
    {
        mHandler.post(mShowDropDownRunnable);
    }

    public void setAdapter(ListAdapter listadapter)
    {
        if (mObserver != null) goto _L2; else goto _L1
_L1:
        mObserver = new PopupDataSetObserver();
_L4:
        mAdapter = listadapter;
        if (mAdapter != null)
        {
            listadapter.registerDataSetObserver(mObserver);
        }
        if (mDropDownList != null)
        {
            mDropDownList.setAdapter(mAdapter);
        }
        return;
_L2:
        if (mAdapter != null)
        {
            mAdapter.unregisterDataSetObserver(mObserver);
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public void setAnchorView(View view)
    {
        mDropDownAnchorView = view;
    }

    public void setAnimationStyle(int i)
    {
        mPopup.setAnimationStyle(i);
    }

    public void setBackgroundDrawable(Drawable drawable)
    {
        mPopup.setBackgroundDrawable(drawable);
    }

    public void setContentWidth(int i)
    {
        Drawable drawable = mPopup.getBackground();
        if (drawable != null)
        {
            drawable.getPadding(mTempRect);
            mDropDownWidth = i + (mTempRect.left + mTempRect.right);
            return;
        } else
        {
            setWidth(i);
            return;
        }
    }

    public void setDropDownAlwaysVisible(boolean flag)
    {
        mDropDownAlwaysVisible = flag;
    }

    public void setForceIgnoreOutsideTouch(boolean flag)
    {
        mForceIgnoreOutsideTouch = flag;
    }

    public void setHeight(int i)
    {
        mDropDownHeight = i;
    }

    public void setHorizontalOffset(int i)
    {
        mDropDownHorizontalOffset = i;
    }

    public void setInputMethodMode(int i)
    {
        mPopup.setInputMethodMode(i);
    }

    void setListItemExpandMax(int i)
    {
        mListItemExpandMaximum = i;
    }

    public void setListSelector(Drawable drawable)
    {
        mDropDownListHighlight = drawable;
    }

    public void setModal(boolean flag)
    {
        mModal = true;
        mPopup.setFocusable(flag);
    }

    public void setOnDismissListener(android.widget.PopupWindow.OnDismissListener ondismisslistener)
    {
        mPopup.setOnDismissListener(ondismisslistener);
    }

    public void setOnItemClickListener(android.widget.AdapterView.OnItemClickListener onitemclicklistener)
    {
        mItemClickListener = onitemclicklistener;
    }

    public void setOnItemSelectedListener(android.widget.AdapterView.OnItemSelectedListener onitemselectedlistener)
    {
        mItemSelectedListener = onitemselectedlistener;
    }

    public void setPromptPosition(int i)
    {
        mPromptPosition = i;
    }

    public void setPromptView(View view)
    {
        boolean flag = isShowing();
        if (flag)
        {
            removePromptView();
        }
        mPromptView = view;
        if (flag)
        {
            show();
        }
    }

    public void setSelection(int i)
    {
        DropDownListView dropdownlistview = mDropDownList;
        if (isShowing() && dropdownlistview != null)
        {
            dropdownlistview.mListSelectionHidden = false;
            dropdownlistview.setSelection(i);
            if (dropdownlistview.getChoiceMode() != 0)
            {
                dropdownlistview.setItemChecked(i, true);
            }
        }
    }

    public void setSoftInputMode(int i)
    {
        mPopup.setSoftInputMode(i);
    }

    public void setVerticalOffset(int i)
    {
        mDropDownVerticalOffset = i;
        mDropDownVerticalOffsetSet = true;
    }

    public void setWidth(int i)
    {
        mDropDownWidth = i;
    }

    public void show()
    {
        boolean flag = true;
        byte byte0 = -1;
        int i = buildDropDown();
        boolean flag1 = isInputMethodNotNeeded();
        if (mPopup.isShowing())
        {
            int j;
            int k;
            if (mDropDownWidth == byte0)
            {
                j = -1;
            } else
            if (mDropDownWidth == -2)
            {
                j = getAnchorView().getWidth();
            } else
            {
                j = mDropDownWidth;
            }
            if (mDropDownHeight == byte0)
            {
                if (flag1)
                {
                    k = i;
                } else
                {
                    k = byte0;
                }
                if (flag1)
                {
                    PopupWindow popupwindow3 = mPopup;
                    PopupWindow popupwindow1;
                    boolean flag2;
                    boolean flag3;
                    boolean flag4;
                    if (mDropDownWidth != byte0)
                    {
                        byte0 = 0;
                    }
                    popupwindow3.setWindowLayoutMode(byte0, 0);
                } else
                {
                    PopupWindow popupwindow2 = mPopup;
                    byte byte3;
                    if (mDropDownWidth == byte0)
                    {
                        byte3 = byte0;
                    } else
                    {
                        byte3 = 0;
                    }
                    popupwindow2.setWindowLayoutMode(byte3, byte0);
                }
            } else
            if (mDropDownHeight == -2)
            {
                k = i;
            } else
            {
                k = mDropDownHeight;
            }
            popupwindow1 = mPopup;
            flag2 = mForceIgnoreOutsideTouch;
            flag3 = false;
            if (!flag2)
            {
                flag4 = mDropDownAlwaysVisible;
                flag3 = false;
                if (!flag4)
                {
                    flag3 = flag;
                }
            }
            popupwindow1.setOutsideTouchable(flag3);
            mPopup.update(getAnchorView(), mDropDownHorizontalOffset, mDropDownVerticalOffset, j, k);
        } else
        {
            byte byte1;
            byte byte2;
            PopupWindow popupwindow;
            if (mDropDownWidth == byte0)
            {
                byte1 = -1;
            } else
            if (mDropDownWidth == -2)
            {
                mPopup.setWidth(getAnchorView().getWidth());
                byte1 = 0;
            } else
            {
                mPopup.setWidth(mDropDownWidth);
                byte1 = 0;
            }
            if (mDropDownHeight == byte0)
            {
                byte2 = -1;
            } else
            if (mDropDownHeight == -2)
            {
                mPopup.setHeight(i);
                byte2 = 0;
            } else
            {
                mPopup.setHeight(mDropDownHeight);
                byte2 = 0;
            }
            mPopup.setWindowLayoutMode(byte1, byte2);
            popupwindow = mPopup;
            if (mForceIgnoreOutsideTouch || mDropDownAlwaysVisible)
            {
                flag = false;
            }
            popupwindow.setOutsideTouchable(flag);
            mPopup.setTouchInterceptor(mTouchInterceptor);
            mPopup.showAsDropDown(getAnchorView(), mDropDownHorizontalOffset, mDropDownVerticalOffset);
            mDropDownList.setSelection(byte0);
            if (!mModal || mDropDownList.isInTouchMode())
            {
                clearListSelection();
            }
            if (!mModal)
            {
                mHandler.post(mHideSelector);
                return;
            }
        }
    }




}
