package edu.kit.pse17.go_app.view.recyclerView.listItems;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.view.GroupDetailActivity;
import edu.kit.pse17.go_app.view.GroupListActivity;
import edu.kit.pse17.go_app.view.recyclerView.OnListItemClicked;

/**
 * Diese Klasse repräsentiert ListItems, die Informationen über eine Gruppe in einem RecyclerView darstellen sollen
 *
 * Created by tina on 17.06.17.
 */

public class GroupListItem implements ListItem<Integer>, OnListItemClicked {

    private static final String SUBTITLE_TEXT = " Teilnehmer";

    private String name; //name of the group
    private int memberCount; //# of members in the group
    private Drawable icon; //groupIcon
    private Group group;

    /**
     * Konstruktor
     * @param title Gruppenname
     * @param memberCount Anzahl der Gruppenmitglieder
     * @param icon Gruppenbild
     */
    public GroupListItem(String title, int memberCount, Drawable icon) {
        this.name = title;
        this.memberCount = memberCount;
        this.icon = icon;
    }

    /**
     * Konstruktor
     * @param group gruppen-Objekt, das von dem ListItem repräsentiert werden soll
     */
    public GroupListItem(Group group) {
        this.name = group.getName();
        this.memberCount = group.getMembershipList().size();//group.getMemberCount();
        this.icon = GroupListActivity.default_group_icon;//group.getIcon();
        this.group = group;
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
    public Drawable getIcon() {
        return icon;
    }
/*
    @Override
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }*/

    @Override
    public void onItemClicked(Activity activity, int position) {
        GroupDetailActivity.start(activity, position);
    }
}
