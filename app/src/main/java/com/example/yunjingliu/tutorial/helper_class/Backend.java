package com.example.yunjingliu.tutorial.helper_class;

/**
 * Created by rui on 3/15/18.
 */

public class Backend {
    private static final String urlBase = "http://vcm-3307.vm.duke.edu:8000";

    public static String url(String path) {
        return urlBase + path;
    }
}
