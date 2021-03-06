package com.example.yunjingliu.tutorial.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.example.yunjingliu.tutorial.R;
import com.example.yunjingliu.tutorial.helper_class.Backend;
import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.example.yunjingliu.tutorial.helper_class.MyApp;
import com.example.yunjingliu.tutorial.helper_class.TutorHistoryListAdapter;
import com.zr.auth.JsonArrayAuthRequest;
import com.zr.json.Conversions;

import org.json.JSONException;
import org.json.JSONObject;

public class TutorHistoryFragment extends Fragment implements AdapterView.OnItemClickListener{
    private TutorHistoryListAdapter listAdapter;

    public TutorHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutor_history, container, false);

        ListView sessionList = view.findViewById(R.id.lvSessionDone);
        listAdapter = new TutorHistoryListAdapter(getContext(), android.R.layout.simple_list_item_1, null);
        sessionList.setAdapter(listAdapter);
        sessionList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        makeRequest();
    }

    private void makeRequest() {
        MyApp app = (MyApp) getActivity().getApplication();
        app.addRequest(new JsonArrayAuthRequest(
                Request.Method.GET,
                Backend.url("/feedbacks/"),
                app.getAuthProvider(),
                null,
                listAdapter, new ErrorListener(getContext())));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            SessionDetailFragment sessionDetailFragment = new SessionDetailFragment();
            JSONObject object = listAdapter.getItem(i).getJSONObject("session");
            Bundle b = Conversions.jsonToBundle(object);
            sessionDetailFragment.setArguments(b);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().add(R.id.flContent, sessionDetailFragment, "sessionDetail").addToBackStack("sessionList").commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

