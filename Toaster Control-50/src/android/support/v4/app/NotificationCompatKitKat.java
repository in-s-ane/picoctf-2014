// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package android.support.v4.app:
//            NotificationCompatJellybean, NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions

class NotificationCompatKitKat
{
    public static class Builder
        implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions
    {

        private android.app.Notification.Builder b;
        private List mActionExtrasList;
        private Bundle mExtras;

        public void addAction(NotificationCompatBase.Action action)
        {
            mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(b, action));
        }

        public Notification build()
        {
            SparseArray sparsearray = NotificationCompatJellybean.buildActionExtrasMap(mActionExtrasList);
            if (sparsearray != null)
            {
                mExtras.putSparseParcelableArray("android.support.actionExtras", sparsearray);
            }
            b.setExtras(mExtras);
            return b.build();
        }

        public android.app.Notification.Builder getBuilder()
        {
            return b;
        }

        public Builder(Context context, Notification notification, CharSequence charsequence, CharSequence charsequence1, CharSequence charsequence2, RemoteViews remoteviews, int i, 
                PendingIntent pendingintent, PendingIntent pendingintent1, Bitmap bitmap, int j, int k, boolean flag, boolean flag1, 
                int l, CharSequence charsequence3, boolean flag2, Bundle bundle, String s, boolean flag3, String s1)
        {
            mActionExtrasList = new ArrayList();
            android.app.Notification.Builder builder = (new android.app.Notification.Builder(context)).setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteviews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS);
            boolean flag4;
            android.app.Notification.Builder builder1;
            boolean flag5;
            android.app.Notification.Builder builder2;
            boolean flag6;
            android.app.Notification.Builder builder3;
            boolean flag7;
            if ((2 & notification.flags) != 0)
            {
                flag4 = true;
            } else
            {
                flag4 = false;
            }
            builder1 = builder.setOngoing(flag4);
            if ((8 & notification.flags) != 0)
            {
                flag5 = true;
            } else
            {
                flag5 = false;
            }
            builder2 = builder1.setOnlyAlertOnce(flag5);
            if ((0x10 & notification.flags) != 0)
            {
                flag6 = true;
            } else
            {
                flag6 = false;
            }
            builder3 = builder2.setAutoCancel(flag6).setDefaults(notification.defaults).setContentTitle(charsequence).setContentText(charsequence1).setSubText(charsequence3).setContentInfo(charsequence2).setContentIntent(pendingintent).setDeleteIntent(notification.deleteIntent);
            if ((0x80 & notification.flags) != 0)
            {
                flag7 = true;
            } else
            {
                flag7 = false;
            }
            b = builder3.setFullScreenIntent(pendingintent1, flag7).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(flag1).setPriority(l).setProgress(j, k, flag);
            mExtras = new Bundle();
            if (bundle != null)
            {
                mExtras.putAll(bundle);
            }
            if (flag2)
            {
                mExtras.putBoolean("android.support.localOnly", true);
            }
            if (s != null)
            {
                mExtras.putString("android.support.groupKey", s);
                if (flag3)
                {
                    mExtras.putBoolean("android.support.isGroupSummary", true);
                } else
                {
                    mExtras.putBoolean("android.support.useSideChannel", true);
                }
            }
            if (s1 != null)
            {
                mExtras.putString("android.support.sortKey", s1);
            }
        }
    }


    NotificationCompatKitKat()
    {
    }

    public static NotificationCompatBase.Action getAction(Notification notification, int i, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory1)
    {
        android.app.Notification.Action action = notification.actions[i];
        SparseArray sparsearray = notification.extras.getSparseParcelableArray("android.support.actionExtras");
        Bundle bundle = null;
        if (sparsearray != null)
        {
            bundle = (Bundle)sparsearray.get(i);
        }
        return NotificationCompatJellybean.readAction(factory, factory1, action.icon, action.title, action.actionIntent, bundle);
    }

    public static int getActionCount(Notification notification)
    {
        if (notification.actions != null)
        {
            return notification.actions.length;
        } else
        {
            return 0;
        }
    }

    public static Bundle getExtras(Notification notification)
    {
        return notification.extras;
    }

    public static String getGroup(Notification notification)
    {
        return notification.extras.getString("android.support.groupKey");
    }

    public static boolean getLocalOnly(Notification notification)
    {
        return notification.extras.getBoolean("android.support.localOnly");
    }

    public static String getSortKey(Notification notification)
    {
        return notification.extras.getString("android.support.sortKey");
    }

    public static boolean isGroupSummary(Notification notification)
    {
        return notification.extras.getBoolean("android.support.isGroupSummary");
    }
}
