package com.zr.json;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by rui on 4/1/18.
 */

public class Conversions {
    public static Bundle jsonToBundle(JSONObject jsonObject) throws JSONException {
        Bundle bundle = new Bundle(jsonObject.length());
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();
            String value = jsonObject.getString(key);
            bundle.putString(key, value);
        }
        return bundle;
    }
}
