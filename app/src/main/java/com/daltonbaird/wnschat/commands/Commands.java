package com.daltonbaird.wnschat.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dalton on 3/27/2016.
 */
public class Commands
{
    /** A list of all of the commands */
    public static final List<Command> allCommands = new ArrayList<Command>();

    public static final Command help = new Command("help", "Shows help about the commands you can enter", "/help", PermissionLevel.USER);
    public static final Command meCommand = new Command("me", "Allows you to say something in third person.", "/me does some action.", PermissionLevel.USER);
    public static final Command setUserLevel = new Command("setUserLevel", "Sets a user's authority level.", "/setUserLevel USERNAME USER|OPERATOR|ADMIN|SERVER", PermissionLevel.ADMIN);
    public static final Command kick = new Command("kick", "Kicks a user from the server.  They can still join again.", "kick USERNAME [REASON]", PermissionLevel.OPERATOR);
    public static final Command list = new Command("list", "Lists the users connected to the server.", "/list", PermissionLevel.USER);
    public static final Command say = new Command("say", "Makes you say something, same as just typing a message.", "/say Hello World!", PermissionLevel.USER);
    public static final Command tell = new Command("tell", "Sends another user a message, only them and the server will receive it.", "/tell USERNAME MESSAGE", PermissionLevel.USER);
    public static final Command stats = new Command("stats", "Prints information about the server.", "/stats", PermissionLevel.OPERATOR);
    public static final Command ping = new Command("ping", "Pings the server or a user, the server or user will reply after receiving the message.", "/ping or /ping USER", PermissionLevel.USER);
    public static final Command stop = new Command("stop", "Shuts down the server.", "/stop", PermissionLevel.ADMIN);
    public static final Command password = new Command("password", "Changes the server's password.", "/password PASSWORD or /password (removes password)", PermissionLevel.ADMIN);
    public static final Command serverName = new Command("serverName", "Changes the server's name.", "/serverName My Awesome Server", PermissionLevel.ADMIN);
    public static final Command sudo = new Command("sudo", "Makes another user execute a command, optionally with your permission level. Only works on server side commands.", "/sudo USERNAME [useMyPermissions] COMMAND", PermissionLevel.OPERATOR);
    public static final Command logout = new Command("logout", "Logs you out of the server.", "/logout", PermissionLevel.USER);
    public static final Command clear = new Command("clear", "Clears your chat history.", "/clear", PermissionLevel.USER);
}
