package edu.kit.pse17.go_app.view.recyclerView.listItems;

import android.graphics.drawable.Drawable;

import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.view.GroupListActivity;

/**
 *  Diese Klasse repräsentiert ListItems, die Informationen über einen User in einem RecyclerView darstellen sollen
 *
 * Created by tina on 19.06.17.
 */

public class UserMailListItem implements ListItem<String> {

    private String title;
    private String email;
    private Drawable icon;

    /**
     * Konstruktor
     * @param title Benutzername
     * @param email EMail-Adresse, die zur Anmeldung verwendet wurde
     */
    public UserMailListItem(String title, String email, boolean isAdmin) {
        this.title = title;
        this.email = email;
        if(isAdmin)
            this.icon = GroupListActivity.default_admin_icon;
        else
            this.icon = GroupListActivity.default_user_icon;
    }

    /**
     * Konstruktor
     * @param user Das User-Objekt, das von dem ListItem repräsentiert werden soll
     */
    public UserMailListItem(User user) {
        this.title = user.getName();
        this.email = user.getEmail();

    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSubtitle() {
        return email;
    }

    @Override
    public void setSubtitle(String s) {
        this.email = s;
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
}
