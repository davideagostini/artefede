package it.artefedeacireale.api;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Response;

import it.artefedeacireale.api.models.Itinerary;
import it.artefedeacireale.api.models.ItineraryDetail;


/**
 * Created by davide on 14/03/16.
 */
public class ItineraryAPI {

    public void getItineraries(Context context, Response.Listener<Itinerary[]> listener, Response.ErrorListener errorListener){
        String url = APIConstants.getBaseUrl();
        String uri = Uri.parse(url).buildUpon().appendPath("itinerario").build().toString();
        APIRequest<Itinerary[]> myReq = new APIRequest<Itinerary[]>(context, uri, Itinerary[].class, null, true, listener, errorListener);
        APIHandler.getInstance(context).addToRequestQueue(myReq);
    }

    public void getItinerary(Context context, String id_itinerario, Response.Listener<ItineraryDetail> listener, Response.ErrorListener errorListener){
        String url = APIConstants.getBaseUrl();
        String uri = Uri.parse(url).buildUpon().appendPath("itinerario").appendPath(id_itinerario).build().toString();
        APIRequest<ItineraryDetail> myReq = new APIRequest<ItineraryDetail>(context, uri, ItineraryDetail.class, null, true, listener, errorListener);
        APIHandler.getInstance(context).addToRequestQueue(myReq);
    }
}
