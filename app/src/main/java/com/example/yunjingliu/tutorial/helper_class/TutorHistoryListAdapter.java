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
 * Created by Haozhe Wang on 04/09/2018.
 */

public class TutorHistoryListAdapter extends JsonArrayAdapter {
    public TutorHistoryListAdapter(Context context, int resId, JSONArray jsonArray) {
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
            double rating = item.getDouble("rating");
            String text = session.getString("title");

            text = "Session Title: "+text+"\nRating: "+rating;
            view.setText(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}
