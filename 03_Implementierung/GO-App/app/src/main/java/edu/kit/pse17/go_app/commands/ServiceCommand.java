package edu.kit.pse17.go_app.commands;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by tina on 30.06.17.
 */

public abstract class ServiceCommand extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ServiceCommand(String name) {
        super(name);
    }


    public void execute(Activity activity, @Nullable Intent intent) {

        activity.startService(intent);

    }
}
