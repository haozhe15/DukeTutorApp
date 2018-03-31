package com.zr.forms;

import android.view.View;

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

    public abstract String getString();

    public abstract void setError(String error);
}
