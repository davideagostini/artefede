package it.artefedeacireale.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.api.models.GalleryFoto;
import it.artefedeacireale.fragments.ImageChurchFragment;

public class DetailListActivity extends AppCompatActivity {

    private MyPagerAdapter adapterViewPager;
    private ViewPager vpPager;
    private ArrayList<GalleryFoto> galleryFotos;
    private int position;
    private ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        galleryFotos = (ArrayList<GalleryFoto>) getIntent().getSerializableExtra("gallery");
        position = getIntent().getIntExtra("position", -1);

        vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), galleryFotos);
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(position);

        close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
}
