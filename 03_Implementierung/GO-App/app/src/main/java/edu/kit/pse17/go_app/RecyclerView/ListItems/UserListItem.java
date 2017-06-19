package edu.kit.pse17.go_app.RecyclerView.ListItems;

import android.graphics.drawable.Icon;

import edu.kit.pse17.go_app.RecyclerView.ListItems.ListItem;
import edu.kit.pse17.go_app.User;

/**
 * Created by tina on 19.06.17.
 */

public class UserListItem implements ListItem<String> {

    private String title;
    private String email;
    private Icon icon;

    public UserListItem(String title, String email, Icon icon) {
        this.title = title;
        this.email = email;
        this.icon = icon;
    }

    public UserListItem(User user) {
        title = user.getName();
        email = user.getEmail();
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
        return email;
    }

    @Override
    public void setSubtitle(String s) {
        this.email = s;
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
