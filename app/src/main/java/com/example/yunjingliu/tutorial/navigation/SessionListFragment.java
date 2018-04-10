package com.example.yunjingliu.tutorial.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.example.yunjingliu.tutorial.TutorSessionPostActivity;
import com.example.yunjingliu.tutorial.helper_class.Backend;
import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.example.yunjingliu.tutorial.helper_class.MyApp;
import com.example.yunjingliu.tutorial.R;
import com.example.yunjingliu.tutorial.SessionDetailActivity;
import com.example.yunjingliu.tutorial.helper_class.SessionListAdapter;
import com.zr.auth.JsonArrayAuthRequest;
import com.zr.json.Conversions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YunjingLiu on 4/8/18.
 */

public class SessionListFragment extends Fragment implements Response.Listener<JSONArray>, AdapterView.OnItemClickListener{
    private SessionListAdapter sessionListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.session_list_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getActivity(), TutorSessionPostActivity.class);
        startActivity(intent);
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();
        getProfile();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_session_list, container, false);
        ListView sessionList = view.findViewById(R.id.lvSessionPosted);
        sessionListAdapter = new SessionListAdapter(getActivity(), android.R.layout.simple_list_item_1, null);
        sessionList.setAdapter(sessionListAdapter);
        sessionList.setOnItemClickListener(this);
        return view;
    }

    private void getProfile() {
        final MyApp app = (MyApp) getActivity().getApplication();
        JsonArrayAuthRequest getProfileRequest = new JsonArrayAuthRequest(
                Request.Method.GET,
                Backend.url("/sessions/"),
                app.getAuthProvider(),
                null,
                this, new ErrorListener(getActivity()));
        RequestQueue queue = app.getRequestQueue();
        queue.add(getProfileRequest);
    }

    public void onResponse(JSONArray array) {
        sessionListAdapter.setJsonArray(array);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            SessionDetailFragment sessionDetailFragment = new SessionDetailFragment();
            JSONObject object = sessionListAdapter.getItem(i);
            Bundle b = Conversions.jsonToBundle(object);
            sessionDetailFragment.setArguments(b);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().add(R.id.flContent, sessionDetailFragment, "sessionDetail").addToBackStack("sessionList").commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
