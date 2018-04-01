package com.zr.forms;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by rui on 3/13/18.
 *
 * JsonForm is a helper class that can convert from
 * Android views to a JSONObject and vice-versa.
 */
public class JsonForm implements Response.ErrorListener {
    private final HashMap<String, FormEntryAdapter> viewMap;
    private final Activity activity;
    private final Response.ErrorListener errorListener;

    /**
     * Constructor.
     *
     * @param activity The activity this form belongs to.
     */
    public JsonForm(Activity activity, Response.ErrorListener errorListener) {
        this.viewMap = new HashMap<>();
        this.activity = activity;
        this.errorListener = errorListener;
    }

    public Activity getActivity() {
        return activity;
    }

    /**
     * Convenience function that finds a view by its ID
     * and binds a name to it.
     *
     * Note: currently only EditText is supported.
     * @param name The name of the form entry.
     * @param id The ID of the Android view.
     */
    public void put(String name, @IdRes int id) {
        View view = activity.findViewById(id);
        if (view == null) {
            return;
        }
        if (view instanceof EditText) {
            put(name, new EditTextAdapter(view));
        } else {
            // TODO more views
            throw new UnsupportedOperationException("JsonForm does not support this class");
        }
    }

    /**
     * Binds a name to a view.
     *
     * The caller must provide a FormEntryAdapter instead of a view.
     *
     * @param name The name of the entry.
     * @param adapter An adapter associated to the view.
     */
    public void put(String name, FormEntryAdapter adapter) {
        viewMap.put(name, adapter);
    }

    public View get(String name) {
        FormEntryAdapter v = viewMap.get(name);
        if (v == null) {
            return null;
        }
        return v.getView();
    }

    /**
     * Get the string value of an form entry by a name.
     * @param name The name of the entry.
     * @return The string value of the entry.
     */
    public String getString(String name) {
        FormEntryAdapter v = viewMap.get(name);
        if (v == null) {
            return null;
        }
        return v.getString();
    }

    /**
     * Get a JSONObject representation of the form.
     * @return The JSONObject.
     */
    public JSONObject getJson() {
        HashMap<String, String> params = new HashMap<>();
        for (Map.Entry<String, FormEntryAdapter> e : viewMap.entrySet()) {
            params.put(e.getKey(), e.getValue().getString());
        }
        return new JSONObject(params);
    }

    /**
     * Set the value of form entries given by a JSONObject.
     *
     * In the JSONObject, the keys corresponds to the names
     * registered by put, and the values must be strings.
     *
     * Note: unknown names or invalid values are ignored.
     * If the name of an entry does not appear in the JSONObject,
     * its value is unchanged.
     *
     * @param obj The JSONObject containing form values.
     */
    public void setJson(JSONObject obj) {
        Iterator<String> it = obj.keys();
        while (it.hasNext()) {
            try {
                String key = it.next();
                String value = obj.getString(key);
                FormEntryAdapter v = viewMap.get(key);
                if (v != null) {
                    v.setString(value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set the value of form entries given by a Bundle.
     *
     * The rules are similar to setJson.
     *
     * @param bundle The Bundle containing form values.
     */
    public void setBundle(Bundle bundle) {
        for (String key : bundle.keySet()) {
            String value = bundle.getString(key);
            FormEntryAdapter v = viewMap.get(key);
            if (v != null && value != null) {
                v.setString(value);
            }
        }
    }

    /**
     * Set the error prompt of form entries given by a JSONObject.
     *
     * In the JSONObject, the keys corresponds to the names
     * registered by put, and each value must be an array
     * of strings which contains error messages for an entry.
     *
     * Note: unknown names or invalid values are ignored.
     *
     * @param error The JSONObject containing error messages.
     */
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

    @Override
    public void onErrorResponse(VolleyError error) {
        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            try {
                String jsonString = new String(response.data);
                JSONObject errorObject = new JSONObject(jsonString);
                String detail = errorObject.optString("detail");
                if (!detail.isEmpty()) {
                    generalError(new VolleyError(detail));
                }
                setError(errorObject);
            } catch (JSONException e) {
                e.printStackTrace();
                generalError(error);
            }
        } else {
            generalError(error);
        }
    }

    private void generalError(VolleyError error) {
        if (errorListener != null) {
            errorListener.onErrorResponse(error);
        }
    }
}
