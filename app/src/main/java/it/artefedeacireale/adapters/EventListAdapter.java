package it.artefedeacireale.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.artefedeacireale.R;
import it.artefedeacireale.api.models.Holiday;

/**
 * Created by davide on 18/03/16.
 */
public class EventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Object> itemList;
    private Context context;

    private final int TITLE = 0, ITEM = 1;

    public EventListAdapter(Context context) {
        this.itemList = new ArrayList<Object>();
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(itemList.get(position) instanceof Holiday)
            return ITEM;
        else if(itemList.get(position) instanceof String)
            return TITLE;

        return -1;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TITLE:
                View title = inflater.inflate(R.layout.item_title_event, parent, false);
                viewHolder = new TitleEventViewHolder(title);
                break;
            default:
                View v1 = inflater.inflate(R.layout.item_event, parent, false);
                viewHolder = new ItemEventHolder(v1);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TITLE:
                TitleEventViewHolder titleEventViewHolder = (TitleEventViewHolder) holder;
                configureTitleEventViewHolder(titleEventViewHolder, position);
                break;
            default:
                ItemEventHolder itemEventHolder = (ItemEventHolder) holder;
                configureItemEventHolder(itemEventHolder, position);

        }
    }

    private void configureItemEventHolder(final ItemEventHolder v1, int position) {
        Holiday holiday = (Holiday) itemList.get(position);
        if(holiday != null) {
            v1.getTitle_event().setText(holiday.getTitolo());
            v1.getDate_event().setText(holiday.getGiorno()+" "+holiday.getMese()+" - "+holiday.getLuogo());
        }
    }

    private void configureTitleEventViewHolder(TitleEventViewHolder v2, int position) {
        v2.getTitleView().setText(itemList.get(position).toString());
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(ArrayList<Holiday> holidays) {
        String month = "";
        for(Holiday h: holidays) {
            if(!h.getMese().equalsIgnoreCase(month)) {
                month = h.getMese();
                this.itemList.add(month);
            }
            this.itemList.add(h);
        }
        notifyDataSetChanged();
    }

    public Object get(int position) {
        return itemList.get(position);
    }

}



