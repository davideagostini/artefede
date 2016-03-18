package it.artefedeacireale.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import it.artefedeacireale.R;

/**
 * Created by davide on 18/03/16.
 */
public class ItemEventHolder extends RecyclerView.ViewHolder {

    public TextView title_event;
    public TextView date_event;

    public ItemEventHolder(View v) {
        super(v);
        title_event = (TextView) v.findViewById(R.id.title_event);
        date_event = (TextView) v.findViewById(R.id.date_event);
    }

    public TextView getTitle_event() {
        return title_event;
    }

    public void setTitle_event(TextView title_event) {
        this.title_event = title_event;
    }

    public TextView getDate_event() {
        return date_event;
    }

    public void setDate_event(TextView date_event) {
        this.date_event = date_event;
    }
}
