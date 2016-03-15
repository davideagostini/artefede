package it.artefedeacireale.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.api.models.Itinerary;

/**
 * Created by davide on 14/03/16.
 */
public class ItineraryListAdapter extends RecyclerView.Adapter<ItineraryListAdapter.ViewHolder> {

    private ArrayList<Itinerary> itineraries;
    private Context context;

    public ItineraryListAdapter(Context context) {
        this.itineraries = new ArrayList<Itinerary>();
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_list, parent, false);

        ViewHolder vh = new ViewHolder(itemView);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Itinerary itinerary = itineraries.get(position);

        Glide.with(context).load(itinerary.getImage()).crossFade().into(holder.image);
        holder.name.setText(itinerary.getNome());
        holder.time.setText(itinerary.getTempo());

        // setAnimation(holder.container, position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itineraries.size();
    }

    public Itinerary get(int position) {
        return itineraries.get(position);
    }

    public void setItineraries(ArrayList<Itinerary> itineraries) {
        this.itineraries = itineraries;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //public ProgressBar spinner;
        public TextView name;
        public TextView time;
        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            time = (TextView) v.findViewById(R.id.time);
            image = (ImageView) v.findViewById(R.id.image);
            //spinner = (ProgressBar) v.findViewById(R.id.loader_image);
        }
    }
}
