package it.artefedeacireale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import it.artefedeacireale.activities.MainActivity;
import it.artefedeacireale.api.models.Itinerary;
import it.artefedeacireale.services.ItineraryService;
import it.artefedeacireale.util.NetworkUtil;

public class SplashScreen extends AppCompatActivity {

    private Intent itineraryIntentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startDownloadItineraries();
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(ItineraryService.ACTION_ITINERARY);
        LocalBroadcastManager.getInstance(this).registerReceiver(itineraryReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(itineraryReceiver);
    }

    private void startDownloadItineraries() {

        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {

            if (itineraryIntentService != null) stopService(itineraryIntentService);
            itineraryIntentService = new Intent(this, ItineraryService.class);
            itineraryIntentService.setAction(ItineraryService.ACTION_ITINERARY);
            startService(itineraryIntentService);

        }
    }

    private BroadcastReceiver itineraryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Itinerary> itineraries = (ArrayList<Itinerary>) intent.getSerializableExtra("itineraries");
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            i.putExtra("itineraries", itineraries);
            startActivity(i);
            finish();
        }
    };
}
