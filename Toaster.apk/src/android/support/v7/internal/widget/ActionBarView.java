// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v7.internal.view.menu.ActionMenuItem;
import android.support.v7.internal.view.menu.ActionMenuPresenter;
import android.support.v7.internal.view.menu.ActionMenuView;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.internal.view.menu.MenuPresenter;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.internal.view.menu.SubMenuBuilder;
import android.support.v7.view.CollapsibleActionView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import java.util.List;

// Referenced classes of package android.support.v7.internal.widget:
//            AbsActionBarView, SpinnerICS, ProgressBarICS, ScrollingTabContainerView, 
//            ActionBarContextView, ActionBarContainer, AdapterViewICS

public class ActionBarView extends AbsActionBarView
{
    private class ExpandedActionViewMenuPresenter
        implements MenuPresenter
    {

        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;
        final ActionBarView this$0;

        public boolean collapseItemActionView(MenuBuilder menubuilder, MenuItemImpl menuitemimpl)
        {
            if (mExpandedActionView instanceof CollapsibleActionView)
            {
                ((CollapsibleActionView)mExpandedActionView).onActionViewCollapsed();
            }
            removeView(mExpandedActionView);
            removeView(mExpandedHomeLayout);
            mExpandedActionView = null;
            if ((2 & mDisplayOptions) != 0)
            {
                mHomeLayout.setVisibility(0);
            }
            if ((8 & mDisplayOptions) != 0)
            {
                if (mTitleLayout == null)
                {
                    initTitle();
                } else
                {
                    mTitleLayout.setVisibility(0);
                }
            }
            if (mTabScrollView != null && mNavigationMode == 2)
            {
                mTabScrollView.setVisibility(0);
            }
            if (mSpinner != null && mNavigationMode == 1)
            {
                mSpinner.setVisibility(0);
            }
            if (mCustomNavView != null && (0x10 & mDisplayOptions) != 0)
            {
                mCustomNavView.setVisibility(0);
            }
            mExpandedHomeLayout.setIcon(null);
            mCurrentExpandedItem = null;
            requestLayout();
            menuitemimpl.setActionViewExpanded(false);
            return true;
        }

        public boolean expandItemActionView(MenuBuilder menubuilder, MenuItemImpl menuitemimpl)
        {
            mExpandedActionView = menuitemimpl.getActionView();
            mExpandedHomeLayout.setIcon(mIcon.getConstantState().newDrawable(getResources()));
            mCurrentExpandedItem = menuitemimpl;
            if (mExpandedActionView.getParent() != ActionBarView.this)
            {
                addView(mExpandedActionView);
            }
            if (mExpandedHomeLayout.getParent() != ActionBarView.this)
            {
                addView(mExpandedHomeLayout);
            }
            mHomeLayout.setVisibility(8);
            if (mTitleLayout != null)
            {
                mTitleLayout.setVisibility(8);
            }
            if (mTabScrollView != null)
            {
                mTabScrollView.setVisibility(8);
            }
            if (mSpinner != null)
            {
                mSpinner.setVisibility(8);
            }
            if (mCustomNavView != null)
            {
                mCustomNavView.setVisibility(8);
            }
            requestLayout();
            menuitemimpl.setActionViewExpanded(true);
            if (mExpandedActionView instanceof CollapsibleActionView)
            {
                ((CollapsibleActionView)mExpandedActionView).onActionViewExpanded();
            }
            return true;
        }

        public boolean flagActionItems()
        {
            return false;
        }

        public int getId()
        {
            return 0;
        }

        public MenuView getMenuView(ViewGroup viewgroup)
        {
            return null;
        }

        public void initForMenu(Context context, MenuBuilder menubuilder)
        {
            if (mMenu != null && mCurrentExpandedItem != null)
            {
                mMenu.collapseItemActionView(mCurrentExpandedItem);
            }
            mMenu = menubuilder;
        }

        public void onCloseMenu(MenuBuilder menubuilder, boolean flag)
        {
        }

        public void onRestoreInstanceState(Parcelable parcelable)
        {
        }

        public Parcelable onSaveInstanceState()
        {
            return null;
        }

        public boolean onSubMenuSelected(SubMenuBuilder submenubuilder)
        {
            return false;
        }

        public void setCallback(android.support.v7.internal.view.menu.MenuPresenter.Callback callback)
        {
        }

        public void updateMenuView(boolean flag)
        {
            if (mCurrentExpandedItem == null) goto _L2; else goto _L1
_L1:
            MenuBuilder menubuilder;
            boolean flag1;
            menubuilder = mMenu;
            flag1 = false;
            if (menubuilder == null) goto _L4; else goto _L3
_L3:
            int i;
            int j;
            i = mMenu.size();
            j = 0;
_L9:
            flag1 = false;
            if (j >= i) goto _L4; else goto _L5
_L5:
            if ((SupportMenuItem)mMenu.getItem(j) != mCurrentExpandedItem) goto _L7; else goto _L6
_L6:
            flag1 = true;
_L4:
            if (!flag1)
            {
                collapseItemActionView(mMenu, mCurrentExpandedItem);
            }
_L2:
            return;
_L7:
            j++;
            if (true) goto _L9; else goto _L8
_L8:
        }

        private ExpandedActionViewMenuPresenter()
        {
            this$0 = ActionBarView.this;
            super();
        }

    }

    private static class HomeView extends FrameLayout
    {

        private Drawable mDefaultUpIndicator;
        private ImageView mIconView;
        private int mUpIndicatorRes;
        private ImageView mUpView;
        private int mUpWidth;

        public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityevent)
        {
            CharSequence charsequence = getContentDescription();
            if (!TextUtils.isEmpty(charsequence))
            {
                accessibilityevent.getText().add(charsequence);
            }
            return true;
        }

        public int getLeftOffset()
        {
            if (mUpView.getVisibility() == 8)
            {
                return mUpWidth;
            } else
            {
                return 0;
            }
        }

        protected void onConfigurationChanged(Configuration configuration)
        {
            super.onConfigurationChanged(configuration);
            if (mUpIndicatorRes != 0)
            {
                setUpIndicator(mUpIndicatorRes);
            }
        }

        protected void onFinishInflate()
        {
            mUpView = (ImageView)findViewById(android.support.v7.appcompat.R.id.up);
            mIconView = (ImageView)findViewById(android.support.v7.appcompat.R.id.home);
            mDefaultUpIndicator = mUpView.getDrawable();
        }

        protected void onLayout(boolean flag, int i, int j, int k, int l)
        {
            int i1 = (l - j) / 2;
            int j1 = k - i;
            int k1 = mUpView.getVisibility();
            int l1 = 0;
            if (k1 != 8)
            {
                android.widget.FrameLayout.LayoutParams layoutparams1 = (android.widget.FrameLayout.LayoutParams)mUpView.getLayoutParams();
                int j3 = mUpView.getMeasuredHeight();
                int k3 = mUpView.getMeasuredWidth();
                int l3 = i1 - j3 / 2;
                mUpView.layout(0, l3, k3, l3 + j3);
                l1 = k3 + layoutparams1.leftMargin + layoutparams1.rightMargin;
                int _tmp = j1 - l1;
                i += l1;
            }
            android.widget.FrameLayout.LayoutParams layoutparams = (android.widget.FrameLayout.LayoutParams)mIconView.getLayoutParams();
            int i2 = mIconView.getMeasuredHeight();
            int j2 = mIconView.getMeasuredWidth();
            int k2 = (k - i) / 2;
            int l2 = l1 + Math.max(layoutparams.leftMargin, k2 - j2 / 2);
            int i3 = Math.max(layoutparams.topMargin, i1 - i2 / 2);
            mIconView.layout(l2, i3, l2 + j2, i3 + i2);
        }

        protected void onMeasure(int i, int j)
        {
            int i1;
            int j1;
            int i2;
            int j2;
            measureChildWithMargins(mUpView, i, 0, j, 0);
            android.widget.FrameLayout.LayoutParams layoutparams = (android.widget.FrameLayout.LayoutParams)mUpView.getLayoutParams();
            mUpWidth = layoutparams.leftMargin + mUpView.getMeasuredWidth() + layoutparams.rightMargin;
            int k;
            int l;
            android.widget.FrameLayout.LayoutParams layoutparams1;
            int k1;
            int l1;
            if (mUpView.getVisibility() == 8)
            {
                k = 0;
            } else
            {
                k = mUpWidth;
            }
            l = layoutparams.topMargin + mUpView.getMeasuredHeight() + layoutparams.bottomMargin;
            measureChildWithMargins(mIconView, i, k, j, 0);
            layoutparams1 = (android.widget.FrameLayout.LayoutParams)mIconView.getLayoutParams();
            i1 = k + (layoutparams1.leftMargin + mIconView.getMeasuredWidth() + layoutparams1.rightMargin);
            j1 = Math.max(l, layoutparams1.topMargin + mIconView.getMeasuredHeight() + layoutparams1.bottomMargin);
            k1 = android.view.View.MeasureSpec.getMode(i);
            l1 = android.view.View.MeasureSpec.getMode(j);
            i2 = android.view.View.MeasureSpec.getSize(i);
            j2 = android.view.View.MeasureSpec.getSize(j);
            k1;
            JVM INSTR lookupswitch 2: default 204
        //                       -2147483648: 250
        //                       1073741824: 262;
               goto _L1 _L2 _L3
_L1:
            l1;
            JVM INSTR lookupswitch 2: default 232
        //                       -2147483648: 269
        //                       1073741824: 281;
               goto _L4 _L5 _L6
_L4:
            setMeasuredDimension(i1, j1);
            return;
_L2:
            i1 = Math.min(i1, i2);
              goto _L1
_L3:
            i1 = i2;
              goto _L1
_L5:
            j1 = Math.min(j1, j2);
              goto _L4
_L6:
            j1 = j2;
              goto _L4
        }

        public void setIcon(Drawable drawable)
        {
            mIconView.setImageDrawable(drawable);
        }

        public void setUp(boolean flag)
        {
            ImageView imageview = mUpView;
            int i;
            if (flag)
            {
                i = 0;
            } else
            {
                i = 8;
            }
            imageview.setVisibility(i);
        }

        public void setUpIndicator(int i)
        {
            mUpIndicatorRes = i;
            ImageView imageview = mUpView;
            Drawable drawable;
            if (i != 0)
            {
                drawable = getResources().getDrawable(i);
            } else
            {
                drawable = mDefaultUpIndicator;
            }
            imageview.setImageDrawable(drawable);
        }

        public void setUpIndicator(Drawable drawable)
        {
            ImageView imageview = mUpView;
            if (drawable == null)
            {
                drawable = mDefaultUpIndicator;
            }
            imageview.setImageDrawable(drawable);
            mUpIndicatorRes = 0;
        }

        public HomeView(Context context)
        {
            this(context, null);
        }

        public HomeView(Context context, AttributeSet attributeset)
        {
            super(context, attributeset);
        }
    }

    static class SavedState extends android.view.View.BaseSavedState
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
        int expandedMenuItemId;
        boolean isOverflowOpen;

        public void writeToParcel(Parcel parcel, int i)
        {
            super.writeToParcel(parcel, i);
            parcel.writeInt(expandedMenuItemId);
            int j;
            if (isOverflowOpen)
            {
                j = 1;
            } else
            {
                j = 0;
            }
            parcel.writeInt(j);
        }


        private SavedState(Parcel parcel)
        {
            super(parcel);
            expandedMenuItemId = parcel.readInt();
            boolean flag;
            if (parcel.readInt() != 0)
            {
                flag = true;
            } else
            {
                flag = false;
            }
            isOverflowOpen = flag;
        }


        SavedState(Parcelable parcelable)
        {
            super(parcelable);
        }
    }


    private static final int DEFAULT_CUSTOM_GRAVITY = 19;
    public static final int DISPLAY_DEFAULT = 0;
    private static final int DISPLAY_RELAYOUT_MASK = 31;
    private static final String TAG = "ActionBarView";
    private android.support.v7.app.ActionBar.OnNavigationListener mCallback;
    private Context mContext;
    private ActionBarContextView mContextView;
    private View mCustomNavView;
    private int mDisplayOptions;
    View mExpandedActionView;
    private final android.view.View.OnClickListener mExpandedActionViewUpListener = new android.view.View.OnClickListener() {

        final ActionBarView this$0;

        public void onClick(View view)
        {
            MenuItemImpl menuitemimpl = mExpandedMenuPresenter.mCurrentExpandedItem;
            if (menuitemimpl != null)
            {
                menuitemimpl.collapseActionView();
            }
        }

            
            {
                this$0 = ActionBarView.this;
                super();
            }
    };
    private HomeView mExpandedHomeLayout;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private HomeView mHomeLayout;
    private Drawable mIcon;
    private boolean mIncludeTabs;
    private int mIndeterminateProgressStyle;
    private ProgressBarICS mIndeterminateProgressView;
    private boolean mIsCollapsable;
    private boolean mIsCollapsed;
    private int mItemPadding;
    private LinearLayout mListNavLayout;
    private Drawable mLogo;
    private ActionMenuItem mLogoNavItem;
    private final AdapterViewICS.OnItemSelectedListener mNavItemSelectedListener = new AdapterViewICS.OnItemSelectedListener() {

        final ActionBarView this$0;

        public void onItemSelected(AdapterViewICS adapterviewics, View view, int k, long l)
        {
            if (mCallback != null)
            {
                mCallback.onNavigationItemSelected(k, l);
            }
        }

        public void onNothingSelected(AdapterViewICS adapterviewics)
        {
        }

            
            {
                this$0 = ActionBarView.this;
                super();
            }
    };
    private int mNavigationMode;
    private MenuBuilder mOptionsMenu;
    private int mProgressBarPadding;
    private int mProgressStyle;
    private ProgressBarICS mProgressView;
    private SpinnerICS mSpinner;
    private SpinnerAdapter mSpinnerAdapter;
    private CharSequence mSubtitle;
    private int mSubtitleStyleRes;
    private TextView mSubtitleView;
    private ScrollingTabContainerView mTabScrollView;
    private Runnable mTabSelector;
    private CharSequence mTitle;
    private LinearLayout mTitleLayout;
    private int mTitleStyleRes;
    private View mTitleUpView;
    private TextView mTitleView;
    private final android.view.View.OnClickListener mUpClickListener = new android.view.View.OnClickListener() {

        final ActionBarView this$0;

        public void onClick(View view)
        {
            mWindowCallback.onMenuItemSelected(0, mLogoNavItem);
        }

            
            {
                this$0 = ActionBarView.this;
                super();
            }
    };
    private boolean mUserTitle;
    android.view.Window.Callback mWindowCallback;

    public ActionBarView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mDisplayOptions = -1;
        mContext = context;
        setBackgroundResource(0);
        TypedArray typedarray = context.obtainStyledAttributes(attributeset, android.support.v7.appcompat.R.styleable.ActionBar, android.support.v7.appcompat.R.attr.actionBarStyle, 0);
        ApplicationInfo applicationinfo = context.getApplicationInfo();
        PackageManager packagemanager = context.getPackageManager();
        mNavigationMode = typedarray.getInt(2, 0);
        mTitle = typedarray.getText(1);
        mSubtitle = typedarray.getText(4);
        mLogo = typedarray.getDrawable(8);
        if (mLogo == null && android.os.Build.VERSION.SDK_INT >= 9)
        {
            LayoutInflater layoutinflater;
            int i;
            int j;
            if (context instanceof Activity)
            {
                try
                {
                    mLogo = packagemanager.getActivityLogo(((Activity)context).getComponentName());
                }
                catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception1)
                {
                    Log.e("ActionBarView", "Activity component name not found!", namenotfoundexception1);
                }
            }
            if (mLogo == null)
            {
                mLogo = applicationinfo.loadLogo(packagemanager);
            }
        }
        mIcon = typedarray.getDrawable(7);
        if (mIcon == null)
        {
            if (context instanceof Activity)
            {
                try
                {
                    mIcon = packagemanager.getActivityIcon(((Activity)context).getComponentName());
                }
                catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
                {
                    Log.e("ActionBarView", "Activity component name not found!", namenotfoundexception);
                }
            }
            if (mIcon == null)
            {
                mIcon = applicationinfo.loadIcon(packagemanager);
            }
        }
        layoutinflater = LayoutInflater.from(context);
        i = typedarray.getResourceId(14, android.support.v7.appcompat.R.layout.abc_action_bar_home);
        mHomeLayout = (HomeView)layoutinflater.inflate(i, this, false);
        mExpandedHomeLayout = (HomeView)layoutinflater.inflate(i, this, false);
        mExpandedHomeLayout.setUp(true);
        mExpandedHomeLayout.setOnClickListener(mExpandedActionViewUpListener);
        mExpandedHomeLayout.setContentDescription(getResources().getText(android.support.v7.appcompat.R.string.abc_action_bar_up_description));
        mTitleStyleRes = typedarray.getResourceId(5, 0);
        mSubtitleStyleRes = typedarray.getResourceId(6, 0);
        mProgressStyle = typedarray.getResourceId(15, 0);
        mIndeterminateProgressStyle = typedarray.getResourceId(16, 0);
        mProgressBarPadding = typedarray.getDimensionPixelOffset(17, 0);
        mItemPadding = typedarray.getDimensionPixelOffset(18, 0);
        setDisplayOptions(typedarray.getInt(3, 0));
        j = typedarray.getResourceId(13, 0);
        if (j != 0)
        {
            mCustomNavView = layoutinflater.inflate(j, this, false);
            mNavigationMode = 0;
            setDisplayOptions(0x10 | mDisplayOptions);
        }
        mContentHeight = typedarray.getLayoutDimension(0, 0);
        typedarray.recycle();
        mLogoNavItem = new ActionMenuItem(context, 0, 0x102002c, 0, 0, mTitle);
        mHomeLayout.setOnClickListener(mUpClickListener);
        mHomeLayout.setClickable(true);
        mHomeLayout.setFocusable(true);
    }

    private void configPresenters(MenuBuilder menubuilder)
    {
        if (menubuilder != null)
        {
            menubuilder.addMenuPresenter(mActionMenuPresenter);
            menubuilder.addMenuPresenter(mExpandedMenuPresenter);
        } else
        {
            mActionMenuPresenter.initForMenu(mContext, null);
            mExpandedMenuPresenter.initForMenu(mContext, null);
        }
        mActionMenuPresenter.updateMenuView(true);
        mExpandedMenuPresenter.updateMenuView(true);
    }

    private void initTitle()
    {
        boolean flag = true;
        if (mTitleLayout == null)
        {
            mTitleLayout = (LinearLayout)LayoutInflater.from(getContext()).inflate(android.support.v7.appcompat.R.layout.abc_action_bar_title_item, this, false);
            mTitleView = (TextView)mTitleLayout.findViewById(android.support.v7.appcompat.R.id.action_bar_title);
            mSubtitleView = (TextView)mTitleLayout.findViewById(android.support.v7.appcompat.R.id.action_bar_subtitle);
            mTitleUpView = mTitleLayout.findViewById(android.support.v7.appcompat.R.id.up);
            mTitleLayout.setOnClickListener(mUpClickListener);
            if (mTitleStyleRes != 0)
            {
                mTitleView.setTextAppearance(mContext, mTitleStyleRes);
            }
            if (mTitle != null)
            {
                mTitleView.setText(mTitle);
            }
            if (mSubtitleStyleRes != 0)
            {
                mSubtitleView.setTextAppearance(mContext, mSubtitleStyleRes);
            }
            if (mSubtitle != null)
            {
                mSubtitleView.setText(mSubtitle);
                mSubtitleView.setVisibility(0);
            }
            boolean flag1;
            boolean flag2;
            View view;
            int i;
            LinearLayout linearlayout;
            if ((4 & mDisplayOptions) != 0)
            {
                flag1 = flag;
            } else
            {
                flag1 = false;
            }
            if ((2 & mDisplayOptions) != 0)
            {
                flag2 = flag;
            } else
            {
                flag2 = false;
            }
            view = mTitleUpView;
            if (!flag2)
            {
                if (flag1)
                {
                    i = 0;
                } else
                {
                    i = 4;
                }
            } else
            {
                i = 8;
            }
            view.setVisibility(i);
            linearlayout = mTitleLayout;
            if (!flag1 || flag2)
            {
                flag = false;
            }
            linearlayout.setEnabled(flag);
        }
        addView(mTitleLayout);
        if (mExpandedActionView != null || TextUtils.isEmpty(mTitle) && TextUtils.isEmpty(mSubtitle))
        {
            mTitleLayout.setVisibility(8);
        }
    }

    private void setTitleImpl(CharSequence charsequence)
    {
        mTitle = charsequence;
        if (mTitleView != null)
        {
            mTitleView.setText(charsequence);
            boolean flag;
            LinearLayout linearlayout;
            int i;
            if (mExpandedActionView == null && (8 & mDisplayOptions) != 0 && (!TextUtils.isEmpty(mTitle) || !TextUtils.isEmpty(mSubtitle)))
            {
                flag = true;
            } else
            {
                flag = false;
            }
            linearlayout = mTitleLayout;
            i = 0;
            if (!flag)
            {
                i = 8;
            }
            linearlayout.setVisibility(i);
        }
        if (mLogoNavItem != null)
        {
            mLogoNavItem.setTitle(charsequence);
        }
    }

    public volatile void animateToVisibility(int i)
    {
        super.animateToVisibility(i);
    }

    public void collapseActionView()
    {
        MenuItemImpl menuitemimpl;
        if (mExpandedMenuPresenter == null)
        {
            menuitemimpl = null;
        } else
        {
            menuitemimpl = mExpandedMenuPresenter.mCurrentExpandedItem;
        }
        if (menuitemimpl != null)
        {
            menuitemimpl.collapseActionView();
        }
    }

    public volatile void dismissPopupMenus()
    {
        super.dismissPopupMenus();
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams()
    {
        return new android.support.v7.app.ActionBar.LayoutParams(19);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeset)
    {
        return new android.support.v7.app.ActionBar.LayoutParams(getContext(), attributeset);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutparams)
    {
        if (layoutparams == null)
        {
            layoutparams = generateDefaultLayoutParams();
        }
        return layoutparams;
    }

    public volatile int getAnimatedVisibility()
    {
        return super.getAnimatedVisibility();
    }

    public volatile int getContentHeight()
    {
        return super.getContentHeight();
    }

    public View getCustomNavigationView()
    {
        return mCustomNavView;
    }

    public int getDisplayOptions()
    {
        return mDisplayOptions;
    }

    public SpinnerAdapter getDropdownAdapter()
    {
        return mSpinnerAdapter;
    }

    public int getDropdownSelectedPosition()
    {
        return mSpinner.getSelectedItemPosition();
    }

    public int getNavigationMode()
    {
        return mNavigationMode;
    }

    public CharSequence getSubtitle()
    {
        return mSubtitle;
    }

    public CharSequence getTitle()
    {
        return mTitle;
    }

    public boolean hasEmbeddedTabs()
    {
        return mIncludeTabs;
    }

    public boolean hasExpandedActionView()
    {
        return mExpandedMenuPresenter != null && mExpandedMenuPresenter.mCurrentExpandedItem != null;
    }

    public volatile boolean hideOverflowMenu()
    {
        return super.hideOverflowMenu();
    }

    public void initIndeterminateProgress()
    {
        mIndeterminateProgressView = new ProgressBarICS(mContext, null, 0, mIndeterminateProgressStyle);
        mIndeterminateProgressView.setId(android.support.v7.appcompat.R.id.progress_circular);
        mIndeterminateProgressView.setVisibility(8);
        addView(mIndeterminateProgressView);
    }

    public void initProgress()
    {
        mProgressView = new ProgressBarICS(mContext, null, 0, mProgressStyle);
        mProgressView.setId(android.support.v7.appcompat.R.id.progress_horizontal);
        mProgressView.setMax(10000);
        mProgressView.setVisibility(8);
        addView(mProgressView);
    }

    public boolean isCollapsed()
    {
        return mIsCollapsed;
    }

    public volatile boolean isOverflowMenuShowing()
    {
        return super.isOverflowMenuShowing();
    }

    public volatile boolean isOverflowReserved()
    {
        return super.isOverflowReserved();
    }

    public boolean isSplitActionBar()
    {
        return mSplitActionBar;
    }

    protected void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
        mTitleView = null;
        mSubtitleView = null;
        mTitleUpView = null;
        if (mTitleLayout != null && mTitleLayout.getParent() == this)
        {
            removeView(mTitleLayout);
        }
        mTitleLayout = null;
        if ((8 & mDisplayOptions) != 0)
        {
            initTitle();
        }
        if (mTabScrollView != null && mIncludeTabs)
        {
            android.view.ViewGroup.LayoutParams layoutparams = mTabScrollView.getLayoutParams();
            if (layoutparams != null)
            {
                layoutparams.width = -2;
                layoutparams.height = -1;
            }
            mTabScrollView.setAllowCollapse(true);
        }
        if (mProgressView != null)
        {
            removeView(mProgressView);
            initProgress();
        }
        if (mIndeterminateProgressView != null)
        {
            removeView(mIndeterminateProgressView);
            initIndeterminateProgress();
        }
    }

    public void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        removeCallbacks(mTabSelector);
        if (mActionMenuPresenter != null)
        {
            mActionMenuPresenter.hideOverflowMenu();
            mActionMenuPresenter.hideSubMenus();
        }
    }

    protected void onFinishInflate()
    {
        super.onFinishInflate();
        addView(mHomeLayout);
        if (mCustomNavView != null && (0x10 & mDisplayOptions) != 0)
        {
            android.view.ViewParent viewparent = mCustomNavView.getParent();
            if (viewparent != this)
            {
                if (viewparent instanceof ViewGroup)
                {
                    ((ViewGroup)viewparent).removeView(mCustomNavView);
                }
                addView(mCustomNavView);
            }
        }
    }

    protected void onLayout(boolean flag, int i, int j, int k, int l)
    {
        int i1;
        int j1;
        int k1;
        i1 = getPaddingLeft();
        j1 = getPaddingTop();
        k1 = l - j - getPaddingTop() - getPaddingBottom();
        if (k1 > 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int l1;
        View view;
        int l2;
        int i3;
        int j3;
        int l3;
        int j4;
        boolean flag1;
        HomeView homeview;
        int j2;
        android.view.ViewGroup.LayoutParams layoutparams;
        int i4;
        int k4;
        int l4;
        int i5;
        if (mExpandedActionView != null)
        {
            homeview = mExpandedHomeLayout;
        } else
        {
            homeview = mHomeLayout;
        }
        if (homeview.getVisibility() != 8)
        {
            int l5 = homeview.getLeftOffset();
            i1 += l5 + positionChild(homeview, i1 + l5, j1, k1);
        }
        if (mExpandedActionView != null) goto _L4; else goto _L3
_L3:
        if (mTitleLayout != null && mTitleLayout.getVisibility() != 8 && (8 & mDisplayOptions) != 0)
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        if (flag1)
        {
            i1 += positionChild(mTitleLayout, i1, j1, k1);
        }
        mNavigationMode;
        JVM INSTR tableswitch 0 2: default 180
    //                   0 180
    //                   1 628
    //                   2 676;
           goto _L4 _L4 _L5 _L6
_L4:
        l1 = k - i - getPaddingRight();
        if (mMenuView != null && mMenuView.getParent() == this)
        {
            positionChildInverse(mMenuView, l1, j1, k1);
            l1 -= mMenuView.getMeasuredWidth();
        }
        if (mIndeterminateProgressView != null && mIndeterminateProgressView.getVisibility() != 8)
        {
            positionChildInverse(mIndeterminateProgressView, l1, j1, k1);
            l1 -= mIndeterminateProgressView.getMeasuredWidth();
        }
        android.support.v7.app.ActionBar.LayoutParams layoutparams1;
        int k2;
        int k3;
        if (mExpandedActionView != null)
        {
            view = mExpandedActionView;
        } else
        {
            int i2 = 0x10 & mDisplayOptions;
            view = null;
            if (i2 != 0)
            {
                View view1 = mCustomNavView;
                view = null;
                if (view1 != null)
                {
                    view = mCustomNavView;
                }
            }
        }
        if (view == null)
        {
            continue; /* Loop/switch isn't completed */
        }
        layoutparams = view.getLayoutParams();
        if (layoutparams instanceof android.support.v7.app.ActionBar.LayoutParams)
        {
            layoutparams1 = (android.support.v7.app.ActionBar.LayoutParams)layoutparams;
        } else
        {
            layoutparams1 = null;
        }
        if (layoutparams1 != null)
        {
            k2 = layoutparams1.gravity;
        } else
        {
            k2 = 19;
        }
        l2 = view.getMeasuredWidth();
        i3 = 0;
        j3 = 0;
        if (layoutparams1 != null)
        {
            i1 += layoutparams1.leftMargin;
            l1 -= layoutparams1.rightMargin;
            j3 = layoutparams1.topMargin;
            i3 = layoutparams1.bottomMargin;
        }
        k3 = k2 & 7;
        if (k3 == 1)
        {
            int k5 = (getWidth() - l2) / 2;
            if (k5 < i1)
            {
                k3 = 3;
            } else
            if (k5 + l2 > l1)
            {
                k3 = 5;
            }
        } else
        if (k2 == -1)
        {
            k3 = 3;
        }
        l3 = 0;
        k3;
        JVM INSTR tableswitch 1 5: default 460
    //                   1 805
    //                   2 460
    //                   3 819
    //                   4 460
    //                   5 826;
           goto _L7 _L8 _L7 _L9 _L7 _L10
_L7:
        i4 = k2 & 0x70;
        if (k2 == -1)
        {
            i4 = 16;
        }
        j4 = 0;
        i4;
        JVM INSTR lookupswitch 3: default 516
    //                   16: 836
    //                   48: 867
    //                   80: 879;
           goto _L11 _L12 _L13 _L14
_L11:
        break; /* Loop/switch isn't completed */
_L14:
        break MISSING_BLOCK_LABEL_879;
_L16:
        k4 = view.getMeasuredWidth();
        l4 = l3 + k4;
        i5 = j4 + view.getMeasuredHeight();
        view.layout(l3, j4, l4, i5);
        i1 + k4;
        if (mProgressView == null) goto _L1; else goto _L15
_L15:
        mProgressView.bringToFront();
        j2 = mProgressView.getMeasuredHeight() / 2;
        mProgressView.layout(mProgressBarPadding, -j2, mProgressBarPadding + mProgressView.getMeasuredWidth(), j2);
        return;
_L5:
        if (mListNavLayout != null)
        {
            if (flag1)
            {
                i1 += mItemPadding;
            }
            i1 += positionChild(mListNavLayout, i1, j1, k1) + mItemPadding;
        }
          goto _L4
_L6:
        if (mTabScrollView != null)
        {
            if (flag1)
            {
                i1 += mItemPadding;
            }
            i1 += positionChild(mTabScrollView, i1, j1, k1) + mItemPadding;
        }
          goto _L4
_L8:
        l3 = (getWidth() - l2) / 2;
          goto _L7
_L9:
        l3 = i1;
          goto _L7
_L10:
        l3 = l1 - l2;
          goto _L7
_L12:
        int j5 = getPaddingTop();
        j4 = (getHeight() - getPaddingBottom() - j5 - view.getMeasuredHeight()) / 2;
          goto _L16
_L13:
        j4 = j3 + getPaddingTop();
          goto _L16
        j4 = getHeight() - getPaddingBottom() - view.getMeasuredHeight() - i3;
          goto _L16
    }

    protected void onMeasure(int i, int j)
    {
        int k = getChildCount();
        if (!mIsCollapsable) goto _L2; else goto _L1
_L1:
        int i9;
        i9 = 0;
        for (int j9 = 0; j9 < k; j9++)
        {
            View view2 = getChildAt(j9);
            if (view2.getVisibility() != 8 && (view2 != mMenuView || mMenuView.getChildCount() != 0))
            {
                i9++;
            }
        }

        if (i9 != 0) goto _L2; else goto _L3
_L3:
        setMeasuredDimension(0, 0);
        mIsCollapsed = true;
_L10:
        return;
_L2:
        int l;
        int i1;
        int i2;
        int k2;
        int l2;
        boolean flag;
        int k3;
        mIsCollapsed = false;
        if (android.view.View.MeasureSpec.getMode(i) != 0x40000000)
        {
            throw new IllegalStateException((new StringBuilder()).append(getClass().getSimpleName()).append(" can only be used ").append("with android:layout_width=\"MATCH_PARENT\" (or fill_parent)").toString());
        }
        if (android.view.View.MeasureSpec.getMode(j) != 0x80000000)
        {
            throw new IllegalStateException((new StringBuilder()).append(getClass().getSimpleName()).append(" can only be used ").append("with android:layout_height=\"wrap_content\"").toString());
        }
        l = android.view.View.MeasureSpec.getSize(i);
        int j1;
        int k1;
        int l1;
        int j2;
        int i3;
        HomeView homeview;
        if (mContentHeight > 0)
        {
            i1 = mContentHeight;
        } else
        {
            i1 = android.view.View.MeasureSpec.getSize(j);
        }
        j1 = getPaddingTop() + getPaddingBottom();
        k1 = getPaddingLeft();
        l1 = getPaddingRight();
        i2 = i1 - j1;
        j2 = android.view.View.MeasureSpec.makeMeasureSpec(i2, 0x80000000);
        k2 = l - k1 - l1;
        l2 = k2 / 2;
        i3 = l2;
        if (mExpandedActionView != null)
        {
            homeview = mExpandedHomeLayout;
        } else
        {
            homeview = mHomeLayout;
        }
        if (homeview.getVisibility() != 8)
        {
            android.view.ViewGroup.LayoutParams layoutparams2 = homeview.getLayoutParams();
            int l3;
            int i4;
            int j4;
            int k4;
            int i5;
            int l5;
            int j8;
            int k8;
            int l8;
            if (layoutparams2.width < 0)
            {
                j8 = android.view.View.MeasureSpec.makeMeasureSpec(k2, 0x80000000);
            } else
            {
                j8 = android.view.View.MeasureSpec.makeMeasureSpec(layoutparams2.width, 0x40000000);
            }
            k8 = android.view.View.MeasureSpec.makeMeasureSpec(i2, 0x40000000);
            homeview.measure(j8, k8);
            l8 = homeview.getMeasuredWidth() + homeview.getLeftOffset();
            k2 = Math.max(0, k2 - l8);
            l2 = Math.max(0, k2 - l8);
        }
        if (mMenuView != null && mMenuView.getParent() == this)
        {
            k2 = measureChildView(mMenuView, k2, j2, 0);
            i3 = Math.max(0, i3 - mMenuView.getMeasuredWidth());
        }
        if (mIndeterminateProgressView != null && mIndeterminateProgressView.getVisibility() != 8)
        {
            k2 = measureChildView(mIndeterminateProgressView, k2, j2, 0);
            i3 = Math.max(0, i3 - mIndeterminateProgressView.getMeasuredWidth());
        }
        if (mTitleLayout != null && mTitleLayout.getVisibility() != 8 && (8 & mDisplayOptions) != 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        if (mExpandedActionView != null) goto _L5; else goto _L4
_L4:
        mNavigationMode;
        JVM INSTR tableswitch 1 2: default 536
    //                   1 918
    //                   2 1026;
           goto _L6 _L7 _L8
_L6:
        break; /* Loop/switch isn't completed */
_L8:
        break MISSING_BLOCK_LABEL_1026;
_L5:
        View view;
        if (mExpandedActionView != null)
        {
            view = mExpandedActionView;
        } else
        {
            int j3 = 0x10 & mDisplayOptions;
            view = null;
            if (j3 != 0)
            {
                View view1 = mCustomNavView;
                view = null;
                if (view1 != null)
                {
                    view = mCustomNavView;
                }
            }
        }
        if (view != null)
        {
            android.view.ViewGroup.LayoutParams layoutparams = generateLayoutParams(view.getLayoutParams());
            android.support.v7.app.ActionBar.LayoutParams layoutparams1;
            int l4;
            int j5;
            int k5;
            int i6;
            int j6;
            int k6;
            int l6;
            int i7;
            int j7;
            int k7;
            int l7;
            int i8;
            if (layoutparams instanceof android.support.v7.app.ActionBar.LayoutParams)
            {
                layoutparams1 = (android.support.v7.app.ActionBar.LayoutParams)layoutparams;
            } else
            {
                layoutparams1 = null;
            }
            j4 = 0;
            k4 = 0;
            if (layoutparams1 != null)
            {
                j4 = layoutparams1.leftMargin + layoutparams1.rightMargin;
                k4 = layoutparams1.topMargin + layoutparams1.bottomMargin;
            }
            if (mContentHeight <= 0)
            {
                l4 = 0x80000000;
            } else
            if (layoutparams.height != -2)
            {
                l4 = 0x40000000;
            } else
            {
                l4 = 0x80000000;
            }
            if (layoutparams.height >= 0)
            {
                i2 = Math.min(layoutparams.height, i2);
            }
            i5 = Math.max(0, i2 - k4);
            if (layoutparams.width != -2)
            {
                j5 = 0x40000000;
            } else
            {
                j5 = 0x80000000;
            }
            if (layoutparams.width >= 0)
            {
                k5 = Math.min(layoutparams.width, k2);
            } else
            {
                k5 = k2;
            }
            l5 = Math.max(0, k5 - j4);
            if (layoutparams1 != null)
            {
                i6 = layoutparams1.gravity;
            } else
            {
                i6 = 19;
            }
            if ((i6 & 7) == 1 && layoutparams.width == -1)
            {
                l5 = 2 * Math.min(l2, i3);
            }
            view.measure(android.view.View.MeasureSpec.makeMeasureSpec(l5, j5), android.view.View.MeasureSpec.makeMeasureSpec(i5, l4));
            k2 -= j4 + view.getMeasuredWidth();
        }
        if (mExpandedActionView == null && flag)
        {
            measureChildView(mTitleLayout, k2, android.view.View.MeasureSpec.makeMeasureSpec(mContentHeight, 0x40000000), 0);
            Math.max(0, l2 - mTitleLayout.getMeasuredWidth());
        }
        if (mContentHeight > 0)
        {
            break; /* Loop/switch isn't completed */
        }
        k3 = 0;
        for (l3 = 0; l3 < k; l3++)
        {
            i4 = j1 + getChildAt(l3).getMeasuredHeight();
            if (i4 > k3)
            {
                k3 = i4;
            }
        }

        break MISSING_BLOCK_LABEL_1228;
_L7:
        if (mListNavLayout != null)
        {
            if (flag)
            {
                j7 = 2 * mItemPadding;
            } else
            {
                j7 = mItemPadding;
            }
            k7 = Math.max(0, k2 - j7);
            l7 = Math.max(0, l2 - j7);
            mListNavLayout.measure(android.view.View.MeasureSpec.makeMeasureSpec(k7, 0x80000000), android.view.View.MeasureSpec.makeMeasureSpec(i2, 0x40000000));
            i8 = mListNavLayout.getMeasuredWidth();
            k2 = Math.max(0, k7 - i8);
            l2 = Math.max(0, l7 - i8);
        }
          goto _L5
        if (mTabScrollView != null)
        {
            if (flag)
            {
                j6 = 2 * mItemPadding;
            } else
            {
                j6 = mItemPadding;
            }
            k6 = Math.max(0, k2 - j6);
            l6 = Math.max(0, l2 - j6);
            mTabScrollView.measure(android.view.View.MeasureSpec.makeMeasureSpec(k6, 0x80000000), android.view.View.MeasureSpec.makeMeasureSpec(i2, 0x40000000));
            i7 = mTabScrollView.getMeasuredWidth();
            k2 = Math.max(0, k6 - i7);
            l2 = Math.max(0, l6 - i7);
        }
          goto _L5
        setMeasuredDimension(l, k3);
_L11:
        if (mContextView != null)
        {
            mContextView.setContentHeight(getMeasuredHeight());
        }
        if (mProgressView != null && mProgressView.getVisibility() != 8)
        {
            mProgressView.measure(android.view.View.MeasureSpec.makeMeasureSpec(l - 2 * mProgressBarPadding, 0x40000000), android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0x80000000));
            return;
        }
        if (true) goto _L10; else goto _L9
_L9:
        setMeasuredDimension(l, i1);
          goto _L11
        if (true) goto _L10; else goto _L12
_L12:
    }

    public void onRestoreInstanceState(Parcelable parcelable)
    {
        SavedState savedstate = (SavedState)parcelable;
        super.onRestoreInstanceState(savedstate.getSuperState());
        if (savedstate.expandedMenuItemId != 0 && mExpandedMenuPresenter != null && mOptionsMenu != null)
        {
            SupportMenuItem supportmenuitem = (SupportMenuItem)mOptionsMenu.findItem(savedstate.expandedMenuItemId);
            if (supportmenuitem != null)
            {
                supportmenuitem.expandActionView();
            }
        }
        if (savedstate.isOverflowOpen)
        {
            postShowOverflowMenu();
        }
    }

    public Parcelable onSaveInstanceState()
    {
        SavedState savedstate = new SavedState(super.onSaveInstanceState());
        if (mExpandedMenuPresenter != null && mExpandedMenuPresenter.mCurrentExpandedItem != null)
        {
            savedstate.expandedMenuItemId = mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        savedstate.isOverflowOpen = isOverflowMenuShowing();
        return savedstate;
    }

    public volatile void postShowOverflowMenu()
    {
        super.postShowOverflowMenu();
    }

    public void setCallback(android.support.v7.app.ActionBar.OnNavigationListener onnavigationlistener)
    {
        mCallback = onnavigationlistener;
    }

    public void setCollapsable(boolean flag)
    {
        mIsCollapsable = flag;
    }

    public volatile void setContentHeight(int i)
    {
        super.setContentHeight(i);
    }

    public void setContextView(ActionBarContextView actionbarcontextview)
    {
        mContextView = actionbarcontextview;
    }

    public void setCustomNavigationView(View view)
    {
        boolean flag;
        if ((0x10 & mDisplayOptions) != 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        if (mCustomNavView != null && flag)
        {
            removeView(mCustomNavView);
        }
        mCustomNavView = view;
        if (mCustomNavView != null && flag)
        {
            addView(mCustomNavView);
        }
    }

    public void setDisplayOptions(int i)
    {
        byte byte0 = 8;
        int j = -1;
        boolean flag = true;
        if (mDisplayOptions != j)
        {
            j = i ^ mDisplayOptions;
        }
        mDisplayOptions = i;
        if ((j & 0x1f) != 0)
        {
            boolean flag1;
            int k;
            if ((i & 2) != 0)
            {
                flag1 = flag;
            } else
            {
                flag1 = false;
            }
            if (flag1 && mExpandedActionView == null)
            {
                k = 0;
            } else
            {
                k = byte0;
            }
            mHomeLayout.setVisibility(k);
            if ((j & 4) != 0)
            {
                View view;
                LinearLayout linearlayout;
                HomeView homeview;
                boolean flag4;
                if ((i & 4) != 0)
                {
                    flag4 = flag;
                } else
                {
                    flag4 = false;
                }
                mHomeLayout.setUp(flag4);
                if (flag4)
                {
                    setHomeButtonEnabled(flag);
                }
            }
            if ((j & 1) != 0)
            {
                boolean flag3;
                Drawable drawable;
                if (mLogo != null && (i & 1) != 0)
                {
                    flag3 = flag;
                } else
                {
                    flag3 = false;
                }
                homeview = mHomeLayout;
                if (flag3)
                {
                    drawable = mLogo;
                } else
                {
                    drawable = mIcon;
                }
                homeview.setIcon(drawable);
            }
            if ((j & 8) != 0)
            {
                if ((i & 8) != 0)
                {
                    initTitle();
                } else
                {
                    removeView(mTitleLayout);
                }
            }
            if (mTitleLayout != null && (j & 6) != 0)
            {
                boolean flag2;
                if ((4 & mDisplayOptions) != 0)
                {
                    flag2 = flag;
                } else
                {
                    flag2 = false;
                }
                view = mTitleUpView;
                if (!flag1)
                {
                    if (flag2)
                    {
                        byte0 = 0;
                    } else
                    {
                        byte0 = 4;
                    }
                }
                view.setVisibility(byte0);
                linearlayout = mTitleLayout;
                if (flag1 || !flag2)
                {
                    flag = false;
                }
                linearlayout.setEnabled(flag);
            }
            if ((j & 0x10) != 0 && mCustomNavView != null)
            {
                if ((i & 0x10) != 0)
                {
                    addView(mCustomNavView);
                } else
                {
                    removeView(mCustomNavView);
                }
            }
            requestLayout();
        } else
        {
            invalidate();
        }
        if (!mHomeLayout.isEnabled())
        {
            mHomeLayout.setContentDescription(null);
            return;
        }
        if ((i & 4) != 0)
        {
            mHomeLayout.setContentDescription(mContext.getResources().getText(android.support.v7.appcompat.R.string.abc_action_bar_up_description));
            return;
        } else
        {
            mHomeLayout.setContentDescription(mContext.getResources().getText(android.support.v7.appcompat.R.string.abc_action_bar_home_description));
            return;
        }
    }

    public void setDropdownAdapter(SpinnerAdapter spinneradapter)
    {
        mSpinnerAdapter = spinneradapter;
        if (mSpinner != null)
        {
            mSpinner.setAdapter(spinneradapter);
        }
    }

    public void setDropdownSelectedPosition(int i)
    {
        mSpinner.setSelection(i);
    }

    public void setEmbeddedTabView(ScrollingTabContainerView scrollingtabcontainerview)
    {
        if (mTabScrollView != null)
        {
            removeView(mTabScrollView);
        }
        mTabScrollView = scrollingtabcontainerview;
        boolean flag;
        if (scrollingtabcontainerview != null)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        mIncludeTabs = flag;
        if (mIncludeTabs && mNavigationMode == 2)
        {
            addView(mTabScrollView);
            android.view.ViewGroup.LayoutParams layoutparams = mTabScrollView.getLayoutParams();
            layoutparams.width = -2;
            layoutparams.height = -1;
            scrollingtabcontainerview.setAllowCollapse(true);
        }
    }

    public void setHomeAsUpIndicator(int i)
    {
        mHomeLayout.setUpIndicator(i);
    }

    public void setHomeAsUpIndicator(Drawable drawable)
    {
        mHomeLayout.setUpIndicator(drawable);
    }

    public void setHomeButtonEnabled(boolean flag)
    {
        mHomeLayout.setEnabled(flag);
        mHomeLayout.setFocusable(flag);
        if (!flag)
        {
            mHomeLayout.setContentDescription(null);
            return;
        }
        if ((4 & mDisplayOptions) != 0)
        {
            mHomeLayout.setContentDescription(mContext.getResources().getText(android.support.v7.appcompat.R.string.abc_action_bar_up_description));
            return;
        } else
        {
            mHomeLayout.setContentDescription(mContext.getResources().getText(android.support.v7.appcompat.R.string.abc_action_bar_home_description));
            return;
        }
    }

    public void setIcon(int i)
    {
        setIcon(mContext.getResources().getDrawable(i));
    }

    public void setIcon(Drawable drawable)
    {
        mIcon = drawable;
        if (drawable != null && ((1 & mDisplayOptions) == 0 || mLogo == null))
        {
            mHomeLayout.setIcon(drawable);
        }
        if (mExpandedActionView != null)
        {
            mExpandedHomeLayout.setIcon(mIcon.getConstantState().newDrawable(getResources()));
        }
    }

    public void setLogo(int i)
    {
        setLogo(mContext.getResources().getDrawable(i));
    }

    public void setLogo(Drawable drawable)
    {
        mLogo = drawable;
        if (drawable != null && (1 & mDisplayOptions) != 0)
        {
            mHomeLayout.setIcon(drawable);
        }
    }

    public void setMenu(SupportMenu supportmenu, android.support.v7.internal.view.menu.MenuPresenter.Callback callback)
    {
        if (supportmenu == mOptionsMenu)
        {
            return;
        }
        if (mOptionsMenu != null)
        {
            mOptionsMenu.removeMenuPresenter(mActionMenuPresenter);
            mOptionsMenu.removeMenuPresenter(mExpandedMenuPresenter);
        }
        MenuBuilder menubuilder = (MenuBuilder)supportmenu;
        mOptionsMenu = menubuilder;
        if (mMenuView != null)
        {
            ViewGroup viewgroup2 = (ViewGroup)mMenuView.getParent();
            if (viewgroup2 != null)
            {
                viewgroup2.removeView(mMenuView);
            }
        }
        if (mActionMenuPresenter == null)
        {
            mActionMenuPresenter = new ActionMenuPresenter(mContext);
            mActionMenuPresenter.setCallback(callback);
            mActionMenuPresenter.setId(android.support.v7.appcompat.R.id.action_menu_presenter);
            mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }
        android.view.ViewGroup.LayoutParams layoutparams = new android.view.ViewGroup.LayoutParams(-2, -1);
        ActionMenuView actionmenuview;
        if (!mSplitActionBar)
        {
            mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(android.support.v7.appcompat.R.bool.abc_action_bar_expanded_action_views_exclusive));
            configPresenters(menubuilder);
            actionmenuview = (ActionMenuView)mActionMenuPresenter.getMenuView(this);
            actionmenuview.initialize(menubuilder);
            ViewGroup viewgroup1 = (ViewGroup)actionmenuview.getParent();
            if (viewgroup1 != null && viewgroup1 != this)
            {
                viewgroup1.removeView(actionmenuview);
            }
            addView(actionmenuview, layoutparams);
        } else
        {
            mActionMenuPresenter.setExpandedActionViewsExclusive(false);
            mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
            mActionMenuPresenter.setItemLimit(0x7fffffff);
            layoutparams.width = -1;
            configPresenters(menubuilder);
            actionmenuview = (ActionMenuView)mActionMenuPresenter.getMenuView(this);
            if (mSplitView != null)
            {
                ViewGroup viewgroup = (ViewGroup)actionmenuview.getParent();
                if (viewgroup != null && viewgroup != mSplitView)
                {
                    viewgroup.removeView(actionmenuview);
                }
                actionmenuview.setVisibility(getAnimatedVisibility());
                mSplitView.addView(actionmenuview, layoutparams);
            } else
            {
                actionmenuview.setLayoutParams(layoutparams);
            }
        }
        mMenuView = actionmenuview;
    }

    public void setNavigationMode(int i)
    {
        int j = mNavigationMode;
        if (i == j) goto _L2; else goto _L1
_L1:
        j;
        JVM INSTR tableswitch 1 2: default 32
    //                   1 66
    //                   2 84;
           goto _L3 _L4 _L5
_L3:
        i;
        JVM INSTR tableswitch 1 2: default 56
    //                   1 109
    //                   2 232;
           goto _L6 _L7 _L8
_L6:
        mNavigationMode = i;
        requestLayout();
_L2:
        return;
_L4:
        if (mListNavLayout != null)
        {
            removeView(mListNavLayout);
        }
          goto _L3
_L5:
        if (mTabScrollView != null && mIncludeTabs)
        {
            removeView(mTabScrollView);
        }
          goto _L3
_L7:
        if (mSpinner == null)
        {
            mSpinner = new SpinnerICS(mContext, null, android.support.v7.appcompat.R.attr.actionDropDownStyle);
            mListNavLayout = (LinearLayout)LayoutInflater.from(mContext).inflate(android.support.v7.appcompat.R.layout.abc_action_bar_view_list_nav_layout, null);
            android.widget.LinearLayout.LayoutParams layoutparams = new android.widget.LinearLayout.LayoutParams(-2, -1);
            layoutparams.gravity = 17;
            mListNavLayout.addView(mSpinner, layoutparams);
        }
        if (mSpinner.getAdapter() != mSpinnerAdapter)
        {
            mSpinner.setAdapter(mSpinnerAdapter);
        }
        mSpinner.setOnItemSelectedListener(mNavItemSelectedListener);
        addView(mListNavLayout);
          goto _L6
_L8:
        if (mTabScrollView != null && mIncludeTabs)
        {
            addView(mTabScrollView);
        }
          goto _L6
    }

    public void setSplitActionBar(boolean flag)
    {
        if (mSplitActionBar != flag)
        {
            if (mMenuView != null)
            {
                ViewGroup viewgroup = (ViewGroup)mMenuView.getParent();
                if (viewgroup != null)
                {
                    viewgroup.removeView(mMenuView);
                }
                ActionBarContainer actionbarcontainer;
                if (flag)
                {
                    if (mSplitView != null)
                    {
                        mSplitView.addView(mMenuView);
                    }
                    mMenuView.getLayoutParams().width = -1;
                } else
                {
                    addView(mMenuView);
                    mMenuView.getLayoutParams().width = -2;
                }
                mMenuView.requestLayout();
            }
            if (mSplitView != null)
            {
                actionbarcontainer = mSplitView;
                int i;
                if (flag)
                {
                    i = 0;
                } else
                {
                    i = 8;
                }
                actionbarcontainer.setVisibility(i);
            }
            if (mActionMenuPresenter != null)
            {
                if (!flag)
                {
                    mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(android.support.v7.appcompat.R.bool.abc_action_bar_expanded_action_views_exclusive));
                } else
                {
                    mActionMenuPresenter.setExpandedActionViewsExclusive(false);
                    mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                    mActionMenuPresenter.setItemLimit(0x7fffffff);
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
        if (mSubtitleView != null)
        {
            mSubtitleView.setText(charsequence);
            TextView textview = mSubtitleView;
            int i;
            boolean flag;
            LinearLayout linearlayout;
            int j;
            if (charsequence != null)
            {
                i = 0;
            } else
            {
                i = 8;
            }
            textview.setVisibility(i);
            if (mExpandedActionView == null && (8 & mDisplayOptions) != 0 && (!TextUtils.isEmpty(mTitle) || !TextUtils.isEmpty(mSubtitle)))
            {
                flag = true;
            } else
            {
                flag = false;
            }
            linearlayout = mTitleLayout;
            j = 0;
            if (!flag)
            {
                j = 8;
            }
            linearlayout.setVisibility(j);
        }
    }

    public void setTitle(CharSequence charsequence)
    {
        mUserTitle = true;
        setTitleImpl(charsequence);
    }

    public volatile void setVisibility(int i)
    {
        super.setVisibility(i);
    }

    public void setWindowCallback(android.view.Window.Callback callback)
    {
        mWindowCallback = callback;
    }

    public void setWindowTitle(CharSequence charsequence)
    {
        if (!mUserTitle)
        {
            setTitleImpl(charsequence);
        }
    }

    public boolean shouldDelayChildPressedState()
    {
        return false;
    }

    public volatile boolean showOverflowMenu()
    {
        return super.showOverflowMenu();
    }













}
