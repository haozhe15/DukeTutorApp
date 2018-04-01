package com.zr.forms;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by rui on 3/31/18.
 */

public class SpinnerAdapter extends FormEntryAdapter {
    private final String[] choices;

    public SpinnerAdapter(View spinner, String[] choices) {
        super(spinner);
        this.choices = choices;
    }

    @Override
    public String getString() {
        int i = ((Spinner) getView()).getSelectedItemPosition();
        return choices[i];
    }

    @Override
    public void setString(String value) {
        for (int i = 0; i < choices.length; i++) {
            if (choices[i].equals(value)) {
                ((Spinner) getView()).setSelection(i);
            }
        }
    }

    @Override
    public void setError(String error) {
        Spinner spinner = (Spinner) getView();
        ((TextView) spinner.getSelectedView()).setError(error);
    }
}
