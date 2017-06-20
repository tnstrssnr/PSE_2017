package edu.kit.pse17.go_app.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.View.GroupDetailActivity;

/**
 * Klasse dekoriert die GroupDetailActivity und fügt ihr die Admin-Funktionalitäten hinzu
 *
 * Created by tina on 19.06.17.
 */

public class GroupDetailActivityAdmin extends GroupDetailActivity {

    private GroupDetailActivity gda;

    private ImageView edit;
    private ImageView addMember; //onClick: Dialog mit add User oder add Admin

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        edit  = (ImageView) findViewById(R.id.edit);
        addMember = (ImageView) findViewById(R.id.add_member);

        edit.setVisibility(View.VISIBLE);
        edit.setClickable(true);

        addMember.setVisibility(View.VISIBLE);
        edit.setClickable(true);
    }
}
