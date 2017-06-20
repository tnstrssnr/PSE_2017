package edu.kit.pse17.go_app.View.RecyclerView.Adapter;

import android.support.v7.app.AlertDialog;

import java.util.List;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.View.RecyclerView.ListItems.ListItem;
import edu.kit.pse17.go_app.View.RecyclerView.OnListItemClicked;

/**
 *  * Konkreter ViewHolder, der die ListItems an das user_list_item.xml Layout bindet
 *
 * Created by tina on 19.06.17.
 */

public class UserListAdapter extends ListAdapter {

    private static final int layout = R.layout.user_list_item;

    public UserListAdapter(List<ListItem> data, OnListItemClicked onListItemClicked) {
        super(data, onListItemClicked);
    }

    @Override
    protected int setLayout() {
        return layout;
    }
}
