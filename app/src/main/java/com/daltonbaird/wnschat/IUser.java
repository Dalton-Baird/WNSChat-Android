package com.daltonbaird.wnschat;

import com.daltonbaird.wnschat.commands.PermissionLevel;

/**
 * Created by Dalton on 3/26/2016.
 */
public interface IUser
{
    /** Gets the user's username */
    String getUsername();

    /** Sets the user's username */
    void setUsername(String username);

    /** Gets the user's permission level */
    PermissionLevel getPermissionLevel();

    /** Sets the user's permission level */
    void setPermissionLevel(PermissionLevel permissionLevel);

    /**
     * Sends the user a message
     * @param message The message to send
     */
    void sendMessage(String message);
}
