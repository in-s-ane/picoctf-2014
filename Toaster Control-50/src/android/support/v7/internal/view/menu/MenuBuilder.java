// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Referenced classes of package android.support.v7.internal.view.menu:
//            MenuItemImpl, MenuPresenter, SubMenuBuilder

public class MenuBuilder
    implements SupportMenu
{
    public static interface Callback
    {

        public abstract boolean onMenuItemSelected(MenuBuilder menubuilder, MenuItem menuitem);

        public abstract void onMenuModeChange(MenuBuilder menubuilder);
    }

    public static interface ItemInvoker
    {

        public abstract boolean invokeItem(MenuItemImpl menuitemimpl);
    }


    private static final String ACTION_VIEW_STATES_KEY = "android:menu:actionviewstates";
    private static final String EXPANDED_ACTION_VIEW_ID = "android:menu:expandedactionview";
    private static final String PRESENTER_KEY = "android:menu:presenters";
    private static final String TAG = "MenuBuilder";
    private static final int sCategoryToOrder[] = {
        1, 4, 5, 3, 2, 0
    };
    private ArrayList mActionItems;
    private Callback mCallback;
    private final Context mContext;
    private android.view.ContextMenu.ContextMenuInfo mCurrentMenuInfo;
    private int mDefaultShowAsAction;
    private MenuItemImpl mExpandedItem;
    Drawable mHeaderIcon;
    CharSequence mHeaderTitle;
    View mHeaderView;
    private boolean mIsActionItemsStale;
    private boolean mIsClosing;
    private boolean mIsVisibleItemsStale;
    private ArrayList mItems;
    private boolean mItemsChangedWhileDispatchPrevented;
    private ArrayList mNonActionItems;
    private boolean mOptionalIconsVisible;
    private CopyOnWriteArrayList mPresenters;
    private boolean mPreventDispatchingItemsChanged;
    private boolean mQwertyMode;
    private final Resources mResources;
    private boolean mShortcutsVisible;
    private ArrayList mTempShortcutItemList;
    private ArrayList mVisibleItems;

    public MenuBuilder(Context context)
    {
        mDefaultShowAsAction = 0;
        mPreventDispatchingItemsChanged = false;
        mItemsChangedWhileDispatchPrevented = false;
        mOptionalIconsVisible = false;
        mIsClosing = false;
        mTempShortcutItemList = new ArrayList();
        mPresenters = new CopyOnWriteArrayList();
        mContext = context;
        mResources = context.getResources();
        mItems = new ArrayList();
        mVisibleItems = new ArrayList();
        mIsVisibleItemsStale = true;
        mActionItems = new ArrayList();
        mNonActionItems = new ArrayList();
        mIsActionItemsStale = true;
        setShortcutsVisibleInner(true);
    }

    private MenuItem addInternal(int i, int j, int k, CharSequence charsequence)
    {
        int l = getOrdering(k);
        MenuItemImpl menuitemimpl = new MenuItemImpl(this, i, j, k, l, charsequence, mDefaultShowAsAction);
        if (mCurrentMenuInfo != null)
        {
            menuitemimpl.setMenuInfo(mCurrentMenuInfo);
        }
        mItems.add(findInsertIndex(mItems, l), menuitemimpl);
        onItemsChanged(true);
        return menuitemimpl;
    }

    private void dispatchPresenterUpdate(boolean flag)
    {
        if (mPresenters.isEmpty())
        {
            return;
        }
        stopDispatchingItemsChanged();
        for (Iterator iterator = mPresenters.iterator(); iterator.hasNext();)
        {
            WeakReference weakreference = (WeakReference)iterator.next();
            MenuPresenter menupresenter = (MenuPresenter)weakreference.get();
            if (menupresenter == null)
            {
                mPresenters.remove(weakreference);
            } else
            {
                menupresenter.updateMenuView(flag);
            }
        }

        startDispatchingItemsChanged();
    }

    private void dispatchRestoreInstanceState(Bundle bundle)
    {
        SparseArray sparsearray = bundle.getSparseParcelableArray("android:menu:presenters");
        if (sparsearray != null && !mPresenters.isEmpty())
        {
            Iterator iterator = mPresenters.iterator();
            while (iterator.hasNext()) 
            {
                WeakReference weakreference = (WeakReference)iterator.next();
                MenuPresenter menupresenter = (MenuPresenter)weakreference.get();
                if (menupresenter == null)
                {
                    mPresenters.remove(weakreference);
                } else
                {
                    int i = menupresenter.getId();
                    if (i > 0)
                    {
                        Parcelable parcelable = (Parcelable)sparsearray.get(i);
                        if (parcelable != null)
                        {
                            menupresenter.onRestoreInstanceState(parcelable);
                        }
                    }
                }
            }
        }
    }

    private void dispatchSaveInstanceState(Bundle bundle)
    {
        if (mPresenters.isEmpty())
        {
            return;
        }
        SparseArray sparsearray = new SparseArray();
        Iterator iterator = mPresenters.iterator();
        do
        {
            if (!iterator.hasNext())
            {
                break;
            }
            WeakReference weakreference = (WeakReference)iterator.next();
            MenuPresenter menupresenter = (MenuPresenter)weakreference.get();
            if (menupresenter == null)
            {
                mPresenters.remove(weakreference);
            } else
            {
                int i = menupresenter.getId();
                if (i > 0)
                {
                    Parcelable parcelable = menupresenter.onSaveInstanceState();
                    if (parcelable != null)
                    {
                        sparsearray.put(i, parcelable);
                    }
                }
            }
        } while (true);
        bundle.putSparseParcelableArray("android:menu:presenters", sparsearray);
    }

    private boolean dispatchSubMenuSelected(SubMenuBuilder submenubuilder)
    {
        boolean flag;
        if (mPresenters.isEmpty())
        {
            flag = false;
        } else
        {
            flag = false;
            Iterator iterator = mPresenters.iterator();
            while (iterator.hasNext()) 
            {
                WeakReference weakreference = (WeakReference)iterator.next();
                MenuPresenter menupresenter = (MenuPresenter)weakreference.get();
                if (menupresenter == null)
                {
                    mPresenters.remove(weakreference);
                } else
                if (!flag)
                {
                    flag = menupresenter.onSubMenuSelected(submenubuilder);
                }
            }
        }
        return flag;
    }

    private static int findInsertIndex(ArrayList arraylist, int i)
    {
        for (int j = -1 + arraylist.size(); j >= 0; j--)
        {
            if (((MenuItemImpl)arraylist.get(j)).getOrdering() <= i)
            {
                return j + 1;
            }
        }

        return 0;
    }

    private static int getOrdering(int i)
    {
        int j = (0xffff0000 & i) >> 16;
        if (j < 0 || j >= sCategoryToOrder.length)
        {
            throw new IllegalArgumentException("order does not contain a valid category.");
        } else
        {
            return sCategoryToOrder[j] << 16 | 0xffff & i;
        }
    }

    private void removeItemAtInt(int i, boolean flag)
    {
        if (i >= 0 && i < mItems.size())
        {
            mItems.remove(i);
            if (flag)
            {
                onItemsChanged(true);
                return;
            }
        }
    }

    private void setHeaderInternal(int i, CharSequence charsequence, int j, Drawable drawable, View view)
    {
        Resources resources = getResources();
        if (view == null) goto _L2; else goto _L1
_L1:
        mHeaderView = view;
        mHeaderTitle = null;
        mHeaderIcon = null;
_L4:
        onItemsChanged(false);
        return;
_L2:
        if (i > 0)
        {
            mHeaderTitle = resources.getText(i);
        } else
        if (charsequence != null)
        {
            mHeaderTitle = charsequence;
        }
        if (j <= 0)
        {
            break; /* Loop/switch isn't completed */
        }
        mHeaderIcon = resources.getDrawable(j);
_L6:
        mHeaderView = null;
        if (true) goto _L4; else goto _L3
_L3:
        if (drawable == null) goto _L6; else goto _L5
_L5:
        mHeaderIcon = drawable;
          goto _L6
    }

    private void setShortcutsVisibleInner(boolean flag)
    {
        boolean flag1 = true;
        if (!flag || mResources.getConfiguration().keyboard == flag1 || !mResources.getBoolean(android.support.v7.appcompat.R.bool.abc_config_showMenuShortcutsWhenKeyboardPresent))
        {
            flag1 = false;
        }
        mShortcutsVisible = flag1;
    }

    public MenuItem add(int i)
    {
        return addInternal(0, 0, 0, mResources.getString(i));
    }

    public MenuItem add(int i, int j, int k, int l)
    {
        return addInternal(i, j, k, mResources.getString(l));
    }

    public MenuItem add(int i, int j, int k, CharSequence charsequence)
    {
        return addInternal(i, j, k, charsequence);
    }

    public MenuItem add(CharSequence charsequence)
    {
        return addInternal(0, 0, 0, charsequence);
    }

    public int addIntentOptions(int i, int j, int k, ComponentName componentname, Intent aintent[], Intent intent, int l, 
            MenuItem amenuitem[])
    {
        PackageManager packagemanager = mContext.getPackageManager();
        List list = packagemanager.queryIntentActivityOptions(componentname, aintent, intent, 0);
        int i1;
        int j1;
        if (list != null)
        {
            i1 = list.size();
        } else
        {
            i1 = 0;
        }
        if ((l & 1) == 0)
        {
            removeGroup(i);
        }
        j1 = 0;
        while (j1 < i1) 
        {
            ResolveInfo resolveinfo = (ResolveInfo)list.get(j1);
            Intent intent1;
            Intent intent2;
            MenuItem menuitem;
            if (resolveinfo.specificIndex < 0)
            {
                intent1 = intent;
            } else
            {
                intent1 = aintent[resolveinfo.specificIndex];
            }
            intent2 = new Intent(intent1);
            intent2.setComponent(new ComponentName(resolveinfo.activityInfo.applicationInfo.packageName, resolveinfo.activityInfo.name));
            menuitem = add(i, j, k, resolveinfo.loadLabel(packagemanager)).setIcon(resolveinfo.loadIcon(packagemanager)).setIntent(intent2);
            if (amenuitem != null && resolveinfo.specificIndex >= 0)
            {
                amenuitem[resolveinfo.specificIndex] = menuitem;
            }
            j1++;
        }
        return i1;
    }

    public void addMenuPresenter(MenuPresenter menupresenter)
    {
        mPresenters.add(new WeakReference(menupresenter));
        menupresenter.initForMenu(mContext, this);
        mIsActionItemsStale = true;
    }

    public SubMenu addSubMenu(int i)
    {
        return addSubMenu(0, 0, 0, ((CharSequence) (mResources.getString(i))));
    }

    public SubMenu addSubMenu(int i, int j, int k, int l)
    {
        return addSubMenu(i, j, k, ((CharSequence) (mResources.getString(l))));
    }

    public SubMenu addSubMenu(int i, int j, int k, CharSequence charsequence)
    {
        MenuItemImpl menuitemimpl = (MenuItemImpl)addInternal(i, j, k, charsequence);
        SubMenuBuilder submenubuilder = new SubMenuBuilder(mContext, this, menuitemimpl);
        menuitemimpl.setSubMenu(submenubuilder);
        return submenubuilder;
    }

    public SubMenu addSubMenu(CharSequence charsequence)
    {
        return addSubMenu(0, 0, 0, charsequence);
    }

    public void changeMenuMode()
    {
        if (mCallback != null)
        {
            mCallback.onMenuModeChange(this);
        }
    }

    public void clear()
    {
        if (mExpandedItem != null)
        {
            collapseItemActionView(mExpandedItem);
        }
        mItems.clear();
        onItemsChanged(true);
    }

    public void clearAll()
    {
        mPreventDispatchingItemsChanged = true;
        clear();
        clearHeader();
        mPreventDispatchingItemsChanged = false;
        mItemsChangedWhileDispatchPrevented = false;
        onItemsChanged(true);
    }

    public void clearHeader()
    {
        mHeaderIcon = null;
        mHeaderTitle = null;
        mHeaderView = null;
        onItemsChanged(false);
    }

    public void close()
    {
        close(true);
    }

    final void close(boolean flag)
    {
        if (mIsClosing)
        {
            return;
        }
        mIsClosing = true;
        for (Iterator iterator = mPresenters.iterator(); iterator.hasNext();)
        {
            WeakReference weakreference = (WeakReference)iterator.next();
            MenuPresenter menupresenter = (MenuPresenter)weakreference.get();
            if (menupresenter == null)
            {
                mPresenters.remove(weakreference);
            } else
            {
                menupresenter.onCloseMenu(this, flag);
            }
        }

        mIsClosing = false;
    }

    public boolean collapseItemActionView(MenuItemImpl menuitemimpl)
    {
        boolean flag;
        if (mPresenters.isEmpty() || mExpandedItem != menuitemimpl)
        {
            flag = false;
        } else
        {
            flag = false;
            stopDispatchingItemsChanged();
            Iterator iterator = mPresenters.iterator();
label0:
            do
            {
                MenuPresenter menupresenter;
                do
                {
                    if (!iterator.hasNext())
                    {
                        break label0;
                    }
                    WeakReference weakreference = (WeakReference)iterator.next();
                    menupresenter = (MenuPresenter)weakreference.get();
                    if (menupresenter != null)
                    {
                        break;
                    }
                    mPresenters.remove(weakreference);
                } while (true);
                flag = menupresenter.collapseItemActionView(this, menuitemimpl);
            } while (!flag);
            startDispatchingItemsChanged();
            if (flag)
            {
                mExpandedItem = null;
                return flag;
            }
        }
        return flag;
    }

    boolean dispatchMenuItemSelected(MenuBuilder menubuilder, MenuItem menuitem)
    {
        return mCallback != null && mCallback.onMenuItemSelected(menubuilder, menuitem);
    }

    public boolean expandItemActionView(MenuItemImpl menuitemimpl)
    {
        boolean flag;
        if (mPresenters.isEmpty())
        {
            flag = false;
        } else
        {
            flag = false;
            stopDispatchingItemsChanged();
            Iterator iterator = mPresenters.iterator();
label0:
            do
            {
                MenuPresenter menupresenter;
                do
                {
                    if (!iterator.hasNext())
                    {
                        break label0;
                    }
                    WeakReference weakreference = (WeakReference)iterator.next();
                    menupresenter = (MenuPresenter)weakreference.get();
                    if (menupresenter != null)
                    {
                        break;
                    }
                    mPresenters.remove(weakreference);
                } while (true);
                flag = menupresenter.expandItemActionView(this, menuitemimpl);
            } while (!flag);
            startDispatchingItemsChanged();
            if (flag)
            {
                mExpandedItem = menuitemimpl;
                return flag;
            }
        }
        return flag;
    }

    public int findGroupIndex(int i)
    {
        return findGroupIndex(i, 0);
    }

    public int findGroupIndex(int i, int j)
    {
        int k = size();
        if (j < 0)
        {
            j = 0;
        }
        for (int l = j; l < k; l++)
        {
            if (((MenuItemImpl)mItems.get(l)).getGroupId() == i)
            {
                return l;
            }
        }

        return -1;
    }

    public MenuItem findItem(int i)
    {
        int j = size();
        for (int k = 0; k < j; k++)
        {
            MenuItemImpl menuitemimpl = (MenuItemImpl)mItems.get(k);
            if (menuitemimpl.getItemId() == i)
            {
                return menuitemimpl;
            }
            if (!menuitemimpl.hasSubMenu())
            {
                continue;
            }
            MenuItem menuitem = menuitemimpl.getSubMenu().findItem(i);
            if (menuitem != null)
            {
                return menuitem;
            }
        }

        return null;
    }

    public int findItemIndex(int i)
    {
        int j = size();
        for (int k = 0; k < j; k++)
        {
            if (((MenuItemImpl)mItems.get(k)).getItemId() == i)
            {
                return k;
            }
        }

        return -1;
    }

    MenuItemImpl findItemWithShortcutForKey(int i, KeyEvent keyevent)
    {
        ArrayList arraylist = mTempShortcutItemList;
        arraylist.clear();
        findItemsWithShortcutForKey(arraylist, i, keyevent);
        if (!arraylist.isEmpty())
        {
            int j = keyevent.getMetaState();
            android.view.KeyCharacterMap.KeyData keydata = new android.view.KeyCharacterMap.KeyData();
            keyevent.getKeyData(keydata);
            int k = arraylist.size();
            if (k == 1)
            {
                return (MenuItemImpl)arraylist.get(0);
            }
            boolean flag = isQwertyMode();
            int l = 0;
            while (l < k) 
            {
                MenuItemImpl menuitemimpl = (MenuItemImpl)arraylist.get(l);
                char c;
                if (flag)
                {
                    c = menuitemimpl.getAlphabeticShortcut();
                } else
                {
                    c = menuitemimpl.getNumericShortcut();
                }
                if (c == keydata.meta[0] && (j & 2) == 0 || c == keydata.meta[2] && (j & 2) != 0 || flag && c == '\b' && i == 67)
                {
                    return menuitemimpl;
                }
                l++;
            }
        }
        return null;
    }

    void findItemsWithShortcutForKey(List list, int i, KeyEvent keyevent)
    {
        boolean flag = isQwertyMode();
        int j = keyevent.getMetaState();
        android.view.KeyCharacterMap.KeyData keydata = new android.view.KeyCharacterMap.KeyData();
        if (keyevent.getKeyData(keydata) || i == 67)
        {
            int k = mItems.size();
            int l = 0;
            while (l < k) 
            {
                MenuItemImpl menuitemimpl = (MenuItemImpl)mItems.get(l);
                if (menuitemimpl.hasSubMenu())
                {
                    ((MenuBuilder)menuitemimpl.getSubMenu()).findItemsWithShortcutForKey(list, i, keyevent);
                }
                char c;
                if (flag)
                {
                    c = menuitemimpl.getAlphabeticShortcut();
                } else
                {
                    c = menuitemimpl.getNumericShortcut();
                }
                if ((j & 5) == 0 && c != 0 && (c == keydata.meta[0] || c == keydata.meta[2] || flag && c == '\b' && i == 67) && menuitemimpl.isEnabled())
                {
                    list.add(menuitemimpl);
                }
                l++;
            }
        }
    }

    public void flagActionItems()
    {
        if (!mIsActionItemsStale)
        {
            return;
        }
        boolean flag = false;
        for (Iterator iterator = mPresenters.iterator(); iterator.hasNext();)
        {
            WeakReference weakreference = (WeakReference)iterator.next();
            MenuPresenter menupresenter = (MenuPresenter)weakreference.get();
            if (menupresenter == null)
            {
                mPresenters.remove(weakreference);
            } else
            {
                flag |= menupresenter.flagActionItems();
            }
        }

        if (flag)
        {
            mActionItems.clear();
            mNonActionItems.clear();
            ArrayList arraylist = getVisibleItems();
            int i = arraylist.size();
            int j = 0;
            while (j < i) 
            {
                MenuItemImpl menuitemimpl = (MenuItemImpl)arraylist.get(j);
                if (menuitemimpl.isActionButton())
                {
                    mActionItems.add(menuitemimpl);
                } else
                {
                    mNonActionItems.add(menuitemimpl);
                }
                j++;
            }
        } else
        {
            mActionItems.clear();
            mNonActionItems.clear();
            mNonActionItems.addAll(getVisibleItems());
        }
        mIsActionItemsStale = false;
    }

    ArrayList getActionItems()
    {
        flagActionItems();
        return mActionItems;
    }

    protected String getActionViewStatesKey()
    {
        return "android:menu:actionviewstates";
    }

    public Context getContext()
    {
        return mContext;
    }

    public MenuItemImpl getExpandedItem()
    {
        return mExpandedItem;
    }

    public Drawable getHeaderIcon()
    {
        return mHeaderIcon;
    }

    public CharSequence getHeaderTitle()
    {
        return mHeaderTitle;
    }

    public View getHeaderView()
    {
        return mHeaderView;
    }

    public MenuItem getItem(int i)
    {
        return (MenuItem)mItems.get(i);
    }

    ArrayList getNonActionItems()
    {
        flagActionItems();
        return mNonActionItems;
    }

    boolean getOptionalIconsVisible()
    {
        return mOptionalIconsVisible;
    }

    Resources getResources()
    {
        return mResources;
    }

    public MenuBuilder getRootMenu()
    {
        return this;
    }

    ArrayList getVisibleItems()
    {
        if (!mIsVisibleItemsStale)
        {
            return mVisibleItems;
        }
        mVisibleItems.clear();
        int i = mItems.size();
        for (int j = 0; j < i; j++)
        {
            MenuItemImpl menuitemimpl = (MenuItemImpl)mItems.get(j);
            if (menuitemimpl.isVisible())
            {
                mVisibleItems.add(menuitemimpl);
            }
        }

        mIsVisibleItemsStale = false;
        mIsActionItemsStale = true;
        return mVisibleItems;
    }

    public boolean hasVisibleItems()
    {
        int i = size();
        for (int j = 0; j < i; j++)
        {
            if (((MenuItemImpl)mItems.get(j)).isVisible())
            {
                return true;
            }
        }

        return false;
    }

    boolean isQwertyMode()
    {
        return mQwertyMode;
    }

    public boolean isShortcutKey(int i, KeyEvent keyevent)
    {
        return findItemWithShortcutForKey(i, keyevent) != null;
    }

    public boolean isShortcutsVisible()
    {
        return mShortcutsVisible;
    }

    void onItemActionRequestChanged(MenuItemImpl menuitemimpl)
    {
        mIsActionItemsStale = true;
        onItemsChanged(true);
    }

    void onItemVisibleChanged(MenuItemImpl menuitemimpl)
    {
        mIsVisibleItemsStale = true;
        onItemsChanged(true);
    }

    void onItemsChanged(boolean flag)
    {
        if (!mPreventDispatchingItemsChanged)
        {
            if (flag)
            {
                mIsVisibleItemsStale = true;
                mIsActionItemsStale = true;
            }
            dispatchPresenterUpdate(flag);
            return;
        } else
        {
            mItemsChangedWhileDispatchPrevented = true;
            return;
        }
    }

    public boolean performIdentifierAction(int i, int j)
    {
        return performItemAction(findItem(i), j);
    }

    public boolean performItemAction(MenuItem menuitem, int i)
    {
        MenuItemImpl menuitemimpl = (MenuItemImpl)menuitem;
        if (menuitemimpl != null && menuitemimpl.isEnabled()) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        ActionProvider actionprovider;
        boolean flag1;
        flag = menuitemimpl.invoke();
        actionprovider = menuitemimpl.getSupportActionProvider();
        if (actionprovider != null && actionprovider.hasSubMenu())
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        if (!menuitemimpl.hasCollapsibleActionView())
        {
            break; /* Loop/switch isn't completed */
        }
        flag |= menuitemimpl.expandActionView();
        if (flag)
        {
            close(true);
            return flag;
        }
        if (true) goto _L4; else goto _L3
_L3:
        if (!menuitemimpl.hasSubMenu() && !flag1)
        {
            continue; /* Loop/switch isn't completed */
        }
        close(false);
        if (!menuitemimpl.hasSubMenu())
        {
            menuitemimpl.setSubMenu(new SubMenuBuilder(getContext(), this, menuitemimpl));
        }
        SubMenuBuilder submenubuilder = (SubMenuBuilder)menuitemimpl.getSubMenu();
        if (flag1)
        {
            actionprovider.onPrepareSubMenu(submenubuilder);
        }
        flag |= dispatchSubMenuSelected(submenubuilder);
        if (flag) goto _L4; else goto _L5
_L5:
        close(true);
        return flag;
        if ((i & 1) != 0) goto _L4; else goto _L6
_L6:
        close(true);
        return flag;
    }

    public boolean performShortcut(int i, KeyEvent keyevent, int j)
    {
        MenuItemImpl menuitemimpl = findItemWithShortcutForKey(i, keyevent);
        boolean flag = false;
        if (menuitemimpl != null)
        {
            flag = performItemAction(menuitemimpl, j);
        }
        if ((j & 2) != 0)
        {
            close(true);
        }
        return flag;
    }

    public void removeGroup(int i)
    {
        int j = findGroupIndex(i);
        if (j >= 0)
        {
            int k = mItems.size() - j;
            int l = 0;
            do
            {
                int i1 = l + 1;
                if (l >= k || ((MenuItemImpl)mItems.get(j)).getGroupId() != i)
                {
                    break;
                }
                removeItemAtInt(j, false);
                l = i1;
            } while (true);
            onItemsChanged(true);
        }
    }

    public void removeItem(int i)
    {
        removeItemAtInt(findItemIndex(i), true);
    }

    public void removeItemAt(int i)
    {
        removeItemAtInt(i, true);
    }

    public void removeMenuPresenter(MenuPresenter menupresenter)
    {
        Iterator iterator = mPresenters.iterator();
        do
        {
            if (!iterator.hasNext())
            {
                break;
            }
            WeakReference weakreference = (WeakReference)iterator.next();
            MenuPresenter menupresenter1 = (MenuPresenter)weakreference.get();
            if (menupresenter1 == null || menupresenter1 == menupresenter)
            {
                mPresenters.remove(weakreference);
            }
        } while (true);
    }

    public void restoreActionViewStates(Bundle bundle)
    {
        if (bundle != null)
        {
            SparseArray sparsearray = bundle.getSparseParcelableArray(getActionViewStatesKey());
            int i = size();
            for (int j = 0; j < i; j++)
            {
                MenuItem menuitem1 = getItem(j);
                View view = MenuItemCompat.getActionView(menuitem1);
                if (view != null && view.getId() != -1)
                {
                    view.restoreHierarchyState(sparsearray);
                }
                if (menuitem1.hasSubMenu())
                {
                    ((SubMenuBuilder)menuitem1.getSubMenu()).restoreActionViewStates(bundle);
                }
            }

            int k = bundle.getInt("android:menu:expandedactionview");
            if (k > 0)
            {
                MenuItem menuitem = findItem(k);
                if (menuitem != null)
                {
                    MenuItemCompat.expandActionView(menuitem);
                    return;
                }
            }
        }
    }

    public void restorePresenterStates(Bundle bundle)
    {
        dispatchRestoreInstanceState(bundle);
    }

    public void saveActionViewStates(Bundle bundle)
    {
        SparseArray sparsearray = null;
        int i = size();
        for (int j = 0; j < i; j++)
        {
            MenuItem menuitem = getItem(j);
            View view = MenuItemCompat.getActionView(menuitem);
            if (view != null && view.getId() != -1)
            {
                if (sparsearray == null)
                {
                    sparsearray = new SparseArray();
                }
                view.saveHierarchyState(sparsearray);
                if (MenuItemCompat.isActionViewExpanded(menuitem))
                {
                    bundle.putInt("android:menu:expandedactionview", menuitem.getItemId());
                }
            }
            if (menuitem.hasSubMenu())
            {
                ((SubMenuBuilder)menuitem.getSubMenu()).saveActionViewStates(bundle);
            }
        }

        if (sparsearray != null)
        {
            bundle.putSparseParcelableArray(getActionViewStatesKey(), sparsearray);
        }
    }

    public void savePresenterStates(Bundle bundle)
    {
        dispatchSaveInstanceState(bundle);
    }

    public void setCallback(Callback callback)
    {
        mCallback = callback;
    }

    public void setCurrentMenuInfo(android.view.ContextMenu.ContextMenuInfo contextmenuinfo)
    {
        mCurrentMenuInfo = contextmenuinfo;
    }

    public MenuBuilder setDefaultShowAsAction(int i)
    {
        mDefaultShowAsAction = i;
        return this;
    }

    void setExclusiveItemChecked(MenuItem menuitem)
    {
        int i = menuitem.getGroupId();
        int j = mItems.size();
        int k = 0;
        do
        {
            if (k >= j)
            {
                break;
            }
            MenuItemImpl menuitemimpl = (MenuItemImpl)mItems.get(k);
            if (menuitemimpl.getGroupId() == i && menuitemimpl.isExclusiveCheckable() && menuitemimpl.isCheckable())
            {
                boolean flag;
                if (menuitemimpl == menuitem)
                {
                    flag = true;
                } else
                {
                    flag = false;
                }
                menuitemimpl.setCheckedInt(flag);
            }
            k++;
        } while (true);
    }

    public void setGroupCheckable(int i, boolean flag, boolean flag1)
    {
        int j = mItems.size();
        for (int k = 0; k < j; k++)
        {
            MenuItemImpl menuitemimpl = (MenuItemImpl)mItems.get(k);
            if (menuitemimpl.getGroupId() == i)
            {
                menuitemimpl.setExclusiveCheckable(flag1);
                menuitemimpl.setCheckable(flag);
            }
        }

    }

    public void setGroupEnabled(int i, boolean flag)
    {
        int j = mItems.size();
        for (int k = 0; k < j; k++)
        {
            MenuItemImpl menuitemimpl = (MenuItemImpl)mItems.get(k);
            if (menuitemimpl.getGroupId() == i)
            {
                menuitemimpl.setEnabled(flag);
            }
        }

    }

    public void setGroupVisible(int i, boolean flag)
    {
        int j = mItems.size();
        boolean flag1 = false;
        for (int k = 0; k < j; k++)
        {
            MenuItemImpl menuitemimpl = (MenuItemImpl)mItems.get(k);
            if (menuitemimpl.getGroupId() == i && menuitemimpl.setVisibleInt(flag))
            {
                flag1 = true;
            }
        }

        if (flag1)
        {
            onItemsChanged(true);
        }
    }

    protected MenuBuilder setHeaderIconInt(int i)
    {
        setHeaderInternal(0, null, i, null, null);
        return this;
    }

    protected MenuBuilder setHeaderIconInt(Drawable drawable)
    {
        setHeaderInternal(0, null, 0, drawable, null);
        return this;
    }

    protected MenuBuilder setHeaderTitleInt(int i)
    {
        setHeaderInternal(i, null, 0, null, null);
        return this;
    }

    protected MenuBuilder setHeaderTitleInt(CharSequence charsequence)
    {
        setHeaderInternal(0, charsequence, 0, null, null);
        return this;
    }

    protected MenuBuilder setHeaderViewInt(View view)
    {
        setHeaderInternal(0, null, 0, null, view);
        return this;
    }

    void setOptionalIconsVisible(boolean flag)
    {
        mOptionalIconsVisible = flag;
    }

    public void setQwertyMode(boolean flag)
    {
        mQwertyMode = flag;
        onItemsChanged(false);
    }

    public void setShortcutsVisible(boolean flag)
    {
        if (mShortcutsVisible == flag)
        {
            return;
        } else
        {
            setShortcutsVisibleInner(flag);
            onItemsChanged(false);
            return;
        }
    }

    public int size()
    {
        return mItems.size();
    }

    public void startDispatchingItemsChanged()
    {
        mPreventDispatchingItemsChanged = false;
        if (mItemsChangedWhileDispatchPrevented)
        {
            mItemsChangedWhileDispatchPrevented = false;
            onItemsChanged(true);
        }
    }

    public void stopDispatchingItemsChanged()
    {
        if (!mPreventDispatchingItemsChanged)
        {
            mPreventDispatchingItemsChanged = true;
            mItemsChangedWhileDispatchPrevented = false;
        }
    }

}
