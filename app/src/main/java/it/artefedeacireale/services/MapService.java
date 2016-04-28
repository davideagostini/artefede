package it.artefedeacireale.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Arrays;

import it.artefedeacireale.api.OtherAPI;
import it.artefedeacireale.api.models.MarkerMaps;

public class MapService extends IntentService {

    public static final String ACTION_MAP = "it.artefedeacireale.services.MAP";
    private static final String TAG = MapService.class.getSimpleName();
    private LocalBroadcastManager mLocalBroadcastManager;
    final OtherAPI otherAPI = new OtherAPI();

    public MapService() {
        super("MapService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        otherAPI.getMap(this, new Response.Listener<MarkerMaps[]>() {
            @Override
            public void onResponse(MarkerMaps[] response) {
                ArrayList<MarkerMaps> maps = new ArrayList<MarkerMaps>(Arrays.asList(response));
                Intent intentResponse = new Intent();
                intentResponse.setAction(ACTION_MAP);
                intentResponse.putExtra("maps", maps);
                mLocalBroadcastManager.sendBroadcast(intentResponse);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
    }
}