package com.daltonbaird.wnschat.commands;

/**
 * Created by Dalton on 3/27/2016.
 */
public class CommandException extends RuntimeException
{
    public CommandException()
    {
        super();
    }

    public CommandException(String message)
    {
        super(message);
    }

    public CommandException(String message, Throwable t)
    {
        super(message, t);
    }

    public CommandException(Throwable t)
    {
        super(t);
    }
}
