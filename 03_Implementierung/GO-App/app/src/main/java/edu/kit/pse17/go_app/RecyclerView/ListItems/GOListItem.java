package edu.kit.pse17.go_app.RecyclerView.ListItems;

import android.graphics.drawable.Icon;

import java.util.Date;

import edu.kit.pse17.go_app.GO;


/**
 * This class represents ListItems that display information about a GO to be displayed in a RecyclerView
 * Created by tina on 17.06.17.
 */

public class GOListItem implements ListItem<Date> {

    private static final String SUBTITLE_TEXT = "Startzeitpunkt: ";

    private String name;
    private Date start;
    private Icon icon;

    public GOListItem(String name, Date start, Icon icon) {
        this.name = name;
        this.start = start;
        this.icon = icon;
    }

    public GOListItem(GO go) {
        this.name = go.getName();
        this.start = go.getStart();
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
        return SUBTITLE_TEXT + start.toString();
    }

    @Override
    public void setSubtitle(Date date) {
        this.start = date;
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public void setIcon(Icon icon) {

    }
}
