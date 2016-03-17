package it.artefedeacireale.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import it.artefedeacireale.api.ChurchAPI;
import it.artefedeacireale.api.models.Artwork;


public class ArtworkDetailService extends IntentService {

    public static final String ACTION_ARTWORK_DETAIL = "it.artefedeacireale.services.ARTWORK_DETAIL";
    private static final String TAG = ArtworkDetailService.class.getSimpleName();
    private LocalBroadcastManager mLocalBroadcastManager;
    final ChurchAPI churchAPI = new ChurchAPI();

    public ArtworkDetailService() {
        super("ArtworkDetailService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        churchAPI.getArtwork(this, String.valueOf(intent.getIntExtra("id_artwork", -1)), new Response.Listener<Artwork>() {
            @Override
            public void onResponse(Artwork response) {
                Log.d(TAG, TAG + response.getNome() + " - " + response.getAnno());
                Intent intentResponse = new Intent();
                intentResponse.setAction(ACTION_ARTWORK_DETAIL);
                intentResponse.putExtra("artwork", response);
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