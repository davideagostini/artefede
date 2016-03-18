package it.artefedeacireale.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.adapters.ChurchListAdapter;
import it.artefedeacireale.adapters.EventListAdapter;
import it.artefedeacireale.api.models.Holiday;
import it.artefedeacireale.services.ChurchDetailService;
import it.artefedeacireale.services.EventService;
import it.artefedeacireale.util.NetworkUtil;

public class HolidayActivity extends AppCompatActivity {

    private Intent intentService;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private EventListAdapter eventListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Eventi principali");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        eventListAdapter = new EventListAdapter(getApplicationContext());
        mRecyclerView.setAdapter(eventListAdapter);

        startDownloadData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(EventService.ACTION_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void startDownloadData() {

        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {

            if (intentService != null) stopService(intentService);
            intentService = new Intent(this, EventService.class);
            intentService.setAction(EventService.ACTION_EVENT);
            startService(intentService);

        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Holiday> holidays = (ArrayList<Holiday>) intent.getSerializableExtra("holidays");
            setMyView(holidays);
        }
    };

    private void setMyView(ArrayList<Holiday> holidays) {
        eventListAdapter.setItemList(holidays);
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
}
