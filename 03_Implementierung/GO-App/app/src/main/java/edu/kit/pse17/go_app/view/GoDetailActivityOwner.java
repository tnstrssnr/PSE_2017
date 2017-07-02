package edu.kit.pse17.go_app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import edu.kit.pse17.go_app.R;

/**
 * Die Klasse dekoriert dir Aktivity-Klasse "GoDetailActivity".
 *
 * Die Go-Detailansicht eines Go-Verantwortlichen
 * unterscheidet dich von der Detailansicht eines "normalen" Teilnahemers nur in einer zusätzlichen Schaltfläche ("edit"), die geklickt
 * werden kann, um die Änderungsansicht des GOs aufzurufen.
 */

public class GoDetailActivityOwner extends GoDetailActivity {

    /**
     * zusätzliche Schaltflöche, um die GoEditView aufzurufen
     */
    private ImageView edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        edit = (ImageView) findViewById(R.id.edit);

        edit.setVisibility(View.VISIBLE);
        edit.setClickable(true)
        ;
    }
}
