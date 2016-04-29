package it.artefedeacireale.adapters;

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
import it.artefedeacireale.api.models.GalleryVideo;

/**
 * Created by davide on 29/04/16.
 */
public class GalleryVideoListAdapter extends RecyclerView.Adapter<GalleryVideoListAdapter.ViewHolder> {

    private ArrayList<GalleryVideo> arrayList;
    private Context context;

    public GalleryVideoListAdapter(Context context) {
        this.arrayList = new ArrayList<GalleryVideo>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_list_video, parent, false);

        ViewHolder vh = new ViewHolder(itemView);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final GalleryVideo video = arrayList.get(position);

        Glide.with(context).load(video.getImage()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.image) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.image.setImageDrawable(circularBitmapDrawable);
            }
        });

        holder.title.setText(video.getTitle());
        holder.subtitle.setText(video.getSubtitle());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public GalleryVideo get(int position) {
        return arrayList.get(position);
    }

    public void setArrayList(ArrayList<GalleryVideo> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView subtitle;
        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            subtitle = (TextView) v.findViewById(R.id.subtitle);
            image = (ImageView) v.findViewById(R.id.image_video);
        }
    }
}

