package edu.kit.pse17.go_app.view.recyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.view.BaseActivity;
import edu.kit.pse17.go_app.view.GroupDetailActivity;
import edu.kit.pse17.go_app.view.GroupDetailsActivity;
import edu.kit.pse17.go_app.view.GroupListActivity;
import edu.kit.pse17.go_app.viewModel.GroupViewModel;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Die Klasse erzeugt ViewHolder-Objekte, die die Datenobjekt für die RecyclerView enthalten
 *
 * Created by tina on 17.06.17.
 */

public class ListViewHolder extends ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

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
        itemView.setOnCreateContextMenuListener(this);
    }

    public ListViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View v) {
        Log.d("CLICKED", "clicked at " + getAdapterPosition());
        v.getId();
        //if(v.getId() == )
        if(activity.getClass() == GroupListActivity.class || activity.getClass() == GroupDetailActivity.class){
        Intent intent = new Intent(activity, activity.getNextActivity());
        intent.putExtra("index", getAdapterPosition());
        activity.startActivity(intent);}
        //GroupDetailActivity.start(activity, getAdapterPosition());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(activity.getClass() == GroupDetailsActivity.class){
            //subtitle.getText is Email of the user in this context
            String thisUserId = GroupListActivity.getUserId();
            if(GroupViewModel.getCurrentViewModel().getGroup().getValue().isAdmin(thisUserId)) {
                // if this user is admin in this group then add remove member option
                menu.add(R.string.remove_user).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String email = subtitle.getText().toString();
                        long groupId;
                        GroupViewModel instance = GroupViewModel.getCurrentViewModel();
                        groupId = instance.getGroup().getValue().getId();
                        instance.deleteMember(groupId, email);
                        return true;
                    }
                });
            }
        }
    }

}
