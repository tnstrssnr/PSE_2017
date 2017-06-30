package edu.kit.pse17.go_app.view.recyclerView.ListItems;

import android.graphics.drawable.Icon;

/**
 * Interface für ListItems, die die Datenobjekt in den verschiedenen RecyclerViews der App sind
 *
 * Created by tina on 18.06.17.
 */

public interface ListItem<T> {

    /**
     * getter-Methode für Überschrift des ListItems
     * @return Titel des Datenobjekts
     */
    String getTitle();

    /**
     * setter-Methode für Überschrift des ListItems
     * @param title der neue Titel
     */
    void setTitle(String title );

    /**
     * getter-Methode für Untertitel des ListItems. Muss ggfs. erst generiert werden, die Information wird als Datentyp T im Objekt gespeichert
     * @return Untertitel des Datenobjekts
     */
    String getSubtitle();

    /**
     * setter-Methode für Untertitel. Methode erwartet Datentyp T, der Untertitel wird dann innerhalb der Klasse als String-Objekt erzeugt
     * @param t Objekt/Datentyp, aus dem Untertitel erzeugt wird
     */
    void setSubtitle(T t);

    /**
     * getter-Methode für Icon des ListItems
     * @return Icon des Datenobjekts
     */
    Icon getIcon();

    /**
     * setter-Methode für icon des ListItems
     * @param icon das neue Icon
     */
    void setIcon(Icon icon);

}
