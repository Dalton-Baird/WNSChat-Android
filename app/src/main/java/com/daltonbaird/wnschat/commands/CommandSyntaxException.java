package com.daltonbaird.wnschat.commands;

/**
 * Created by Dalton on 3/27/2016.
 */
public class CommandSyntaxException extends CommandException
{
    public CommandSyntaxException()
    {
        super();
    }

    public CommandSyntaxException(String message)
    {
        super(message);
    }

    public CommandSyntaxException(String message, Throwable t)
    {
        super(message, t);
    }

    public CommandSyntaxException(Throwable t)
    {
        super(t);
    }
}
