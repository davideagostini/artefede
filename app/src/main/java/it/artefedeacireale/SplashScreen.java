package it.artefedeacireale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import it.artefedeacireale.activities.MainActivity;
import it.artefedeacireale.api.models.Itinerary;
import it.artefedeacireale.services.ItineraryService;
import it.artefedeacireale.util.NetworkUtil;

public class SplashScreen extends AppCompatActivity {

    private Intent itineraryIntentService;
    private LinearLayout noConnection;
    private LinearLayout linearLayout;
    private ProgressBar progressBar2;
    private boolean connectivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        noConnection = (LinearLayout) findViewById(R.id.no_connection);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        startDownloadItineraries();

        noConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressBar2.getVisibility() == View.GONE && connectivity) progressBar2.setVisibility(View.VISIBLE);
                if (itineraryIntentService != null) stopService(itineraryIntentService);
                startDownloadItineraries();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(ItineraryService.ACTION_ITINERARY);
        LocalBroadcastManager.getInstance(this).registerReceiver(itineraryReceiver, filter);
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(itineraryReceiver);
        unregisterReceiver(networkReceiver);
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

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION) && !NetworkUtil.isNetworkConnected(context)) {
                if(noConnection.getVisibility() == View.GONE) noConnection.setVisibility(View.VISIBLE);
                if(linearLayout.getVisibility() == View.VISIBLE)linearLayout.setVisibility(View.GONE);
            } else {
                connectivity = true;
            }
        }
    };


}
