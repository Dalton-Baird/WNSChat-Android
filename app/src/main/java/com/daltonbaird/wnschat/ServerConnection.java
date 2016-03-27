package com.daltonbaird.wnschat;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Dalton on 3/26/2016.
 */
public class ServerConnection implements Closeable
{
    /** The socket used to access the server */
    protected Socket socket;

    /** The server's name */
    protected String serverName;

    /** The user count on the server */
    protected int userCount;

    public ServerConnection(Socket socket)
    {
        this.socket = socket;
        this.serverName = String.format("Server@%s", socket.getInetAddress());
    }

    @Override
    public void close() throws IOException
    {
        //this.stream.close();
    }

    @Override
    public String toString()
    {
        return this.serverName;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }

    public String getServerName()
    {
        return serverName;
    }

    public void setServerName(String serverName)
    {
        this.serverName = serverName;
    }

    public int getUserCount()
    {
        return userCount;
    }

    public void setUserCount(int userCount)
    {
        this.userCount = userCount;
    }
}
