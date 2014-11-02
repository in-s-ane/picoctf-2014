// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseArray;

public abstract class WakefulBroadcastReceiver extends BroadcastReceiver
{

    private static final String EXTRA_WAKE_LOCK_ID = "android.support.content.wakelockid";
    private static final SparseArray mActiveWakeLocks = new SparseArray();
    private static int mNextId = 1;

    public WakefulBroadcastReceiver()
    {
    }

    public static boolean completeWakefulIntent(Intent intent)
    {
        int i;
        i = intent.getIntExtra("android.support.content.wakelockid", 0);
        if (i == 0)
        {
            return false;
        }
        SparseArray sparsearray = mActiveWakeLocks;
        sparsearray;
        JVM INSTR monitorenter ;
        android.os.PowerManager.WakeLock wakelock = (android.os.PowerManager.WakeLock)mActiveWakeLocks.get(i);
        if (wakelock == null)
        {
            break MISSING_BLOCK_LABEL_53;
        }
        wakelock.release();
        mActiveWakeLocks.remove(i);
        sparsearray;
        JVM INSTR monitorexit ;
        return true;
        Log.w("WakefulBroadcastReceiver", (new StringBuilder()).append("No active wake lock id #").append(i).toString());
        sparsearray;
        JVM INSTR monitorexit ;
        return true;
        Exception exception;
        exception;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public static ComponentName startWakefulService(Context context, Intent intent)
    {
        SparseArray sparsearray = mActiveWakeLocks;
        sparsearray;
        JVM INSTR monitorenter ;
        int i;
        ComponentName componentname;
        i = mNextId;
        mNextId = 1 + mNextId;
        if (mNextId <= 0)
        {
            mNextId = 1;
        }
        intent.putExtra("android.support.content.wakelockid", i);
        componentname = context.startService(intent);
        if (componentname != null)
        {
            break MISSING_BLOCK_LABEL_54;
        }
        sparsearray;
        JVM INSTR monitorexit ;
        return null;
        android.os.PowerManager.WakeLock wakelock = ((PowerManager)context.getSystemService("power")).newWakeLock(1, (new StringBuilder()).append("wake:").append(componentname.flattenToShortString()).toString());
        wakelock.setReferenceCounted(false);
        wakelock.acquire(60000L);
        mActiveWakeLocks.put(i, wakelock);
        sparsearray;
        JVM INSTR monitorexit ;
        return componentname;
        Exception exception;
        exception;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception;
    }

}
