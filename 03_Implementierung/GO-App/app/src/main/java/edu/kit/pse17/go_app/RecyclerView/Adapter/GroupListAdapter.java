package edu.kit.pse17.go_app.RecyclerView.Adapter;

import java.util.List;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.RecyclerView.ListItems.ListItem;
import edu.kit.pse17.go_app.RecyclerView.OnListItemClicked;

/**
 * Created by tina on 19.06.17.
 */

public class GroupListAdapter extends ListAdapter {

    private static final int layout = R.layout.group_list_item;

    public GroupListAdapter(List<ListItem> data, OnListItemClicked onListItemClicked) {
        super(data, onListItemClicked);
    }

    @Override
    protected int setLayout() {
        return layout;
    }

}
