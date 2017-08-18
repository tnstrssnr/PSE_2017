package edu.kit.pse17.go_app.ClientCommunication.Downstream;

/**
 * Enum to identify the different Events that trigger a notification to one of the observers. Each enum value is
 * associated w/ an observer.
 */
public enum EventArg {

    GROUP_REMOVED_EVENT,

    MEMBER_REMOVED_EVENT,

    GO_REMOVED_EVENT,

    MEMBER_ADDED_EVENT,

    GROUP_REQUEST_RECEIVED_EVENT,

    GROUP_REQUEST_DENIED_EVENT,

    GO_ADDED_EVENT,

    GO_EDITED_EVENT,

    GROUP_EDITED_EVENT,

    ADMIN_ADDED_EVENT,

    STATUS_CHANGED_EVENT,

    USER_DELETED_EVENT

}
