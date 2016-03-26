package com.daltonbaird.wnschat.viewmodels;

import com.daltonbaird.wnschat.ClientUser;
import com.daltonbaird.wnschat.ServerConnection;
import com.daltonbaird.wnschat.commands.PermissionLevel;
import com.daltonbaird.wnschat.functional.Func;
import com.daltonbaird.wnschat.messages.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dalton on 3/26/2016.
 */
public class ChatClientViewModel
{
    protected Object client;

    protected ClientUser clientUser;

    protected ServerConnection server;

    protected Object serverIP;

    protected short serverPort;

    protected List<Message> messageLog;

    public ChatClientViewModel(String username, Object serverIP, short port)
    {
        this.serverIP = serverIP;
        this.serverPort = port;
        this.clientUser = new ClientUser(username, PermissionLevel.USER);

        this.initCommands();
    }

    public void initCommands()
    {

    }

    public Object getClient()
    {
        return client;
    }

    public void setClient(Object client)
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

    public Object getServerIP()
    {
        return serverIP;
    }

    public void setServerIP(Object serverIP)
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

    /**
     * Attempts to connect to the server
     * @param getPassword A function to call to get a password, if one is required
     */
    public void connectToServer(Func<String> getPassword)
    {
        try
        {
            //TODO: implement this!
        }
        catch (Exception e)
        {

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
                //TODO: Implement this!
            }
            catch (Exception e)
            {

            }
        }

        //TODO: Implement this!
    }

    /**
     * Ran in a separate thread to process incoming information from the server
     */
    public void processServerThread()
    {

    }

    /**
     * Updates the client's data about the server. If checkVersion is true, it does a version check
     * and throws an exception if the versions don't match.
     * @param serverInfo The server info packet
     * @param checkVersion If true, will do a version check
     */
    public void onServerInfoUpdate(Object serverInfo, boolean checkVersion)
    {
        if (checkVersion) //Check protocol versions
        {
            //TODO: Implement this!
        }

        //Login stuff
        //this.server.setServerName(serverInfo.serverName);
        //this.server.setUserCount(serverInfo.userCount);
    }
}
