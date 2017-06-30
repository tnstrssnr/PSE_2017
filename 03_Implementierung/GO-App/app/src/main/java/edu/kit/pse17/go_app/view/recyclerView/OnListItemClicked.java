package edu.kit.pse17.go_app.View.RecyclerView;

/**
 * ClickListener für die ListItems eines RecyclerViews
 *
 * Created by tina on 17.06.17.
 */

public interface OnListItemClicked {

    /**
     * führt gewünschte Aktion der implemetierenden Klasse aus, falls auf das ListItem an Position position geklickt wird
     * @param position Position des ListItems, auf das geklickt wurde
     */
    void onItemClicked(int position);
}
