package com.csi.contactmanager.model;

/**
 * Enum created for compile time validation
 */
public enum MainMenuAction {

    ADD_CONTACT(1),
    UPDATE_CONTACT(2),
    DELETE_CONTACT(3),
    SEARCH_CONTACT(4),
    LIST_CONTACTS(5),
    QUIT(6),
    UNKNOWN_COMMAND(7);

    private final int commandNo;

    MainMenuAction(int commandNo){
        this.commandNo = commandNo;
    }

    /**
     * Need this method for int - enum functionality because an enum cannot have a public constructor
     * We could move this enum declaration in the classes that use it, but I decided to keep it separated and use a method instead
     * @param commandNo
     * @return
     */
    public static MainMenuAction valueOf(int commandNo) {
        for (MainMenuAction action : values()) {
            if (action.commandNo == commandNo) {
                return action;
            }
        }
        return UNKNOWN_COMMAND;
    }

}
