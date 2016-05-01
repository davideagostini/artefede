package it.artefedeacireale.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import it.artefedeacireale.R;


public class ImageChurchFragment extends Fragment {
    // Store instance variables
    private String urlImage;
    private int idImage;

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


        Glide.with(getActivity()).load(urlImage != null ? urlImage : idImage).crossFade().into((ImageView) view.findViewById(R.id.image));

        return view;
    }
}