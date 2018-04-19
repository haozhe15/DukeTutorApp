package com.example.yunjingliu.tutorial;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.util.Patterns;
import android.widget.EditText;

import com.example.yunjingliu.tutorial.registration_and_login.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class LoginActivityTest {
    private EditText etUsername, etPassword;

    // Set up the test inputs
    private static final String USERNAME = "Bili";
    private static final String PASSWORD = "09098989";


    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);
    private LoginActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

        etUsername = (EditText)mActivity.findViewById(R.id.etUsername);
        etPassword = (EditText) mActivity.findViewById(R.id.etPassword);
    }

    @Test
    public void testUI() {
        // Test if all the UI components are launched
        assertNotNull(mActivity.findViewById(R.id.etUsername));
        assertNotNull(mActivity.findViewById(R.id.etPassword));
        assertNotNull(mActivity.findViewById(R.id.tvRegisterHere));
        assertNotNull(mActivity.findViewById(R.id.btLogin));
    }

    @Test
    public void testInput(){
        // Test the format of user's input
        // Types a message into a EditText element.
        onView(withId(R.id.etUsername))
                .perform(typeText(USERNAME), closeSoftKeyboard());
        onView(withId(R.id.etPassword))
                .perform(typeText(PASSWORD),closeSoftKeyboard());
        // Do the validation test
        assertTrue(validate());

    }

    public boolean validate() {
        boolean valid = true;
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || username.length() > 32) {
            valid = false;
        }
        if (password.isEmpty() || password.length() < 8) {
            valid = false;
        }

        return valid;
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}

