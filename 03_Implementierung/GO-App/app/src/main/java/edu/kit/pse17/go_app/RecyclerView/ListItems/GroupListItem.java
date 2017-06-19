package edu.kit.pse17.go_app.RecyclerView;

import android.graphics.drawable.Icon;

import edu.kit.pse17.go_app.Group;
import edu.kit.pse17.go_app.RecyclerView.ListItem;

/**
 * This class represents ListItems that display information about a group to be displayed in a RecyclerView
 * Created by tina on 17.06.17.
 */

public class GroupListItem implements ListItem<Integer> {

    private static final String SUBTITLE_TEXT = " Teilnehmer";

    private String name; //name of the group
    private int memberCount; //# of members in the group
    private Icon icon; //groupIcon

    public GroupListItem(String title, int memberCount, Icon icon) {
        this.name = title;
        this.memberCount = memberCount;
        this.icon = icon;
    }

    public GroupListItem(Group group) {

    }


    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public void setTitle(String title) {
        this.name = title;
    }

    @Override
    public String getSubtitle() {
        return String.valueOf(memberCount) + SUBTITLE_TEXT;
    }

    @Override
    public void setSubtitle(Integer memberCount) {
        this.memberCount = memberCount;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
