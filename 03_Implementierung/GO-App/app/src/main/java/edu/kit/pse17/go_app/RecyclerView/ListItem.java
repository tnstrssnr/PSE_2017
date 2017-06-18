package edu.kit.pse17.go_app.RecyclerView;

import android.graphics.drawable.Icon;

/**
 * This class represents a ListItem to be displayed in a RecyclerView.
 * Created by tina on 17.06.17.
 */

abstract public class ListItem {

    private static final Icon default_icon = null; //default icon if no icon is selected

    private String title; // title of the Item
    private String subtitle;
    private Object subtitleData;
    private Icon icon; //icon displayed next to the title

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitleText() {
        return this.subtitle;
    }

    public void setSubtitle(Object data) {
        this.subtitle = generateSubtitleText(data);
    }

    public Object getSubtitleData() {
        return subtitleData;
    }

    private String generateSubtitleText(Object subtitleData) {
        return String.valueOf(subtitleData);
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
