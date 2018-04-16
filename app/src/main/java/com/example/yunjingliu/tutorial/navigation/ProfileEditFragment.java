package com.example.yunjingliu.tutorial.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.yunjingliu.tutorial.R;

/**
 * Created by YunjingLiu on 4/15/18.
 */

public class ProfileEditFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        EditText username = (EditText)view.findViewById(R.id.etUsername);
        EditText email = (EditText)view.findViewById(R.id.etEmail);
        EditText firstname = (EditText)view.findViewById(R.id.etFirstname);
        EditText lastname = (EditText)view.findViewById(R.id.etLastname);
        username.setText(getArguments().getString("username"));
        email.setText(getArguments().getString("email"));
        firstname.setText(getArguments().getString("firstname"));
        lastname.setText(getArguments().getString("lastname"));
        return view;
    }
}
