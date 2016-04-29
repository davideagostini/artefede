package it.artefedeacireale;

import android.content.Context;
import android.support.multidex.MultiDex;

import it.artefedeacireale.api.APIConstants;

/**
 * Created by davide on 14/03/16.
 */
public class Application extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        APIConstants.debugApiType = APIConstants.DebugApi.full;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
