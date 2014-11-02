// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

class ActionBarDrawerToggleHoneycomb
{
    private static class SetIndicatorInfo
    {

        public Method setHomeActionContentDescription;
        public Method setHomeAsUpIndicator;
        public ImageView upIndicatorView;

        SetIndicatorInfo(Activity activity)
        {
            setHomeAsUpIndicator = android/app/ActionBar.getDeclaredMethod("setHomeAsUpIndicator", new Class[] {
                android/graphics/drawable/Drawable
            });
            Class aclass[] = new Class[1];
            aclass[0] = Integer.TYPE;
            setHomeActionContentDescription = android/app/ActionBar.getDeclaredMethod("setHomeActionContentDescription", aclass);
_L1:
            return;
            NoSuchMethodException nosuchmethodexception;
            nosuchmethodexception;
            View view = activity.findViewById(0x102002c);
            if (view != null)
            {
                ViewGroup viewgroup = (ViewGroup)view.getParent();
                if (viewgroup.getChildCount() == 2)
                {
                    View view1 = viewgroup.getChildAt(0);
                    View view2 = viewgroup.getChildAt(1);
                    View view3;
                    if (view1.getId() == 0x102002c)
                    {
                        view3 = view2;
                    } else
                    {
                        view3 = view1;
                    }
                    if (view3 instanceof ImageView)
                    {
                        upIndicatorView = (ImageView)view3;
                        return;
                    }
                }
            }
              goto _L1
        }
    }


    private static final String TAG = "ActionBarDrawerToggleHoneycomb";
    private static final int THEME_ATTRS[] = {
        0x101030b
    };

    ActionBarDrawerToggleHoneycomb()
    {
    }

    public static Drawable getThemeUpIndicator(Activity activity)
    {
        TypedArray typedarray = activity.obtainStyledAttributes(THEME_ATTRS);
        Drawable drawable = typedarray.getDrawable(0);
        typedarray.recycle();
        return drawable;
    }

    public static Object setActionBarDescription(Object obj, Activity activity, int i)
    {
        if (obj == null)
        {
            obj = new SetIndicatorInfo(activity);
        }
        SetIndicatorInfo setindicatorinfo = (SetIndicatorInfo)obj;
        if (setindicatorinfo.setHomeAsUpIndicator != null)
        {
            try
            {
                ActionBar actionbar = activity.getActionBar();
                Method method = setindicatorinfo.setHomeActionContentDescription;
                Object aobj[] = new Object[1];
                aobj[0] = Integer.valueOf(i);
                method.invoke(actionbar, aobj);
                if (android.os.Build.VERSION.SDK_INT <= 19)
                {
                    actionbar.setSubtitle(actionbar.getSubtitle());
                }
            }
            catch (Exception exception)
            {
                Log.w("ActionBarDrawerToggleHoneycomb", "Couldn't set content description via JB-MR2 API", exception);
                return obj;
            }
        }
        return obj;
    }

    public static Object setActionBarUpIndicator(Object obj, Activity activity, Drawable drawable, int i)
    {
        if (obj == null)
        {
            obj = new SetIndicatorInfo(activity);
        }
        SetIndicatorInfo setindicatorinfo = (SetIndicatorInfo)obj;
        if (setindicatorinfo.setHomeAsUpIndicator != null)
        {
            try
            {
                ActionBar actionbar = activity.getActionBar();
                setindicatorinfo.setHomeAsUpIndicator.invoke(actionbar, new Object[] {
                    drawable
                });
                Method method = setindicatorinfo.setHomeActionContentDescription;
                Object aobj[] = new Object[1];
                aobj[0] = Integer.valueOf(i);
                method.invoke(actionbar, aobj);
            }
            catch (Exception exception)
            {
                Log.w("ActionBarDrawerToggleHoneycomb", "Couldn't set home-as-up indicator via JB-MR2 API", exception);
                return obj;
            }
            return obj;
        }
        if (setindicatorinfo.upIndicatorView != null)
        {
            setindicatorinfo.upIndicatorView.setImageDrawable(drawable);
            return obj;
        } else
        {
            Log.w("ActionBarDrawerToggleHoneycomb", "Couldn't set home-as-up indicator");
            return obj;
        }
    }

}
