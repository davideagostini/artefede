package it.artefedeacireale.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.api.models.GalleryFoto;

/**
 * Created by davide on 29/04/16.
 */
public class GalleryFotoListAdapter extends RecyclerView.Adapter<GalleryFotoListAdapter.ViewHolder> {

    private ArrayList<GalleryFoto> galleries;
    private Context context;

    public GalleryFotoListAdapter(Context context) {
        this.galleries = new ArrayList<GalleryFoto>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_gallery, parent, false);

        ViewHolder vh = new ViewHolder(itemView);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final GalleryFoto gallery = galleries.get(position);

        Glide.with(context).load(gallery.getNome()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }

    public GalleryFoto get(int position) {
        return galleries.get(position);
    }

    public void setGalleries(ArrayList<GalleryFoto> galleries) {
        this.galleries = galleries;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
        }
    }
}

