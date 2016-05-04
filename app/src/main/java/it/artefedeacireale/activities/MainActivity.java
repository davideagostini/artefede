package it.artefedeacireale.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.api.models.Itinerary;
import it.artefedeacireale.fragments.CreditsFragment;
import it.artefedeacireale.fragments.GalleryFragment;
import it.artefedeacireale.fragments.HolidayFragment;
import it.artefedeacireale.fragments.InfoFragment;
import it.artefedeacireale.fragments.ItineraryFragment;
import it.artefedeacireale.fragments.MapsFragment;
import it.artefedeacireale.util.NetworkUtil;

public class MainActivity extends AppCompatActivity {

    private Drawer drawer;
    private Toolbar toolbar;
    private static final int PERMISSION_ACCESS_WRITE_EXTERNAL_STORAGE = 2;
    final PrimaryDrawerItem itemItinerary = new PrimaryDrawerItem().withName(R.string.itinerari).withIcon(R.mipmap.ic_map_black_24dp);
    final PrimaryDrawerItem itemMap = new PrimaryDrawerItem().withName(R.string.mappa).withIcon(R.mipmap.ic_place_black_24dp);
    final PrimaryDrawerItem itemFotoVideo = new PrimaryDrawerItem().withName(R.string.foto_video).withIcon(R.mipmap.ic_photo_library_black_24dp);
    final PrimaryDrawerItem itemEvent = new PrimaryDrawerItem().withName(R.string.eventi).withIcon(R.mipmap.ic_event_black_24dp);
    final PrimaryDrawerItem itemInfo = new PrimaryDrawerItem().withName(R.string.info).withIcon(R.mipmap.ic_email_black_24dp);
    final PrimaryDrawerItem itemCredits = new PrimaryDrawerItem().withName(R.string.credits).withIcon(R.mipmap.ic_info_outline_black_24dp);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.itinerari));
        setSupportActionBar(toolbar);

        isPermissionsWriteGranted();

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
                .withSavedInstance(savedInstanceState)
                .withActionBarDrawerToggle(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.equals(itemEvent)) openHolyday();
                        else if (drawerItem.equals(itemItinerary)) openItinerary();
                        else if (drawerItem.equals(itemMap)) openMap();
                        else if (drawerItem.equals(itemFotoVideo)) openGallery();
                        else if (drawerItem.equals(itemInfo)) openInfo();
                        else if (drawerItem.equals(itemCredits)) openCredits();


                        return false;
                    }
                })
                .build();

        openItinerary();

    }

    public void openItinerary() {
        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_placeholder, new ItineraryFragment().newInstance((ArrayList<Itinerary>) getIntent().getSerializableExtra("itineraries")));
            ft.commit();
            toolbar.setTitle(getResources().getString(R.string.itinerari));
        }
    }

    public void openMap() {
        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_placeholder, new MapsFragment());
            ft.commit();
            toolbar.setTitle(getResources().getString(R.string.mappa));
        }
    }

    public void openGallery() {
        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_placeholder, new GalleryFragment());
            ft.commit();
            toolbar.setTitle(getResources().getString(R.string.foto_video));
        }
    }

    public void openHolyday() {
        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_placeholder, new HolidayFragment());
            ft.commit();
            toolbar.setTitle(getResources().getString(R.string.eventi));
        }
    }

    public void openInfo() {
        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_placeholder, new InfoFragment());
            ft.commit();
            toolbar.setTitle(getResources().getString(R.string.info));
        }
    }

    public void openCredits() {
        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_placeholder, new CreditsFragment());
            ft.commit();
            toolbar.setTitle(getResources().getString(R.string.credits));
        }
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


            } else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
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
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkReceiver);
    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {

        private Snackbar snackbar;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION) && !NetworkUtil.isNetworkConnected(context)) {
                snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.no_connection, Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            } else {
                if (snackbar != null && snackbar.isShown())
                    snackbar.dismiss();
            }
        }
    };
}
