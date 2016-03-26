package com.daltonbaird.wnschat;

import com.daltonbaird.wnschat.commands.PermissionLevel;

/**
 * Created by Dalton on 3/26/2016.
 */
public class ClientUser implements IUser
{
    /** The client's username */
    private String username;

    /** The client's permission level */
    private PermissionLevel permissionLevel;

    public ClientUser()
    {
        this(null, PermissionLevel.USER);
    }

    public ClientUser(String username, PermissionLevel permissionLevel)
    {
        this.username = username;
        this.permissionLevel = permissionLevel;
    }

    @Override
    public String getUsername()
    {
        return this.username;
    }

    @Override
    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public PermissionLevel getPermissionLevel()
    {
        return this.permissionLevel;
    }

    @Override
    public void setPermissionLevel(PermissionLevel permissionLevel)
    {
        this.permissionLevel = permissionLevel;
    }

    @Override
    public void sendMessage(String message)
    {
        //TODO: Implement this!
    }
}
