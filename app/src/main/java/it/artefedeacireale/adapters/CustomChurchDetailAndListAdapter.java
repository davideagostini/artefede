package it.artefedeacireale.adapters;

/**
 * Created by davide on 14/03/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import it.artefedeacireale.api.models.Church;
import it.artefedeacireale.api.models.ChurchInfo;

/**
 * Created by davide on 14/03/16.
 */
public class CustomChurchDetailAndListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Object> itemList;
    private Context context;

    private final int INFO = 0, DESC = 1, ARTWORK = 2, TITLE = 3;

    public CustomChurchDetailAndListAdapter(Context context) {
        this.itemList = new ArrayList<Object>();
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(itemList.get(position) instanceof Artwork)
            return ARTWORK;
        else if(itemList.get(position) instanceof ChurchInfo) {
            if(((ChurchInfo) itemList.get(position)).getType() == 0)
                return INFO;
            else return DESC;
        }
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
            case INFO:
                View v2 = inflater.inflate(R.layout.item_list_textview, parent, false);
                viewHolder = new TextViewHolder(v2);
                break;
            case DESC:
                View description = inflater.inflate(R.layout.item_description, parent, false);
                viewHolder = new DescriptionViewHolder(description);
                break;
            case TITLE:
                View title = inflater.inflate(R.layout.item_title, parent, false);
                viewHolder = new TitleViewHolder(title);
                break;
            default:
                View v1 = inflater.inflate(R.layout.item_list_artwork, parent, false);
                viewHolder = new ItemArtworkHolder(v1);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case INFO:
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                configureTextViewHolder(textViewHolder, position);
                break;
            case DESC:
                DescriptionViewHolder descriptionViewHolder = (DescriptionViewHolder) holder;
                configureDescriptionViewHolder(descriptionViewHolder, position);
                break;
            case TITLE:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                configureTitleViewHolder(titleViewHolder, position);
                break;
            default:
                ItemArtworkHolder itemArtworkHolder = (ItemArtworkHolder) holder;
                configureItemArtworkHolder(itemArtworkHolder, position);

        }
    }

    private void configureItemArtworkHolder(final ItemArtworkHolder v1, int position) {
        Artwork artwork = (Artwork) itemList.get(position);
        if(artwork != null) {
            v1.getName().setText(artwork.getNome());
            v1.getYear().setText(artwork.getAnno());
            if(artwork.getImage_opera().size()>0) {
                Glide.with(context).load(artwork.getImage_opera().get(0).getImage()).asBitmap().centerCrop().into(new BitmapImageViewTarget(v1.getImageArtwork()) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        v1.getImageArtwork().setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
        }
    }

    private void configureTextViewHolder(TextViewHolder v2, int position) {
        ChurchInfo churchInfo = (ChurchInfo) itemList.get(position);
        if(churchInfo != null)
            v2.getTextViewInfo().setText(Html.fromHtml(churchInfo.getText()));
    }

    private void configureTitleViewHolder(TitleViewHolder v2, int position) {
        v2.getTitleView().setText(itemList.get(position).toString());
    }

    private void configureDescriptionViewHolder(DescriptionViewHolder v2, int position) {
        ChurchInfo churchInfo = (ChurchInfo) itemList.get(position);
        if(churchInfo != null) {
            String html = churchInfo.getText().replaceAll("<h2>", "").replaceAll("</h2>", "");
            html = html.replaceAll("\r\n\r\n", "<br><br>");
            html = html.replaceAll("<br/>\\r\\n", "<br>");
            v2.getDescriptionTextView().setText(Html.fromHtml(html));
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(Church c) {
        ChurchInfo churchInfoApertura = new ChurchInfo(c.getOrario_apertura(), 0);
        if(churchInfoApertura.getText().length()>0)
            this.itemList.add(new ChurchInfo(c.getOrario_apertura(), 0));
        ChurchInfo churchInfoMessa = new ChurchInfo(c.getOrario_s_messe(), 0);
        if(churchInfoMessa.getText().length()>0)
            this.itemList.add(new ChurchInfo(c.getOrario_s_messe(), 0));
        this.itemList.add("Descrizione");
        this.itemList.add(new ChurchInfo(c.getDescrizione(), 1));
        this.itemList.add("Opere principali");
        ArrayList<Artwork> artworkArrayList = c.getOpere_chiese();
        for(Artwork a : artworkArrayList)
                this.itemList.add(a);
        notifyDataSetChanged();
    }

}


