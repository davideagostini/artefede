package it.artefedeacireale.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Arrays;

import it.artefedeacireale.api.OtherAPI;
import it.artefedeacireale.api.models.Holiday;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class EventService extends IntentService {

    public static final String ACTION_EVENT = "it.artefedeacireale.services.EVENT";
    private static final String TAG = EventService.class.getSimpleName();
    private LocalBroadcastManager mLocalBroadcastManager;
    final OtherAPI otherAPI = new OtherAPI();

    public EventService() {
        super("EventService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        otherAPI.getHolidays(this, new Response.Listener<Holiday[]>() {
            @Override
            public void onResponse(Holiday[] response) {
                ArrayList<Holiday> holidays = new ArrayList<Holiday>(Arrays.asList(response));
                Intent intentResponse = new Intent();
                intentResponse.setAction(ACTION_EVENT);
                intentResponse.putExtra("holidays", holidays);
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