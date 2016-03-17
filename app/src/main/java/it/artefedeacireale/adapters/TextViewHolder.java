package it.artefedeacireale.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import it.artefedeacireale.R;

/**
 * Created by davide on 17/03/16.
 */
public class TextViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewInfo;

    public TextViewHolder(View v) {
        super(v);
        textViewInfo = (TextView) v.findViewById(R.id.text_church_info);
    }

    public TextView getTextViewInfo() {
        return textViewInfo;
    }

    public void setTextViewInfo(TextView textViewInfo) {
        this.textViewInfo = textViewInfo;
    }
}
