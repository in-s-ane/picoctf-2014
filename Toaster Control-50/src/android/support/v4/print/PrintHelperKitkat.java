// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.print;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class PrintHelperKitkat
{

    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    private static final String LOG_TAG = "PrintHelperKitkat";
    private static final int MAX_PRINT_SIZE = 3500;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    int mColorMode;
    final Context mContext;
    android.graphics.BitmapFactory.Options mDecodeOptions;
    private final Object mLock = new Object();
    int mOrientation;
    int mScaleMode;

    PrintHelperKitkat(Context context)
    {
        mDecodeOptions = null;
        mScaleMode = 2;
        mColorMode = 2;
        mOrientation = 1;
        mContext = context;
    }

    private Matrix getMatrix(int i, int j, RectF rectf, int k)
    {
        Matrix matrix = new Matrix();
        float f = rectf.width() / (float)i;
        float f1;
        if (k == 2)
        {
            f1 = Math.max(f, rectf.height() / (float)j);
        } else
        {
            f1 = Math.min(f, rectf.height() / (float)j);
        }
        matrix.postScale(f1, f1);
        matrix.postTranslate((rectf.width() - f1 * (float)i) / 2.0F, (rectf.height() - f1 * (float)j) / 2.0F);
        return matrix;
    }

    private Bitmap loadBitmap(Uri uri, android.graphics.BitmapFactory.Options options)
        throws FileNotFoundException
    {
        InputStream inputstream;
        if (uri == null || mContext == null)
        {
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }
        inputstream = null;
        Bitmap bitmap;
        inputstream = mContext.getContentResolver().openInputStream(uri);
        bitmap = BitmapFactory.decodeStream(inputstream, null, options);
        if (inputstream != null)
        {
            try
            {
                inputstream.close();
            }
            catch (IOException ioexception1)
            {
                Log.w("PrintHelperKitkat", "close fail ", ioexception1);
                return bitmap;
            }
        }
        return bitmap;
        Exception exception;
        exception;
        if (inputstream != null)
        {
            try
            {
                inputstream.close();
            }
            catch (IOException ioexception)
            {
                Log.w("PrintHelperKitkat", "close fail ", ioexception);
            }
        }
        throw exception;
    }

    private Bitmap loadConstrainedBitmap(Uri uri, int i)
        throws FileNotFoundException
    {
        int j;
        int k;
        if (i <= 0 || uri == null || mContext == null)
        {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
        android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        loadBitmap(uri, options);
        j = options.outWidth;
        k = options.outHeight;
        if (j > 0 && k > 0) goto _L2; else goto _L1
_L1:
        return null;
_L2:
        int i1;
        int l = Math.max(j, k);
        for (i1 = 1; l > i; i1 <<= 1)
        {
            l >>>= 1;
        }

        if (i1 <= 0 || Math.min(j, k) / i1 <= 0) goto _L1; else goto _L3
_L3:
        android.graphics.BitmapFactory.Options options1;
        synchronized (mLock)
        {
            mDecodeOptions = new android.graphics.BitmapFactory.Options();
            mDecodeOptions.inMutable = true;
            mDecodeOptions.inSampleSize = i1;
            options1 = mDecodeOptions;
        }
        Bitmap bitmap = loadBitmap(uri, options1);
        synchronized (mLock)
        {
            mDecodeOptions = null;
        }
        return bitmap;
        exception3;
        obj2;
        JVM INSTR monitorexit ;
        throw exception3;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        Exception exception1;
        exception1;
        synchronized (mLock)
        {
            mDecodeOptions = null;
        }
        throw exception1;
        exception2;
        obj1;
        JVM INSTR monitorexit ;
        throw exception2;
    }

    public int getColorMode()
    {
        return mColorMode;
    }

    public int getOrientation()
    {
        return mOrientation;
    }

    public int getScaleMode()
    {
        return mScaleMode;
    }

    public void printBitmap(final String jobName, final Bitmap bitmap)
    {
        if (bitmap == null)
        {
            return;
        }
        final int fittingMode = mScaleMode;
        PrintManager printmanager = (PrintManager)mContext.getSystemService("print");
        android.print.PrintAttributes.MediaSize mediasize = android.print.PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
        if (bitmap.getWidth() > bitmap.getHeight())
        {
            mediasize = android.print.PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
        }
        PrintAttributes printattributes = (new android.print.PrintAttributes.Builder()).setMediaSize(mediasize).setColorMode(mColorMode).build();
        printmanager.print(jobName, new PrintDocumentAdapter() {

            private PrintAttributes mAttributes;
            final PrintHelperKitkat this$0;
            final Bitmap val$bitmap;
            final int val$fittingMode;
            final String val$jobName;

            public void onLayout(PrintAttributes printattributes1, PrintAttributes printattributes2, CancellationSignal cancellationsignal, android.print.PrintDocumentAdapter.LayoutResultCallback layoutresultcallback, Bundle bundle)
            {
                boolean flag = true;
                mAttributes = printattributes2;
                android.print.PrintDocumentInfo printdocumentinfo = (new android.print.PrintDocumentInfo.Builder(jobName)).setContentType(flag).setPageCount(flag).build();
                if (printattributes2.equals(printattributes1))
                {
                    flag = false;
                }
                layoutresultcallback.onLayoutFinished(printdocumentinfo, flag);
            }

            public void onWrite(PageRange apagerange[], ParcelFileDescriptor parcelfiledescriptor, CancellationSignal cancellationsignal, android.print.PrintDocumentAdapter.WriteResultCallback writeresultcallback)
            {
                PrintedPdfDocument printedpdfdocument = new PrintedPdfDocument(mContext, mAttributes);
                android.graphics.pdf.PdfDocument.Page page = printedpdfdocument.startPage(1);
                RectF rectf = new RectF(page.getInfo().getContentRect());
                Matrix matrix = getMatrix(bitmap.getWidth(), bitmap.getHeight(), rectf, fittingMode);
                page.getCanvas().drawBitmap(bitmap, matrix, null);
                printedpdfdocument.finishPage(page);
                printedpdfdocument.writeTo(new FileOutputStream(parcelfiledescriptor.getFileDescriptor()));
                PageRange apagerange1[] = new PageRange[1];
                apagerange1[0] = PageRange.ALL_PAGES;
                writeresultcallback.onWriteFinished(apagerange1);
_L2:
                if (printedpdfdocument != null)
                {
                    printedpdfdocument.close();
                }
                if (parcelfiledescriptor == null)
                {
                    break MISSING_BLOCK_LABEL_150;
                }
                parcelfiledescriptor.close();
                return;
                IOException ioexception1;
                ioexception1;
                Log.e("PrintHelperKitkat", "Error writing printed content", ioexception1);
                writeresultcallback.onWriteFailed(null);
                if (true) goto _L2; else goto _L1
_L1:
                Exception exception;
                exception;
                if (printedpdfdocument != null)
                {
                    printedpdfdocument.close();
                }
                IOException ioexception2;
                if (parcelfiledescriptor != null)
                {
                    try
                    {
                        parcelfiledescriptor.close();
                    }
                    catch (IOException ioexception) { }
                }
                throw exception;
                ioexception2;
            }

            
            {
                this$0 = PrintHelperKitkat.this;
                jobName = s;
                bitmap = bitmap1;
                fittingMode = i;
                super();
            }
        }, printattributes);
    }

    public void printBitmap(final String jobName, final Uri imageFile)
        throws FileNotFoundException
    {
        PrintDocumentAdapter printdocumentadapter;
        PrintManager printmanager;
        android.print.PrintAttributes.Builder builder;
        printdocumentadapter = new PrintDocumentAdapter() {

            AsyncTask loadBitmap;
            private PrintAttributes mAttributes;
            Bitmap mBitmap;
            final PrintHelperKitkat this$0;
            final int val$fittingMode;
            final Uri val$imageFile;
            final String val$jobName;

            private void cancelLoad()
            {
                synchronized (mLock)
                {
                    if (mDecodeOptions != null)
                    {
                        mDecodeOptions.requestCancelDecode();
                        mDecodeOptions = null;
                    }
                }
                return;
                exception;
                obj;
                JVM INSTR monitorexit ;
                throw exception;
            }

            public void onFinish()
            {
                super.onFinish();
                cancelLoad();
                loadBitmap.cancel(true);
            }

            public void onLayout(final PrintAttributes oldPrintAttributes, final PrintAttributes newPrintAttributes, final CancellationSignal cancellationSignal, android.print.PrintDocumentAdapter.LayoutResultCallback layoutresultcallback, Bundle bundle)
            {
                boolean flag = true;
                if (cancellationSignal.isCanceled())
                {
                    layoutresultcallback.onLayoutCancelled();
                    mAttributes = newPrintAttributes;
                    return;
                }
                if (mBitmap != null)
                {
                    android.print.PrintDocumentInfo printdocumentinfo = (new android.print.PrintDocumentInfo.Builder(jobName)).setContentType(flag).setPageCount(flag).build();
                    if (newPrintAttributes.equals(oldPrintAttributes))
                    {
                        flag = false;
                    }
                    layoutresultcallback.onLayoutFinished(printdocumentinfo, flag);
                    return;
                } else
                {
                    loadBitmap = layoutresultcallback. new AsyncTask() {

                        final _cls2 this$1;
                        final CancellationSignal val$cancellationSignal;
                        final android.print.PrintDocumentAdapter.LayoutResultCallback val$layoutResultCallback;
                        final PrintAttributes val$newPrintAttributes;
                        final PrintAttributes val$oldPrintAttributes;

                        protected transient Bitmap doInBackground(Uri auri[])
                        {
                            Bitmap bitmap;
                            try
                            {
                                bitmap = loadConstrainedBitmap(imageFile, 3500);
                            }
                            catch (FileNotFoundException filenotfoundexception)
                            {
                                return null;
                            }
                            return bitmap;
                        }

                        protected volatile Object doInBackground(Object aobj[])
                        {
                            return doInBackground((Uri[])aobj);
                        }

                        protected void onCancelled(Bitmap bitmap)
                        {
                            layoutResultCallback.onLayoutCancelled();
                        }

                        protected volatile void onCancelled(Object obj)
                        {
                            onCancelled((Bitmap)obj);
                        }

                        protected void onPostExecute(Bitmap bitmap)
                        {
                            boolean flag = true;
                            super.onPostExecute(bitmap);
                            mBitmap = bitmap;
                            if (bitmap != null)
                            {
                                android.print.PrintDocumentInfo printdocumentinfo = (new android.print.PrintDocumentInfo.Builder(jobName)).setContentType(flag).setPageCount(flag).build();
                                if (newPrintAttributes.equals(oldPrintAttributes))
                                {
                                    flag = false;
                                }
                                layoutResultCallback.onLayoutFinished(printdocumentinfo, flag);
                                return;
                            } else
                            {
                                layoutResultCallback.onLayoutFailed(null);
                                return;
                            }
                        }

                        protected volatile void onPostExecute(Object obj)
                        {
                            onPostExecute((Bitmap)obj);
                        }

                        protected void onPreExecute()
                        {
                            cancellationSignal.setOnCancelListener(new android.os.CancellationSignal.OnCancelListener() {

                                final _cls1 this$2;

                                public void onCancel()
                                {
                                    cancelLoad();
                                    cancel(false);
                                }

            
            {
                this$2 = _cls1.this;
                super();
            }
                            });
                        }

            
            {
                this$1 = final__pcls2;
                cancellationSignal = cancellationsignal;
                newPrintAttributes = printattributes;
                oldPrintAttributes = printattributes1;
                layoutResultCallback = android.print.PrintDocumentAdapter.LayoutResultCallback.this;
                super();
            }
                    };
                    loadBitmap.execute(new Uri[0]);
                    mAttributes = newPrintAttributes;
                    return;
                }
            }

            public void onWrite(PageRange apagerange[], ParcelFileDescriptor parcelfiledescriptor, CancellationSignal cancellationsignal, android.print.PrintDocumentAdapter.WriteResultCallback writeresultcallback)
            {
                PrintedPdfDocument printedpdfdocument = new PrintedPdfDocument(mContext, mAttributes);
                android.graphics.pdf.PdfDocument.Page page = printedpdfdocument.startPage(1);
                RectF rectf = new RectF(page.getInfo().getContentRect());
                Matrix matrix = getMatrix(mBitmap.getWidth(), mBitmap.getHeight(), rectf, fittingMode);
                page.getCanvas().drawBitmap(mBitmap, matrix, null);
                printedpdfdocument.finishPage(page);
                printedpdfdocument.writeTo(new FileOutputStream(parcelfiledescriptor.getFileDescriptor()));
                PageRange apagerange1[] = new PageRange[1];
                apagerange1[0] = PageRange.ALL_PAGES;
                writeresultcallback.onWriteFinished(apagerange1);
_L2:
                if (printedpdfdocument != null)
                {
                    printedpdfdocument.close();
                }
                if (parcelfiledescriptor == null)
                {
                    break MISSING_BLOCK_LABEL_150;
                }
                parcelfiledescriptor.close();
                return;
                IOException ioexception1;
                ioexception1;
                Log.e("PrintHelperKitkat", "Error writing printed content", ioexception1);
                writeresultcallback.onWriteFailed(null);
                if (true) goto _L2; else goto _L1
_L1:
                Exception exception;
                exception;
                if (printedpdfdocument != null)
                {
                    printedpdfdocument.close();
                }
                IOException ioexception2;
                if (parcelfiledescriptor != null)
                {
                    try
                    {
                        parcelfiledescriptor.close();
                    }
                    catch (IOException ioexception) { }
                }
                throw exception;
                ioexception2;
            }


            
            {
                this$0 = PrintHelperKitkat.this;
                jobName = s;
                imageFile = uri;
                fittingMode = i;
                super();
                mBitmap = null;
            }
        };
        printmanager = (PrintManager)mContext.getSystemService("print");
        builder = new android.print.PrintAttributes.Builder();
        builder.setColorMode(mColorMode);
        if (mOrientation != 1) goto _L2; else goto _L1
_L1:
        builder.setMediaSize(android.print.PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
_L4:
        printmanager.print(jobName, printdocumentadapter, builder.build());
        return;
_L2:
        if (mOrientation == 2)
        {
            builder.setMediaSize(android.print.PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public void setColorMode(int i)
    {
        mColorMode = i;
    }

    public void setOrientation(int i)
    {
        mOrientation = i;
    }

    public void setScaleMode(int i)
    {
        mScaleMode = i;
    }



}
