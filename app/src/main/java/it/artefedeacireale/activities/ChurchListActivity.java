package it.artefedeacireale.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import it.artefedeacireale.R;
import it.artefedeacireale.adapters.ChurchListAdapter;
import it.artefedeacireale.api.models.Church;
import it.artefedeacireale.api.models.ItineraryDetail;
import it.artefedeacireale.services.ChurchService;
import it.artefedeacireale.util.NetworkUtil;
import it.artefedeacireale.util.RecyclerViewClickListener;

public class ChurchListActivity extends AppCompatActivity {

    private Intent intentService;
    private static final String TAG = ChurchListActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ChurchListAdapter mChurchListAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church_list);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getIntent().getStringExtra("name_itinerary"));
        mToolbar.setSubtitle(getResources().getString(R.string.time) + " " + getIntent().getStringExtra("time_itinerary"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mChurchListAdapter = new ChurchListAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mChurchListAdapter);

        startDownloadItinerary();

        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getApplicationContext(), mRecyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(), ChurchDetailActivity.class);
                    Church church = mChurchListAdapter.get(position);
                    intent.putExtra("id_church", church.getId());
                    intent.putExtra("name_church", church.getNome());
                    intent.putExtra("city_church", church.getCitta());
                    intent.putExtra("time_church", church.getTempo());
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

        IntentFilter filter = new IntentFilter(ChurchService.ACTION_CHURCH_LIST);
        LocalBroadcastManager.getInstance(this).registerReceiver(itineraryReceiver, filter);

        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(itineraryReceiver);
        unregisterReceiver(networkReceiver);
    }

    private void startDownloadItinerary() {

        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {

            if (intentService != null) stopService(intentService);
            intentService = new Intent(this, ChurchService.class);
            intentService.setAction(ChurchService.ACTION_CHURCH_LIST);
            intentService.putExtra("id_itinerary", getIntent().getIntExtra("id_itinerary", -1));
            startService(intentService);

        }
    }

    private BroadcastReceiver itineraryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ItineraryDetail itinerary = (ItineraryDetail) intent.getSerializableExtra("itinerary_detail");
            mChurchListAdapter.setChurches(itinerary.getChiese());
            hideProgressBar();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {

        private Snackbar snackbar;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION) && !NetworkUtil.isNetworkConnected(context)) {
                snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.no_connection, Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }
            else {
                if(snackbar != null && snackbar.isShown())
                    snackbar.dismiss();
            }
        }
    };
}
