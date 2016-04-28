package it.artefedeacireale.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import it.artefedeacireale.R;
import it.artefedeacireale.adapters.CustomChurchDetailAndListAdapter;
import it.artefedeacireale.api.models.Artwork;
import it.artefedeacireale.api.models.Church;
import it.artefedeacireale.services.ChurchDetailService;
import it.artefedeacireale.util.NetworkUtil;
import it.artefedeacireale.util.RecyclerViewClickListener;

public class ChurchDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = ChurchDetailActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar mToolbar;
    private TextView nameChurch, cityChurch, timeChurch;
    private Intent intentService;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CustomChurchDetailAndListAdapter customChurchDetailAndListAdapter;
    private Church church;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church_detail);

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

        nameChurch = (TextView) findViewById(R.id.name);
        cityChurch = (TextView) findViewById(R.id.city);
        timeChurch = (TextView) findViewById(R.id.time);
        nameChurch.setText(getIntent().getStringExtra("name_church"));
        cityChurch.setText(getIntent().getStringExtra("city_church"));
        timeChurch.setText(getResources().getString(R.string.time) + " " + getIntent().getStringExtra("time_church"));

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        customChurchDetailAndListAdapter = new CustomChurchDetailAndListAdapter(getApplicationContext());
        mRecyclerView.setAdapter(customChurchDetailAndListAdapter);

        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("image")).crossFade().into((ImageView) findViewById(R.id.image));

        startDownloadData();

        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getApplicationContext(), mRecyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (new NetworkUtil().isNetworkConnected(getApplicationContext())) {
                    if (customChurchDetailAndListAdapter.get(position) instanceof Artwork) {
                        Artwork artwork = (Artwork) customChurchDetailAndListAdapter.get(position);
                        Intent intent = new Intent(getApplicationContext(), ArtworkDetailActivity.class);
                        intent.putExtra("id_artwork", artwork.getId());
                        intent.putExtra("name_artwork", artwork.getNome());
                        if (artwork.getImage_opera().size() > 0)
                            intent.putExtra("image_artwork", artwork.getImage_opera().get(0).getImage());
                        startActivity(intent);
                    }
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

        IntentFilter filter = new IntentFilter(ChurchDetailService.ACTION_CHURCH_DETAIL);
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
            intentService = new Intent(this, ChurchDetailService.class);
            intentService.putExtra("id_church", getIntent().getIntExtra("id_church", -1));
            intentService.setAction(ChurchDetailService.ACTION_CHURCH_DETAIL);
            startService(intentService);

        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            church = (Church) intent.getSerializableExtra("church");
            setMyView(church);

            if (getIntent().getStringExtra("city_church") == null) {
                nameChurch.setText(church.getNome());
                cityChurch.setText(church.getCitta());
                timeChurch.setText(getResources().getString(R.string.time) + " " + church.getTempo());
                Glide.with(getApplicationContext()).load(church.getImage_chiese().get(0).getImage()).crossFade().into((ImageView) findViewById(R.id.image));
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_telephone:
                callPhone();
                return true;
            case R.id.action_mail:
                String uriText =
                        "mailto:" + church.getEmail() +
                                "?subject=" + Uri.encode("Informazioni") +
                                "&body=" + Uri.encode("");
                Uri uri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                startActivity(Intent.createChooser(sendIntent, "Invia email"));
                return true;
            case R.id.action_video:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(church.getVideo()));
                startActivity(browserIntent);
                return true;
            case R.id.action_map:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String data = String.format("geo:%s,%s", church.getLatitudine(), church.getLongitudine());
                String zoomLevel = "18";
                if (zoomLevel != null) {
                    data = String.format("%s?z=%s", data, zoomLevel);
                }
                intent.setData(Uri.parse(data));
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        if (Math.abs(offset) <= appBarLayout.getTotalScrollRange() - mToolbar.getHeight()) {
            collapsingToolbarLayout.setTitle("");
        } else {
            collapsingToolbarLayout.setTitle(getIntent().getStringExtra("name_church"));
        }
    }

    private void setMyView(Church c) {
        customChurchDetailAndListAdapter.setItemList(c);
        progressBar.setVisibility(View.GONE);
    }

    private void callPhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
        } else {
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:" + church.getTelefono()));
            startActivity(phoneIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_PHONE_CALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Effettua chiamata")
                        .setMessage("Per effettuare la chiamata sono richiesti i permessi. Attivali in impostazioni del telefono.")
                        .show();
            }
        }
    }
}