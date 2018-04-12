package com.example.yunjingliu.tutorial;

import android.support.test.rule.ActivityTestRule;

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
    public void testLaunch() {
        // Test if all the EditText are launched
        assertNotNull(mActivity.findViewById(R.id.etUsername));
        assertNotNull(mActivity.findViewById(R.id.etFirstname));
        assertNotNull(mActivity.findViewById(R.id.etLastname));
        assertNotNull(mActivity.findViewById(R.id.etEmail));
        assertNotNull(mActivity.findViewById(R.id.etPassword));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}