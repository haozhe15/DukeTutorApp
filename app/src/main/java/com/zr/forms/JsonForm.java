package com.zr.forms;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by rui on 3/13/18.
 */

public class JsonForm {
    private final HashMap<String, FormEntryAdapter> viewMap;
    private final Activity activity;

    public JsonForm(Activity activity) {
        this.viewMap = new HashMap<>();
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void put(String name, @IdRes int id) {
        View view = activity.findViewById(id);
        if (view == null) {
            return;
        }
        if (view instanceof EditText) {
            put(name, id, EditTextAdapter.class);
        } else {
            // TODO more views
            throw new UnsupportedOperationException("JsonForm does not support this class");
        }
    }

    public void put(String name, @IdRes int id, Class<? extends FormEntryAdapter> cls) {
        View view = activity.findViewById(id);
        if (view == null) {
            return;
        }
        try {
            Constructor<? extends FormEntryAdapter> ctor = cls.getConstructor(View.class);
            viewMap.put(name, ctor.newInstance(view));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public View get(String name) {
        FormEntryAdapter v = viewMap.get(name);
        if (v == null) {
            return null;
        }
        return v.getView();
    }

    public String getString(String name) {
        FormEntryAdapter v = viewMap.get(name);
        if (v == null) {
            return null;
        }
        return v.getString();
    }

    public JSONObject getJson() {
        HashMap<String, String> params = new HashMap<>();
        for (Map.Entry<String, FormEntryAdapter> e : viewMap.entrySet()) {
            params.put(e.getKey(), e.getValue().getString());
        }
        return new JSONObject(params);
    }

    public void setError(JSONObject error) {
        Iterator<String> it = error.keys();
        while (it.hasNext()) {
            try {
                String key = it.next();
                JSONArray value = error.getJSONArray(key);
                FormEntryAdapter v = viewMap.get(key);
                ArrayList<String> lines = new ArrayList<>();
                for (int i = 0; i < value.length(); i++) {
                    String line = value.getString(i);
                    lines.add(line);
                }
                if (v != null) {
                    v.setError(TextUtils.join("\n", lines));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
