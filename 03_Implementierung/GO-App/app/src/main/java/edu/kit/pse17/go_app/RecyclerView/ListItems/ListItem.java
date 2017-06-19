package edu.kit.pse17.go_app.RecyclerView.ListItems;

import android.graphics.drawable.Icon;

/**
 * Created by tina on 18.06.17.
 */

public interface ListItem<T> {

    public String getTitle();

    public void setTitle(String title );

    public String getSubtitle();

    public void setSubtitle(T t);

    public Icon getIcon();

    public void setIcon(Icon icon);

}
