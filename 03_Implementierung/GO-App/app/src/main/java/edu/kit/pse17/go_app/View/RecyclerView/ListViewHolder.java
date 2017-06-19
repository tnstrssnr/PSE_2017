package edu.kit.pse17.go_app.View.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.kit.pse17.go_app.R;

/**
 * Die Klasse erzeugt ViewHolder-Objekte, die die Datenobjekt für die RecyclerView enthalten
 *
 * Created by tina on 17.06.17.
 */

public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final OnListItemClicked onListItemClicked;

    /**
     * Titel des Items
     */
    public TextView title;
    /**
     * Untertitel des Items
     */
    public TextView subtitle;
    /**
     * Icon, das zum Item angezeigt werden soll
     */
    public ImageView icon;

    /**
     * Konstruktor
     * @param itemView View, in der die Items angezeigt werden sollen
     * @param onListItemClicked ClickListener für ListItems
     */
    public ListViewHolder(View itemView, OnListItemClicked onListItemClicked) {
        super(itemView);
        this.onListItemClicked = onListItemClicked;
        this.title = (TextView) itemView.findViewById(R.id.list_item_title);
        this.subtitle = (TextView) itemView.findViewById(R.id.list_item_subtitle);
        this.icon = (ImageView) itemView.findViewById(R.id.list_item_icon);
    }
    @Override
    public void onClick(View v) {
        onListItemClicked.onItemClicked(getAdapterPosition());
    }
}
