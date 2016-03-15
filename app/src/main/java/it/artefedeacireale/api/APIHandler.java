package it.artefedeacireale.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by davide on 13/03/16.
 */
public class APIHandler {
    private static APIHandler mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private APIHandler(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized APIHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new APIHandler(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        Log.d("API", "adding request to queue with URL " + req.getUrl());
        getRequestQueue().add(req);
    }
}