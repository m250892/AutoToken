package com.example.manoj.autotokencompletelib.searchtext;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.QwertyKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.MultiAutoCompleteTextView;


/**
 * Created by manoj on 06/04/16.
 */
public class CustomMultiCompleteEditText<T> extends EditText implements BaseTokenRecyclerAdapter.SearchOnItemClickListener, Filter.FilterListener, TextWatcher {

    private MultiAutoCompleteTextView.Tokenizer mTokenizer;
    private BaseTokenRecyclerAdapter searchResultAdapter;

    public CustomMultiCompleteEditText(Context context) {
        super(context);
    }

    public CustomMultiCompleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomMultiCompleteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomMultiCompleteEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    {
        addTextChangedListener(this);
        setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
    }

    @Override
    public void afterTextChanged(Editable s) {
        performFiltering(s.toString(), -1);
    }

    protected BaseTokenRecyclerAdapter getAdapter() {
        return searchResultAdapter;
    }

    protected void performFiltering(CharSequence text, int keyCode) {
        if (enoughToFilter()) {
            int end = getSelectionEnd();
            int start = mTokenizer.findTokenStart(text, end);

            performFiltering(text, start, end, keyCode);
        } else {
            //dismissDropDown();
            Filter f = getFilter();
            if (f != null) {
                f.filter(null);
            }
        }
    }

    protected void performFiltering(CharSequence text, int start, int end,
                                    int keyCode) {
        getFilter().filter(text.subSequence(start, end));
    }

    public boolean enoughToFilter() {
        Editable text = getText();

        int end = getSelectionEnd();
        if (end < 0 || mTokenizer == null) {
            return false;
        }

        int start = mTokenizer.findTokenStart(text, end);

        if (end - start >= getThreshold()) {
            return true;
        } else {
            return false;
        }
    }

    public void setAdapter(BaseTokenRecyclerAdapter searchResultAdapter) {
        this.searchResultAdapter = searchResultAdapter;
        this.searchResultAdapter.setSearchOnItemClickListener(this);
    }

    protected void replaceText(CharSequence text) {
        Log.d("manoj", "Parent replace text called : " + text);
        clearComposingText();

        int end = getSelectionEnd();
        int start = mTokenizer.findTokenStart(getText(), end);

        Editable editable = getText();
        String original = TextUtils.substring(editable, start, end);

        QwertyKeyListener.markAsReplaced(editable, start, end, original);
        editable.replace(start, end, mTokenizer.terminateToken(text));
    }


    @Override
    public void onSearchItemSelected(View view, int position) {
        T selectedText = (T) searchResultAdapter.getItem(position);
        Log.d("manoj", "Search selected text : " + selectedText);
        replaceText(convertSelectionToString(selectedText));
    }

    protected CharSequence convertSelectionToString(Object selectedItem) {
        return getFilter().convertResultToString(selectedItem);
    }


    public void setTokenizer(MultiAutoCompleteTextView.Tokenizer t) {
        mTokenizer = t;
    }

    public Filter getFilter() {
        return searchResultAdapter.getFilter();
    }

    public int getThreshold() {
        return 0;
    }

    @Override
    public void onFilterComplete(int count) {
        //TODO: filter listener
    }

    public void performCompletion() {
        //TODO: filter listener
    }
}
