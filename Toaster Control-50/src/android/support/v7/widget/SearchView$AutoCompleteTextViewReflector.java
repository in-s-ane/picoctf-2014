// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.widget;

import android.os.ResultReceiver;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import java.lang.reflect.Method;

// Referenced classes of package android.support.v7.widget:
//            SearchView

private static class showSoftInputUnchecked
{

    private Method doAfterTextChanged;
    private Method doBeforeTextChanged;
    private Method ensureImeVisible;
    private Method showSoftInputUnchecked;

    void doAfterTextChanged(AutoCompleteTextView autocompletetextview)
    {
        if (doAfterTextChanged == null)
        {
            break MISSING_BLOCK_LABEL_20;
        }
        doAfterTextChanged.invoke(autocompletetextview, new Object[0]);
        return;
        Exception exception;
        exception;
    }

    void doBeforeTextChanged(AutoCompleteTextView autocompletetextview)
    {
        if (doBeforeTextChanged == null)
        {
            break MISSING_BLOCK_LABEL_20;
        }
        doBeforeTextChanged.invoke(autocompletetextview, new Object[0]);
        return;
        Exception exception;
        exception;
    }

    void ensureImeVisible(AutoCompleteTextView autocompletetextview, boolean flag)
    {
        if (ensureImeVisible == null)
        {
            break MISSING_BLOCK_LABEL_36;
        }
        Method method = ensureImeVisible;
        Object aobj[] = new Object[1];
        aobj[0] = Boolean.valueOf(flag);
        method.invoke(autocompletetextview, aobj);
        return;
        Exception exception;
        exception;
    }

    void showSoftInputUnchecked(InputMethodManager inputmethodmanager, View view, int i)
    {
        if (showSoftInputUnchecked != null)
        {
            try
            {
                Method method = showSoftInputUnchecked;
                Object aobj[] = new Object[2];
                aobj[0] = Integer.valueOf(i);
                aobj[1] = null;
                method.invoke(inputmethodmanager, aobj);
                return;
            }
            catch (Exception exception) { }
        }
        inputmethodmanager.showSoftInput(view, i);
    }

    ()
    {
        try
        {
            doBeforeTextChanged = android/widget/AutoCompleteTextView.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
            doBeforeTextChanged.setAccessible(true);
        }
        catch (NoSuchMethodException nosuchmethodexception) { }
        try
        {
            doAfterTextChanged = android/widget/AutoCompleteTextView.getDeclaredMethod("doAfterTextChanged", new Class[0]);
            doAfterTextChanged.setAccessible(true);
        }
        catch (NoSuchMethodException nosuchmethodexception1) { }
        try
        {
            Class aclass1[] = new Class[1];
            aclass1[0] = Boolean.TYPE;
            ensureImeVisible = android/widget/AutoCompleteTextView.getMethod("ensureImeVisible", aclass1);
            ensureImeVisible.setAccessible(true);
        }
        catch (NoSuchMethodException nosuchmethodexception2) { }
        try
        {
            Class aclass[] = new Class[2];
            aclass[0] = Integer.TYPE;
            aclass[1] = android/os/ResultReceiver;
            showSoftInputUnchecked = android/view/inputmethod/InputMethodManager.getMethod("showSoftInputUnchecked", aclass);
            showSoftInputUnchecked.setAccessible(true);
            return;
        }
        catch (NoSuchMethodException nosuchmethodexception3)
        {
            return;
        }
    }
}
