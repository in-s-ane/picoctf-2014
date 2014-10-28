// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.view.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ListAdapter;

// Referenced classes of package android.support.v7.internal.view.menu:
//            ListMenuPresenter, MenuItemImpl, MenuBuilder

public class MenuDialogHelper
    implements android.content.DialogInterface.OnKeyListener, android.content.DialogInterface.OnClickListener, android.content.DialogInterface.OnDismissListener, MenuPresenter.Callback
{

    private AlertDialog mDialog;
    private MenuBuilder mMenu;
    ListMenuPresenter mPresenter;
    private MenuPresenter.Callback mPresenterCallback;

    public MenuDialogHelper(MenuBuilder menubuilder)
    {
        mMenu = menubuilder;
    }

    public void dismiss()
    {
        if (mDialog != null)
        {
            mDialog.dismiss();
        }
    }

    public void onClick(DialogInterface dialoginterface, int i)
    {
        mMenu.performItemAction((MenuItemImpl)mPresenter.getAdapter().getItem(i), 0);
    }

    public void onCloseMenu(MenuBuilder menubuilder, boolean flag)
    {
        if (flag || menubuilder == mMenu)
        {
            dismiss();
        }
        if (mPresenterCallback != null)
        {
            mPresenterCallback.onCloseMenu(menubuilder, flag);
        }
    }

    public void onDismiss(DialogInterface dialoginterface)
    {
        mPresenter.onCloseMenu(mMenu, true);
    }

    public boolean onKey(DialogInterface dialoginterface, int i, KeyEvent keyevent)
    {
        if (i == 82 || i == 4)
        {
            if (keyevent.getAction() == 0 && keyevent.getRepeatCount() == 0)
            {
                Window window1 = mDialog.getWindow();
                if (window1 != null)
                {
                    View view1 = window1.getDecorView();
                    if (view1 != null)
                    {
                        android.view.KeyEvent.DispatcherState dispatcherstate1 = view1.getKeyDispatcherState();
                        if (dispatcherstate1 != null)
                        {
                            dispatcherstate1.startTracking(keyevent, this);
                            return true;
                        }
                    }
                }
            } else
            if (keyevent.getAction() == 1 && !keyevent.isCanceled())
            {
                Window window = mDialog.getWindow();
                if (window != null)
                {
                    View view = window.getDecorView();
                    if (view != null)
                    {
                        android.view.KeyEvent.DispatcherState dispatcherstate = view.getKeyDispatcherState();
                        if (dispatcherstate != null && dispatcherstate.isTracking(keyevent))
                        {
                            mMenu.close(true);
                            dialoginterface.dismiss();
                            return true;
                        }
                    }
                }
            }
        }
        return mMenu.performShortcut(i, keyevent, 0);
    }

    public boolean onOpenSubMenu(MenuBuilder menubuilder)
    {
        if (mPresenterCallback != null)
        {
            return mPresenterCallback.onOpenSubMenu(menubuilder);
        } else
        {
            return false;
        }
    }

    public void setPresenterCallback(MenuPresenter.Callback callback)
    {
        mPresenterCallback = callback;
    }

    public void show(IBinder ibinder)
    {
        MenuBuilder menubuilder = mMenu;
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(menubuilder.getContext());
        mPresenter = new ListMenuPresenter(android.support.v7.appcompat.R.layout.abc_list_menu_item_layout, android.support.v7.appcompat.R.style.Theme_AppCompat_CompactMenu_Dialog);
        mPresenter.setCallback(this);
        mMenu.addMenuPresenter(mPresenter);
        builder.setAdapter(mPresenter.getAdapter(), this);
        View view = menubuilder.getHeaderView();
        android.view.WindowManager.LayoutParams layoutparams;
        if (view != null)
        {
            builder.setCustomTitle(view);
        } else
        {
            builder.setIcon(menubuilder.getHeaderIcon()).setTitle(menubuilder.getHeaderTitle());
        }
        builder.setOnKeyListener(this);
        mDialog = builder.create();
        mDialog.setOnDismissListener(this);
        layoutparams = mDialog.getWindow().getAttributes();
        layoutparams.type = 1003;
        if (ibinder != null)
        {
            layoutparams.token = ibinder;
        }
        layoutparams.flags = 0x20000 | layoutparams.flags;
        mDialog.show();
    }
}
