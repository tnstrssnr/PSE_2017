package edu.kit.pse17.go_app.commands;

import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by tina on 01.07.17.
 */

public class RemoveGroupmemberCommand extends ServiceCommand {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RemoveGroupmemberCommand(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
