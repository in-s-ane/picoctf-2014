// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import java.io.File;

// Referenced classes of package android.support.v4.content:
//            ContextCompatKitKat, ContextCompatFroyo, ContextCompatHoneycomb, ContextCompatJellybean

public class ContextCompat
{

    private static final String DIR_ANDROID = "Android";
    private static final String DIR_CACHE = "cache";
    private static final String DIR_DATA = "data";
    private static final String DIR_FILES = "files";
    private static final String DIR_OBB = "obb";

    public ContextCompat()
    {
    }

    private static transient File buildPath(File file, String as[])
    {
        int i = as.length;
        int j = 0;
        File file1 = file;
        while (j < i) 
        {
            String s = as[j];
            File file2;
            if (file1 == null)
            {
                file2 = new File(s);
            } else
            if (s != null)
            {
                file2 = new File(file1, s);
            } else
            {
                file2 = file1;
            }
            j++;
            file1 = file2;
        }
        return file1;
    }

    public static File[] getExternalCacheDirs(Context context)
    {
        int i = android.os.Build.VERSION.SDK_INT;
        if (i >= 19)
        {
            return ContextCompatKitKat.getExternalCacheDirs(context);
        }
        File file1;
        if (i >= 8)
        {
            file1 = ContextCompatFroyo.getExternalCacheDir(context);
        } else
        {
            File file = Environment.getExternalStorageDirectory();
            String as[] = new String[4];
            as[0] = "Android";
            as[1] = "data";
            as[2] = context.getPackageName();
            as[3] = "cache";
            file1 = buildPath(file, as);
        }
        return (new File[] {
            file1
        });
    }

    public static File[] getExternalFilesDirs(Context context, String s)
    {
        int i = android.os.Build.VERSION.SDK_INT;
        if (i >= 19)
        {
            return ContextCompatKitKat.getExternalFilesDirs(context, s);
        }
        File file1;
        if (i >= 8)
        {
            file1 = ContextCompatFroyo.getExternalFilesDir(context, s);
        } else
        {
            File file = Environment.getExternalStorageDirectory();
            String as[] = new String[5];
            as[0] = "Android";
            as[1] = "data";
            as[2] = context.getPackageName();
            as[3] = "files";
            as[4] = s;
            file1 = buildPath(file, as);
        }
        return (new File[] {
            file1
        });
    }

    public static File[] getObbDirs(Context context)
    {
        int i = android.os.Build.VERSION.SDK_INT;
        if (i >= 19)
        {
            return ContextCompatKitKat.getObbDirs(context);
        }
        File file1;
        if (i >= 11)
        {
            file1 = ContextCompatHoneycomb.getObbDir(context);
        } else
        {
            File file = Environment.getExternalStorageDirectory();
            String as[] = new String[3];
            as[0] = "Android";
            as[1] = "obb";
            as[2] = context.getPackageName();
            file1 = buildPath(file, as);
        }
        return (new File[] {
            file1
        });
    }

    public static boolean startActivities(Context context, Intent aintent[])
    {
        return startActivities(context, aintent, null);
    }

    public static boolean startActivities(Context context, Intent aintent[], Bundle bundle)
    {
        int i = android.os.Build.VERSION.SDK_INT;
        if (i >= 16)
        {
            ContextCompatJellybean.startActivities(context, aintent, bundle);
            return true;
        }
        if (i >= 11)
        {
            ContextCompatHoneycomb.startActivities(context, aintent);
            return true;
        } else
        {
            return false;
        }
    }
}
