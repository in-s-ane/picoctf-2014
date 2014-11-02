// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.view.MenuItem;

// Referenced classes of package android.support.v4.app:
//            ShareCompat

static class 
    implements 
{

    private static void withinStyle(StringBuilder stringbuilder, CharSequence charsequence, int i, int j)
    {
        int k = i;
        while (k < j) 
        {
            char c = charsequence.charAt(k);
            if (c == '<')
            {
                stringbuilder.append("&lt;");
            } else
            if (c == '>')
            {
                stringbuilder.append("&gt;");
            } else
            if (c == '&')
            {
                stringbuilder.append("&amp;");
            } else
            if (c > '~' || c < ' ')
            {
                stringbuilder.append((new StringBuilder()).append("&#").append(c).append(";").toString());
            } else
            if (c == ' ')
            {
                for (; k + 1 < j && charsequence.charAt(k + 1) == ' '; k++)
                {
                    stringbuilder.append("&nbsp;");
                }

                stringbuilder.append(' ');
            } else
            {
                stringbuilder.append(c);
            }
            k++;
        }
    }

    public void configureMenuItem(MenuItem menuitem,  )
    {
        menuitem.setIntent(.ChooserIntent());
    }

    public String escapeHtml(CharSequence charsequence)
    {
        StringBuilder stringbuilder = new StringBuilder();
        withinStyle(stringbuilder, charsequence, 0, charsequence.length());
        return stringbuilder.toString();
    }

    ()
    {
    }
}
