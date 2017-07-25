package edu.kit.pse17.go_app.view.recyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.view.BaseActivity;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Die Klasse erzeugt ViewHolder-Objekte, die die Datenobjekt für die RecyclerView enthalten
 *
 * Created by tina on 17.06.17.
 */

public class ListViewHolder extends ViewHolder implements View.OnClickListener {

    private OnListItemClicked onListItemClicked;

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

    private BaseActivity activity;

    /**
     * Konstruktor
     * @param itemView View, in der die Items angezeigt werden sollen
     * @param onListItemClicked ClickListener für ListItems
     */
    public ListViewHolder(View itemView, OnListItemClicked onListItemClicked) {
        super(itemView);
        this.onListItemClicked = onListItemClicked;
        itemView.setOnClickListener(this);
        this.title = (TextView) itemView.findViewById(R.id.list_item_title);
        this.subtitle = (TextView) itemView.findViewById(R.id.list_item_subtitle);
        this.icon = (ImageView) itemView.findViewById(R.id.list_item_icon);
        activity = (BaseActivity)itemView.getContext();
    }

    public ListViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View v) {
        Log.d("CLICKED", "clicked at " + getAdapterPosition());
        v.getId();
        //if(v.getId() == )
        Intent intent = new Intent(activity, activity.getNextActivity());
        intent.putExtra("index", getAdapterPosition());
        activity.startActivity(intent);
        //GroupDetailActivity.start(activity, getAdapterPosition());
    }
}
