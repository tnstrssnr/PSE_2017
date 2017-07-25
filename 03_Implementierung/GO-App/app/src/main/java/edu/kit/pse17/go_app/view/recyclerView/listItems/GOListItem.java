package edu.kit.pse17.go_app.view.recyclerView.listItems;

import android.graphics.drawable.Drawable;

import java.util.Date;

import edu.kit.pse17.go_app.model.entities.Go;


/**
 * Diese Klasse repräsentiert ListItems, die Informationen über ein Go in einem RecyclerView darstellen sollen
 *
 * Created by tina on 17.06.17.
 */

public class GOListItem implements ListItem<Date> {

    private static final String SUBTITLE_TEXT = "Startzeitpunkt: ";

    private String name;
    private Date start;
    private Drawable icon;

    /**
     * Konstruktor
     * @param name Go-Bezeichnung
     * @param start Startzeitpunkt des GOs
     * @param icon Go-Icon
     */
    public GOListItem(String name, Date start, Drawable icon) {
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

    /*@Override
    public Drawable getIcon() {
        return null;
    }

    @Override
    public void setIcon(Drawable icon) {

    }*/
}
