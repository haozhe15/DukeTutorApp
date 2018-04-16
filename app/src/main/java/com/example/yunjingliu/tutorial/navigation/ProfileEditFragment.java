package com.example.yunjingliu.tutorial.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.yunjingliu.tutorial.R;
import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.example.yunjingliu.tutorial.helper_class.MyApp;
import com.zr.auth.JsonObjectAuthRequest;
import com.zr.forms.EditTextAdapter;
import com.zr.forms.JsonForm;
import com.zr.json.Conversions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YunjingLiu on 4/15/18.
 */

public class ProfileEditFragment extends Fragment {
    final JsonForm form = new JsonForm(getActivity(), new ErrorListener(getContext()));


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu_finish, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final MyApp app = (MyApp) getActivity().getApplication();
        app.addRequest(new JsonObjectAuthRequest(
                    Request.Method.PATCH,
                    getArguments().getString("url"),
                    app.getAuthProvider(),
                    form.getJson(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            FragmentActivity activity = getActivity();
                            MyApp app = (MyApp) activity.getApplication();

                            try {
                                app.setUserInfo(Conversions.jsonToBundle(response));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            activity.getSupportFragmentManager().popBackStack();
                        }
                    },
                    form));
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        EditText username = view.findViewById(R.id.etUsername);
        EditText email = view.findViewById(R.id.etEmail);
        EditText firstname = view.findViewById(R.id.etFirstname);
        EditText lastname = view.findViewById(R.id.etLastname);

        form.put("username", new EditTextAdapter(username));
        form.put("email", new EditTextAdapter(email));
        form.put("first_name", new EditTextAdapter(firstname));
        form.put("last_name", new EditTextAdapter(lastname));

        form.setBundle(getArguments());
        return view;
    }
}
