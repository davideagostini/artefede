package it.artefedeacireale.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import it.artefedeacireale.R;

/**
 * Created by davide on 17/03/16.
 */
public class DescriptionViewHolder extends RecyclerView.ViewHolder {

    private TextView descriptionTextView;

    public DescriptionViewHolder(View v) {
        super(v);
        descriptionTextView = (TextView) v.findViewById(R.id.text_description);
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public void setDescriptionTextView(TextView descriptionTextView) {
        this.descriptionTextView = descriptionTextView;
    }
}
