// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

// Referenced classes of package android.support.v4.app:
//            ActionBarDrawerToggleHoneycomb

private static class upIndicatorView
{

    public Method setHomeActionContentDescription;
    public Method setHomeAsUpIndicator;
    public ImageView upIndicatorView;

    (Activity activity)
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
