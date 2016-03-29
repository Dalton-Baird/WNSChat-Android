package com.daltonbaird.wnschat.viewmodels;

import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.databinding.ObservableShort;
import android.util.Log;

import com.daltonbaird.wnschat.ClientUser;
import com.daltonbaird.wnschat.IUser;
import com.daltonbaird.wnschat.NetworkManager;
import com.daltonbaird.wnschat.ServerConnection;
import com.daltonbaird.wnschat.commands.Command;
import com.daltonbaird.wnschat.commands.CommandException;
import com.daltonbaird.wnschat.commands.Commands;
import com.daltonbaird.wnschat.commands.PermissionLevel;
import com.daltonbaird.wnschat.eventhandlers.TernaryEventHandler;
import com.daltonbaird.wnschat.functional.Action;
import com.daltonbaird.wnschat.functional.BinaryAction;
import com.daltonbaird.wnschat.functional.Function;
import com.daltonbaird.wnschat.functional.Predicate;
import com.daltonbaird.wnschat.functional.UnaryAction;
import com.daltonbaird.wnschat.functional.UnaryPredicate;
import com.daltonbaird.wnschat.messages.Message;
import com.daltonbaird.wnschat.messages.MessageText;
import com.daltonbaird.wnschat.packets.Packet;
import com.daltonbaird.wnschat.packets.PacketDisconnect;
import com.daltonbaird.wnschat.packets.PacketLogin;
import com.daltonbaird.wnschat.packets.PacketPing;
import com.daltonbaird.wnschat.packets.PacketServerInfo;
import com.daltonbaird.wnschat.packets.PacketSimpleMessage;
import com.daltonbaird.wnschat.packets.PacketUserInfo;
import com.daltonbaird.wnschat.eventhandlers.EventHandler;
import com.daltonbaird.wnschat.eventhandlers.UnaryEventHandler;
import com.daltonbaird.wnschat.utilities.ButtonCommand;
import com.daltonbaird.wnschat.utilities.ChatUtils;
import com.daltonbaird.wnschat.utilities.MathUtils;
import com.daltonbaird.wnschat.utilities.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dalton on 3/26/2016.
 */
public class ChatClientViewModel
{
    /*
    protected Socket client;

    protected ClientUser clientUser;

    protected ServerConnection server;

    protected InetAddress serverIP;

    protected short serverPort;

    protected List<Message> messageLog;
    */

    public ChatClientViewModel(String username, InetAddress serverIP, short port)
    {
        //this.messageLog = new ArrayList<Message>();

        this.serverIP.set(serverIP);
        this.serverPort.set(port);
        this.clientUser.set(new ClientUser(username, PermissionLevel.USER, this));

        //Hook up the message log to fire this event when modified
        //this.messageLog.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Message>>() //... (Doesn't work)

        this.message.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback()
        {
            @Override
            public void onPropertyChanged(Observable observable, int i)
            {
                Log.i(this.getClass().getName(), "Message changed!");
            }
        });

        //Create commands
        this.sendCommand = new ButtonCommand(new Action() //OnSend
        {
            @Override
            public void invoke()
            {
                if (ChatClientViewModel.this.server.get().getSocket() != null)
                {
                    try //Try to parse the command
                    {
                        ChatUtils.CommandParseResult result = ChatUtils.parseCommand(ChatClientViewModel.this.clientUser.get(), ChatClientViewModel.this.message.get());

                        result.command.onExecute(ChatClientViewModel.this.clientUser.get(), result.restOfCommand);
                    }
                    catch (CommandException e)
                    {
                        ChatClientViewModel.this.clientUser.get().sendMessage(String.format("Command Error: %s", e.getMessage()));
                    }
                }

                //ChatClientViewModel.this.messageCleared.fire();
                ChatClientViewModel.this.message.set("");
            }
        },
        new Predicate() //CanSend
        {
            @Override
            public boolean invoke()
            {
                String message = ChatClientViewModel.this.message.get();

                return ChatClientViewModel.this.server.get().getSocket() != null && ChatClientViewModel.this.server.get().getSocket().isConnected() && message != null && !message.isEmpty();
            }
        });

        this.disconnectCommand = new ButtonCommand<String>(new UnaryAction<String>() //OnDisconnect
        {
            @Override
            public void invoke(String s)
            {
                ChatClientViewModel.this.disconnectFromServer(s, false, null);
            }
        },
        new UnaryPredicate<String>() //CanDisconnect
        {
            @Override
            public boolean invoke(String s)
            {
                return ChatClientViewModel.this.server.get().getSocket() != null;
            }
        });

        this.logoutCommand = new ButtonCommand(new Action() //OnLogout
        {
            @Override
            public void invoke()
            {
                Commands.logout.onExecute(ChatClientViewModel.this.clientUser.get(), ""); //Have the logout command handle it
            }
        },
        new Predicate() //CanLogout
        {
            @Override
            public boolean invoke()
            {
                return ChatClientViewModel.this.server.get().getSocket() != null && !ChatClientViewModel.this.server.get().getSocket().isOutputShutdown();
            }
        });

        this.initCommands();
    }

    public void initCommands()
    {
        List<Command> commandsToNotSend = new ArrayList<Command>();

        Commands.say.execute.add(new BinaryAction<IUser, String>()
        {
            @Override
            public void invoke(IUser iUser, String s)
            {
                NetworkManager.INSTANCE.writePacket(ChatClientViewModel.this.server.get().getSocket(), new PacketSimpleMessage(s));
            }
        });
        commandsToNotSend.add(Commands.say);

        Commands.logout.execute.add(new BinaryAction<IUser, String>()
        {
            @Override
            public void invoke(IUser iUser, String s)
            {
                String disconnectReason = "Logging out";
                if (ChatClientViewModel.this.disconnectCommand.canExecute(disconnectReason))
                    ChatClientViewModel.this.disconnectCommand.execute(disconnectReason);
            }
        });
        commandsToNotSend.add(Commands.logout);

        Commands.ping.execute.add(new BinaryAction<IUser, String>()
        {
            @Override
            public void invoke(IUser iUser, String s)
            {
                String usernameToPing = s.trim();

                PacketPing packet = new PacketPing();
                packet.destinationUsername = usernameToPing;
                packet.packetState = PacketPing.State.GOING_TO;
                packet.sendingUsername = iUser.getUsername();

                packet.addTimestamp(iUser.getUsername()); //Add a timestamp now

                NetworkManager.INSTANCE.writePacket(ChatClientViewModel.this.server.get().getSocket(), packet); //Send the packet
            }
        });
        commandsToNotSend.add(Commands.ping);

        Commands.clear.execute.add(new BinaryAction<IUser, String>()
        {
            @Override
            public void invoke(IUser iUser, String s)
            {
                //Dispose of the messages if they need disposed of
                //for (Message message : ChatClientViewModel.this.messageLog)
                //    if (message instanceof Closeable)
                //        ((Closeable) message).close();

                ChatClientViewModel.this.messageLog.clear(); //TODO: This never gets seen by the Android activity, make an ObservableCollection that can show it
            }
        });
        commandsToNotSend.add(Commands.clear);

        //Hook up unhandled commands to the say command so that the server can handle them
        for (final Command command : Commands.allCommands)
            if (!commandsToNotSend.contains(command))
                command.execute.add(new BinaryAction<IUser, String>()
                {
                    @Override
                    public void invoke(IUser iUser, String s)
                    {
                        Commands.say.onExecute(iUser, String.format("/%s %s", command.name, s));
                    }
                });
    }

    public void unInitCommands()
    {
        for (Command command : Commands.allCommands)
            command.clearExecuteHandlers();
    }

    //PROPERTIES

    public final ObservableField<Socket> tcpClient = new ObservableField<Socket>();

    public final ObservableField<ClientUser> clientUser = new ObservableField<ClientUser>();

    public final ObservableField<ServerConnection> server = new ObservableField<ServerConnection>();

    public final ObservableField<InetAddress> serverIP = new ObservableField<InetAddress>();

    public final ObservableShort serverPort = new ObservableShort();

    public final ObservableList<Message> messageLog = new ObservableArrayList<Message>();

    public final ObservableField<String> message = new ObservableField<String>()
    {
        @Override
        public void set(String value)
        {
            Log.i(this.getClass().getName(), "Message set() called!");

            super.set(value);
        }
    };

    //END PROPERTIES

    /*
    public Socket getClient()
    {
        return client;
    }

    public void setClient(Socket client)
    {
        this.client = client;
    }

    public ClientUser getClientUser()
    {
        return clientUser;
    }

    public void setClientUser(ClientUser clientUser)
    {
        this.clientUser = clientUser;
    }

    public ServerConnection getServer()
    {
        return server;
    }

    public void setServer(ServerConnection server)
    {
        this.server = server;
    }

    public InetAddress getServerIP()
    {
        return serverIP;
    }

    public void setServerIP(InetAddress serverIP)
    {
        this.serverIP = serverIP;
    }

    public short getServerPort()
    {
        return serverPort;
    }

    public void setServerPort(short serverPort)
    {
        this.serverPort = serverPort;
    }

    public List<Message> getMessageLog()
    {
        if (this.messageLog == null)
            this.messageLog = new ArrayList<Message>();

        return messageLog;
    }

    public void setMessageLog(List<Message> messageLog)
    {
        this.messageLog = messageLog;
    }
    */

    /**
     * Attempts to connect to the server
     * @param getPassword A function to call to get a password, if one is required
     * @return true if successful
     */
    public boolean connectToServer(Function<String> getPassword)
    {
        try
        {
            this.displayMessage(String.format("Connecting to server at %s:%d...", this.serverIP.get(), this.serverPort.get()));

            this.tcpClient.set(new Socket(this.serverIP.get(), this.serverPort.get()));
            this.server.set(new ServerConnection(this.tcpClient.get()));

            this.displayMessage("Connected!");

            this.sendCommand.onCanExecuteChanged(); //The send button's CanSend conditions changed
            this.disconnectCommand.onCanExecuteChanged(); //The disconnect command's CanDisconnect conditions changed

            Packet packet = NetworkManager.INSTANCE.readPacket(this.server.get().getSocket());

            if (packet instanceof PacketServerInfo)
            {
                PacketServerInfo serverInfo = (PacketServerInfo)packet;

                //Update the client data on the server, and do a version check.  This may throw an exception
                this.onServerInfoUpdate(serverInfo, true);

                String passwordHash = "";

                if (serverInfo.passwordRequired) //If the server requires a password, get one from the user with the delegate
                {
                    String password = getPassword.invoke();

                    passwordHash = MathUtils.sha1Hash(password); //Compute the SHA-1 hash of the password
                }

                //Login
                NetworkManager.INSTANCE.writePacket(this.server.get().getSocket(), new PacketLogin(NetworkManager.PROTOCOL_VERSION, this.clientUser.get().getUsername(), passwordHash));
            }
            else if (packet instanceof PacketDisconnect)
            {
                PacketDisconnect packetDisconnect = (PacketDisconnect)packet;

                this.displayMessage(String.format("Server refused connection.  Reason: %s", packetDisconnect.reason));
                this.disconnectFromServer(null, false, packetDisconnect.reason);
                return false;
            }
            else
            {
                throw new RuntimeException(String.format("ERROR: Server sent a \"%s\" packet instead of a \"%s\" packet!", packet.getClass().getSimpleName(), PacketServerInfo.class.getSimpleName()));
            }

            new Thread(new Runnable() //Start a thread to process incoming server data
            {
                @Override
                public void run()
                {
                    ChatClientViewModel.this.processServerThread();
                }
            }).start();

            return true;
        }
        catch (Exception e)
        {
            this.displayMessage(String.format("Error encountered in client loop: %s", e));
            this.displayMessage(StringUtils.getStackTrace(e));

            this.disconnectFromServer("Encountered an error while connecting", true, null);
            return false;
        }
    }

    /**
     * Disconnects from the server.  If a non-null string is provided, a disconnect packet will be sent
     * with that as the reason
     * @param clientDisconnectedReason The client's reason for disconnecting
     * @param clientReasonIsBad If true, the client's reason is an error
     * @param serverDisconnectReason The server's reason for disconnecting, will be set if the server
     *                               sends a disconnect packet
     */
    public void disconnectFromServer(String clientDisconnectedReason, boolean clientReasonIsBad, String serverDisconnectReason)
    {
        if (clientDisconnectedReason != null)
        {
            try
            {
                NetworkManager.INSTANCE.writePacket(this.server.get().getSocket(), new PacketDisconnect(clientDisconnectedReason));
            }
            catch (Exception e)
            {
                this.displayMessage(String.format("Error sending disconnect packet: %s", e));
            }
        }

        this.sendCommand.onCanExecuteChanged(); //The send button's CanSend conditions changed
        this.disconnectCommand.onCanExecuteChanged(); //The disconnect command's CanDisconnect conditions changed

        this.unInitCommands(); //Remove the command handlers

        try
        {
            if (this.tcpClient.get() != null)
                this.tcpClient.get().close();

            if (this.server.get() != null)
                this.server.get().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.tcpClient.set(null);
            this.server.set(null);
        }

        this.disconnected.fire(clientDisconnectedReason, clientReasonIsBad, serverDisconnectReason); //Fire the disconnected event
    }

    /**
     * Ran in a separate thread to process incoming information from the server
     */
    public void processServerThread() //TODO: have this be able to disconnect
    {
        if (this.server.get() == null || this.server.get().getSocket() == null)
            throw new NullPointerException("Cannot listen to server, server or server socket is null!");

        for(;;) //Infinite loop
        {
            try
            {
                Packet packet = NetworkManager.INSTANCE.readPacket(this.server.get().getSocket());

                //Decide what to do based on the packet type
                if (packet instanceof PacketSimpleMessage)
                {
                    PacketSimpleMessage packetSimpleMessage = (PacketSimpleMessage)packet;

                    this.displayMessage(packetSimpleMessage.message);
                }
                else if (packet instanceof PacketDisconnect)
                {
                    PacketDisconnect packetDisconnect = (PacketDisconnect)packet;

                    this.displayMessage(String.format("Server closed connection.  Reason: %s", packetDisconnect.reason));

                    return; //Exit thread
                }
                else if (packet instanceof PacketServerInfo)
                {
                    this.onServerInfoUpdate((PacketServerInfo)packet, false);
                }
                else if (packet instanceof PacketPing)
                {
                    PacketPing packetPing = (PacketPing)packet;

                    packetPing.addTimestamp(this.clientUser.get().getUsername()); //Add a timestamp

                    if (packetPing.packetState == PacketPing.State.GOING_TO) //The packet is going somewhere
                    {
                        //It got here
                        packetPing.packetState = PacketPing.State.GOING_BACK; //Send it back
                        NetworkManager.INSTANCE.writePacket(this.server.get().getSocket(), packetPing);
                    }
                    else if (packetPing.packetState == PacketPing.State.GOING_BACK)
                    {
                        //Packet is going back to whoever sent it
                        if (this.clientUser.get().getUsername().equals(packetPing.sendingUsername)) //It's my ping packet
                        {
                            this.displayMessage(packetPing.trace()); //Show the ping trace
                        } else //It's somebody else's packet, but was sent to me
                        {
                            this.displayMessage(String.format("ERROR: Got a ping packet sent back to user \"%s\", but this user is \"%s\"!", packetPing.sendingUsername, this.clientUser.get().getUsername()));
                        }
                    }
                }
                else if (packet instanceof PacketUserInfo)
                {
                    PacketUserInfo packetUserInfo = (PacketUserInfo)packet;

                    if (this.clientUser.get().getUsername().equals(packetUserInfo.username)) //If it's info about this client
                    {
                        this.clientUser.get().setPermissionLevel(packetUserInfo.permissionLevel); //Update the permission level
                        this.clientUser.notifyChange();
                    }
                }
            }
            catch (Exception e)
            {
                if (this.server.get() != null && this.server.get().getSocket() != null) //Only show the errors and disconnect if the server exists
                {
                    this.displayMessage(String.format("Error handling data from server!\n%s", e));
                    this.disconnectFromServer(String.format("Error handling data from server: %s", e), true, null);
                }

                break; //Exit the for loop
            }
        }
    }

    /**
     * Updates the client's data about the server. If checkVersion is true, it does a version check
     * and throws an exception if the versions don't match.
     * @param serverInfo The server info packet
     * @param checkVersion If true, will do a version check
     */
    public void onServerInfoUpdate(PacketServerInfo serverInfo, boolean checkVersion)
    {
        if (checkVersion) //Check protocol versions
        {
            if (serverInfo.protocolVersion < NetworkManager.PROTOCOL_VERSION) //Client is out of date
            {
                this.displayMessage(String.format("The server is out of date! Client protocol version: %d.  Server protocol version: %d.", NetworkManager.PROTOCOL_VERSION, serverInfo.protocolVersion));
                NetworkManager.INSTANCE.writePacket(this.server.get().getSocket(), new PacketDisconnect("Server out of date"));
                throw new RuntimeException("Out of date server.");
            }
            else if (serverInfo.protocolVersion > NetworkManager.PROTOCOL_VERSION) //Server is out of date
            {
                this.displayMessage(String.format("Your client is out of date! Client protocol version: %d.  Server protocol version: %d.", NetworkManager.PROTOCOL_VERSION, serverInfo.protocolVersion));
                NetworkManager.INSTANCE.writePacket(this.server.get().getSocket(), new PacketDisconnect("Client out of date"));
                throw new RuntimeException("Out of date client.");
            }
        }

        //Login stuff
        this.server.get().setServerName(serverInfo.serverName);
        this.server.get().setUserCount(serverInfo.userCount);
        this.server.notifyChange();
    }

    /**
     * Displays a MessageText to the user with the specified string as the text
     * @param message The string to display
     */
    public void displayMessage(String message)
    {
        this.displayMessage(new MessageText(message));
    }

    /**
     * Displays a message to the user
     * @param message The message to display
     */
    public void displayMessage(final Message message)
    {
        this.runOnUIThread.invoke(new Runnable()
        {
            @Override
            public void run()
            {
                Log.i(this.getClass().getName(), "displayMessage() called! (on UI thread)");

                ChatClientViewModel.this.messageLog.add(message); //TODO: remove old messages

                ChatClientViewModel.this.messageLogModified.fire(); //The list just got changed (it can't notify itself for some reason, even when an event handler is manually registered)
            }
        });
        //this.messageAdded.fire(message);
    }

    public final ButtonCommand sendCommand;
    public final ButtonCommand disconnectCommand;
    public final ButtonCommand logoutCommand;

    //public final UnaryEventHandler<Message> messageAdded = new UnaryEventHandler<>();
    //public final EventHandler messageCleared = new EventHandler(); //Custom for the Java version, since it doesn't have WPF's data binding

    /**
     * Fired when the client gets disconnected. First string is the client's reason if the client
     * initiated it, second string is the server's reason if the server initiated it.  Will NOT be fired
     * on the UI thread. If the bool is true, the client reason is bad (an error).
     */
    public final TernaryEventHandler<String, Boolean, String> disconnected = new TernaryEventHandler<String, Boolean, String>();

    /** Called by this class to get the current message string */
    /*public Function<String> getMessageString = new Function<String>() //Custom for the Java version, since it doesn't have WPF's data binding
    {
        @Override
        public String invoke()
        {
            throw new RuntimeException("ERROR: ChatClientViewModel.getMessageString hasn't been changed from it's default value.  It should be hooked up by the Activity to get the message text.");
        }
    };
    */

    /** Fired when the message log gets modified, since it's not technically bound, and can't notify by itself */
    public final EventHandler messageLogModified = new EventHandler();

    /** Runs code on the UI thread */
    public UnaryAction<Runnable> runOnUIThread = new UnaryAction<Runnable>()
    {
        @Override
        public void invoke(Runnable runnable)
        {
            throw new RuntimeException("ERROR: ChatClientViewModel.runOnUIThread hasn't been changed from it's default value.  It should be hooked up by the Activity.");
        }
    };
}
