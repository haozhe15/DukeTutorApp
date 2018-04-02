package com.example.yunjingliu.tutorial;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zr.json.JsonArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rui on 3/31/18.
 */

class MsgListAdapter extends JsonArrayAdapter {
    public MsgListAdapter(Context context, int resId, JSONArray jsonArray) {
        super(context, resId, jsonArray);
    }

    @Override
    public JSONObject getItem(int i) {
        return (JSONObject) super.getItem(i);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView != null && convertView instanceof TextView) {
            view = (TextView) convertView;
        } else {
            view = (TextView) inflater.inflate(resId, parent, false);
        }
        view.setText("");
        JSONObject item = getItem(i);
        try {
            view.setText(item.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}
