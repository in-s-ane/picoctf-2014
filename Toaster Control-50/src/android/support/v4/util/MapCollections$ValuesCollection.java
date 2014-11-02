// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.util;

import java.util.Collection;
import java.util.Iterator;

// Referenced classes of package android.support.v4.util:
//            MapCollections

final class this._cls0
    implements Collection
{

    final MapCollections this$0;

    public boolean add(Object obj)
    {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection collection)
    {
        throw new UnsupportedOperationException();
    }

    public void clear()
    {
        colClear();
    }

    public boolean contains(Object obj)
    {
        return colIndexOfValue(obj) >= 0;
    }

    public boolean containsAll(Collection collection)
    {
        for (Iterator iterator1 = collection.iterator(); iterator1.hasNext();)
        {
            if (!contains(iterator1.next()))
            {
                return false;
            }
        }

        return true;
    }

    public boolean isEmpty()
    {
        return colGetSize() == 0;
    }

    public Iterator iterator()
    {
        return new it>(MapCollections.this, 1);
    }

    public boolean remove(Object obj)
    {
        int i = colIndexOfValue(obj);
        if (i >= 0)
        {
            colRemoveAt(i);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean removeAll(Collection collection)
    {
        int i = colGetSize();
        boolean flag = false;
        for (int j = 0; j < i; j++)
        {
            if (collection.contains(colGetEntry(j, 1)))
            {
                colRemoveAt(j);
                j--;
                i--;
                flag = true;
            }
        }

        return flag;
    }

    public boolean retainAll(Collection collection)
    {
        int i = colGetSize();
        boolean flag = false;
        for (int j = 0; j < i; j++)
        {
            if (!collection.contains(colGetEntry(j, 1)))
            {
                colRemoveAt(j);
                j--;
                i--;
                flag = true;
            }
        }

        return flag;
    }

    public int size()
    {
        return colGetSize();
    }

    public Object[] toArray()
    {
        return toArrayHelper(1);
    }

    public Object[] toArray(Object aobj[])
    {
        return toArrayHelper(aobj, 1);
    }

    ()
    {
        this$0 = MapCollections.this;
        super();
    }
}
