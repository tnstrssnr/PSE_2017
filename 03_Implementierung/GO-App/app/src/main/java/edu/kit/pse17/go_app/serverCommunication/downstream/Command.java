package edu.kit.pse17.go_app.serverCommunication.downstream;

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
 * Created by Сеня on 11.08.2017.
 */

public enum Command {
    ADMIN_ADDED_EVENT {
        @Override
        public ServerCommand getCommand() {
            return new AdminAddedCommand();
        }
    },

    GO_ADDED_EVENT {
        @Override
        public ServerCommand getCommand() {
            return new GoAddedCommand();
        }
    },

    GO_EDITED_EVENT {
        @Override
        public ServerCommand getCommand() {
            return new GoEditedCommand();
        }
    },

    GO_REMOVED_EVENT {
        @Override
        public ServerCommand getCommand() {
            return new GoRemovedCommand();
        }
    },

    GROUP_EDITED_EVENT {
        @Override
        public ServerCommand getCommand() {
            return new GroupEditedCommand();
        }
    },

    GROUP_REMOVED_EVENT {
        @Override
        public ServerCommand getCommand() {
            return new GroupRemovedCommand();
        }
    },

    GROUP_REQUEST_RECEIVED_EVENT {
        @Override
        public ServerCommand getCommand() {
            return new GroupRequestReceivedCommand();
        }
    },

    MEMBER_ADDED_EVENT {
        @Override
        public ServerCommand getCommand() {
            return new MemberAddedCommand();
        }
    },

    MEMBER_REMOVED_EVENT {
        @Override
        public ServerCommand getCommand() {
            return new MemberRemovedCommand();
        }
    },

    REQUEST_DENIED_EVENT {
        @Override
        public ServerCommand getCommand() {
            return new RequestDeniedCommand();
        }
    },

    STATUS_CHANGED_COMMAND {
        @Override
        public ServerCommand getCommand() {
            return new StatusChangedCommand();
        }
    },

    USER_DELETED_EVENT {
        @Override
        public ServerCommand getCommand() {
            return new UserDeletedCommand();
        }
    };


    public abstract ServerCommand getCommand();
}
