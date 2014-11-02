// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view;

import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

// Referenced classes of package android.support.v4.view:
//            AccessibilityDelegateCompatJellyBean, AccessibilityDelegateCompat

static class _cls1.val.compat extends _cls1.val.compat
{

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(Object obj, View view)
    {
        Object obj1 = AccessibilityDelegateCompatJellyBean.getAccessibilityNodeProvider(obj, view);
        if (obj1 != null)
        {
            return new AccessibilityNodeProviderCompat(obj1);
        } else
        {
            return null;
        }
    }

    public Object newAccessiblityDelegateBridge(final AccessibilityDelegateCompat compat)
    {
        return AccessibilityDelegateCompatJellyBean.newAccessibilityDelegateBridge(new AccessibilityDelegateCompatJellyBean.AccessibilityDelegateBridgeJellyBean() {

            final AccessibilityDelegateCompat.AccessibilityDelegateJellyBeanImpl this$0;
            final AccessibilityDelegateCompat val$compat;

            public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityevent)
            {
                return compat.dispatchPopulateAccessibilityEvent(view, accessibilityevent);
            }

            public Object getAccessibilityNodeProvider(View view)
            {
                AccessibilityNodeProviderCompat accessibilitynodeprovidercompat = compat.getAccessibilityNodeProvider(view);
                if (accessibilitynodeprovidercompat != null)
                {
                    return accessibilitynodeprovidercompat.getProvider();
                } else
                {
                    return null;
                }
            }

            public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityevent)
            {
                compat.onInitializeAccessibilityEvent(view, accessibilityevent);
            }

            public void onInitializeAccessibilityNodeInfo(View view, Object obj)
            {
                compat.onInitializeAccessibilityNodeInfo(view, new AccessibilityNodeInfoCompat(obj));
            }

            public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityevent)
            {
                compat.onPopulateAccessibilityEvent(view, accessibilityevent);
            }

            public boolean onRequestSendAccessibilityEvent(ViewGroup viewgroup, View view, AccessibilityEvent accessibilityevent)
            {
                return compat.onRequestSendAccessibilityEvent(viewgroup, view, accessibilityevent);
            }

            public boolean performAccessibilityAction(View view, int i, Bundle bundle)
            {
                return compat.performAccessibilityAction(view, i, bundle);
            }

            public void sendAccessibilityEvent(View view, int i)
            {
                compat.sendAccessibilityEvent(view, i);
            }

            public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityevent)
            {
                compat.sendAccessibilityEventUnchecked(view, accessibilityevent);
            }

            
            {
                this$0 = AccessibilityDelegateCompat.AccessibilityDelegateJellyBeanImpl.this;
                compat = accessibilitydelegatecompat;
                super();
            }
        });
    }

    public boolean performAccessibilityAction(Object obj, View view, int i, Bundle bundle)
    {
        return AccessibilityDelegateCompatJellyBean.performAccessibilityAction(obj, view, i, bundle);
    }

    _cls1.val.compat()
    {
    }
}
