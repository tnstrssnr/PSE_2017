package edu.kit.pse17.go_app.RecyclerView.GORecyclerView;

import android.graphics.drawable.Icon;

import java.util.Date;

import edu.kit.pse17.go_app.GO;
import edu.kit.pse17.go_app.RecyclerView.ListItem;


/**
 * This class represents ListItems that display information about a GO to be displayed in a RecyclerView
 * Created by tina on 17.06.17.
 */

public class GOListItem extends ListItem {

    /**
     *
     * @param go Das GO für das ein List-Item erstellt werden soll
     */
    public GOListItem(GO go) {
        this.setTitle(go.getName());
        this.setSubtitle(go.getStart());
        this.setIcon(null);
    }

    private String generateSubtitleText(Date date) {
        return "Starzeitpunkt: " + date.toString();
    }
}
