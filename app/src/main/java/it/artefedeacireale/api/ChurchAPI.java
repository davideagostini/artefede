package it.artefedeacireale.api;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Response;

import it.artefedeacireale.api.models.Artwork;
import it.artefedeacireale.api.models.Church;


/**
 * Created by davide on 14/03/16.
 */
public class ChurchAPI {

    public void getChurch(Context context, String id_church, Response.Listener<Church> listener, Response.ErrorListener errorListener){
        String url = APIConstants.getBaseUrl();
        String uri = Uri.parse(url).buildUpon().appendPath("chiesa").appendPath(id_church).build().toString();
        APIRequest<Church> myReq = new APIRequest<Church>(context, uri, Church.class, null, true, listener, errorListener);
        APIHandler.getInstance(context).addToRequestQueue(myReq);
    }

    public void getArtwork(Context context, String id_artwork, Response.Listener<Artwork> listener, Response.ErrorListener errorListener){
        String url = APIConstants.getBaseUrl();
        String uri = Uri.parse(url).buildUpon().appendPath("opera").appendPath(id_artwork).build().toString();
        APIRequest<Artwork> myReq = new APIRequest<Artwork>(context, uri, Artwork.class, null, true, listener, errorListener);
        APIHandler.getInstance(context).addToRequestQueue(myReq);
    }
}
