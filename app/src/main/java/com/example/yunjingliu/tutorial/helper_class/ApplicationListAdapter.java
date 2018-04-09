package com.example.yunjingliu.tutorial.helper_class;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zr.json.JsonArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YunjingLiu on 4/1/18.
 */

public class ApplicationListAdapter extends JsonArrayAdapter {
    public ApplicationListAdapter(Context context, int resId, JSONArray jsonArray) {
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
            JSONObject session = item.getJSONObject("session");
            String text = session.getString("title");
            String status;
            if(item.isNull("accepted")){
                status = "Undecided";
            }
            else if(item.getBoolean("accepted")){
                status = "Accepted";
            }
            else{
                status = "Denied";
            }
            text = "Session Title: "+text+"\nStatus: "+status;
            view.setText(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}
