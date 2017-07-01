package edu.kit.pse17.go_app.controller;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import edu.kit.pse17.go_app.controller.ServiceCommand;

/**
 * Created by tina on 30.06.17.
 */

public class ChangeGroupDataCommand extends ServiceCommand {

    public static final String GROUP_CREATED = "group_created";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ChangeGroupDataCommand(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Intent resultIntent = new Intent(GROUP_CREATED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
    }
}
