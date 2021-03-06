package edu.kit.pse17.go_app.view.recyclerView.listItems;

import android.graphics.drawable.Drawable;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.view.GroupListActivity;

/**
 *  Diese Klasse repräsentiert ListItems, die Informationen über einen User in einem RecyclerView darstellen sollen
 *
 * Created by tina on 19.06.17.
 */

public class UserStatusListItem implements ListItem<Status> {

    private static final String going = "is going";
    private static final String gone = "is out";
    private static final String notGoing = "not going";
    private static final String[] statusArr = {notGoing, going, gone};

    private String title;
    private Status status;
    private Drawable icon;

    /**
     * Konstruktor
     * @param title Benutzername
     * @param status Status des Users
     * //@param icon Profilbild
     */
    public UserStatusListItem(String title, Status status) {
        this.title = title;
        this.status = status;
        if(status.ordinal() == 0){
            //ie not going
            this.icon = GroupListActivity.user_not_going_icon;
        } else if(status.ordinal() == 1){
            //ie going
            this.icon = GroupListActivity.user_going_icon;
        } else {
            //ordinal 2, ie gone, user is out
            this.icon = GroupListActivity.user_gone_icon;
        }
    }

    public UserStatusListItem(User user, Go go) {
        this.title = user.getName();
        //this.icon = user.getIcon();
        //this.status = go.getUserStatus();
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
    public Drawable getIcon() {
        return icon;
    }
/*
    @Override
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }*/
}
