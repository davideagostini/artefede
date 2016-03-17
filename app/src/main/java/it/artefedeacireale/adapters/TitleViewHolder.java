package it.artefedeacireale.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import it.artefedeacireale.R;

/**
 * Created by davide on 17/03/16.
 */
public class TitleViewHolder extends RecyclerView.ViewHolder{

    private TextView titleView;

    public TitleViewHolder(View v) {
        super(v);
        titleView = (TextView) v.findViewById(R.id.title_list);
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setTitleView(TextView titleView) {
        this.titleView = titleView;
    }
}
