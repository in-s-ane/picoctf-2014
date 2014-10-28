// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package picoapp453.picoctf.com.picoapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ToasterActivity extends ActionBarActivity
{

    String mystery;

    public ToasterActivity()
    {
        mystery = new String(new char[] {
            'f', 'l', 'a', 'g', ' ', 'i', 's', ':', ' ', 'w', 
            'h', 'a', 't', '_', 'd', 'o', 'e', 's', '_', 't', 
            'h', 'e', '_', 'l', 'o', 'g', 'c', 'a', 't', '_', 
            's', 'a', 'y'
        });
    }

    public void displayMessage(View view)
    {
        Toast.makeText(getApplicationContext(), "Toasters don't toast toast, toast toast toast!", 1).show();
        Log.d("Debug tag", mystery);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030018);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f0c0000, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        if (menuitem.getItemId() == 0x7f07003d)
        {
            return true;
        } else
        {
            return super.onOptionsItemSelected(menuitem);
        }
    }
}
