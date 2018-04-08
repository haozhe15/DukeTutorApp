package com.example.yunjingliu.tutorial;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.zr.auth.JsonArrayAuthRequest;
import com.zr.json.Conversions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Haozhe Wang on 3/30/18.
 */
public class SearchableActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, Response.Listener<JSONArray> {
    private SessionListAdapter sessionListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Initialize the search result listview
        ListView searchList = findViewById(R.id.lvSearchResult);
        sessionListAdapter = new SessionListAdapter(this, android.R.layout.simple_list_item_1, null);
        searchList.setAdapter(sessionListAdapter);
        searchList.setOnItemClickListener(this);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            // Do my search activity
            HashMap<String, String> params = new HashMap<>();
            params.put("keyword", query);
            getSearchResult(new JSONObject(params));
        }
    }

    public void getSearchResult(JSONObject kwJSON) {
        final MyApp app = (MyApp) getApplication();
        JsonArrayAuthRequest req = new JsonArrayAuthRequest(
                Request.Method.POST,
                Backend.url("/search/"),
                app.getAuthProvider(),
                kwJSON,
                this, new ErrorListener(this));
        app.getRequestQueue().add(req);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            Intent intent = new Intent(this, SessionDetailActivity.class);
            JSONObject object = sessionListAdapter.getItem(i);
            Bundle b = Conversions.jsonToBundle(object);
            b.putString("apply", "yes");
            intent.putExtras(b);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(JSONArray array) {
        sessionListAdapter.setJsonArray(array);
    }
}
