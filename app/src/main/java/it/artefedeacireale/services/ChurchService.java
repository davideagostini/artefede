package it.artefedeacireale.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import it.artefedeacireale.api.ItineraryAPI;
import it.artefedeacireale.api.models.ItineraryDetail;

public class ChurchService extends IntentService {

    public static final String ACTION_CHURCH_LIST = "it.artefedeacireale.services.CHURCH_LIST";
    private static final String TAG = ChurchService.class.getSimpleName();
    private LocalBroadcastManager mLocalBroadcastManager;
    final ItineraryAPI itineraryAPI = new ItineraryAPI();

    public ChurchService() {
        super("ChurchService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        itineraryAPI.getItinerary(this, String.valueOf(intent.getIntExtra("id_itinerary", -1)), new Response.Listener<ItineraryDetail>() {
            @Override
            public void onResponse(ItineraryDetail response) {
                Intent intentResponse2 = new Intent();
                intentResponse2.setAction(ACTION_CHURCH_LIST);
                intentResponse2.putExtra("itinerary_detail", response);
                mLocalBroadcastManager.sendBroadcast(intentResponse2);
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
