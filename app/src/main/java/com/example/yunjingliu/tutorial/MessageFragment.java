package com.example.yunjingliu.tutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.zr.auth.JsonArrayAuthRequest;

import org.json.JSONArray;

/**
 * Created by YunjingLiu on 4/8/18.
 */

public class MessageFragment extends Fragment implements Response.Listener<JSONArray>, AdapterView.OnItemClickListener{
    MsgListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_msg, container, false);
        getMsg();
        ListView msgList = (ListView) view.findViewById(R.id.lvMsg);
        adapter = new MsgListAdapter(getActivity(), android.R.layout.simple_list_item_1, null);
        msgList.setAdapter(adapter);
        msgList.setOnItemClickListener(this);
        return view;
    }

    private void getMsg() {
        final MyApp app = (MyApp) getActivity().getApplication();
        JsonArrayAuthRequest getProfileRequest = new JsonArrayAuthRequest(
                Request.Method.GET,
                Backend.url("/messages/"),
                app.getAuthProvider(),
                null,
                this, new ErrorListener(getActivity()));
        RequestQueue queue = app.getRequestQueue();
        queue.add(getProfileRequest);
    }

    public void onResponse(JSONArray array) {
        adapter.setJsonArray(array);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        /*try {
            Intent intent = new Intent(this, SessionDetailActivity.class);
            JSONObject object = adapter.getItem(i);
            Bundle b = Conversions.jsonToBundle(object);
            b.putString("apply", "no");
            intent.putExtras(b);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
}
