package edu.kit.pse17.go_app.view.recyclerView;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.view.recyclerView.listItems.ListItem;

/**
 * Abstrakte Klasse, die Schablone für konkrete Adapter-Klassen bietet.
 * Unterklassen müssen die Methode setLayout() implementieren, um dem Adapter ein passendes XML-Layout zuzuweisen
 *
 * Created by tina on 17.06.17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    /**
     * ListItems, die in dem RecyclerView angezeigt werden sollen
     */
    private List<ListItem> data;

    /**
     * ClickListener für die Listenelemente
     */
    private final OnListItemClicked onListItemClicked;

    /**
     * Konstruktor
     *
     * @param data              ListItems, die in dem RecyclerView angezeigt werden sollen
     * @param onListItemClicked ClickListener für die Listenelemente
     */
    public ListAdapter(List<ListItem> data, OnListItemClicked onListItemClicked) {
        this.data = data;
        this.onListItemClicked = onListItemClicked;
    }

    /**
     * Schablonenmethode: erzeugt ListViewHolder, dem das passende XML layout zugewiesen wird
     * wird aufgerufen, wenn ein RecyclerView einen neuen ViewHolder braucht, um ein ListItem zu repräsentieren
     *
     * @param parent   Viewgroup, zu der der neue View hinzugefügt werden soll
     * @param viewType viewType des neuen Views
     * @return neuer ViewHolder des gewünschten Typs
     */
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view, onListItemClicked);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        ListItem item = data.get(position);
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
        holder.icon.setImageDrawable(item.getIcon());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * gibt das ListItem an der angegebenen Position zurück
     *
     * @param position Listenposition des gewünschten ListItems
     * @return ListItem, an der angegebenen Position aus der Liste data
     */
    public ListItem getItem(int position) {
        return data.get(position);
    }

    public void addItem(ListItem item) {
        data.add(item);
    }

    public void clickRedirect(Activity activity, int position){

    }
}