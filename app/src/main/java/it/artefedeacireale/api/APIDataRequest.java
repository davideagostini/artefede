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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by davide on 13/03/16.
 */
public class APIDataRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final boolean authed;
    private Map<String, String> headers;
    private final Map<String, String> postParams;
    private final Response.Listener<T> listener;
    private final Context context;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers MarkerMaps of request headers
     */
    public APIDataRequest(Context context, String url, Class<T> clazz, Map<String, String> headers,
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

    public APIDataRequest(Context context, String url, int method, Class<T> clazz, Map<String, String> headers, Map<String, String> params,
                          boolean authed,
                          Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.context = context;
        this.clazz = clazz;
        this.postParams = params;
        this.headers = headers;
        this.authed = authed;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
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
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            String responseStr = new String(response.data);
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(responseStr).getAsJsonObject();
            T data = gson.fromJson(jsonObject.get("results"), clazz);

            //String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            //return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));

            if(APIConstants.debugApiType == APIConstants.DebugApi.full) {
                Log.d("API RESP HEADERS", response.headers + "");
                Log.d("API SENT HEADERS", this.headers + "");
                Log.d("API SENT DATA", this.postParams + "");
                Log.d("API RESP DATA", data+"");
            }else if(APIConstants.debugApiType == APIConstants.DebugApi.response_only){
                Log.d("API RESP DATA", data+"");
            }

            return Response.success(data,HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}