package com.zr.auth;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * Created by YunjingLiu on 4/10/18.
 */

public class JsonStringAuthRequest extends JsonAuthRequest<String> {
    /**
     * Constructor.
     *
     * @param method        The request method.
     * @param url           The URL of the request.
     * @param auth          The AuthProvider object (null for no authorization).
     * @param requestBody   The request body (null for no request body).
     * @param listener      The response listener.
     * @param errorListener The error listener.
     */
    public JsonStringAuthRequest(int method, String url, AuthProvider auth, Object requestBody, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, auth, requestBody, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String string = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(parseJson(string),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
    @Override
    protected String parseJson(String jsonString) {
        return jsonString;
    };
}
