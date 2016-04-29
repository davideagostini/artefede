package it.artefedeacireale.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.api.models.MarkerMaps;
import it.artefedeacireale.services.MapService;
import it.artefedeacireale.util.NetworkUtil;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private Intent intentService;
    private int id_church;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.mappa));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(MapService.ACTION_MAP);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);

        startDownloadData();
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void startDownloadData() {

        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {

            if (intentService != null) stopService(intentService);
            intentService = new Intent(this, MapService.class);
            intentService.setAction(MapService.ACTION_MAP);
            startService(intentService);

        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<MarkerMaps> maps = (ArrayList<MarkerMaps>) intent.getSerializableExtra("maps");
            setMarker(maps);
        }
    };

    private void setMarker(ArrayList<MarkerMaps> maps) {
        for (MarkerMaps m : maps) {
            LatLng lat_lng = new LatLng(Double.parseDouble(m.getLatitudine()), Double.parseDouble(m.getLongitudine()));
            mMap.addMarker(new MarkerOptions().position(lat_lng).title(m.getNome()).snippet(String.valueOf(m.getId())));
        }

        mMap.setInfoWindowAdapter(new MyInfoWindowAdaper(getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enabledMyLocation();

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(37.613137, 15.165795));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(getApplicationContext(), ChurchDetailActivity.class);
        intent.putExtra("id_church", id_church);
        intent.putExtra("name_church", marker.getTitle());
        startActivity(intent);
    }

    private void enabledMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Permission is missing and must be requested.
            requestLocationPermissions();
        }
    }

    private void requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_location_permission_title)
                    .setMessage(R.string.no_location_permission_message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            PERMISSION_ACCESS_FINE_LOCATION);
                                }
                            })
                    .create()
                    .show();


        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.no_location_permission_title)
                            .setMessage(R.string.no_location_permission_message)
                            .setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                            .create()
                            .show();

                }
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyInfoWindowAdaper implements GoogleMap.InfoWindowAdapter {

        private final View myContentView;

        MyInfoWindowAdaper(Context context) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myContentView = inflater.inflate(R.layout.map_info, null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            TextView title = ((TextView) myContentView.findViewById(R.id.church_name));
            title.setText(marker.getTitle());

            id_church = Integer.valueOf(marker.getSnippet());

            return myContentView;
        }

        @Override
        public View getInfoWindow(Marker arg0) {
            return null;
        }
    }
}
