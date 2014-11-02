// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.os.Bundle;

// Referenced classes of package android.support.v4.app:
//            NotificationCompat

public static final class mFlags
    implements mFlags
{

    private static final int DEFAULT_FLAGS = 1;
    private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
    private static final int FLAG_AVAILABLE_OFFLINE = 1;
    private static final String KEY_FLAGS = "flags";
    private int mFlags;

    private void setFlag(int i, boolean flag)
    {
        if (flag)
        {
            mFlags = i | mFlags;
            return;
        } else
        {
            mFlags = mFlags & ~i;
            return;
        }
    }

    public mFlags clone()
    {
        mFlags mflags = new <init>();
        mflags.mFlags = mFlags;
        return mflags;
    }

    public volatile Object clone()
        throws CloneNotSupportedException
    {
        return clone();
    }

    public clone extend(clone clone1)
    {
        Bundle bundle = new Bundle();
        if (mFlags != 1)
        {
            bundle.putInt("flags", mFlags);
        }
        clone1.().putBundle("android.wearable.EXTENSIONS", bundle);
        return clone1;
    }

    public boolean isAvailableOffline()
    {
        return (1 & mFlags) != 0;
    }

    public mFlags setAvailableOffline(boolean flag)
    {
        setFlag(1, flag);
        return this;
    }

    public ()
    {
        mFlags = 1;
    }

    public mFlags(mFlags mflags)
    {
        mFlags = 1;
        Bundle bundle = mflags.mFlags().getBundle("android.wearable.EXTENSIONS");
        if (bundle != null)
        {
            mFlags = bundle.getInt("flags", 1);
        }
    }
}
