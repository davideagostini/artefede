package it.artefedeacireale.fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.activities.ChurchDetailActivity;
import it.artefedeacireale.api.models.MarkerMaps;
import it.artefedeacireale.services.MapService;
import it.artefedeacireale.util.NetworkUtil;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    private static final String TAG = MapsFragment.class.getSimpleName();
    private Intent intentService;
    private int id_church;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //startDownloadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(MapService.ACTION_MAP);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, filter);

        startDownloadData();
    }

    @Override
    public void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    private void startDownloadData() {

        if (new NetworkUtil().isNetworkConnected(getActivity())) {

            if (intentService != null) getActivity().stopService(intentService);
            intentService = new Intent(getActivity(), MapService.class);
            intentService.setAction(MapService.ACTION_MAP);
            getActivity().startService(intentService);

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

        mMap.setInfoWindowAdapter(new MyInfoWindowAdaper(getActivity()));
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
        Intent intent = new Intent(getActivity(), ChurchDetailActivity.class);
        intent.putExtra("id_church", id_church);
        intent.putExtra("name_church", marker.getTitle());
        startActivity(intent);
    }

    private void enabledMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.no_location_permission_title)
                    .setMessage(R.string.no_location_permission_message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
                                }
                            })
                    .create()
                    .show();

        } else
            mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                enabledMyLocation();
            else {
                new AlertDialog.Builder(getActivity())
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
