package edu.kit.pse17.go_app.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.kit.pse17.go_app.RecyclerView.ListItems.ListItem;

/**
 * Created by tina on 17.06.17.
 */

public abstract class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    protected List<ListItem> data;
    protected final OnListItemClicked onListItemClicked;

    public ListAdapter(List<ListItem> data, OnListItemClicked onListItemClicked) {
        this.data = data;
        this.onListItemClicked = onListItemClicked;
    }

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

    public ListItem getItem(int position) {
        return data.get(position);
    }

    protected abstract int setLayout();


}