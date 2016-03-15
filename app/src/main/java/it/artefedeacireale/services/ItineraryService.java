package it.artefedeacireale.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Arrays;

import it.artefedeacireale.api.ChurchAPI;
import it.artefedeacireale.api.ItineraryAPI;
import it.artefedeacireale.api.models.Church;
import it.artefedeacireale.api.models.Itinerary;
import it.artefedeacireale.api.models.ItineraryDetail;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ItineraryService extends IntentService {

    public static final String ACTION_ITINERARY = "it.artefedeacireale.services.ITINERARY";
    public static final String ACTION_CHURCH_DETAIL = "it.artefedeacireale.services.CHURCH_DETAIL";
    private static final String TAG = ItineraryService.class.getSimpleName();
    private LocalBroadcastManager mLocalBroadcastManager;
    final ItineraryAPI itineraryAPI = new ItineraryAPI();

    public ItineraryService() {
        super("ItineraryService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        itineraryAPI.getItineraries(this, new Response.Listener<Itinerary[]>() {
            @Override
            public void onResponse(Itinerary[] response) {
                ArrayList<Itinerary> itineraries = new ArrayList<Itinerary>(Arrays.asList(response));
                Intent intentResponse = new Intent();
                intentResponse.setAction(ACTION_ITINERARY);
                intentResponse.putExtra("itineraries", itineraries);
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

/*
churchAPI.getChurch(this, "1", new Response.Listener<Church>() {
            @Override
            public void onResponse(Church response) {
                Log.d(TAG, TAG + response.getNome() + " - " + response.getCitta());
                ArrayList<ImageChurch> image_church = response.getImage_chiese();
                ArrayList<Artwork> artworks = response.getOpere_chiese();
                for (ImageChurch i_c : image_church)
                    Log.d(TAG, TAG + i_c.getImage());

                for (Artwork a : artworks)
                    Log.d(TAG, TAG + a.getNome());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });


        churchAPI.getArtwork(this, "1", new Response.Listener<Artwork>() {
            @Override
            public void onResponse(Artwork response) {
                Log.d(TAG, TAG + response.getNome() + " - " + response.getAnno());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });


        otherAPI.getQuote(this, new Response.Listener<Quote[]>() {
            @Override
            public void onResponse(Quote[] response) {
                Log.d(TAG, TAG + response[0].getFrase() + " - " + response[0].getAutore());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

otherAPI.getHolidays(this, new Response.Listener<Holiday[]>() {
@Override
public void onResponse(Holiday[] response) {
        List<Holiday> holidays = Arrays.asList(response);
        for (Holiday h : holidays)
        Log.d(TAG, TAG + h.getTitolo());

        }
        }, new Response.ErrorListener() {
@Override
public void onErrorResponse(VolleyError error) {
        System.out.println(error);
        }
        });
 */
