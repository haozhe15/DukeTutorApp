package com.example.yunjingliu.tutorial;

/**
 * Created by rui on 3/13/18.
 */

/**
 * Provides the functionality to return an authorization string
 * that will be used in the header of HTTP requests.
 */

interface AuthProvider {
    /**
     * Returns a string that will be used as the value of
     * the "Authorization" header field.
     *
     * For example, if it returns "Basic xyzzy", then
     * the following line will be sent in HTTP header:
     *
     *     Authorization: Basic xyzzy
     *
     * @returns The authorization string.
     */
    String getAuthorization();
}