package edu.kit.pse17.go_app.View.RecyclerView.Adapter;

import java.util.List;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.View.RecyclerView.ListItems.ListItem;
import edu.kit.pse17.go_app.View.RecyclerView.OnListItemClicked;

/**
 * Konkreter ViewHolder, der die ListItems an das go_list_item.xml Layout bindet
 *
 * Created by tina on 19.06.17.
 */

public class GOListAdapter extends ListAdapter {

    private static final int layout = R.layout.go_list_item;

    public GOListAdapter(List<ListItem> data, OnListItemClicked onListItemClicked) {
        super(data, onListItemClicked);
    }

    @Override
    protected int setLayout() {
        return layout;
    }
}
