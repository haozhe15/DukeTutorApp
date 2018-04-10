package com.example.yunjingliu.tutorial.navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.yunjingliu.tutorial.R;
import com.example.yunjingliu.tutorial.TutorSessionPostActivity;
import com.example.yunjingliu.tutorial.helper_class.Backend;
import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.example.yunjingliu.tutorial.helper_class.MyApp;
import com.zr.auth.JsonObjectAuthRequest;
import com.zr.json.Conversions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YunjingLiu on 4/8/18.
 */

public class SessionDetailFragment extends Fragment implements Response.Listener<JSONObject>{
    private final ErrorListener errorListener = new ErrorListener(getActivity());
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_session_detail, container, false);
        requestDetail();
        updateDetail(this.getArguments());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MyApp app = (MyApp) getActivity().getApplication();
        Bundle temp = this.getArguments();
        String tutor = temp.getString("tutor");
        if (app.isCurrentUser(tutor)) {
            inflater.inflate(R.menu.session_detail_menu, menu);

        } else if (temp.getBoolean("can_apply")) {
            inflater.inflate(R.menu.session_detail_apply_menu, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_option_edit:
                onEditClick();
                break;
            case R.id.menu_option_apply:
                onApplyClick();
                break;
        }
        return true;
    }




    public void requestDetail() {
        final MyApp app = (MyApp) getActivity().getApplication();
        app.addRequest(new JsonObjectAuthRequest(
                Request.Method.GET,
                this.getArguments().getString("url"),
                app.getAuthProvider(),
                null,
                this, errorListener));
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            Bundle bundle = Conversions.jsonToBundle(response);
            getActivity().getIntent().putExtras(bundle);
            updateDetail(bundle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateDetail(Bundle bundle) {
        // TODO nicer appearance?
        StringBuilder b = new StringBuilder();
        final String[] fields = {"title", "description", "day", "time", "place"};
        for (String f : fields) {
            b.append(f).append(": ").append(bundle.getString(f)).append('\n');
        }
        TextView sessionDetail = view.findViewById(R.id.tvSessionDetail);
        sessionDetail.setText(b);
    }

    private void onEditClick() {
        Intent intent = new Intent(getActivity(), TutorSessionPostActivity.class);
        intent.putExtras(getActivity().getIntent());
        startActivity(intent);
    }

    private void onApplyClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Apply to this session?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        doApply();
                    }
                })
                .setNegativeButton("No", null);
        builder.show();
    }

    private void doApply() {
        try {
            final MyApp app = (MyApp) getActivity().getApplication();
            JSONObject requestBody = new JSONObject();
            requestBody.put("session", getActivity().getIntent().getStringExtra("url"));
            app.addRequest(new JsonObjectAuthRequest(
                    Request.Method.POST,
                    Backend.url("/applications/"),
                    app.getAuthProvider(),
                    requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            onApplyResponse(response);
                        }
                    }, errorListener));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onApplyResponse(JSONObject response) {
        Toast toast = Toast.makeText(getActivity(), "Application sent", Toast.LENGTH_SHORT);
        toast.show();
    }
}