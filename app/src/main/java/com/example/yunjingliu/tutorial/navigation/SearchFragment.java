package com.example.yunjingliu.tutorial.navigation;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Request;
import com.example.yunjingliu.tutorial.R;
import com.example.yunjingliu.tutorial.SessionDetailActivity;
import com.example.yunjingliu.tutorial.helper_class.Backend;
import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.example.yunjingliu.tutorial.helper_class.MyApp;
import com.example.yunjingliu.tutorial.helper_class.SessionListAdapter;
import com.zr.auth.JsonArrayAuthRequest;
import com.zr.json.Conversions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {
    private SessionListAdapter sessionListAdapter;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Initialize the search result listview
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        ListView searchList = view.findViewById(R.id.lvSearchResult);
        sessionListAdapter = new SessionListAdapter(getActivity(), android.R.layout.simple_list_item_1, null);
        searchList.setAdapter(sessionListAdapter);
        searchList.setOnItemClickListener(this);
        return view;
    }


    public void getSearchResult(JSONObject kwJSON) {
        final MyApp app = (MyApp) getActivity().getApplication();
        app.addRequest(new JsonArrayAuthRequest(
                Request.Method.POST,
                Backend.url("/search/"),
                app.getAuthProvider(),
                kwJSON,
                sessionListAdapter, new ErrorListener(getActivity())));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            Intent intent = new Intent(getActivity(), SessionDetailActivity.class);
            JSONObject object = sessionListAdapter.getItem(i);
            Bundle b = Conversions.jsonToBundle(object);
            b.putBoolean("can_apply", true);
            intent.putExtras(b);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        HashMap<String, String> params = new HashMap<>();
        params.put("keyword", s);
        getSearchResult(new JSONObject(params));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
