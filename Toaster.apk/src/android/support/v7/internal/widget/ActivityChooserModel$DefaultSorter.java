// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.widget;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Referenced classes of package android.support.v7.internal.widget:
//            ActivityChooserModel

private final class <init>
    implements 
{

    private static final float WEIGHT_DECAY_COEFFICIENT = 0.95F;
    private final Map mPackageNameToActivityMap;
    final ActivityChooserModel this$0;

    public void sort(Intent intent, List list, List list1)
    {
        Map map = mPackageNameToActivityMap;
        map.clear();
        int i = list.size();
        for (int j = 0; j < i; j++)
        {
            eInfo einfo1 = (eInfo)list.get(j);
            einfo1.weight = 0.0F;
            map.put(einfo1.resolveInfo.activityInfo.packageName, einfo1);
        }

        int k = -1 + list1.size();
        float f = 1.0F;
        for (int l = k; l >= 0; l--)
        {
            rd rd = (rd)list1.get(l);
            eInfo einfo = (eInfo)map.get(rd.activity.getPackageName());
            if (einfo != null)
            {
                einfo.weight = einfo.weight + f * rd.weight;
                f *= 0.95F;
            }
        }

        Collections.sort(list);
    }

    private rd()
    {
        this$0 = ActivityChooserModel.this;
        super();
        mPackageNameToActivityMap = new HashMap();
    }

    mPackageNameToActivityMap(mPackageNameToActivityMap mpackagenametoactivitymap)
    {
        this();
    }
}
