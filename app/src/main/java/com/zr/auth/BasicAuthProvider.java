package com.zr.auth;

import android.util.Base64;

/**
 * Created by YunjingLiu on 3/4/18.
 */

/**
 * An AuthProvider that implements the basic authorization
 * conforming to RFC 2617.
 */
public class BasicAuthProvider implements AuthProvider {
    /**
     * The authorization string.
     * It contains the word "Basic" and a base64-encoded string
     * containing username and password information.
     */
    private final String authorization;

    /**
     * Constructor.
     *
     * @param username The username for authorization.
     * @param password The password for authorization.
     */
    public BasicAuthProvider(String username, String password) {
        // HTTP Basic authentication. See:
        // https://tools.ietf.org/html/rfc2617#section-2
        String userpass = username + ":" + password;
        authorization = "Basic " + Base64.encodeToString(userpass.getBytes(), Base64.NO_WRAP);
    }

    @Override
    public String getAuthorization() {
        return authorization;
    }
}
