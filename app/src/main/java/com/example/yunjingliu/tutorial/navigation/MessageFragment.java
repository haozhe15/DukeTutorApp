package com.example.yunjingliu.tutorial.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.example.yunjingliu.tutorial.helper_class.Backend;
import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.example.yunjingliu.tutorial.helper_class.MsgListAdapter;
import com.example.yunjingliu.tutorial.helper_class.MyApp;
import com.example.yunjingliu.tutorial.R;
import com.zr.auth.JsonArrayAuthRequest;

/**
 * Created by YunjingLiu on 4/8/18.
 */

public class MessageFragment extends Fragment implements AdapterView.OnItemClickListener{
    private MsgListAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, container, false);

        ListView msgList = (ListView) view.findViewById(R.id.lvMsg);
        listAdapter = new MsgListAdapter(getContext(), android.R.layout.simple_list_item_1, null);
        msgList.setAdapter(listAdapter);
        msgList.setOnItemClickListener(this);
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
                Backend.url("/messages/"),
                app.getAuthProvider(),
                null,
                listAdapter, new ErrorListener(getContext())));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        /*try {
            Intent intent = new Intent(this, SessionDetailActivity.class);
            JSONObject object = listAdapter.getItem(i);
            Bundle b = Conversions.jsonToBundle(object);
            b.putString("apply", "no");
            intent.putExtras(b);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
}
