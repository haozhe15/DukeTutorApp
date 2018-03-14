package com.example.yunjingliu.tutorial;

import android.view.View;
import android.widget.EditText;

/**
 * Created by rui on 3/13/18.
 */

public abstract class FormEntryAdapter {
    final View view;

    public FormEntryAdapter(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public abstract String toString();
    public abstract void setError(String error);
}
