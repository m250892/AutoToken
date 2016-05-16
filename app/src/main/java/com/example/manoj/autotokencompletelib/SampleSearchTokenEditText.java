package com.example.manoj.autotokencompletelib;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.manoj.autotokencompletelib.searchtext.BaseTokenEditText;

/**
 * Created by manoj on 07/04/16.
 */
public class SampleSearchTokenEditText extends BaseTokenEditText<String> {

    public SampleSearchTokenEditText(Context context) {
        super(context);
    }

    public SampleSearchTokenEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SampleSearchTokenEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View getViewForObject(String object) {
        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) l.inflate(R.layout.contact_token, (ViewGroup) SampleSearchTokenEditText.this.getParent(), false);
        ((TextView) view.findViewById(R.id.name)).setText(object);
        return view;
    }

    @Override
    protected String defaultObject(String completionText) {
        return completionText;
    }
}
