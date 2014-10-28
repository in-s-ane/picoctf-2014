// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.widget;

import android.util.SparseArray;
import android.view.View;

// Referenced classes of package android.support.v7.internal.widget:
//            AbsSpinnerICS

class this._cls0
{

    private final SparseArray mScrapHeap = new SparseArray();
    final AbsSpinnerICS this$0;

    void clear()
    {
        SparseArray sparsearray = mScrapHeap;
        int i = sparsearray.size();
        for (int j = 0; j < i; j++)
        {
            View view = (View)sparsearray.valueAt(j);
            if (view != null)
            {
                AbsSpinnerICS.access$100(AbsSpinnerICS.this, view, true);
            }
        }

        sparsearray.clear();
    }

    View get(int i)
    {
        View view = (View)mScrapHeap.get(i);
        if (view != null)
        {
            mScrapHeap.delete(i);
        }
        return view;
    }

    public void put(int i, View view)
    {
        mScrapHeap.put(i, view);
    }

    A()
    {
        this$0 = AbsSpinnerICS.this;
        super();
    }
}
