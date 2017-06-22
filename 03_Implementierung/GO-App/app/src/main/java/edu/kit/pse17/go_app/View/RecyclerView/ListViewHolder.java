package edu.kit.pse17.go_app.View.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.View.RecyclerView.ListItems.ListViewHolder;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Die Klasse erzeugt ViewHolder-Objekte, die die Datenobjekt für die RecyclerView enthalten
 *
 * Created by tina on 17.06.17.
 */

public class SimpleListViewHolder extends ViewHolder implements View.OnClickListener {

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

    public SimpleListViewHolder() {
        super(null);

        ViewHolder vh = new ViewHolder(null);
        
    };

    /**
     * Konstruktor
     * @param itemView View, in der die Items angezeigt werden sollen
     * @param onListItemClicked ClickListener für ListItems
     */
    private SimpleListViewHolder(View itemView, OnListItemClicked onListItemClicked) {
        super(itemView);
        this.onListItemClicked = onListItemClicked;
        this.title = (TextView) itemView.findViewById(R.id.list_item_title);
        this.subtitle = (TextView) itemView.findViewById(R.id.list_item_subtitle);
        this.icon = (ImageView) itemView.findViewById(R.id.list_item_icon);
    }

    public SimpleListViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public static SimpleListViewHolder createViewHolder(View itemView, OnListItemClicked onListItemClicked) {
        return new SimpleListViewHolder(itemView, onListItemClicked);
    }
    @Override
    public void onClick(View v) {
        onListItemClicked.onItemClicked(getAdapterPosition());
    }
}
