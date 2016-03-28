package com.daltonbaird.wnschat.commands;

import com.daltonbaird.wnschat.IUser;
import com.daltonbaird.wnschat.utilities.BinaryEventHandler;

/**
 * Created by Dalton on 3/27/2016.
 */
public class Command
{
    /** The name of the command, what you have to type in */
    public final String name;
    /** The description of the command */
    public final String description;
    /** A string describing how to use the command */
    public final String usage;
    /** The minimum permission level required to run the command */
    public final PermissionLevel permissionLevel;

    /**
     * Constructs a new Command
     * @param name The name of the command, what you have to type in
     * @param description The description of the command
     * @param usage A string describing how to use the command
     * @param permissionLevel The minimum permission level required to run the command
     */
    public Command(String name, String description, String usage, PermissionLevel permissionLevel)
    {
        if (name.contains(" "))
            throw new IllegalArgumentException("Invalid command name, contains a space.");

        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permissionLevel = permissionLevel;

        Commands.allCommands.add(this); //Add this command to the list of commands
    }

    /**
     * Called when the command is executed.  CommandExceptions thrown here will be caught by the system.
     * @param user The command user
     * @param restOfLine The rest of the line that the command was entered on
     */
    public void onExecute(IUser user, String restOfLine)
    {
        this.onExecute(user, restOfLine, null);
    }

    /**
     * Called when the command is executed.  CommandExceptions thrown here will be caught by the system.
     * @param user The command user
     * @param restOfLine The rest of the line that the command was entered on
     * @param permissionLevelOverride Use this to override the permission level of the user
     */
    public void onExecute(IUser user, String restOfLine, PermissionLevel permissionLevelOverride)
    {
        if (this.canUserExecuteCommand(user) || permissionLevelOverride != null && permissionLevelOverride.id >= this.permissionLevel.id)
            this.execute.fire(user, restOfLine);
        else
            throw new CommandException(String.format("You do not have permission to use this command! Your permission level: %s, Required: %s", user.getPermissionLevel(), this.permissionLevel));
    }

    /**
     * Finds out if the user can use this command
     * @param user The user trying to use the command
     * @return True if the user can use the command
     */
    public boolean canUserExecuteCommand(IUser user)
    {
        return user.getPermissionLevel().id >= this.permissionLevel.id;
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Command && this.name.equals(((Command)o).name);
    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    public void clearExecuteHandlers()
    {
        this.execute.clear();
    }

    public final BinaryEventHandler<IUser, String> execute = new BinaryEventHandler<IUser, String>();
}
