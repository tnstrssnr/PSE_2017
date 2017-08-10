package edu.kit.pse17.go_app.serverCommunication.downstream;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import edu.kit.pse17.go_app.сommand.AdminAddedCommand;
import edu.kit.pse17.go_app.сommand.GoAddedCommand;
import edu.kit.pse17.go_app.сommand.GoEditedCommand;
import edu.kit.pse17.go_app.сommand.GoRemovedCommand;
import edu.kit.pse17.go_app.сommand.GroupEditedCommand;
import edu.kit.pse17.go_app.сommand.GroupRemovedCommand;
import edu.kit.pse17.go_app.сommand.GroupRequestReceivedCommand;
import edu.kit.pse17.go_app.сommand.MemberAddedCommand;
import edu.kit.pse17.go_app.сommand.MemberRemovedCommand;
import edu.kit.pse17.go_app.сommand.RequestDeniedCommand;
import edu.kit.pse17.go_app.сommand.ServerCommand;
import edu.kit.pse17.go_app.сommand.StatusChangedCommand;
import edu.kit.pse17.go_app.сommand.UserDeletedCommand;

/**
 * Created by Сеня on 09.08.2017.
 */

@Singleton
public class CommandProvider {
    private static CommandProvider provider;

    private Map<String, ServerCommand> commands;

    private CommandProvider() {
        commands = new HashMap<>();
        commands.put("ADMIN_ADDED_EVENT", new AdminAddedCommand());
        commands.put("GO_ADDED_EVENT", new GoAddedCommand());
        commands.put("GO_EDITED_EVENT", new GoEditedCommand());
        commands.put("GO_REMOVED_EVENT", new GoRemovedCommand());
        commands.put("GROUP_EDITED_EVENT", new GroupEditedCommand());
        commands.put("GROUP_REMOVED_EVENT", new GroupRemovedCommand());
        commands.put("GROUP_REQUEST_RECEIVED_EVENT", new GroupRequestReceivedCommand());
        commands.put("MEMBER_ADDED_EVENT", new MemberAddedCommand());
        commands.put("MEMBER_REMOVED_EVENT", new MemberRemovedCommand());
        commands.put("REQUEST_DENIED_EVENT", new RequestDeniedCommand());
        commands.put("STATUS_CHANGED_COMMAND", new StatusChangedCommand());
        commands.put("USER_DELETED_EVENT", new UserDeletedCommand());
    }

    public static CommandProvider getInstance() {
        if (provider == null) {
            provider = new CommandProvider();
        }

        return provider;
    }


    public Map<String, ServerCommand> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, ServerCommand> commands) {
        this.commands = commands;
    }
}
