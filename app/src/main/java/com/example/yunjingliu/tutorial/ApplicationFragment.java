package com.example.yunjingliu.tutorial;


import android.content.Intent;
import android.os.Bundle;
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
import com.zr.json.Conversions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApplicationFragment extends Fragment implements Response.Listener<JSONArray>, AdapterView.OnItemClickListener{

    ApplicationListAdapter adapter;
    public ApplicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_application, container, false);
        getProfile();
        ListView sessionList = (ListView)view.findViewById(R.id.lvSessionApplied);
        adapter = new ApplicationListAdapter(getActivity(), android.R.layout.simple_list_item_1, null);
        sessionList.setAdapter(adapter);
        sessionList.setOnItemClickListener(this);

        return view;
    }
    private void getProfile() {
        final MyApp app = (MyApp) getActivity().getApplication();
        JsonArrayAuthRequest getProfileRequest = new JsonArrayAuthRequest(
                Request.Method.GET,
                Backend.url("/applications/"),
                app.getAuthProvider(),
                null,
                this, new ErrorListener(this.getActivity()));
        RequestQueue queue = app.getRequestQueue();
        queue.add(getProfileRequest);
    }

    public void onResponse(JSONArray array) {
        adapter.setJsonArray(array);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            Intent intent = new Intent(getActivity(), SessionDetailActivity.class);
            JSONObject object = adapter.getItem(i);
            Bundle b = Conversions.jsonToBundle(object);
            b.putString("apply", "no");
            intent.putExtras(b);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
