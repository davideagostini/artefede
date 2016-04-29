package it.artefedeacireale;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import it.artefedeacireale.activities.ChurchListActivity;
import it.artefedeacireale.activities.CreditsActivity;
import it.artefedeacireale.activities.GalleryActivity;
import it.artefedeacireale.activities.HolidayActivity;
import it.artefedeacireale.activities.InfoActivity;
import it.artefedeacireale.activities.MapsActivity;
import it.artefedeacireale.adapters.ItineraryListAdapter;
import it.artefedeacireale.api.models.Itinerary;
import it.artefedeacireale.services.ItineraryService;
import it.artefedeacireale.util.NetworkUtil;
import it.artefedeacireale.util.RecyclerViewClickListener;

public class MainActivity extends AppCompatActivity {

    private Intent itineraryIntentService;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ItineraryListAdapter mItineraryListAdapter;
    private Drawer drawer;
    private ProgressBar progressBar;

    private static final int PERMISSION_ACCESS_CALL_PHONE = 1;
    private static final int PERMISSION_ACCESS_WRITE_EXTERNAL_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.itinerari));
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        startDownloadItineraries();

        isPermissionsWriteGranted();
        isPermissionsPhoneGranted();

        final PrimaryDrawerItem itemItinerary = new PrimaryDrawerItem().withName(R.string.itinerari).withIcon(R.mipmap.ic_map_black_24dp).withSelectable(false);
        final PrimaryDrawerItem itemMap = new PrimaryDrawerItem().withName(R.string.mappa).withIcon(R.mipmap.ic_place_black_24dp).withSelectable(false);
        final PrimaryDrawerItem itemFotoVideo = new PrimaryDrawerItem().withName(R.string.foto_video).withIcon(R.mipmap.ic_photo_library_black_24dp).withSelectable(false);
        final PrimaryDrawerItem itemEvent = new PrimaryDrawerItem().withName(R.string.eventi).withIcon(R.mipmap.ic_event_black_24dp).withSelectable(false);
        final PrimaryDrawerItem itemInfo = new PrimaryDrawerItem().withName(R.string.info).withIcon(R.mipmap.ic_email_black_24dp).withSelectable(false);
        final PrimaryDrawerItem itemCredits = new PrimaryDrawerItem().withName(R.string.credits).withIcon(R.mipmap.ic_info_outline_black_24dp).withSelectable(false);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.side_nav_bar)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(
                        new ProfileDrawerItem().withName(getResources().getString(R.string.app_name))
                                .withEmail(getResources().getString(R.string.diocesi))
                                .withIcon(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_launcher))
                )
                .build();

        drawer = new DrawerBuilder()
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withAccountHeader(new AccountHeaderBuilder()
                                .withActivity(this)
                                .withHeaderBackground(R.drawable.side_nav_bar)
                                .build()
                )
                .withAccountHeader(headerResult)
                .addDrawerItems(
                    itemItinerary,
                    itemMap,
                    itemFotoVideo,
                    itemEvent,
                    itemInfo,
                    itemCredits
                )
                .withSelectedItem(-1)
                .withSavedInstance(savedInstanceState)
                .withActionBarDrawerToggle(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem.equals(itemEvent)) openHolyday();
                        else if(drawerItem.equals(itemMap)) openMap();
                        else if(drawerItem.equals(itemFotoVideo)) openGallery();
                        else if(drawerItem.equals(itemInfo)) openInfo();
                        else if (drawerItem.equals(itemCredits)) openCredits();


                        return false;
                    }
                })
                .build();

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mItineraryListAdapter = new ItineraryListAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mItineraryListAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getApplicationContext(), mRecyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(), ChurchListActivity.class);
                    Itinerary itinerary = mItineraryListAdapter.get(position);
                    intent.putExtra("id_itinerary", itinerary.getId());
                    intent.putExtra("name_itinerary", itinerary.getNome());
                    intent.putExtra("time_itinerary", itinerary.getTempo());
                    startActivity(intent);
                }

            }

            @Override
            public void onItemLongPress(View view, int position) {
            }
        }));

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

    public void openMap() {
        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
    }

    public void openGallery() {
        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
            Intent intent = new Intent(this, GalleryActivity.class);
            startActivity(intent);
        }
    }

    public void openHolyday() {
        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
            Intent intent = new Intent(this, HolidayActivity.class);
            startActivity(intent);
        }
    }

    public void openInfo() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    public void openCredits() {
        Intent intent = new Intent(this, CreditsActivity.class);
        startActivity(intent);
    }

    private BroadcastReceiver itineraryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Itinerary> itineraries = (ArrayList<Itinerary>) intent.getSerializableExtra("itineraries");
            mItineraryListAdapter.setItineraries(itineraries);
            hideProgressBar();

        }
    };

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    public void isPermissionsWriteGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


            }
            else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.no_write_permission_title)
                        .setMessage(R.string.no_write_permission_message)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_ACCESS_WRITE_EXTERNAL_STORAGE);
                                    }
                                })
                        .create()
                        .show();

            }
        }
    }

    public void isPermissionsPhoneGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {


            }
            else if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.no_phone_permission_title)
                        .setMessage(R.string.no_phone_permission_message)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_ACCESS_CALL_PHONE);
                                    }
                                })
                        .create()
                        .show();

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.no_write_permission_title)
                            .setMessage(R.string.no_write_permission_message)
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

            case PERMISSION_ACCESS_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.no_phone_permission_title)
                            .setMessage(R.string.no_phone_permission_message)
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



}
