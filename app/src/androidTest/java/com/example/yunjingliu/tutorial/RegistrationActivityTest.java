package com.example.yunjingliu.tutorial;

import android.support.test.rule.ActivityTestRule;
import android.util.Patterns;
import android.widget.EditText;

import com.example.yunjingliu.tutorial.registration_and_login.RegistrationActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RegistrationActivityTest {
    private EditText etUsername, etPassword, etEmail, etFirstname, etLastname;

    // Set up the test inputs
    private static final String USERNAME = "Bili";
    private static final String PASSWORD = "09098989";
    private static final String EMAIL = "Test@duke.edu";
    private static final String FIRSTNAME = "Tom";
    private static final String LASTNAME = "WANG";

    @Rule
    public ActivityTestRule<RegistrationActivity> mActivityTestRule = new ActivityTestRule<>(RegistrationActivity.class);
    private RegistrationActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

        etUsername = (EditText)mActivity.findViewById(R.id.etUsername);
        etPassword = (EditText) mActivity.findViewById(R.id.etPassword);
        etEmail = (EditText) mActivity.findViewById(R.id.etEmail);
        etFirstname = (EditText) mActivity.findViewById(R.id.etFirstname);
        etLastname = (EditText) mActivity.findViewById(R.id.etLastname);
    }

    @Test
    public void testUI() {
        // Test if all the EditText are launched
        assertNotNull(etUsername);
        assertNotNull(etFirstname);
        assertNotNull(etLastname);
        assertNotNull(etEmail);
        assertNotNull(etPassword);
    }

    @Test
    public void testInput(){
        // Test the format of user's input

        // Types a message into a EditText element.
        onView(withId(R.id.etUsername))
                .perform(typeText(USERNAME), closeSoftKeyboard());
        onView(withId(R.id.etFirstname))
                .perform(typeText(FIRSTNAME), closeSoftKeyboard());
        onView(withId(R.id.etLastname))
                .perform(typeText(LASTNAME),closeSoftKeyboard());
        onView(withId(R.id.etEmail))
                .perform(typeText(EMAIL),closeSoftKeyboard());
        onView(withId(R.id.etPassword))
                .perform(typeText(PASSWORD),closeSoftKeyboard());

        // Do the validation test
        assertTrue(validate());

    }

    public boolean validate() {
        boolean valid = true;
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String first_name = etFirstname.getText().toString().trim();
        String last_name = etLastname.getText().toString().trim();

        if (username.isEmpty() || username.length() > 32) {
            valid = false;
        }
        if (password.isEmpty() || password.length() < 8) {
            valid = false;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
        }
        if (first_name.isEmpty() || first_name.length() > 32) {
            valid = false;
        }
        if (last_name.isEmpty() || last_name.length() > 32) {
            valid = false;
        }
        return valid;
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}