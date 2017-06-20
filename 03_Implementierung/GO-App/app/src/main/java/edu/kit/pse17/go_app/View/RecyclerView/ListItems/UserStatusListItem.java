package edu.kit.pse17.go_app.View.RecyclerView.ListItems;

import android.graphics.drawable.Icon;

import edu.kit.pse17.go_app.Model.GO;
import edu.kit.pse17.go_app.Model.Status;
import edu.kit.pse17.go_app.Model.User;
import edu.kit.pse17.go_app.View.RecyclerView.ListItems.ListItem;

/**
 *  Diese Klasse repräsentiert ListItems, die Informationen über einen User in einem RecyclerView darstellen sollen
 *
 * Created by tina on 19.06.17.
 */

public class UserStatusListItem implements ListItem<Status> {

    private String title;
    private Status status;
    private Icon icon;

    /**
     * Konstruktor
     * @param title Benutzername
     * @param status EMail-Adresse, die zur Anmeldung verwendet wurde
     * @param icon Profilbild
     */
    public UserStatusListItem(String title, Status status, Icon icon) {
        this.title = title;
        this.status = status;
        this.icon = icon;
    }

    /**
     * Konstruktor
     * @param user Das User-Objekt, das von dem ListItem repräsentiert werden soll
     */
    public UserStatusListItem(User user, GO go) {
        title = user.getName();
        status = go.getUserStatus(user);
        icon = user.getIcon();
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
        return status.toString();
    }

    @Override
    public void setSubtitle(Status s) {
        this.status = s;
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
