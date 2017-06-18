package edu.kit.pse17.go_app.RecyclerView.GroupRecyclerView;

import android.graphics.drawable.Icon;

import edu.kit.pse17.go_app.Group;
import edu.kit.pse17.go_app.RecyclerView.ListItem;

/**
 * This class represents ListItems that display information about a group to be displayed in a RecyclerView
 * Created by tina on 17.06.17.
 */

public class GroupListItem extends ListItem {

    public GroupListItem(Group group) {
        this.setTitle(group.getName());
        this.setSubtitle((Integer) group.getMemberCount());
        this.setIcon(group.getIcon());
    }

    private String generateSubtitleText(Integer members) {
        return String.valueOf(members) + " members";
    }

}
