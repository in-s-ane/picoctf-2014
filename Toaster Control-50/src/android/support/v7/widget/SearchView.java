// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.widget;

import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.view.CollapsibleActionView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

// Referenced classes of package android.support.v7.widget:
//            SuggestionsAdapter

public class SearchView extends LinearLayout
    implements CollapsibleActionView
{
    private static class AutoCompleteTextViewReflector
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

        AutoCompleteTextViewReflector()
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

    public static interface OnCloseListener
    {

        public abstract boolean onClose();
    }

    public static interface OnQueryTextListener
    {

        public abstract boolean onQueryTextChange(String s);

        public abstract boolean onQueryTextSubmit(String s);
    }

    public static interface OnSuggestionListener
    {

        public abstract boolean onSuggestionClick(int i);

        public abstract boolean onSuggestionSelect(int i);
    }

    public static class SearchAutoComplete extends AutoCompleteTextView
    {

        private SearchView mSearchView;
        private int mThreshold;

        private boolean isEmpty()
        {
            return TextUtils.getTrimmedLength(getText()) == 0;
        }

        public boolean enoughToFilter()
        {
            return mThreshold <= 0 || super.enoughToFilter();
        }

        protected void onFocusChanged(boolean flag, int i, Rect rect)
        {
            super.onFocusChanged(flag, i, rect);
            mSearchView.onTextFocusChanged();
        }

        public boolean onKeyPreIme(int i, KeyEvent keyevent)
        {
            if (i == 4)
            {
                if (keyevent.getAction() == 0 && keyevent.getRepeatCount() == 0)
                {
                    android.view.KeyEvent.DispatcherState dispatcherstate1 = getKeyDispatcherState();
                    if (dispatcherstate1 != null)
                    {
                        dispatcherstate1.startTracking(keyevent, this);
                    }
                    return true;
                }
                if (keyevent.getAction() == 1)
                {
                    android.view.KeyEvent.DispatcherState dispatcherstate = getKeyDispatcherState();
                    if (dispatcherstate != null)
                    {
                        dispatcherstate.handleUpEvent(keyevent);
                    }
                    if (keyevent.isTracking() && !keyevent.isCanceled())
                    {
                        mSearchView.clearFocus();
                        mSearchView.setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(i, keyevent);
        }

        public void onWindowFocusChanged(boolean flag)
        {
            super.onWindowFocusChanged(flag);
            if (flag && mSearchView.hasFocus() && getVisibility() == 0)
            {
                ((InputMethodManager)getContext().getSystemService("input_method")).showSoftInput(this, 0);
                if (SearchView.isLandscapeMode(getContext()))
                {
                    SearchView.HIDDEN_METHOD_INVOKER.ensureImeVisible(this, true);
                }
            }
        }

        public void performCompletion()
        {
        }

        protected void replaceText(CharSequence charsequence)
        {
        }

        void setSearchView(SearchView searchview)
        {
            mSearchView = searchview;
        }

        public void setThreshold(int i)
        {
            super.setThreshold(i);
            mThreshold = i;
        }


        public SearchAutoComplete(Context context)
        {
            super(context);
            mThreshold = getThreshold();
        }

        public SearchAutoComplete(Context context, AttributeSet attributeset)
        {
            super(context, attributeset);
            mThreshold = getThreshold();
        }

        public SearchAutoComplete(Context context, AttributeSet attributeset, int i)
        {
            super(context, attributeset, i);
            mThreshold = getThreshold();
        }
    }


    private static final boolean DBG = false;
    static final AutoCompleteTextViewReflector HIDDEN_METHOD_INVOKER = new AutoCompleteTextViewReflector();
    private static final String IME_OPTION_NO_MICROPHONE = "nm";
    private static final String LOG_TAG = "SearchView";
    private Bundle mAppSearchData;
    private boolean mClearingFocus;
    private ImageView mCloseButton;
    private int mCollapsedImeOptions;
    private View mDropDownAnchor;
    private boolean mExpandedInActionView;
    private boolean mIconified;
    private boolean mIconifiedByDefault;
    private int mMaxWidth;
    private CharSequence mOldQueryText;
    private final android.view.View.OnClickListener mOnClickListener;
    private OnCloseListener mOnCloseListener;
    private final android.widget.TextView.OnEditorActionListener mOnEditorActionListener;
    private final android.widget.AdapterView.OnItemClickListener mOnItemClickListener;
    private final android.widget.AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    private OnQueryTextListener mOnQueryChangeListener;
    private android.view.View.OnFocusChangeListener mOnQueryTextFocusChangeListener;
    private android.view.View.OnClickListener mOnSearchClickListener;
    private OnSuggestionListener mOnSuggestionListener;
    private final WeakHashMap mOutsideDrawablesCache;
    private CharSequence mQueryHint;
    private boolean mQueryRefinement;
    private SearchAutoComplete mQueryTextView;
    private Runnable mReleaseCursorRunnable = new Runnable() {

        final SearchView this$0;

        public void run()
        {
            if (mSuggestionsAdapter != null && (mSuggestionsAdapter instanceof SuggestionsAdapter))
            {
                mSuggestionsAdapter.changeCursor(null);
            }
        }

            
            {
                this$0 = SearchView.this;
                super();
            }
    };
    private View mSearchButton;
    private View mSearchEditFrame;
    private ImageView mSearchHintIcon;
    private View mSearchPlate;
    private SearchableInfo mSearchable;
    private Runnable mShowImeRunnable = new Runnable() {

        final SearchView this$0;

        public void run()
        {
            InputMethodManager inputmethodmanager = (InputMethodManager)getContext().getSystemService("input_method");
            if (inputmethodmanager != null)
            {
                SearchView.HIDDEN_METHOD_INVOKER.showSoftInputUnchecked(inputmethodmanager, SearchView.this, 0);
            }
        }

            
            {
                this$0 = SearchView.this;
                super();
            }
    };
    private View mSubmitArea;
    private View mSubmitButton;
    private boolean mSubmitButtonEnabled;
    private CursorAdapter mSuggestionsAdapter;
    android.view.View.OnKeyListener mTextKeyListener = new android.view.View.OnKeyListener() {

        final SearchView this$0;

        public boolean onKey(View view, int l, KeyEvent keyevent)
        {
            if (mSearchable != null)
            {
                if (mQueryTextView.isPopupShowing() && mQueryTextView.getListSelection() != -1)
                {
                    return onSuggestionsKey(view, l, keyevent);
                }
                if (!mQueryTextView.isEmpty() && KeyEventCompat.hasNoModifiers(keyevent) && keyevent.getAction() == 1 && l == 66)
                {
                    view.cancelLongPress();
                    launchQuerySearch(0, null, mQueryTextView.getText().toString());
                    return true;
                }
            }
            return false;
        }

            
            {
                this$0 = SearchView.this;
                super();
            }
    };
    private TextWatcher mTextWatcher = new TextWatcher() {

        final SearchView this$0;

        public void afterTextChanged(Editable editable)
        {
        }

        public void beforeTextChanged(CharSequence charsequence1, int l, int i1, int j1)
        {
        }

        public void onTextChanged(CharSequence charsequence1, int l, int i1, int j1)
        {
            SearchView.this.onTextChanged(charsequence1);
        }

            
            {
                this$0 = SearchView.this;
                super();
            }
    };
    private Runnable mUpdateDrawableStateRunnable = new Runnable() {

        final SearchView this$0;

        public void run()
        {
            updateFocusedState();
        }

            
            {
                this$0 = SearchView.this;
                super();
            }
    };
    private CharSequence mUserQuery;
    private final Intent mVoiceAppSearchIntent;
    private View mVoiceButton;
    private boolean mVoiceButtonEnabled;
    private final Intent mVoiceWebSearchIntent;

    public SearchView(Context context)
    {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mOutsideDrawablesCache = new WeakHashMap();
        mOnClickListener = new android.view.View.OnClickListener() {

            final SearchView this$0;

            public void onClick(View view)
            {
                if (view == mSearchButton)
                {
                    onSearchClicked();
                } else
                {
                    if (view == mCloseButton)
                    {
                        onCloseClicked();
                        return;
                    }
                    if (view == mSubmitButton)
                    {
                        onSubmitQuery();
                        return;
                    }
                    if (view == mVoiceButton)
                    {
                        onVoiceClicked();
                        return;
                    }
                    if (view == mQueryTextView)
                    {
                        forceSuggestionQuery();
                        return;
                    }
                }
            }

            
            {
                this$0 = SearchView.this;
                super();
            }
        };
        mOnEditorActionListener = new android.widget.TextView.OnEditorActionListener() {

            final SearchView this$0;

            public boolean onEditorAction(TextView textview, int l, KeyEvent keyevent)
            {
                onSubmitQuery();
                return true;
            }

            
            {
                this$0 = SearchView.this;
                super();
            }
        };
        mOnItemClickListener = new android.widget.AdapterView.OnItemClickListener() {

            final SearchView this$0;

            public void onItemClick(AdapterView adapterview, View view, int l, long l1)
            {
                onItemClicked(l, 0, null);
            }

            
            {
                this$0 = SearchView.this;
                super();
            }
        };
        mOnItemSelectedListener = new android.widget.AdapterView.OnItemSelectedListener() {

            final SearchView this$0;

            public void onItemSelected(AdapterView adapterview, View view, int l, long l1)
            {
                SearchView.this.onItemSelected(l);
            }

            public void onNothingSelected(AdapterView adapterview)
            {
            }

            
            {
                this$0 = SearchView.this;
                super();
            }
        };
        ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(android.support.v7.appcompat.R.layout.abc_search_view, this, true);
        mSearchButton = findViewById(android.support.v7.appcompat.R.id.search_button);
        mQueryTextView = (SearchAutoComplete)findViewById(android.support.v7.appcompat.R.id.search_src_text);
        mQueryTextView.setSearchView(this);
        mSearchEditFrame = findViewById(android.support.v7.appcompat.R.id.search_edit_frame);
        mSearchPlate = findViewById(android.support.v7.appcompat.R.id.search_plate);
        mSubmitArea = findViewById(android.support.v7.appcompat.R.id.submit_area);
        mSubmitButton = findViewById(android.support.v7.appcompat.R.id.search_go_btn);
        mCloseButton = (ImageView)findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        mVoiceButton = findViewById(android.support.v7.appcompat.R.id.search_voice_btn);
        mSearchHintIcon = (ImageView)findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        mSearchButton.setOnClickListener(mOnClickListener);
        mCloseButton.setOnClickListener(mOnClickListener);
        mSubmitButton.setOnClickListener(mOnClickListener);
        mVoiceButton.setOnClickListener(mOnClickListener);
        mQueryTextView.setOnClickListener(mOnClickListener);
        mQueryTextView.addTextChangedListener(mTextWatcher);
        mQueryTextView.setOnEditorActionListener(mOnEditorActionListener);
        mQueryTextView.setOnItemClickListener(mOnItemClickListener);
        mQueryTextView.setOnItemSelectedListener(mOnItemSelectedListener);
        mQueryTextView.setOnKeyListener(mTextKeyListener);
        mQueryTextView.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

            final SearchView this$0;

            public void onFocusChange(View view, boolean flag1)
            {
                if (mOnQueryTextFocusChangeListener != null)
                {
                    mOnQueryTextFocusChangeListener.onFocusChange(SearchView.this, flag1);
                }
            }

            
            {
                this$0 = SearchView.this;
                super();
            }
        });
        TypedArray typedarray = context.obtainStyledAttributes(attributeset, android.support.v7.appcompat.R.styleable.SearchView, 0, 0);
        setIconifiedByDefault(typedarray.getBoolean(3, true));
        int i = typedarray.getDimensionPixelSize(0, -1);
        if (i != -1)
        {
            setMaxWidth(i);
        }
        CharSequence charsequence = typedarray.getText(4);
        if (!TextUtils.isEmpty(charsequence))
        {
            setQueryHint(charsequence);
        }
        int j = typedarray.getInt(2, -1);
        if (j != -1)
        {
            setImeOptions(j);
        }
        int k = typedarray.getInt(1, -1);
        if (k != -1)
        {
            setInputType(k);
        }
        typedarray.recycle();
        TypedArray typedarray1 = context.obtainStyledAttributes(attributeset, android.support.v7.appcompat.R.styleable.View, 0, 0);
        boolean flag = typedarray1.getBoolean(0, true);
        typedarray1.recycle();
        setFocusable(flag);
        mVoiceWebSearchIntent = new Intent("android.speech.action.WEB_SEARCH");
        mVoiceWebSearchIntent.addFlags(0x10000000);
        mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        mVoiceAppSearchIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        mVoiceAppSearchIntent.addFlags(0x10000000);
        mDropDownAnchor = findViewById(mQueryTextView.getDropDownAnchor());
        if (mDropDownAnchor != null)
        {
            if (android.os.Build.VERSION.SDK_INT >= 11)
            {
                addOnLayoutChangeListenerToDropDownAnchorSDK11();
            } else
            {
                addOnLayoutChangeListenerToDropDownAnchorBase();
            }
        }
        updateViewsVisibility(mIconifiedByDefault);
        updateQueryHint();
    }

    private void addOnLayoutChangeListenerToDropDownAnchorBase()
    {
        mDropDownAnchor.getViewTreeObserver().addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener() {

            final SearchView this$0;

            public void onGlobalLayout()
            {
                adjustDropDownSizeAndPosition();
            }

            
            {
                this$0 = SearchView.this;
                super();
            }
        });
    }

    private void addOnLayoutChangeListenerToDropDownAnchorSDK11()
    {
        mDropDownAnchor.addOnLayoutChangeListener(new android.view.View.OnLayoutChangeListener() {

            final SearchView this$0;

            public void onLayoutChange(View view, int i, int j, int k, int l, int i1, int j1, 
                    int k1, int l1)
            {
                adjustDropDownSizeAndPosition();
            }

            
            {
                this$0 = SearchView.this;
                super();
            }
        });
    }

    private void adjustDropDownSizeAndPosition()
    {
        if (mDropDownAnchor.getWidth() > 1)
        {
            Resources resources = getContext().getResources();
            int i = mSearchPlate.getPaddingLeft();
            Rect rect = new Rect();
            int j;
            int k;
            int l;
            if (mIconifiedByDefault)
            {
                j = resources.getDimensionPixelSize(android.support.v7.appcompat.R.dimen.abc_dropdownitem_icon_width) + resources.getDimensionPixelSize(android.support.v7.appcompat.R.dimen.abc_dropdownitem_text_padding_left);
            } else
            {
                j = 0;
            }
            mQueryTextView.getDropDownBackground().getPadding(rect);
            k = i - (j + rect.left);
            mQueryTextView.setDropDownHorizontalOffset(k);
            l = (j + (mDropDownAnchor.getWidth() + rect.left + rect.right)) - i;
            mQueryTextView.setDropDownWidth(l);
        }
    }

    private Intent createIntent(String s, Uri uri, String s1, String s2, int i, String s3)
    {
        Intent intent = new Intent(s);
        intent.addFlags(0x10000000);
        if (uri != null)
        {
            intent.setData(uri);
        }
        intent.putExtra("user_query", mUserQuery);
        if (s2 != null)
        {
            intent.putExtra("query", s2);
        }
        if (s1 != null)
        {
            intent.putExtra("intent_extra_data_key", s1);
        }
        if (mAppSearchData != null)
        {
            intent.putExtra("app_data", mAppSearchData);
        }
        if (i != 0)
        {
            intent.putExtra("action_key", i);
            intent.putExtra("action_msg", s3);
        }
        intent.setComponent(mSearchable.getSearchActivity());
        return intent;
    }

    private Intent createIntentFromSuggestion(Cursor cursor, int i, String s)
    {
        String s1 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_action");
        if (s1 != null) goto _L2; else goto _L1
_L1:
        s1 = mSearchable.getSuggestIntentAction();
          goto _L2
_L8:
        String s2 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_data");
        if (s2 != null)
        {
            break MISSING_BLOCK_LABEL_49;
        }
        s2 = mSearchable.getSuggestIntentData();
        if (s2 == null) goto _L4; else goto _L3
_L3:
        String s3 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_data_id");
        if (s3 == null) goto _L4; else goto _L5
_L5:
        s2 = (new StringBuilder()).append(s2).append("/").append(Uri.encode(s3)).toString();
          goto _L4
_L6:
        Uri uri;
        String s4 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_query");
        return createIntent(s1, uri, SuggestionsAdapter.getColumnString(cursor, "suggest_intent_extra_data"), s4, i, s);
_L10:
        Uri uri1 = Uri.parse(s2);
        uri = uri1;
          goto _L6
        RuntimeException runtimeexception;
        runtimeexception;
        int k = cursor.getPosition();
        int j = k;
_L7:
        Log.w("SearchView", (new StringBuilder()).append("Search suggestions cursor at row ").append(j).append(" returned exception.").toString(), runtimeexception);
        return null;
        RuntimeException runtimeexception1;
        runtimeexception1;
        j = -1;
        if (true) goto _L7; else goto _L2
_L2:
        if (s1 == null)
        {
            s1 = "android.intent.action.SEARCH";
        }
          goto _L8
_L4:
        if (s2 != null) goto _L10; else goto _L9
_L9:
        uri = null;
          goto _L6
    }

    private Intent createVoiceAppSearchIntent(Intent intent, SearchableInfo searchableinfo)
    {
        ComponentName componentname = searchableinfo.getSearchActivity();
        Intent intent1 = new Intent("android.intent.action.SEARCH");
        intent1.setComponent(componentname);
        PendingIntent pendingintent = PendingIntent.getActivity(getContext(), 0, intent1, 0x40000000);
        Bundle bundle = new Bundle();
        if (mAppSearchData != null)
        {
            bundle.putParcelable("app_data", mAppSearchData);
        }
        Intent intent2 = new Intent(intent);
        String s = "free_form";
        int i = 1;
        Resources resources = getResources();
        if (searchableinfo.getVoiceLanguageModeId() != 0)
        {
            s = resources.getString(searchableinfo.getVoiceLanguageModeId());
        }
        int j = searchableinfo.getVoicePromptTextId();
        String s1 = null;
        if (j != 0)
        {
            s1 = resources.getString(searchableinfo.getVoicePromptTextId());
        }
        int k = searchableinfo.getVoiceLanguageId();
        String s2 = null;
        if (k != 0)
        {
            s2 = resources.getString(searchableinfo.getVoiceLanguageId());
        }
        if (searchableinfo.getVoiceMaxResults() != 0)
        {
            i = searchableinfo.getVoiceMaxResults();
        }
        intent2.putExtra("android.speech.extra.LANGUAGE_MODEL", s);
        intent2.putExtra("android.speech.extra.PROMPT", s1);
        intent2.putExtra("android.speech.extra.LANGUAGE", s2);
        intent2.putExtra("android.speech.extra.MAX_RESULTS", i);
        String s3;
        if (componentname == null)
        {
            s3 = null;
        } else
        {
            s3 = componentname.flattenToShortString();
        }
        intent2.putExtra("calling_package", s3);
        intent2.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", pendingintent);
        intent2.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent2;
    }

    private Intent createVoiceWebSearchIntent(Intent intent, SearchableInfo searchableinfo)
    {
        Intent intent1 = new Intent(intent);
        ComponentName componentname = searchableinfo.getSearchActivity();
        String s;
        if (componentname == null)
        {
            s = null;
        } else
        {
            s = componentname.flattenToShortString();
        }
        intent1.putExtra("calling_package", s);
        return intent1;
    }

    private void dismissSuggestions()
    {
        mQueryTextView.dismissDropDown();
    }

    private void forceSuggestionQuery()
    {
        HIDDEN_METHOD_INVOKER.doBeforeTextChanged(mQueryTextView);
        HIDDEN_METHOD_INVOKER.doAfterTextChanged(mQueryTextView);
    }

    private CharSequence getDecoratedHint(CharSequence charsequence)
    {
        if (!mIconifiedByDefault)
        {
            return charsequence;
        } else
        {
            SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder("   ");
            spannablestringbuilder.append(charsequence);
            Drawable drawable = getContext().getResources().getDrawable(getSearchIconId());
            int i = (int)(1.25D * (double)mQueryTextView.getTextSize());
            drawable.setBounds(0, 0, i, i);
            spannablestringbuilder.setSpan(new ImageSpan(drawable), 1, 2, 33);
            return spannablestringbuilder;
        }
    }

    private int getPreferredWidth()
    {
        return getContext().getResources().getDimensionPixelSize(android.support.v7.appcompat.R.dimen.abc_search_view_preferred_width);
    }

    private int getSearchIconId()
    {
        TypedValue typedvalue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.searchViewSearchIcon, typedvalue, true);
        return typedvalue.resourceId;
    }

    private boolean hasVoiceSearch()
    {
        SearchableInfo searchableinfo;
        boolean flag;
        searchableinfo = mSearchable;
        flag = false;
        if (searchableinfo == null) goto _L2; else goto _L1
_L1:
        boolean flag1;
        flag1 = mSearchable.getVoiceSearchEnabled();
        flag = false;
        if (!flag1) goto _L2; else goto _L3
_L3:
        if (!mSearchable.getVoiceSearchLaunchWebSearch()) goto _L5; else goto _L4
_L4:
        Intent intent = mVoiceWebSearchIntent;
_L7:
        flag = false;
        if (intent != null)
        {
            android.content.pm.ResolveInfo resolveinfo = getContext().getPackageManager().resolveActivity(intent, 0x10000);
            flag = false;
            if (resolveinfo != null)
            {
                flag = true;
            }
        }
_L2:
        return flag;
_L5:
        boolean flag2 = mSearchable.getVoiceSearchLaunchRecognizer();
        intent = null;
        if (flag2)
        {
            intent = mVoiceAppSearchIntent;
        }
        if (true) goto _L7; else goto _L6
_L6:
    }

    static boolean isLandscapeMode(Context context)
    {
        return context.getResources().getConfiguration().orientation == 2;
    }

    private boolean isSubmitAreaEnabled()
    {
        return (mSubmitButtonEnabled || mVoiceButtonEnabled) && !isIconified();
    }

    private void launchIntent(Intent intent)
    {
        if (intent == null)
        {
            return;
        }
        try
        {
            getContext().startActivity(intent);
            return;
        }
        catch (RuntimeException runtimeexception)
        {
            Log.e("SearchView", (new StringBuilder()).append("Failed launch activity: ").append(intent).toString(), runtimeexception);
        }
    }

    private void launchQuerySearch(int i, String s, String s1)
    {
        Intent intent = createIntent("android.intent.action.SEARCH", null, null, s1, i, s);
        getContext().startActivity(intent);
    }

    private boolean launchSuggestion(int i, int j, String s)
    {
        Cursor cursor = mSuggestionsAdapter.getCursor();
        if (cursor != null && cursor.moveToPosition(i))
        {
            launchIntent(createIntentFromSuggestion(cursor, j, s));
            return true;
        } else
        {
            return false;
        }
    }

    private void onCloseClicked()
    {
        if (TextUtils.isEmpty(mQueryTextView.getText()))
        {
            if (mIconifiedByDefault && (mOnCloseListener == null || !mOnCloseListener.onClose()))
            {
                clearFocus();
                updateViewsVisibility(true);
            }
            return;
        } else
        {
            mQueryTextView.setText("");
            mQueryTextView.requestFocus();
            setImeVisibility(true);
            return;
        }
    }

    private boolean onItemClicked(int i, int j, String s)
    {
        boolean flag;
label0:
        {
            if (mOnSuggestionListener != null)
            {
                boolean flag1 = mOnSuggestionListener.onSuggestionClick(i);
                flag = false;
                if (flag1)
                {
                    break label0;
                }
            }
            launchSuggestion(i, 0, null);
            setImeVisibility(false);
            dismissSuggestions();
            flag = true;
        }
        return flag;
    }

    private boolean onItemSelected(int i)
    {
        if (mOnSuggestionListener == null || !mOnSuggestionListener.onSuggestionSelect(i))
        {
            rewriteQueryFromSuggestion(i);
            return true;
        } else
        {
            return false;
        }
    }

    private void onSearchClicked()
    {
        updateViewsVisibility(false);
        mQueryTextView.requestFocus();
        setImeVisibility(true);
        if (mOnSearchClickListener != null)
        {
            mOnSearchClickListener.onClick(this);
        }
    }

    private void onSubmitQuery()
    {
        Editable editable = mQueryTextView.getText();
        if (editable != null && TextUtils.getTrimmedLength(editable) > 0 && (mOnQueryChangeListener == null || !mOnQueryChangeListener.onQueryTextSubmit(editable.toString())))
        {
            if (mSearchable != null)
            {
                launchQuerySearch(0, null, editable.toString());
                setImeVisibility(false);
            }
            dismissSuggestions();
        }
    }

    private boolean onSuggestionsKey(View view, int i, KeyEvent keyevent)
    {
        if (mSearchable != null && mSuggestionsAdapter != null && keyevent.getAction() == 0 && KeyEventCompat.hasNoModifiers(keyevent))
        {
            if (i == 66 || i == 84 || i == 61)
            {
                return onItemClicked(mQueryTextView.getListSelection(), 0, null);
            }
            if (i == 21 || i == 22)
            {
                int j;
                if (i == 21)
                {
                    j = 0;
                } else
                {
                    j = mQueryTextView.length();
                }
                mQueryTextView.setSelection(j);
                mQueryTextView.setListSelection(0);
                mQueryTextView.clearListSelection();
                HIDDEN_METHOD_INVOKER.ensureImeVisible(mQueryTextView, true);
                return true;
            }
            if (i == 19 && mQueryTextView.getListSelection() == 0)
            {
                return false;
            }
        }
        return false;
    }

    private void onTextChanged(CharSequence charsequence)
    {
        boolean flag = true;
        Editable editable = mQueryTextView.getText();
        mUserQuery = editable;
        boolean flag1;
        if (!TextUtils.isEmpty(editable))
        {
            flag1 = flag;
        } else
        {
            flag1 = false;
        }
        updateSubmitButton(flag1);
        if (flag1)
        {
            flag = false;
        }
        updateVoiceButton(flag);
        updateCloseButton();
        updateSubmitArea();
        if (mOnQueryChangeListener != null && !TextUtils.equals(charsequence, mOldQueryText))
        {
            mOnQueryChangeListener.onQueryTextChange(charsequence.toString());
        }
        mOldQueryText = charsequence.toString();
    }

    private void onVoiceClicked()
    {
        if (mSearchable != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        SearchableInfo searchableinfo;
        searchableinfo = mSearchable;
        try
        {
            if (searchableinfo.getVoiceSearchLaunchWebSearch())
            {
                Intent intent1 = createVoiceWebSearchIntent(mVoiceWebSearchIntent, searchableinfo);
                getContext().startActivity(intent1);
                return;
            }
        }
        catch (ActivityNotFoundException activitynotfoundexception)
        {
            Log.w("SearchView", "Could not find voice search activity");
            return;
        }
        if (!searchableinfo.getVoiceSearchLaunchRecognizer()) goto _L1; else goto _L3
_L3:
        Intent intent = createVoiceAppSearchIntent(mVoiceAppSearchIntent, searchableinfo);
        getContext().startActivity(intent);
        return;
    }

    private void postUpdateFocusedState()
    {
        post(mUpdateDrawableStateRunnable);
    }

    private void rewriteQueryFromSuggestion(int i)
    {
        Editable editable = mQueryTextView.getText();
        Cursor cursor = mSuggestionsAdapter.getCursor();
        if (cursor == null)
        {
            return;
        }
        if (cursor.moveToPosition(i))
        {
            CharSequence charsequence = mSuggestionsAdapter.convertToString(cursor);
            if (charsequence != null)
            {
                setQuery(charsequence);
                return;
            } else
            {
                setQuery(editable);
                return;
            }
        } else
        {
            setQuery(editable);
            return;
        }
    }

    private void setImeVisibility(boolean flag)
    {
        if (flag)
        {
            post(mShowImeRunnable);
        } else
        {
            removeCallbacks(mShowImeRunnable);
            InputMethodManager inputmethodmanager = (InputMethodManager)getContext().getSystemService("input_method");
            if (inputmethodmanager != null)
            {
                inputmethodmanager.hideSoftInputFromWindow(getWindowToken(), 0);
                return;
            }
        }
    }

    private void setQuery(CharSequence charsequence)
    {
        mQueryTextView.setText(charsequence);
        SearchAutoComplete searchautocomplete = mQueryTextView;
        int i;
        if (TextUtils.isEmpty(charsequence))
        {
            i = 0;
        } else
        {
            i = charsequence.length();
        }
        searchautocomplete.setSelection(i);
    }

    private void updateCloseButton()
    {
        boolean flag = true;
        boolean flag1;
        ImageView imageview;
        int i;
        Drawable drawable;
        int ai[];
        if (!TextUtils.isEmpty(mQueryTextView.getText()))
        {
            flag1 = flag;
        } else
        {
            flag1 = false;
        }
        if (!flag1 && (!mIconifiedByDefault || mExpandedInActionView))
        {
            flag = false;
        }
        imageview = mCloseButton;
        i = 0;
        if (!flag)
        {
            i = 8;
        }
        imageview.setVisibility(i);
        drawable = mCloseButton.getDrawable();
        if (flag1)
        {
            ai = ENABLED_STATE_SET;
        } else
        {
            ai = EMPTY_STATE_SET;
        }
        drawable.setState(ai);
    }

    private void updateFocusedState()
    {
        boolean flag = mQueryTextView.hasFocus();
        Drawable drawable = mSearchPlate.getBackground();
        int ai[];
        Drawable drawable1;
        int ai1[];
        if (flag)
        {
            ai = FOCUSED_STATE_SET;
        } else
        {
            ai = EMPTY_STATE_SET;
        }
        drawable.setState(ai);
        drawable1 = mSubmitArea.getBackground();
        if (flag)
        {
            ai1 = FOCUSED_STATE_SET;
        } else
        {
            ai1 = EMPTY_STATE_SET;
        }
        drawable1.setState(ai1);
        invalidate();
    }

    private void updateQueryHint()
    {
        if (mQueryHint != null)
        {
            mQueryTextView.setHint(getDecoratedHint(mQueryHint));
        } else
        if (mSearchable != null)
        {
            int i = mSearchable.getHintId();
            String s = null;
            if (i != 0)
            {
                s = getContext().getString(i);
            }
            if (s != null)
            {
                mQueryTextView.setHint(getDecoratedHint(s));
                return;
            }
        } else
        {
            mQueryTextView.setHint(getDecoratedHint(""));
            return;
        }
    }

    private void updateSearchAutoComplete()
    {
        int i = 1;
        mQueryTextView.setThreshold(mSearchable.getSuggestThreshold());
        mQueryTextView.setImeOptions(mSearchable.getImeOptions());
        int j = mSearchable.getInputType();
        if ((j & 0xf) == i)
        {
            j &= 0xfffeffff;
            if (mSearchable.getSuggestAuthority() != null)
            {
                j = 0x80000 | (j | 0x10000);
            }
        }
        mQueryTextView.setInputType(j);
        if (mSuggestionsAdapter != null)
        {
            mSuggestionsAdapter.changeCursor(null);
        }
        if (mSearchable.getSuggestAuthority() != null)
        {
            mSuggestionsAdapter = new SuggestionsAdapter(getContext(), this, mSearchable, mOutsideDrawablesCache);
            mQueryTextView.setAdapter(mSuggestionsAdapter);
            SuggestionsAdapter suggestionsadapter = (SuggestionsAdapter)mSuggestionsAdapter;
            if (mQueryRefinement)
            {
                i = 2;
            }
            suggestionsadapter.setQueryRefinement(i);
        }
    }

    private void updateSubmitArea()
    {
        byte byte0 = 8;
        if (isSubmitAreaEnabled() && (mSubmitButton.getVisibility() == 0 || mVoiceButton.getVisibility() == 0))
        {
            byte0 = 0;
        }
        mSubmitArea.setVisibility(byte0);
    }

    private void updateSubmitButton(boolean flag)
    {
        byte byte0 = 8;
        if (mSubmitButtonEnabled && isSubmitAreaEnabled() && hasFocus() && (flag || !mVoiceButtonEnabled))
        {
            byte0 = 0;
        }
        mSubmitButton.setVisibility(byte0);
    }

    private void updateViewsVisibility(boolean flag)
    {
        boolean flag1 = true;
        byte byte0 = 8;
        mIconified = flag;
        int i;
        boolean flag2;
        View view;
        byte byte1;
        ImageView imageview;
        if (flag)
        {
            i = 0;
        } else
        {
            i = byte0;
        }
        if (!TextUtils.isEmpty(mQueryTextView.getText()))
        {
            flag2 = flag1;
        } else
        {
            flag2 = false;
        }
        mSearchButton.setVisibility(i);
        updateSubmitButton(flag2);
        view = mSearchEditFrame;
        if (flag)
        {
            byte1 = byte0;
        } else
        {
            byte1 = 0;
        }
        view.setVisibility(byte1);
        imageview = mSearchHintIcon;
        if (!mIconifiedByDefault)
        {
            byte0 = 0;
        }
        imageview.setVisibility(byte0);
        updateCloseButton();
        if (flag2)
        {
            flag1 = false;
        }
        updateVoiceButton(flag1);
        updateSubmitArea();
    }

    private void updateVoiceButton(boolean flag)
    {
        byte byte0 = 8;
        if (mVoiceButtonEnabled && !isIconified() && flag)
        {
            byte0 = 0;
            mSubmitButton.setVisibility(8);
        }
        mVoiceButton.setVisibility(byte0);
    }

    public void clearFocus()
    {
        mClearingFocus = true;
        setImeVisibility(false);
        super.clearFocus();
        mQueryTextView.clearFocus();
        mClearingFocus = false;
    }

    public int getImeOptions()
    {
        return mQueryTextView.getImeOptions();
    }

    public int getInputType()
    {
        return mQueryTextView.getInputType();
    }

    public int getMaxWidth()
    {
        return mMaxWidth;
    }

    public CharSequence getQuery()
    {
        return mQueryTextView.getText();
    }

    public CharSequence getQueryHint()
    {
        CharSequence charsequence;
        if (mQueryHint != null)
        {
            charsequence = mQueryHint;
        } else
        if (mSearchable != null)
        {
            int i = mSearchable.getHintId();
            charsequence = null;
            if (i != 0)
            {
                return getContext().getString(i);
            }
        } else
        {
            return null;
        }
        return charsequence;
    }

    public CursorAdapter getSuggestionsAdapter()
    {
        return mSuggestionsAdapter;
    }

    public boolean isIconfiedByDefault()
    {
        return mIconifiedByDefault;
    }

    public boolean isIconified()
    {
        return mIconified;
    }

    public boolean isQueryRefinementEnabled()
    {
        return mQueryRefinement;
    }

    public boolean isSubmitButtonEnabled()
    {
        return mSubmitButtonEnabled;
    }

    public void onActionViewCollapsed()
    {
        clearFocus();
        updateViewsVisibility(true);
        mQueryTextView.setImeOptions(mCollapsedImeOptions);
        mExpandedInActionView = false;
    }

    public void onActionViewExpanded()
    {
        if (mExpandedInActionView)
        {
            return;
        } else
        {
            mExpandedInActionView = true;
            mCollapsedImeOptions = mQueryTextView.getImeOptions();
            mQueryTextView.setImeOptions(0x2000000 | mCollapsedImeOptions);
            mQueryTextView.setText("");
            setIconified(false);
            return;
        }
    }

    protected void onDetachedFromWindow()
    {
        removeCallbacks(mUpdateDrawableStateRunnable);
        post(mReleaseCursorRunnable);
        super.onDetachedFromWindow();
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        if (mSearchable == null)
        {
            return false;
        } else
        {
            return super.onKeyDown(i, keyevent);
        }
    }

    protected void onMeasure(int i, int j)
    {
        int k;
        int l;
        if (isIconified())
        {
            super.onMeasure(i, j);
            return;
        }
        k = android.view.View.MeasureSpec.getMode(i);
        l = android.view.View.MeasureSpec.getSize(i);
        k;
        JVM INSTR lookupswitch 3: default 60
    //                   -2147483648: 74
    //                   0: 130
    //                   1073741824: 109;
           goto _L1 _L2 _L3 _L4
_L1:
        super.onMeasure(android.view.View.MeasureSpec.makeMeasureSpec(l, 0x40000000), j);
        return;
_L2:
        if (mMaxWidth > 0)
        {
            l = Math.min(mMaxWidth, l);
        } else
        {
            l = Math.min(getPreferredWidth(), l);
        }
        continue; /* Loop/switch isn't completed */
_L4:
        if (mMaxWidth > 0)
        {
            l = Math.min(mMaxWidth, l);
        }
        continue; /* Loop/switch isn't completed */
_L3:
        if (mMaxWidth > 0)
        {
            l = mMaxWidth;
        } else
        {
            l = getPreferredWidth();
        }
        if (true) goto _L1; else goto _L5
_L5:
    }

    void onQueryRefine(CharSequence charsequence)
    {
        setQuery(charsequence);
    }

    void onTextFocusChanged()
    {
        updateViewsVisibility(isIconified());
        postUpdateFocusedState();
        if (mQueryTextView.hasFocus())
        {
            forceSuggestionQuery();
        }
    }

    public void onWindowFocusChanged(boolean flag)
    {
        super.onWindowFocusChanged(flag);
        postUpdateFocusedState();
    }

    public boolean requestFocus(int i, Rect rect)
    {
        boolean flag;
        if (mClearingFocus)
        {
            flag = false;
        } else
        {
            if (!isFocusable())
            {
                return false;
            }
            if (!isIconified())
            {
                flag = mQueryTextView.requestFocus(i, rect);
                if (flag)
                {
                    updateViewsVisibility(false);
                    return flag;
                }
            } else
            {
                return super.requestFocus(i, rect);
            }
        }
        return flag;
    }

    public void setAppSearchData(Bundle bundle)
    {
        mAppSearchData = bundle;
    }

    public void setIconified(boolean flag)
    {
        if (flag)
        {
            onCloseClicked();
            return;
        } else
        {
            onSearchClicked();
            return;
        }
    }

    public void setIconifiedByDefault(boolean flag)
    {
        if (mIconifiedByDefault == flag)
        {
            return;
        } else
        {
            mIconifiedByDefault = flag;
            updateViewsVisibility(flag);
            updateQueryHint();
            return;
        }
    }

    public void setImeOptions(int i)
    {
        mQueryTextView.setImeOptions(i);
    }

    public void setInputType(int i)
    {
        mQueryTextView.setInputType(i);
    }

    public void setMaxWidth(int i)
    {
        mMaxWidth = i;
        requestLayout();
    }

    public void setOnCloseListener(OnCloseListener oncloselistener)
    {
        mOnCloseListener = oncloselistener;
    }

    public void setOnQueryTextFocusChangeListener(android.view.View.OnFocusChangeListener onfocuschangelistener)
    {
        mOnQueryTextFocusChangeListener = onfocuschangelistener;
    }

    public void setOnQueryTextListener(OnQueryTextListener onquerytextlistener)
    {
        mOnQueryChangeListener = onquerytextlistener;
    }

    public void setOnSearchClickListener(android.view.View.OnClickListener onclicklistener)
    {
        mOnSearchClickListener = onclicklistener;
    }

    public void setOnSuggestionListener(OnSuggestionListener onsuggestionlistener)
    {
        mOnSuggestionListener = onsuggestionlistener;
    }

    public void setQuery(CharSequence charsequence, boolean flag)
    {
        mQueryTextView.setText(charsequence);
        if (charsequence != null)
        {
            mQueryTextView.setSelection(mQueryTextView.length());
            mUserQuery = charsequence;
        }
        if (flag && !TextUtils.isEmpty(charsequence))
        {
            onSubmitQuery();
        }
    }

    public void setQueryHint(CharSequence charsequence)
    {
        mQueryHint = charsequence;
        updateQueryHint();
    }

    public void setQueryRefinementEnabled(boolean flag)
    {
        mQueryRefinement = flag;
        if (mSuggestionsAdapter instanceof SuggestionsAdapter)
        {
            SuggestionsAdapter suggestionsadapter = (SuggestionsAdapter)mSuggestionsAdapter;
            byte byte0;
            if (flag)
            {
                byte0 = 2;
            } else
            {
                byte0 = 1;
            }
            suggestionsadapter.setQueryRefinement(byte0);
        }
    }

    public void setSearchableInfo(SearchableInfo searchableinfo)
    {
        mSearchable = searchableinfo;
        if (mSearchable != null)
        {
            updateSearchAutoComplete();
            updateQueryHint();
        }
        mVoiceButtonEnabled = hasVoiceSearch();
        if (mVoiceButtonEnabled)
        {
            mQueryTextView.setPrivateImeOptions("nm");
        }
        updateViewsVisibility(isIconified());
    }

    public void setSubmitButtonEnabled(boolean flag)
    {
        mSubmitButtonEnabled = flag;
        updateViewsVisibility(isIconified());
    }

    public void setSuggestionsAdapter(CursorAdapter cursoradapter)
    {
        mSuggestionsAdapter = cursoradapter;
        mQueryTextView.setAdapter(mSuggestionsAdapter);
    }






















}
