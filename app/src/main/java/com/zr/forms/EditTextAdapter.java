package com.zr.forms;

import android.view.View;
import android.widget.EditText;

/**
 * Created by rui on 3/13/18.
 */

public class EditTextAdapter extends FormEntryAdapter {
    public EditTextAdapter(View view) {
        super(view);
    }

    @Override
    public String getString() {
        return ((EditText) view).getText().toString();
    }

    @Override
    public void setString(String value) {
        ((EditText) view).setText(value);
    }

    @Override
    public void setError(String error) {
        ((EditText) view).setError(error);
    }
}
