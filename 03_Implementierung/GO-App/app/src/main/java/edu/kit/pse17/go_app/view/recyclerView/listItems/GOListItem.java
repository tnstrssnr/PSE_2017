package edu.kit.pse17.go_app.view.recyclerView.listItems;

import android.graphics.drawable.Drawable;

import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.view.GroupListActivity;


/**
 * Diese Klasse repräsentiert ListItems, die Informationen über ein Go in einem RecyclerView darstellen sollen
 *
 * Created by tina on 17.06.17.
 */

public class GOListItem implements ListItem<String> {

    private static final String SUBTITLE_TEXT = "Startzeitpunkt: ";

    private String name;
    private String start;
    private Drawable icon;

    /**
     * Konstruktor
     * @param name Go-Bezeichnung
     * @param start Startzeitpunkt des GOs
     * @param icon Go-Icon
     */
    public GOListItem(String name, String start, Drawable icon) {
        this.name = name;
        this.start = start;
        this.icon = icon;
    }

    /**
     * Konstruktor
     * @param go Go-Objekt, das von dem ListItem repräsentiert werden soll
     */
    public GOListItem(Go go) {
        this.name = go.getName();
        this.start = go.getStart();
        this.icon = GroupListActivity.default_group_icon;
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
    public void setSubtitle(String date) {
        this.start = date;
    }

    @Override
    public Drawable getIcon() {
        return icon;
    }
/*
    @Override
    public void setIcon(Drawable icon) {

    }*/
}
