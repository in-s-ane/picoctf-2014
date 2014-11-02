// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.widget;

import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.xmlpull.v1.XmlSerializer;

// Referenced classes of package android.support.v7.internal.widget:
//            ActivityChooserModel

private final class <init> extends AsyncTask
{

    final ActivityChooserModel this$0;

    public volatile Object doInBackground(Object aobj[])
    {
        return doInBackground(aobj);
    }

    public transient Void doInBackground(Object aobj[])
    {
        FileOutputStream fileoutputstream;
        XmlSerializer xmlserializer;
        List list = (List)aobj[0];
        String s = (String)aobj[1];
        int i;
        int j;
        doInBackground doinbackground;
        try
        {
            fileoutputstream = ActivityChooserModel.access$200(ActivityChooserModel.this).openFileOutput(s, 0);
        }
        catch (FileNotFoundException filenotfoundexception)
        {
            Log.e(ActivityChooserModel.access$300(), (new StringBuilder()).append("Error writing historical recrod file: ").append(s).toString(), filenotfoundexception);
            return null;
        }
        xmlserializer = Xml.newSerializer();
        xmlserializer.setOutput(fileoutputstream, null);
        xmlserializer.startDocument("UTF-8", Boolean.valueOf(true));
        xmlserializer.startTag(null, "historical-records");
        i = list.size();
        j = 0;
_L2:
        if (j >= i)
        {
            break; /* Loop/switch isn't completed */
        }
        doinbackground = (this._cls0)list.remove(0);
        xmlserializer.startTag(null, "historical-record");
        xmlserializer.attribute(null, "activity", doinbackground.y.flattenToString());
        xmlserializer.attribute(null, "time", String.valueOf(doinbackground.y));
        xmlserializer.attribute(null, "weight", String.valueOf(doinbackground.y));
        xmlserializer.endTag(null, "historical-record");
        j++;
        if (true) goto _L2; else goto _L1
_L1:
        xmlserializer.endTag(null, "historical-records");
        xmlserializer.endDocument();
        ActivityChooserModel.access$502(ActivityChooserModel.this, true);
        Exception exception;
        IOException ioexception1;
        IOException ioexception2;
        IllegalStateException illegalstateexception;
        IOException ioexception3;
        IllegalArgumentException illegalargumentexception;
        IOException ioexception4;
        if (fileoutputstream != null)
        {
            try
            {
                fileoutputstream.close();
            }
            catch (IOException ioexception5) { }
        }
        return null;
        illegalargumentexception;
        Log.e(ActivityChooserModel.access$300(), (new StringBuilder()).append("Error writing historical recrod file: ").append(ActivityChooserModel.access$400(ActivityChooserModel.this)).toString(), illegalargumentexception);
        ActivityChooserModel.access$502(ActivityChooserModel.this, true);
        if (fileoutputstream != null)
        {
            try
            {
                fileoutputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch (IOException ioexception4) { }
        }
        continue; /* Loop/switch isn't completed */
        illegalstateexception;
        Log.e(ActivityChooserModel.access$300(), (new StringBuilder()).append("Error writing historical recrod file: ").append(ActivityChooserModel.access$400(ActivityChooserModel.this)).toString(), illegalstateexception);
        ActivityChooserModel.access$502(ActivityChooserModel.this, true);
        if (fileoutputstream != null)
        {
            try
            {
                fileoutputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch (IOException ioexception3) { }
        }
        continue; /* Loop/switch isn't completed */
        ioexception1;
        Log.e(ActivityChooserModel.access$300(), (new StringBuilder()).append("Error writing historical recrod file: ").append(ActivityChooserModel.access$400(ActivityChooserModel.this)).toString(), ioexception1);
        ActivityChooserModel.access$502(ActivityChooserModel.this, true);
        if (fileoutputstream != null)
        {
            try
            {
                fileoutputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch (IOException ioexception2) { }
        }
        if (true) goto _L4; else goto _L3
_L3:
        break MISSING_BLOCK_LABEL_441;
_L4:
        break MISSING_BLOCK_LABEL_251;
        exception;
        ActivityChooserModel.access$502(ActivityChooserModel.this, true);
        if (fileoutputstream != null)
        {
            try
            {
                fileoutputstream.close();
            }
            catch (IOException ioexception) { }
        }
        throw exception;
    }

    private ()
    {
        this$0 = ActivityChooserModel.this;
        super();
    }

    this._cls0(this._cls0 _pcls0)
    {
        this();
    }
}
