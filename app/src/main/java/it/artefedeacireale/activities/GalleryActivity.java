package it.artefedeacireale.activities;

import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.adapters.GalleryFotoListAdapter;
import it.artefedeacireale.adapters.GalleryVideoListAdapter;
import it.artefedeacireale.api.models.Church;
import it.artefedeacireale.api.models.GalleryFoto;
import it.artefedeacireale.api.models.GalleryVideo;
import it.artefedeacireale.fragments.ImageChurchFragment;
import it.artefedeacireale.util.NetworkUtil;
import it.artefedeacireale.util.RecyclerViewClickListener;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewVideo;
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayoutManager mLinearLayoutManagerVideo;
    private GalleryFotoListAdapter mGalleryListAdapter;
    private GalleryVideoListAdapter mGalleryVideoListAdapter;
    private ArrayList<GalleryFoto> galleries;
    private ArrayList<GalleryVideo> video;
    private RelativeLayout detail;
    private LinearLayout layoutGallery;
    private Toolbar mToolbar;
    private ProgressBar progressBar;
    private ImageView close;
    private boolean isInfoOpen = false;
    private MyPagerAdapter adapterViewPager;
    private ViewPager vpPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        detail = (RelativeLayout) findViewById(R.id.detail);
        layoutGallery = (LinearLayout) findViewById(R.id.layout_gallery);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        close = (ImageView) findViewById(R.id.close);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.foto_video));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        galleries = new ArrayList<>();
        video = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerViewVideo = (RecyclerView) findViewById(R.id.list_video);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerViewVideo.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLinearLayoutManagerVideo = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewVideo.setLayoutManager(mLinearLayoutManagerVideo);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewVideo.setItemAnimator(new DefaultItemAnimator());

        mGalleryListAdapter = new GalleryFotoListAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mGalleryListAdapter);

        mGalleryVideoListAdapter = new GalleryVideoListAdapter(getApplicationContext());
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


        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getApplicationContext(), mRecyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
                    GalleryFoto gallery = mGalleryListAdapter.get(position);
                    vpPager.setCurrentItem(position);
                    openDetail();
                }
            }

            @Override
            public void onItemLongPress(View view, int position) {
            }
        }));

        mRecyclerViewVideo.addOnItemTouchListener(new RecyclerViewClickListener(getApplicationContext(), mRecyclerViewVideo, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {

                    GalleryVideo video = mGalleryVideoListAdapter.get(position);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getUrl()));
                    startActivity(browserIntent);
                }
            }

            @Override
            public void onItemLongPress(View view, int position) {
            }
        }));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDetail();
            }
        });

        vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), galleries);
        vpPager.setAdapter(adapterViewPager);
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

    private void openDetail() {
        detail.setTranslationY(detail.getMeasuredHeight());
        detail.animate().translationY(0).setDuration(300).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                detail.setVisibility(View.VISIBLE);
                isInfoOpen = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                progressBar.setVisibility(View.GONE);
                layoutGallery.setVisibility(View.GONE);
                mToolbar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        }).start();
    }

    public void closeDetail() {
        detail.animate().translationY(detail.getMeasuredHeight()).setDuration(300).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isInfoOpen = false;
                layoutGallery.setVisibility(View.VISIBLE);
                mToolbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                detail.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        }).start();
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 10;
        private ArrayList<GalleryFoto> galleryFotos;

        public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<GalleryFoto> galleryFotos) {
            super(fragmentManager);
            this.galleryFotos = galleryFotos;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            return ImageChurchFragment.newInstance(galleryFotos.get(position).getNome());
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

    }

    @Override
    public void onBackPressed() {

        if(isInfoOpen)
            closeDetail();
        else
            super.onBackPressed();

    }
}
