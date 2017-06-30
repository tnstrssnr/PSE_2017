package edu.kit.pse17.go_app.view.recyclerView.ListItems;

import android.graphics.drawable.Icon;

import edu.kit.pse17.go_app.model.GO;
import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.User;

/**
 *  Diese Klasse repräsentiert ListItems, die Informationen über einen User in einem RecyclerView darstellen sollen
 *
 * Created by tina on 19.06.17.
 */

public class UserStatusListItem implements ListItem<Status> {

    private static final String going = "Bestätigt";
    private static final String gone = "Unterwegs";
    private static final String notGoing = "Abgelehnt";
    private static final String[] statusArr = {notGoing, going, gone};

    private String title;
    private Status status;
    private Icon icon;

    /**
     * Konstruktor
     * @param title Benutzername
     * @param status Status des Users
     * @param icon Profilbild
     */
    public UserStatusListItem(String title, Status status, Icon icon) {
        title = title;
        status = status;
        icon = icon;
    }

    public UserStatusListItem(User user, GO go) {
        this.title = user.getName();
        this.icon = user.getIcon();
        this.status = go.getUserStatus(user);
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
        return statusArr[this.status.ordinal()];
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
