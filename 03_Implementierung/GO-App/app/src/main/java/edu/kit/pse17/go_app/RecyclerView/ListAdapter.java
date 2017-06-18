package edu.kit.pse17.go_app.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.kit.pse17.go_app.R;

/**
 * Created by tina on 17.06.17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private List<ListItem> data;
    private final OnListItemClicked onListItemClicked;

    public ListAdapter(List<ListItem> data, OnListItemClicked onListItemClicked) {
        this.data = data;
        this.onListItemClicked = onListItemClicked;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.group_list_item, parent, false);
        return new ListViewHolder(view, onListItemClicked);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        ListItem item = data.get(position);
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitleText());
        holder.icon.setImageIcon(item.getIcon());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ListItem getItem(int position) {
        return data.get(position);
    }


}