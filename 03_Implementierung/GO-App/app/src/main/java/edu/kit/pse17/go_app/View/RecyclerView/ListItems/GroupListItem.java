package edu.kit.pse17.go_app.View.RecyclerView.ListItems;

import android.graphics.drawable.Icon;

import edu.kit.pse17.go_app.Model.Group;

/**
 * Diese Klasse repräsentiert ListItems, die Informationen über eine Gruppe in einem RecyclerView darstellen sollen
 *
 * Created by tina on 17.06.17.
 */

public class GroupListItem implements ListItem<Integer> {

    private static final String SUBTITLE_TEXT = " Teilnehmer";

    private String name; //name of the group
    private int memberCount; //# of members in the group
    private Icon icon; //groupIcon

    /**
     * Konstruktor
     * @param title Gruppenname
     * @param memberCount Anzahl der Gruppenmitglieder
     * @param icon Gruppenbild
     */
    public GroupListItem(String title, int memberCount, Icon icon) {
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
        this.memberCount = group.getMemberCount();
        this.icon = group.getIcon();
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
