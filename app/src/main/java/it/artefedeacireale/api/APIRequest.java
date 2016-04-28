package it.artefedeacireale.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by davide on 14/03/16.
 */
public class APIRequest<T> extends Request<T> {
    private final Context context;
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final boolean authed;
    private Map<String, String> headers;
    private final Map<String, String> postParams;
    private final Response.Listener<T> listener;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers MarkerMaps of request headers
     */
    public APIRequest(Context context, String url, Class<T> clazz, Map<String, String> headers,
                      boolean authed,
                      Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.context = context;
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        this.authed = authed;
        postParams = null;
    }

    public APIRequest(Context context, String url, int method, Class<T> clazz, Map<String, String> headers, Map<String, String> params,
                      boolean authed,
                      Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.context = context;
        this.clazz = clazz;
        this.postParams = params;
        this.headers = headers;
        this.listener = listener;
        this.authed = authed;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        System.out.println(super.getHeaders().toString());
        if(authed){
            if(headers == null){
                headers = new HashMap<>();
            }
            headers.put("Authorization", "");
        }
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if(postParams != null){
            return postParams;
        }else {
            return super.getParams();
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            Log.d("API RESP HEADERS", response.headers + "");
            Log.d("API SENT HEADERS", this.headers+"");
            Log.d("API SENT DATA", this.postParams+"");
            String json = new String(response.data, "UTF-8");
            Log.d("API RESP DATA", json+"");
            return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
