package com.zr.auth;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rui on 3/31/18.
 */

/**
 * A JsonRequest that contains authorization information.
 */
public abstract class JsonAuthRequest<T> extends JsonRequest<T> {
    private final AuthProvider auth;
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
    public JsonAuthRequest(int method, String url, AuthProvider auth,
                           Object requestBody,
                           Response.Listener<T> listener,
                           Response.ErrorListener errorListener) {
        super(method, url, (requestBody == null) ? null : requestBody.toString(),
                listener, errorListener);
        this.auth = auth;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(parseJson(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        if (auth != null) {
            String authorization = auth.getAuthorization();
            if (authorization != null) {
                headers.put("Authorization", authorization);
            }
        }
        return headers;
    }

    /**
     * Whether T is JSONObject or JSONArray, the child class must
     * implement parseJson which simply returns a new T(jsonString).
     * This cannot be done in this class because Java does not
     * support initiating a type parameter.
     *
     * @param jsonString The string containing JSON data.
     * @return a JSONObject or JSONArray object parsed from jsonString.
     * @throws JSONException When the parsing fails.
     */
    protected abstract T parseJson(String jsonString) throws JSONException;
}