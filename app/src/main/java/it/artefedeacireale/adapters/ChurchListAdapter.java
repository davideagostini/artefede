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
import it.artefedeacireale.api.models.Church;
import it.artefedeacireale.api.models.Itinerary;

/**
 * Created by davide on 14/03/16.
 */
public class ChurchListAdapter extends RecyclerView.Adapter<ChurchListAdapter.ViewHolder> {

    private ArrayList<Church> churches;
    private Context context;

    public ChurchListAdapter(Context context) {
        this.churches = new ArrayList<Church>();
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
        final Church church = churches.get(position);

        Glide.with(context).load(church.getImage_chiese().get(0).getImage()).crossFade().into(holder.image);
        holder.name.setText(church.getNome());
        holder.time.setText(church.getTempo());
        holder.city.setText(church.getCitta());

        // setAnimation(holder.container, position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return churches.size();
    }

    public Church get(int position) {
        return churches.get(position);
    }

    public void setChurches(ArrayList<Church> churches) {
        this.churches = churches;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //public ProgressBar spinner;
        public TextView name;
        public TextView time;
        public TextView city;
        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            time = (TextView) v.findViewById(R.id.time);
            city = (TextView) v.findViewById(R.id.city);
            city.setVisibility(View.VISIBLE);
            image = (ImageView) v.findViewById(R.id.image);

            //spinner = (ProgressBar) v.findViewById(R.id.loader_image);
        }
    }
}
