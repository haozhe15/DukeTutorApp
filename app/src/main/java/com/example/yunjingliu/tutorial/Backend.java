package com.example.yunjingliu.tutorial;

/**
 * Created by rui on 3/15/18.
 */

class Backend {
    private static final String urlBase = "http://vcm-3307.vm.duke.edu:8000";

    public static String url(String path) {
        return urlBase + path;
    }
}
