package com.daltonbaird.wnschat.commands;

/**
 * An enum to represent the various command permission levels
 *
 * Created by Dalton on 3/26/2016.
 */
public enum PermissionLevel
{
    USER(0),
    OPERATOR(1),
    ADMIN(2),
    SERVER(3);

    public final int id;

    PermissionLevel(int id)
    {
        this.id = id;
    }
}
