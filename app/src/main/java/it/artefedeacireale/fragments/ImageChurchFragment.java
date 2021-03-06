package it.artefedeacireale.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import it.artefedeacireale.R;
import it.artefedeacireale.activities.DetailImageActivity;


public class ImageChurchFragment extends Fragment {
    // Store instance variables
    private String urlImage;
    private int idImage;
    private ImageView imageView;
    private ProgressBar progressBar;

    // newInstance constructor for creating fragment with arguments
    public static ImageChurchFragment newInstance(String urlImage) {
        ImageChurchFragment fragment = new ImageChurchFragment();
        Bundle args = new Bundle();
        args.putString("urlImage", urlImage);
        fragment.setArguments(args);
        return fragment;
    }

    public static ImageChurchFragment newInstance(int idImage) {
        ImageChurchFragment fragment = new ImageChurchFragment();
        Bundle args = new Bundle();
        args.putInt("idImage", idImage);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlImage = getArguments().getString("urlImage");
        idImage = getArguments().getInt("idImage");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_church, container, false);

        imageView = (ImageView) view.findViewById(R.id.image);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        Glide.with(getActivity())
                .load(urlImage != null ? urlImage : idImage)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        progressBar.setVisibility(View.GONE);
                    }
                });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(urlImage != null) {
                    Intent intent = new Intent(getActivity(), DetailImageActivity.class);
                    intent.putExtra("urlImage", urlImage);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}