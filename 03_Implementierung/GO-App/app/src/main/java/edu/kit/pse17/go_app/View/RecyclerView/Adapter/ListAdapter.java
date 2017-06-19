package edu.kit.pse17.go_app.View.RecyclerView.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.kit.pse17.go_app.View.RecyclerView.ListItems.ListItem;
import edu.kit.pse17.go_app.View.RecyclerView.ListViewHolder;
import edu.kit.pse17.go_app.View.RecyclerView.OnListItemClicked;

/**
 * Abstrakte Klasse, die Schablone für konkrete Adapter-Klassen bietet.
 * Unterklassen müssen die Methode setLayout() implementieren, um dem Adapter ein passendes XML-Layout zuzuweisen
 *
 * Created by tina on 17.06.17.
 */

public abstract class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    /**
     * ListItems, die in dem RecyclerView angezeigt werden sollen
     */
    protected List<ListItem> data;

    /**
     * ClickListener für die Listenelemente
     */
    protected final OnListItemClicked onListItemClicked;

    /**
     * Konstruktor
     * @param data ListItems, die in dem RecyclerView angezeigt werden sollen
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
     * @param parent Viewgroup, zu der der neue View hinzugefügt werden soll
     * @param viewType viewType des neuen Views
     * @return neuer ViewHolder des gewünschten Typs
     */
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate( setLayout(), parent, false);
        return new ListViewHolder(view, onListItemClicked);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        ListItem item = data.get(position);
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
        holder.icon.setImageIcon(item.getIcon());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * gibt das ListItem an der angegebenen Position zurück
     * @param position Listenposition des gewünschten ListItems
     * @return ListItem, an der angegebenen Position aus der Liste data
     */
    public ListItem getItem(int position) {
        return data.get(position);
    }

    /**
     * Methode wird von Unterklassen implementiert, um einem konkreten Viewholder das richtige Layout zuweisen zu könne
     * @return ID des gewünschten XML Layouts aus R.layout
     */
    protected abstract int setLayout();


}