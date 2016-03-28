package com.daltonbaird.wnschat.utilities;

import com.daltonbaird.wnschat.IUser;
import com.daltonbaird.wnschat.commands.Command;
import com.daltonbaird.wnschat.commands.CommandException;
import com.daltonbaird.wnschat.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dalton on 3/28/2016.
 */
public class ChatUtils
{
    /**
     * Parses the command from the message and the user.  Throws a CommandException if something went wrong.
     * @param user The user that sent the command
     * @param message The raw message that the user sent
     * @throws CommandException If something went wrong while parsing the command
     * @return A CommandParseResult containing the command parsed, and the rest of the text
     */
    public static CommandParseResult parseCommand(IUser user, String message) throws CommandException
    {
        Command command = null;
        String restOfCommand = message;

        if (message.startsWith("/")) //If it is a command
        {
            //Matches command names, for example, in "/say Hello World!", it would match "/say"
            Pattern pattern = Pattern.compile("^/(\\w)+");
            Matcher matcher = pattern.matcher(message);

            if (!matcher.find())
                throw new CommandException(String.format("Unknown command \"%s\"", message));

            int endOfCommandName = matcher.end();
            String commandName = message.substring(1, endOfCommandName);
            restOfCommand = message.substring(endOfCommandName).trim();

            System.out.printf("ChatUtils.ParseCommand(%s, \"%s\"):\n", user, message);
            System.out.printf("\tendOfCommandName: %s, commandName: \"%s\", restOfCommand: \"%s\"\n", endOfCommandName, commandName, restOfCommand);

            for (Command cmd : Commands.allCommands)
                if (cmd.name.equals(commandName))
                {
                    command = cmd;
                    break;
                }

            if (command == null)
                throw new CommandException(String.format("Unknown command \"%s\"", commandName));
        }

        if (command == null) //If the command is still null, set it to say
            command = Commands.say;

        return new CommandParseResult(command, restOfCommand);
    }

    /** Custom class, since there are no Tuples available */
    public static class CommandParseResult
    {
        public final Command command;
        public final String restOfCommand;

        public CommandParseResult(Command command, String restOfCommand)
        {
            this.command = command;
            this.restOfCommand = restOfCommand;
        }
    }
}
