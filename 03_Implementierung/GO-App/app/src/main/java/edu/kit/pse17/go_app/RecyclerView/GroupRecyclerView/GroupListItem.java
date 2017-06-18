package edu.kit.pse17.go_app.RecyclerView.GroupRecyclerView;

import android.graphics.drawable.Icon;

import edu.kit.pse17.go_app.RecyclerView.ListItem;

/**
 * This class represents ListItems that display information about a group to be displayed in a RecyclerView
 * Created by tina on 17.06.17.
 */

public class GroupListItem implements ListItem<Integer> {

    private static final String SUBTITLE_TEXT = " Teilnemher";

    private String name;
    private int memberCount;


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
        return null;
    }

    @Override
    public void setIcon(Icon icon) {

    }
}
