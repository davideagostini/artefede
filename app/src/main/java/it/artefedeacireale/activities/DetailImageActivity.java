package it.artefedeacireale.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import it.artefedeacireale.R;

public class DetailImageActivity extends AppCompatActivity {

    private static final String TAG = DetailImageActivity.class.getSimpleName();
    private ImageView image_detail;
    private ImageView close;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);

        image_detail = (ImageView) findViewById(R.id.image_detail);
        close = (ImageView) findViewById(R.id.close);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Glide.with(getApplicationContext())
                .load(getIntent().getStringExtra("urlImage"))
                .into(new GlideDrawableImageViewTarget(image_detail) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        progressBar.setVisibility(View.GONE);
                    }
                });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}

