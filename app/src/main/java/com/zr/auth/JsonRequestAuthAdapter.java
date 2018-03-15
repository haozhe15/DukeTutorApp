package com.zr.auth;

import java.util.Map;

/**
 * Created by rui on 3/13/18.
 */

/**
 * An adapter for JsonRequest to use AuthProvider.
 */
class JsonRequestAuthAdapter {
    private AuthProvider auth;

    /**
     * Constructor.
     * @param auth The AuthProvider to be used for the request.
     */
    JsonRequestAuthAdapter(AuthProvider auth) {
        this.auth = auth;
    }

    /**
     * Updates the headers to include the authorization information.
     * It will set the "Authorization" field to the value that
     * the AuthProvider provides.
     * @param headers The map to be updated.
     */
    void updateHeaders(Map<String, String> headers) {
        if (auth == null) {
            return; // no authorization
        }
        String authorization = auth.getAuthorization();
        if (authorization != null) {
            headers.put("Authorization", authorization);
        }
    }
}
