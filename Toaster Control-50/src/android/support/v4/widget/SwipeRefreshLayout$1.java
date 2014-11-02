// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.widget;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

// Referenced classes of package android.support.v4.widget:
//            SwipeRefreshLayout

class this._cls0 extends Animation
{

    final SwipeRefreshLayout this$0;

    public void applyTransformation(float f, Transformation transformation)
    {
        int i = SwipeRefreshLayout.access$000(SwipeRefreshLayout.this);
        int j = SwipeRefreshLayout.access$100(SwipeRefreshLayout.this);
        int k = 0;
        if (i != j)
        {
            k = SwipeRefreshLayout.access$000(SwipeRefreshLayout.this) + (int)(f * (float)(SwipeRefreshLayout.access$100(SwipeRefreshLayout.this) - SwipeRefreshLayout.access$000(SwipeRefreshLayout.this)));
        }
        int l = k - SwipeRefreshLayout.access$200(SwipeRefreshLayout.this).getTop();
        int i1 = SwipeRefreshLayout.access$200(SwipeRefreshLayout.this).getTop();
        if (l + i1 < 0)
        {
            l = 0 - i1;
        }
        SwipeRefreshLayout.access$300(SwipeRefreshLayout.this, l);
    }

    ()
    {
        this$0 = SwipeRefreshLayout.this;
        super();
    }
}
