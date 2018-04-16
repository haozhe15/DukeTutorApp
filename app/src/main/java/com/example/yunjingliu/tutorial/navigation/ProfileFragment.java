package com.example.yunjingliu.tutorial.navigation;

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

import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.android.volley.Request;

import com.android.volley.Response;
import com.example.yunjingliu.tutorial.R;
import com.example.yunjingliu.tutorial.helper_class.Backend;
import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.example.yunjingliu.tutorial.helper_class.MyApp;

import com.zr.auth.JsonArrayAuthRequest;
import com.zr.json.Conversions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by YunjingLiu on 4/11/18.
 */

public class ProfileFragment extends Fragment implements Response.Listener<JSONArray>{
    View view;
    private ErrorListener errorListener;
    ArrayList<String> plist = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        errorListener = new ErrorListener(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView profileList = view.findViewById(R.id.lvProfile);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, plist);
        profileList.setAdapter(adapter);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        getProfile();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onEditClick();
        return true;
    }
    private void onEditClick(){
        MyApp app = (MyApp) getActivity().getApplication();
        ProfileEditFragment profileEditFragment = new ProfileEditFragment();
        profileEditFragment.setArguments(app.getUserInfo());
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.flContent, profileEditFragment, "profileEdit").addToBackStack("profile").commit();
    }

    private void getProfile() {
        final MyApp app = (MyApp) getActivity().getApplication();
        app.addRequest(new JsonArrayAuthRequest(
                Request.Method.GET,
                Backend.url("/users/"),
                app.getAuthProvider(),
                null,
                this, errorListener));

    }
    @Override
    public void onResponse(JSONArray response) {
        try {
            MyApp app = (MyApp) getActivity().getApplication();
            plist.clear();
            JSONObject response1 = response.getJSONObject(0);
            app.setUserInfo(Conversions.jsonToBundle(response1));
            plist.add("Username: "+response1.getString("username"));
            plist.add("Email: "+response1.getString("email"));
            plist.add("First Name: "+response1.getString("first_name"));
            plist.add("Last Name: "+response1.getString("last_name"));
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
