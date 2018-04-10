package com.example.yunjingliu.tutorial.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.yunjingliu.tutorial.R;
import com.example.yunjingliu.tutorial.helper_class.Backend;
import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.example.yunjingliu.tutorial.helper_class.MyApp;
import com.zr.auth.JsonObjectAuthRequest;
import com.zr.forms.EditTextAdapter;
import com.zr.forms.JsonForm;
import com.zr.forms.SpinnerAdapter;

import org.json.JSONObject;

/**
 * Created by YunjingLiu on 4/10/18.
 */

public class SessionPostFragment extends Fragment {
    private static final String[] dayChoices = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private JsonForm form;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_post_session, container, false);
        form = new JsonForm(getActivity(), new ErrorListener(getContext()));
        form.put("title", new EditTextAdapter(view.findViewById(R.id.etTitle)));
        form.put("description", new EditTextAdapter(view.findViewById(R.id.etDescription)));
        form.put("time", new EditTextAdapter(view.findViewById(R.id.etTimechoose)));
        form.put("place", new EditTextAdapter(view.findViewById(R.id.etLocationinput)));
        form.put("day", new SpinnerAdapter(view.findViewById(R.id.spDatechoose), dayChoices));

        Bundle extras = getArguments();
        if (extras != null) {
            form.setBundle(extras);
        }

        final Spinner spinner = view.findViewById(R.id.spDatechoose);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.week_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        final Button submit = view.findViewById(R.id.btSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubmit(view);
            }
        });
        return view;
    }

    public void onClickSubmit(View view) {
        final MyApp app = (MyApp) getActivity().getApplication();
        int method;
        String url = null;
        Bundle b = getArguments();
        if(b!=null) {
            url = b.getString("url");
        }
        if (url != null) {
            method = Request.Method.PUT;
        } else {
            method = Request.Method.POST;
            url = Backend.url("/sessions/");
        }
        app.addRequest(new JsonObjectAuthRequest(
                method,
                url,
                app.getAuthProvider(),
                form.getJson(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getActivity().getSupportFragmentManager().popBackStack("sessionList", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                },
                form));

    }
}
