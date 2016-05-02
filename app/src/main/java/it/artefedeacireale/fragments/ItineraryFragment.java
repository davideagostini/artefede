package it.artefedeacireale.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.activities.ChurchListActivity;
import it.artefedeacireale.adapters.ItineraryListAdapter;
import it.artefedeacireale.api.models.Itinerary;
import it.artefedeacireale.util.NetworkUtil;
import it.artefedeacireale.util.RecyclerViewClickListener;

public class ItineraryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ItineraryListAdapter mItineraryListAdapter;
    private ProgressBar progressBar;
    private ArrayList<Itinerary> itineraries;

    public static ItineraryFragment newInstance(ArrayList<Itinerary> itineraries) {
        ItineraryFragment fragmentDemo = new ItineraryFragment();
        Bundle args = new Bundle();
        args.putSerializable("itineraries", itineraries);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itineraries = (ArrayList<Itinerary>) getArguments().getSerializable("itineraries");

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
        mItineraryListAdapter = new ItineraryListAdapter(getActivity());
        mRecyclerView.setAdapter(mItineraryListAdapter);

        mItineraryListAdapter.setItineraries(itineraries);
        hideProgressBar();

        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(), mRecyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (new NetworkUtil().isNetworkConnected(getActivity())) {
                    Intent intent = new Intent(getActivity(), ChurchListActivity.class);
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

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
