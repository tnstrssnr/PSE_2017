package edu.kit.pse17.go_app.view.recyclerView.Adapter;

import java.util.List;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.view.recyclerView.ListItems.ListItem;
import edu.kit.pse17.go_app.view.recyclerView.OnListItemClicked;

/**
 * Konkreter ViewHolder, der die ListItems an das group_list_item.xml Layout bindet
 *
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
