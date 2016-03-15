package it.artefedeacireale.api;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Response;

import it.artefedeacireale.api.models.Holiday;
import it.artefedeacireale.api.models.Quote;


/**
 * Created by davide on 14/03/16.
 */
public class OtherAPI {

    public void getQuote(Context context, Response.Listener<Quote[]> listener, Response.ErrorListener errorListener){
        String url = APIConstants.getBaseUrl();
        String uri = Uri.parse(url).buildUpon().appendPath("quote").build().toString();
        APIRequest<Quote[]> myReq = new APIRequest<Quote[]>(context, uri, Quote[].class, null, true, listener, errorListener);
        APIHandler.getInstance(context).addToRequestQueue(myReq);
    }

    public void getHolidays(Context context, Response.Listener<Holiday[]> listener, Response.ErrorListener errorListener){
        String url = APIConstants.getBaseUrl();
        String uri = Uri.parse(url).buildUpon().appendPath("festa").build().toString();
        APIRequest<Holiday[]> myReq = new APIRequest<Holiday[]>(context, uri, Holiday[].class, null, true, listener, errorListener);
        APIHandler.getInstance(context).addToRequestQueue(myReq);
    }
}
