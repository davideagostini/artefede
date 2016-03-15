package it.artefedeacireale.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import it.artefedeacireale.api.ChurchAPI;
import it.artefedeacireale.api.ItineraryAPI;
import it.artefedeacireale.api.models.Church;
import it.artefedeacireale.api.models.ItineraryDetail;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ChurchDetailService extends IntentService {

    public static final String ACTION_CHURCH_DETAIL = "it.artefedeacireale.services.CHURCH_DETAIL";
    private static final String TAG = ChurchDetailService.class.getSimpleName();
    private LocalBroadcastManager mLocalBroadcastManager;
    final ChurchAPI churchAPI = new ChurchAPI();

    public ChurchDetailService() {
        super("ChurchDetailService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        churchAPI.getChurch(this, String.valueOf(intent.getIntExtra("id_church", -1)), new Response.Listener<Church>() {
            @Override
            public void onResponse(Church response) {
                Intent intentResponse = new Intent();
                intentResponse.setAction(ACTION_CHURCH_DETAIL);
                intentResponse.putExtra("church", response);
                mLocalBroadcastManager.sendBroadcast(intentResponse);
                            /*Log.d(TAG, TAG + response.getNome() + " - " + response.getCitta());
                            ArrayList<ImageChurch> image_church = response.getImage_chiese();
                            ArrayList<Artwork> artworks = response.getOpere_chiese();
                            for (ImageChurch i_c : image_church)
                                Log.d(TAG, TAG + i_c.getImage());

                            for (Artwork a : artworks)
                                Log.d(TAG, TAG + a.getNome());*/


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

    }
}
