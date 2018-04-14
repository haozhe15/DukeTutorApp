package com.example.yunjingliu.tutorial;

import android.support.test.rule.ActivityTestRule;
import android.util.Patterns;
import android.widget.EditText;

import com.example.yunjingliu.tutorial.registration_and_login.RegistrationActivity;
import com.zr.forms.JsonForm;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegistrationActivityTest {
    @Rule
    public ActivityTestRule<RegistrationActivity> mActivityTestRule = new ActivityTestRule<RegistrationActivity>(RegistrationActivity.class);
    private RegistrationActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testUI() {
        // Test if all the EditText are launched
        assertNotNull(mActivity.findViewById(R.id.etUsername));
        assertNotNull(mActivity.findViewById(R.id.etFirstname));
        assertNotNull(mActivity.findViewById(R.id.etLastname));
        assertNotNull(mActivity.findViewById(R.id.etEmail));
        assertNotNull(mActivity.findViewById(R.id.etPassword));
    }

    @Test
    public void testInput(){
        // Test the format of user's input
        EditText etUsername = mActivity.findViewById(R.id.etUsername);
        etUsername.setText("Bili");
        String username = etUsername.getText().toString().trim();
        assertFalse(username.isEmpty() || username.length()>32);

        EditText etFirstname = mActivity.findViewById(R.id.etFirstname);
        etFirstname.setText("Tom");
        String firstname = etFirstname.getText().toString().trim();
        assertFalse(firstname.isEmpty() || firstname.length()>32);

        EditText etLastname = mActivity.findViewById(R.id.etLastname);
        etLastname.setText("Green");
        String lastname = etLastname.getText().toString().trim();
        assertFalse(lastname.isEmpty() || lastname.length()>32);

        EditText etEmail = mActivity.findViewById(R.id.etEmail);
        etEmail.setText("abc@duke.edu");
        String email = etEmail.getText().toString().trim();
        assertFalse(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches());

        EditText etPassword = mActivity.findViewById(R.id.etPassword);
        etPassword.setText("0");
        String password = etPassword.getText().toString().trim();
        assertTrue(password.isEmpty() || password.length()<8);
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}