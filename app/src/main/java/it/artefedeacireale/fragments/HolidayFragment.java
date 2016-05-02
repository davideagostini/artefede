package it.artefedeacireale.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.adapters.EventListAdapter;
import it.artefedeacireale.api.models.Holiday;
import it.artefedeacireale.services.EventService;
import it.artefedeacireale.util.NetworkUtil;

public class HolidayFragment extends Fragment {

    private Intent intentService;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private EventListAdapter eventListAdapter;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startDownloadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_holiday, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        eventListAdapter = new EventListAdapter(getActivity());
        mRecyclerView.setAdapter(eventListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(EventService.ACTION_EVENT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    private void startDownloadData() {

        if (new NetworkUtil().isNetworkConnected(getActivity())) {

            if (intentService != null) getActivity().stopService(intentService);
            intentService = new Intent(getActivity(), EventService.class);
            intentService.setAction(EventService.ACTION_EVENT);
            getActivity().startService(intentService);

        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Holiday> holidays = (ArrayList<Holiday>) intent.getSerializableExtra("holidays");
            setMyView(holidays);
            hideProgressBar();
        }
    };

    private void setMyView(ArrayList<Holiday> holidays) {
        eventListAdapter.setItemList(holidays);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
