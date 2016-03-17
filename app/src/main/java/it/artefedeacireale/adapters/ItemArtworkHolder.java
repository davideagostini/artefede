package it.artefedeacireale.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it.artefedeacireale.R;

/**
 * Created by davide on 17/03/16.
 */
public class ItemArtworkHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView year;
    public ImageView imageArtwork;

    public ItemArtworkHolder(View v) {
        super(v);
        name = (TextView) v.findViewById(R.id.name);
        year = (TextView) v.findViewById(R.id.year);
        imageArtwork = (ImageView) v.findViewById(R.id.imageArtwork);
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getYear() {
        return year;
    }

    public void setYear(TextView year) {
        this.year = year;
    }

    public ImageView getImageArtwork() {
        return imageArtwork;
    }

    public void setImageArtwork(ImageView imageArtwork) {
        this.imageArtwork = imageArtwork;
    }
}
