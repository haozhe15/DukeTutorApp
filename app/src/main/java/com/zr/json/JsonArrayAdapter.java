package com.zr.json;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by rui on 3/31/18.
 */

public class JsonArrayAdapter extends BaseAdapter {
    protected final Context context;
    protected final int resId;
    protected final LayoutInflater inflater;
    protected JSONArray jsonArray;

    public JsonArrayAdapter(Context context, int resId, JSONArray jsonArray) {
        this.context = context;
        this.resId = resId;
        this.jsonArray = jsonArray;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (jsonArray != null) {
            return jsonArray.length();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (jsonArray == null) {
            return null;
        }
        try {
            return jsonArray.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return resId;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView != null && convertView instanceof TextView) {
            view = (TextView) convertView;
        } else {
            view = (TextView) inflater.inflate(resId, parent, false);
        }
        Object item = getItem(i);
        if (item != null) {
            view.setText(item.toString());
        } else {
            view.setText("");
        }
        return view;
    }

    public Context getContext() {
        return context;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        notifyDataSetChanged();
    }
}