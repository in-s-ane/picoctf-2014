// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import java.util.ArrayList;

// Referenced classes of package android.support.v4.app:
//            RemoteInputCompatApi20, NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions

class NotificationCompatApi20
{
    public static class Builder
        implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions
    {

        private android.app.Notification.Builder b;

        public void addAction(NotificationCompatBase.Action action)
        {
            android.app.Notification.Action.Builder builder = new android.app.Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent());
            if (action.getRemoteInputs() != null)
            {
                android.app.RemoteInput aremoteinput[] = RemoteInputCompatApi20.fromCompat(action.getRemoteInputs());
                int i = aremoteinput.length;
                for (int j = 0; j < i; j++)
                {
                    builder.addRemoteInput(aremoteinput[j]);
                }

            }
            if (action.getExtras() != null)
            {
                builder.addExtras(action.getExtras());
            }
            b.addAction(builder.build());
        }

        public Notification build()
        {
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
            b = builder3.setFullScreenIntent(pendingintent1, flag7).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(flag1).setPriority(l).setProgress(j, k, flag).setLocalOnly(flag2).setExtras(bundle).setGroup(s).setGroupSummary(flag3).setSortKey(s1);
        }
    }


    NotificationCompatApi20()
    {
    }

    public static NotificationCompatBase.Action getAction(Notification notification, int i, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory1)
    {
        return getActionCompatFromAction(notification.actions[i], factory, factory1);
    }

    private static NotificationCompatBase.Action getActionCompatFromAction(android.app.Notification.Action action, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory1)
    {
        RemoteInputCompatBase.RemoteInput aremoteinput[] = RemoteInputCompatApi20.toCompat(action.getRemoteInputs(), factory1);
        return factory.build(action.icon, action.title, action.actionIntent, action.getExtras(), aremoteinput);
    }

    private static android.app.Notification.Action getActionFromActionCompat(NotificationCompatBase.Action action)
    {
        android.app.Notification.Action.Builder builder = (new android.app.Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent())).addExtras(action.getExtras());
        RemoteInputCompatBase.RemoteInput aremoteinput[] = action.getRemoteInputs();
        if (aremoteinput != null)
        {
            android.app.RemoteInput aremoteinput1[] = RemoteInputCompatApi20.fromCompat(aremoteinput);
            int i = aremoteinput1.length;
            for (int j = 0; j < i; j++)
            {
                builder.addRemoteInput(aremoteinput1[j]);
            }

        }
        return builder.build();
    }

    public static NotificationCompatBase.Action[] getActionsFromParcelableArrayList(ArrayList arraylist, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory1)
    {
        NotificationCompatBase.Action aaction[];
        if (arraylist == null)
        {
            aaction = null;
        } else
        {
            aaction = factory.newArray(arraylist.size());
            int i = 0;
            while (i < aaction.length) 
            {
                aaction[i] = getActionCompatFromAction((android.app.Notification.Action)arraylist.get(i), factory, factory1);
                i++;
            }
        }
        return aaction;
    }

    public static String getGroup(Notification notification)
    {
        return notification.getGroup();
    }

    public static boolean getLocalOnly(Notification notification)
    {
        return (0x100 & notification.flags) != 0;
    }

    public static ArrayList getParcelableArrayListForActions(NotificationCompatBase.Action aaction[])
    {
        ArrayList arraylist;
        if (aaction == null)
        {
            arraylist = null;
        } else
        {
            arraylist = new ArrayList(aaction.length);
            int i = aaction.length;
            int j = 0;
            while (j < i) 
            {
                arraylist.add(getActionFromActionCompat(aaction[j]));
                j++;
            }
        }
        return arraylist;
    }

    public static String getSortKey(Notification notification)
    {
        return notification.getSortKey();
    }

    public static boolean isGroupSummary(Notification notification)
    {
        return (0x200 & notification.flags) != 0;
    }
}
