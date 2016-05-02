package it.artefedeacireale.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.activities.DetailListActivity;
import it.artefedeacireale.adapters.GalleryFotoListAdapter;
import it.artefedeacireale.adapters.GalleryVideoListAdapter;
import it.artefedeacireale.api.models.GalleryFoto;
import it.artefedeacireale.api.models.GalleryVideo;
import it.artefedeacireale.util.NetworkUtil;
import it.artefedeacireale.util.RecyclerViewClickListener;

public class GalleryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewVideo;
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayoutManager mLinearLayoutManagerVideo;
    private GalleryFotoListAdapter mGalleryListAdapter;
    private GalleryVideoListAdapter mGalleryVideoListAdapter;
    private ArrayList<GalleryFoto> galleries;
    private ArrayList<GalleryVideo> video;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        galleries = new ArrayList<>();
        video = new ArrayList<>();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerViewVideo = (RecyclerView) view.findViewById(R.id.list_video);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerViewVideo.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mLinearLayoutManagerVideo = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewVideo.setLayoutManager(mLinearLayoutManagerVideo);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewVideo.setItemAnimator(new DefaultItemAnimator());
        mGalleryListAdapter = new GalleryFotoListAdapter(getActivity());
        mRecyclerView.setAdapter(mGalleryListAdapter);
        mGalleryVideoListAdapter = new GalleryVideoListAdapter(getActivity());
        mRecyclerViewVideo.setAdapter(mGalleryVideoListAdapter);

        galleries.add(new GalleryFoto(R.drawable.piazza_duomo));
        galleries.add(new GalleryFoto(R.drawable.santa_venera));
        galleries.add(new GalleryFoto(R.drawable.barocco));
        galleries.add(new GalleryFoto(R.drawable.san_sebastiano));
        galleries.add(new GalleryFoto(R.drawable.san_camillo));
        galleries.add(new GalleryFoto(R.drawable.presepe));
        galleries.add(new GalleryFoto(R.drawable.etna));
        galleries.add(new GalleryFoto(R.drawable.s_m_la_scala));
        galleries.add(new GalleryFoto(R.drawable.carnevale));
        galleries.add(new GalleryFoto(R.drawable.carnevale_2));
        mGalleryListAdapter.setGalleries(galleries);

        video.add(new GalleryVideo(R.drawable.t_san_sebastiano, "Festa di San Sebastiano", "Barocco in festa", "https://www.youtube.com/embed/j86ktzkx3iU"));
        video.add(new GalleryVideo(R.drawable.t_santa_venera, "Festa Barocca di Santa Venera", "", "https://www.youtube.com/embed/d-tM_YYeZ1E"));
        video.add(new GalleryVideo(R.drawable.t_candelora, "Le Candelore", "Barocco in festa", "https://www.youtube.com/embed/o7AUAWwcSLc"));
        video.add(new GalleryVideo(R.drawable.t_piazza_duomo, "Acireale una città unica", "video di Gregorio Fisichella", "https://www.youtube.com/embed/9QEFSjbZtgE"));
        mGalleryVideoListAdapter.setArrayList(video);

        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(), mRecyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (new NetworkUtil().isNetworkConnected(getActivity())) {
                    Intent intent = new Intent(getActivity(), DetailListActivity.class);
                    intent.putExtra("gallery", galleries);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongPress(View view, int position) {
            }
        }));

        mRecyclerViewVideo.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(), mRecyclerViewVideo, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (new NetworkUtil().isNetworkConnected(getActivity())) {
                    GalleryVideo video = mGalleryVideoListAdapter.get(position);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getUrl()));
                    startActivity(browserIntent);
                }
            }

            @Override
            public void onItemLongPress(View view, int position) {
            }
        }));
    }
}
