package it.artefedeacireale.adapters;

/**
 * Created by davide on 14/03/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.api.models.Artwork;
import it.artefedeacireale.api.models.Itinerary;

/**
 * Created by davide on 14/03/16.
 */
public class ArtworkListAdapter extends RecyclerView.Adapter<ArtworkListAdapter.ViewHolder> {

    private ArrayList<Artwork> artworks;
    private Context context;

    public ArtworkListAdapter(Context context) {
        this.artworks = new ArrayList<Artwork>();
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_list_artwork, parent, false);

        ViewHolder vh = new ViewHolder(itemView);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Artwork artwork = artworks.get(position);

        Glide.with(context).load(artwork.getImage_opera().get(0).getImage()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.imageArtwork) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.imageArtwork.setImageDrawable(circularBitmapDrawable);
            }
        });
        holder.name.setText(artwork.getNome());
        holder.year.setText(artwork.getAnno());

        // setAnimation(holder.container, position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return artworks.size();
    }

    public Artwork get(int position) {
        return artworks.get(position);
    }

    public void setArtworks(ArrayList<Artwork> artworks) {
        this.artworks = artworks;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //public ProgressBar spinner;
        public TextView name;
        public TextView year;
        public ImageView imageArtwork;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            year = (TextView) v.findViewById(R.id.year);
            imageArtwork = (ImageView) v.findViewById(R.id.imageArtwork);
            //spinner = (ProgressBar) v.findViewById(R.id.loader_image);
        }
    }
}

