// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.os.Bundle;
import java.util.Arrays;

class BundleUtil
{

    BundleUtil()
    {
    }

    public static Bundle[] getBundleArrayFromBundle(Bundle bundle, String s)
    {
        android.os.Parcelable aparcelable[] = bundle.getParcelableArray(s);
        if ((aparcelable instanceof Bundle[]) || aparcelable == null)
        {
            return (Bundle[])(Bundle[])aparcelable;
        } else
        {
            Bundle abundle[] = (Bundle[])Arrays.copyOf(aparcelable, aparcelable.length, [Landroid/os/Bundle;);
            bundle.putParcelableArray(s, abundle);
            return abundle;
        }
    }
}
