// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.ResourceCursorAdapter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

// Referenced classes of package android.support.v7.widget:
//            SearchView

class SuggestionsAdapter extends ResourceCursorAdapter
    implements android.view.View.OnClickListener
{
    private static final class ChildViewCache
    {

        public final ImageView mIcon1;
        public final ImageView mIcon2;
        public final ImageView mIconRefine;
        public final TextView mText1;
        public final TextView mText2;

        public ChildViewCache(View view)
        {
            mText1 = (TextView)view.findViewById(0x1020014);
            mText2 = (TextView)view.findViewById(0x1020015);
            mIcon1 = (ImageView)view.findViewById(0x1020007);
            mIcon2 = (ImageView)view.findViewById(0x1020008);
            mIconRefine = (ImageView)view.findViewById(android.support.v7.appcompat.R.id.edit_query);
        }
    }


    private static final boolean DBG = false;
    static final int INVALID_INDEX = -1;
    private static final String LOG_TAG = "SuggestionsAdapter";
    private static final int QUERY_LIMIT = 50;
    static final int REFINE_ALL = 2;
    static final int REFINE_BY_ENTRY = 1;
    static final int REFINE_NONE;
    private boolean mClosed;
    private int mFlagsCol;
    private int mIconName1Col;
    private int mIconName2Col;
    private WeakHashMap mOutsideDrawablesCache;
    private Context mProviderContext;
    private int mQueryRefinement;
    private SearchManager mSearchManager;
    private SearchView mSearchView;
    private SearchableInfo mSearchable;
    private int mText1Col;
    private int mText2Col;
    private int mText2UrlCol;
    private ColorStateList mUrlColor;

    public SuggestionsAdapter(Context context, SearchView searchview, SearchableInfo searchableinfo, WeakHashMap weakhashmap)
    {
        super(context, android.support.v7.appcompat.R.layout.abc_search_dropdown_item_icons_2line, null, true);
        mClosed = false;
        mQueryRefinement = 1;
        mText1Col = -1;
        mText2Col = -1;
        mText2UrlCol = -1;
        mIconName1Col = -1;
        mIconName2Col = -1;
        mFlagsCol = -1;
        mSearchManager = (SearchManager)mContext.getSystemService("search");
        mSearchView = searchview;
        mSearchable = searchableinfo;
        mProviderContext = context;
        mOutsideDrawablesCache = weakhashmap;
    }

    private Drawable checkIconCache(String s)
    {
        android.graphics.drawable.Drawable.ConstantState constantstate = (android.graphics.drawable.Drawable.ConstantState)mOutsideDrawablesCache.get(s);
        if (constantstate == null)
        {
            return null;
        } else
        {
            return constantstate.newDrawable();
        }
    }

    private CharSequence formatUrl(CharSequence charsequence)
    {
        if (mUrlColor == null)
        {
            TypedValue typedvalue = new TypedValue();
            mContext.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.textColorSearchUrl, typedvalue, true);
            mUrlColor = mContext.getResources().getColorStateList(typedvalue.resourceId);
        }
        SpannableString spannablestring = new SpannableString(charsequence);
        spannablestring.setSpan(new TextAppearanceSpan(null, 0, 0, mUrlColor, null), 0, charsequence.length(), 33);
        return spannablestring;
    }

    private Drawable getActivityIcon(ComponentName componentname)
    {
        PackageManager packagemanager = mContext.getPackageManager();
        ActivityInfo activityinfo;
        int i;
        Drawable drawable;
        try
        {
            activityinfo = packagemanager.getActivityInfo(componentname, 128);
        }
        catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
        {
            Log.w("SuggestionsAdapter", namenotfoundexception.toString());
            return null;
        }
        i = activityinfo.getIconResource();
        if (i == 0)
        {
            drawable = null;
        } else
        {
            drawable = packagemanager.getDrawable(componentname.getPackageName(), i, activityinfo.applicationInfo);
            if (drawable == null)
            {
                Log.w("SuggestionsAdapter", (new StringBuilder()).append("Invalid icon resource ").append(i).append(" for ").append(componentname.flattenToShortString()).toString());
                return null;
            }
        }
        return drawable;
    }

    private Drawable getActivityIconWithCache(ComponentName componentname)
    {
        String s = componentname.flattenToShortString();
        if (mOutsideDrawablesCache.containsKey(s))
        {
            android.graphics.drawable.Drawable.ConstantState constantstate1 = (android.graphics.drawable.Drawable.ConstantState)mOutsideDrawablesCache.get(s);
            if (constantstate1 == null)
            {
                return null;
            } else
            {
                return constantstate1.newDrawable(mProviderContext.getResources());
            }
        }
        Drawable drawable = getActivityIcon(componentname);
        android.graphics.drawable.Drawable.ConstantState constantstate;
        if (drawable == null)
        {
            constantstate = null;
        } else
        {
            constantstate = drawable.getConstantState();
        }
        mOutsideDrawablesCache.put(s, constantstate);
        return drawable;
    }

    public static String getColumnString(Cursor cursor, String s)
    {
        return getStringOrNull(cursor, cursor.getColumnIndex(s));
    }

    private Drawable getDefaultIcon1(Cursor cursor)
    {
        Drawable drawable = getActivityIconWithCache(mSearchable.getSearchActivity());
        if (drawable != null)
        {
            return drawable;
        } else
        {
            return mContext.getPackageManager().getDefaultActivityIcon();
        }
    }

    private Drawable getDrawable(Uri uri)
    {
        boolean flag = "android.resource".equals(uri.getScheme());
        if (!flag)
        {
            break MISSING_BLOCK_LABEL_99;
        }
        Drawable drawable1 = getDrawableFromResourceUri(uri);
        return drawable1;
        android.content.res.Resources.NotFoundException notfoundexception;
        notfoundexception;
        try
        {
            throw new FileNotFoundException((new StringBuilder()).append("Resource does not exist: ").append(uri).toString());
        }
        catch (FileNotFoundException filenotfoundexception)
        {
            Log.w("SuggestionsAdapter", (new StringBuilder()).append("Icon not found: ").append(uri).append(", ").append(filenotfoundexception.getMessage()).toString());
        }
        return null;
        InputStream inputstream = mProviderContext.getContentResolver().openInputStream(uri);
        if (inputstream != null)
        {
            break MISSING_BLOCK_LABEL_145;
        }
        throw new FileNotFoundException((new StringBuilder()).append("Failed to open ").append(uri).toString());
        Drawable drawable = Drawable.createFromStream(inputstream, null);
        inputstream.close();
        return drawable;
        IOException ioexception1;
        ioexception1;
        Log.e("SuggestionsAdapter", (new StringBuilder()).append("Error closing icon stream for ").append(uri).toString(), ioexception1);
        return drawable;
        Exception exception;
        exception;
        inputstream.close();
_L1:
        throw exception;
        IOException ioexception;
        ioexception;
        Log.e("SuggestionsAdapter", (new StringBuilder()).append("Error closing icon stream for ").append(uri).toString(), ioexception);
          goto _L1
    }

    private Drawable getDrawableFromResourceValue(String s)
    {
        if (s != null && s.length() != 0 && !"0".equals(s)) goto _L2; else goto _L1
_L1:
        Drawable drawable = null;
_L4:
        return drawable;
_L2:
        int i;
        String s1;
        i = Integer.parseInt(s);
        s1 = (new StringBuilder()).append("android.resource://").append(mProviderContext.getPackageName()).append("/").append(i).toString();
        drawable = checkIconCache(s1);
        if (drawable != null)
        {
            continue; /* Loop/switch isn't completed */
        }
        Drawable drawable2;
        drawable2 = mProviderContext.getResources().getDrawable(i);
        storeInIconCache(s1, drawable2);
        return drawable2;
        NumberFormatException numberformatexception;
        numberformatexception;
        drawable = checkIconCache(s);
        if (drawable == null)
        {
            Drawable drawable1 = getDrawable(Uri.parse(s));
            storeInIconCache(s, drawable1);
            return drawable1;
        }
        continue; /* Loop/switch isn't completed */
        android.content.res.Resources.NotFoundException notfoundexception;
        notfoundexception;
        Log.w("SuggestionsAdapter", (new StringBuilder()).append("Icon resource not found: ").append(s).toString());
        return null;
        if (true) goto _L4; else goto _L3
_L3:
    }

    private Drawable getIcon1(Cursor cursor)
    {
        Drawable drawable;
        if (mIconName1Col == -1)
        {
            drawable = null;
        } else
        {
            drawable = getDrawableFromResourceValue(cursor.getString(mIconName1Col));
            if (drawable == null)
            {
                return getDefaultIcon1(cursor);
            }
        }
        return drawable;
    }

    private Drawable getIcon2(Cursor cursor)
    {
        if (mIconName2Col == -1)
        {
            return null;
        } else
        {
            return getDrawableFromResourceValue(cursor.getString(mIconName2Col));
        }
    }

    private static String getStringOrNull(Cursor cursor, int i)
    {
        if (i == -1)
        {
            return null;
        }
        String s;
        try
        {
            s = cursor.getString(i);
        }
        catch (Exception exception)
        {
            Log.e("SuggestionsAdapter", "unexpected error retrieving valid column from cursor, did the remote process die?", exception);
            return null;
        }
        return s;
    }

    private void setViewDrawable(ImageView imageview, Drawable drawable, int i)
    {
        imageview.setImageDrawable(drawable);
        if (drawable == null)
        {
            imageview.setVisibility(i);
            return;
        } else
        {
            imageview.setVisibility(0);
            drawable.setVisible(false, false);
            drawable.setVisible(true, false);
            return;
        }
    }

    private void setViewText(TextView textview, CharSequence charsequence)
    {
        textview.setText(charsequence);
        if (TextUtils.isEmpty(charsequence))
        {
            textview.setVisibility(8);
            return;
        } else
        {
            textview.setVisibility(0);
            return;
        }
    }

    private void storeInIconCache(String s, Drawable drawable)
    {
        if (drawable != null)
        {
            mOutsideDrawablesCache.put(s, drawable.getConstantState());
        }
    }

    private void updateSpinnerState(Cursor cursor)
    {
        Bundle bundle;
        if (cursor != null)
        {
            bundle = cursor.getExtras();
        } else
        {
            bundle = null;
        }
        if (bundle != null)
        {
            if (!bundle.getBoolean("in_progress"));
        }
    }

    public void bindView(View view, Context context, Cursor cursor)
    {
        ChildViewCache childviewcache;
        int j;
        childviewcache = (ChildViewCache)view.getTag();
        int i = mFlagsCol;
        j = 0;
        if (i != -1)
        {
            j = cursor.getInt(mFlagsCol);
        }
        if (childviewcache.mText1 != null)
        {
            String s1 = getStringOrNull(cursor, mText1Col);
            setViewText(childviewcache.mText1, s1);
        }
        if (childviewcache.mText2 == null) goto _L2; else goto _L1
_L1:
        String s = getStringOrNull(cursor, mText2UrlCol);
        Object obj;
        if (s != null)
        {
            obj = formatUrl(s);
        } else
        {
            obj = getStringOrNull(cursor, mText2Col);
        }
        if (!TextUtils.isEmpty(((CharSequence) (obj)))) goto _L4; else goto _L3
_L3:
        if (childviewcache.mText1 != null)
        {
            childviewcache.mText1.setSingleLine(false);
            childviewcache.mText1.setMaxLines(2);
        }
_L6:
        setViewText(childviewcache.mText2, ((CharSequence) (obj)));
_L2:
        if (childviewcache.mIcon1 != null)
        {
            setViewDrawable(childviewcache.mIcon1, getIcon1(cursor), 4);
        }
        if (childviewcache.mIcon2 != null)
        {
            setViewDrawable(childviewcache.mIcon2, getIcon2(cursor), 8);
        }
        if (mQueryRefinement == 2 || mQueryRefinement == 1 && (j & 1) != 0)
        {
            childviewcache.mIconRefine.setVisibility(0);
            childviewcache.mIconRefine.setTag(childviewcache.mText1.getText());
            childviewcache.mIconRefine.setOnClickListener(this);
            return;
        } else
        {
            childviewcache.mIconRefine.setVisibility(8);
            return;
        }
_L4:
        if (childviewcache.mText1 != null)
        {
            childviewcache.mText1.setSingleLine(true);
            childviewcache.mText1.setMaxLines(1);
        }
        if (true) goto _L6; else goto _L5
_L5:
    }

    public void changeCursor(Cursor cursor)
    {
        if (!mClosed) goto _L2; else goto _L1
_L1:
        Log.w("SuggestionsAdapter", "Tried to change cursor after adapter was closed.");
        if (cursor != null)
        {
            cursor.close();
        }
_L4:
        return;
_L2:
        super.changeCursor(cursor);
        if (cursor != null)
        {
            try
            {
                mText1Col = cursor.getColumnIndex("suggest_text_1");
                mText2Col = cursor.getColumnIndex("suggest_text_2");
                mText2UrlCol = cursor.getColumnIndex("suggest_text_2_url");
                mIconName1Col = cursor.getColumnIndex("suggest_icon_1");
                mIconName2Col = cursor.getColumnIndex("suggest_icon_2");
                mFlagsCol = cursor.getColumnIndex("suggest_flags");
                return;
            }
            catch (Exception exception)
            {
                Log.e("SuggestionsAdapter", "error changing cursor and caching columns", exception);
            }
            return;
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public void close()
    {
        changeCursor(null);
        mClosed = true;
    }

    public CharSequence convertToString(Cursor cursor)
    {
        Object obj;
        if (cursor == null)
        {
            obj = null;
        } else
        {
            obj = getColumnString(cursor, "suggest_intent_query");
            if (obj == null)
            {
                if (mSearchable.shouldRewriteQueryFromData())
                {
                    String s1 = getColumnString(cursor, "suggest_intent_data");
                    if (s1 != null)
                    {
                        return s1;
                    }
                }
                if (mSearchable.shouldRewriteQueryFromText())
                {
                    String s = getColumnString(cursor, "suggest_text_1");
                    if (s != null)
                    {
                        return s;
                    }
                }
                return null;
            }
        }
        return ((CharSequence) (obj));
    }

    Drawable getDrawableFromResourceUri(Uri uri)
        throws FileNotFoundException
    {
        String s = uri.getAuthority();
        if (TextUtils.isEmpty(s))
        {
            throw new FileNotFoundException((new StringBuilder()).append("No authority: ").append(uri).toString());
        }
        Resources resources;
        List list;
        try
        {
            resources = mContext.getPackageManager().getResourcesForApplication(s);
        }
        catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
        {
            throw new FileNotFoundException((new StringBuilder()).append("No package found for authority: ").append(uri).toString());
        }
        list = uri.getPathSegments();
        if (list == null)
        {
            throw new FileNotFoundException((new StringBuilder()).append("No path: ").append(uri).toString());
        }
        int i = list.size();
        int j;
        if (i == 1)
        {
            int k;
            try
            {
                k = Integer.parseInt((String)list.get(0));
            }
            catch (NumberFormatException numberformatexception)
            {
                throw new FileNotFoundException((new StringBuilder()).append("Single path segment is not a resource ID: ").append(uri).toString());
            }
            j = k;
        } else
        if (i == 2)
        {
            j = resources.getIdentifier((String)list.get(1), (String)list.get(0), s);
        } else
        {
            throw new FileNotFoundException((new StringBuilder()).append("More than two path segments: ").append(uri).toString());
        }
        if (j == 0)
        {
            throw new FileNotFoundException((new StringBuilder()).append("No resource found for: ").append(uri).toString());
        } else
        {
            return resources.getDrawable(j);
        }
    }

    public int getQueryRefinement()
    {
        return mQueryRefinement;
    }

    Cursor getSearchManagerSuggestions(SearchableInfo searchableinfo, String s, int i)
    {
        String s1;
        if (searchableinfo != null)
        {
            if ((s1 = searchableinfo.getSuggestAuthority()) != null)
            {
                android.net.Uri.Builder builder = (new android.net.Uri.Builder()).scheme("content").authority(s1).query("").fragment("");
                String s2 = searchableinfo.getSuggestPath();
                if (s2 != null)
                {
                    builder.appendEncodedPath(s2);
                }
                builder.appendPath("search_suggest_query");
                String s3 = searchableinfo.getSuggestSelection();
                String as[];
                Uri uri;
                if (s3 != null)
                {
                    as = (new String[] {
                        s
                    });
                } else
                {
                    builder.appendPath(s);
                    as = null;
                }
                if (i > 0)
                {
                    builder.appendQueryParameter("limit", String.valueOf(i));
                }
                uri = builder.build();
                return mContext.getContentResolver().query(uri, null, s3, as, null);
            }
        }
        return null;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        View view2 = super.getView(i, view, viewgroup);
        View view1 = view2;
_L2:
        return view1;
        RuntimeException runtimeexception;
        runtimeexception;
        Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", runtimeexception);
        view1 = newView(mContext, mCursor, viewgroup);
        if (view1 != null)
        {
            ((ChildViewCache)view1.getTag()).mText1.setText(runtimeexception.toString());
            return view1;
        }
        if (true) goto _L2; else goto _L1
_L1:
    }

    public boolean hasStableIds()
    {
        return false;
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewgroup)
    {
        View view = super.newView(context, cursor, viewgroup);
        view.setTag(new ChildViewCache(view));
        return view;
    }

    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
        updateSpinnerState(getCursor());
    }

    public void notifyDataSetInvalidated()
    {
        super.notifyDataSetInvalidated();
        updateSpinnerState(getCursor());
    }

    public void onClick(View view)
    {
        Object obj = view.getTag();
        if (obj instanceof CharSequence)
        {
            mSearchView.onQueryRefine((CharSequence)obj);
        }
    }

    public Cursor runQueryOnBackgroundThread(CharSequence charsequence)
    {
        String s;
        if (charsequence == null)
        {
            s = "";
        } else
        {
            s = charsequence.toString();
        }
        if (mSearchView.getVisibility() == 0 && mSearchView.getWindowVisibility() == 0) goto _L2; else goto _L1
_L1:
        Cursor cursor;
        return null;
_L2:
        if ((cursor = getSearchManagerSuggestions(mSearchable, s, 50)) == null) goto _L1; else goto _L3
_L3:
        try
        {
            cursor.getCount();
        }
        catch (RuntimeException runtimeexception)
        {
            Log.w("SuggestionsAdapter", "Search suggestions query threw an exception.", runtimeexception);
            return null;
        }
        return cursor;
    }

    public void setQueryRefinement(int i)
    {
        mQueryRefinement = i;
    }
}
