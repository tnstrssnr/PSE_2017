package edu.kit.pse17.go_app.controller;

import android.app.Activity;
import android.content.Intent;

import java.util.Map;

/**
 * Created by tina on 01.07.17.
 */

public class CommandHandler {

    private Map<String, ServiceCommand> commands;

    public void handleCommand(Activity activity, String viewId, Intent intent) {
        commands.get(viewId).execute(activity, intent);
    }

    public void register(String viewId, ServiceCommand serviceCommand) {
        commands.put(viewId, serviceCommand);
    }

}
