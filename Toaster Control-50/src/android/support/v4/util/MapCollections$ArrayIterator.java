// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.util;

import java.util.Iterator;

// Referenced classes of package android.support.v4.util:
//            MapCollections

final class mSize
    implements Iterator
{

    boolean mCanRemove;
    int mIndex;
    final int mOffset;
    int mSize;
    final MapCollections this$0;

    public boolean hasNext()
    {
        return mIndex < mSize;
    }

    public Object next()
    {
        Object obj = colGetEntry(mIndex, mOffset);
        mIndex = 1 + mIndex;
        mCanRemove = true;
        return obj;
    }

    public void remove()
    {
        if (!mCanRemove)
        {
            throw new IllegalStateException();
        } else
        {
            mIndex = -1 + mIndex;
            mSize = -1 + mSize;
            mCanRemove = false;
            colRemoveAt(mIndex);
            return;
        }
    }

    (int i)
    {
        this$0 = MapCollections.this;
        super();
        mCanRemove = false;
        mOffset = i;
        mSize = colGetSize();
    }
}
