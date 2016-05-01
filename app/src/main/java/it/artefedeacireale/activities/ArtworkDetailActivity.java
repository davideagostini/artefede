package it.artefedeacireale.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import it.artefedeacireale.R;
import it.artefedeacireale.api.models.Artwork;
import it.artefedeacireale.services.ArtworkDetailService;
import it.artefedeacireale.util.NetworkUtil;

public class ArtworkDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = ArtworkDetailActivity.class.getSimpleName();
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar mToolbar;
    private TextView mTitle, mAuthor, mPeriod, mTechnique;
    private Intent intentService;
    private ProgressBar progressBar;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_detail);


        progressBar = (ProgressBar) findViewById(R.id.progress);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        mTitle = (TextView) findViewById(R.id.name);
        mAuthor = (TextView) findViewById(R.id.author);
        mPeriod = (TextView) findViewById(R.id.period);
        mTechnique = (TextView) findViewById(R.id.technique);
        image = (ImageView) findViewById(R.id.image);
        mTitle.setText(getIntent().getStringExtra("name_artwork"));

        if (getIntent().getStringExtra("image_artwork") != null)
            Glide.with(getApplicationContext()).load(getIntent().getStringExtra("image_artwork")).crossFade().into(image);

        startDownloadData();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailImageActivity.class);
                intent.putExtra("urlImage", getIntent().getStringExtra("image_artwork"));
                startActivity(intent);
            }

        });
    }

    private void startDownloadData() {

        if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {

            if (intentService != null) stopService(intentService);
            intentService = new Intent(this, ArtworkDetailService.class);
            intentService.putExtra("id_artwork", getIntent().getIntExtra("id_artwork", -1));
            intentService.setAction(ArtworkDetailService.ACTION_ARTWORK_DETAIL);
            startService(intentService);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(ArtworkDetailService.ACTION_ARTWORK_DETAIL);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
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

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        if (Math.abs(offset) <= appBarLayout.getTotalScrollRange() - mToolbar.getHeight()) {
            collapsingToolbarLayout.setTitle("");
        } else {
            collapsingToolbarLayout.setTitle(getIntent().getStringExtra("name_artwork"));
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Artwork artwork = (Artwork) intent.getSerializableExtra("artwork");
            setMyView(artwork);
            progressBar.setVisibility(View.GONE);
        }
    };

    private void setMyView(Artwork a) {
        LinearLayout layout_description = (LinearLayout) findViewById(R.id.layout_description);
        TextView artwork_description = (TextView) findViewById(R.id.artwork_description);

        String name = "";
        for (int i = 0; i < a.getArtisti().size(); i++) {
            name += a.getArtisti().get(i).getNome_cognome();
            if (i < a.getArtisti().size() - 1)
                name += ", ";
        }
        if (a.getAnno().length() > 0 || a.getTecnica().length() > 0 || name.length() > 0) {
            if (name.length() > 0) mAuthor.setText(name);
            if (a.getAnno().length() > 0) mPeriod.setText(a.getAnno());
            if (a.getTecnica().length() > 0) mTechnique.setText(a.getTecnica());
        }

        artwork_description.setText(a.getDescrizione());
        layout_description.setVisibility(View.VISIBLE);

    }
}
