// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

// Referenced classes of package android.support.v7.internal.view.menu:
//            MenuPopupHelper, MenuBuilder, MenuItemImpl, ListMenuItemView

private class findExpandedIndex extends BaseAdapter
{

    private MenuBuilder mAdapterMenu;
    private int mExpandedIndex;
    final MenuPopupHelper this$0;

    void findExpandedIndex()
    {
        MenuItemImpl menuitemimpl = MenuPopupHelper.access$300(MenuPopupHelper.this).getExpandedItem();
        if (menuitemimpl != null)
        {
            ArrayList arraylist = MenuPopupHelper.access$300(MenuPopupHelper.this).getNonActionItems();
            int i = arraylist.size();
            for (int j = 0; j < i; j++)
            {
                if ((MenuItemImpl)arraylist.get(j) == menuitemimpl)
                {
                    mExpandedIndex = j;
                    return;
                }
            }

        }
        mExpandedIndex = -1;
    }

    public int getCount()
    {
        ArrayList arraylist;
        if (MenuPopupHelper.access$100(MenuPopupHelper.this))
        {
            arraylist = mAdapterMenu.getNonActionItems();
        } else
        {
            arraylist = mAdapterMenu.getVisibleItems();
        }
        if (mExpandedIndex < 0)
        {
            return arraylist.size();
        } else
        {
            return -1 + arraylist.size();
        }
    }

    public MenuItemImpl getItem(int i)
    {
        ArrayList arraylist;
        if (MenuPopupHelper.access$100(MenuPopupHelper.this))
        {
            arraylist = mAdapterMenu.getNonActionItems();
        } else
        {
            arraylist = mAdapterMenu.getVisibleItems();
        }
        if (mExpandedIndex >= 0 && i >= mExpandedIndex)
        {
            i++;
        }
        return (MenuItemImpl)arraylist.get(i);
    }

    public volatile Object getItem(int i)
    {
        return getItem(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        if (view == null)
        {
            view = MenuPopupHelper.access$200(MenuPopupHelper.this).inflate(MenuPopupHelper.ITEM_LAYOUT, viewgroup, false);
        }
        getItem getitem = (this._cls0)view;
        if (mForceShowIcon)
        {
            ((ListMenuItemView)view).setForceShowIcon(true);
        }
        getitem.(getItem(i), 0);
        return view;
    }

    public void notifyDataSetChanged()
    {
        findExpandedIndex();
        super.notifyDataSetChanged();
    }


    public (MenuBuilder menubuilder)
    {
        this$0 = MenuPopupHelper.this;
        super();
        mExpandedIndex = -1;
        mAdapterMenu = menubuilder;
        findExpandedIndex();
    }
}
