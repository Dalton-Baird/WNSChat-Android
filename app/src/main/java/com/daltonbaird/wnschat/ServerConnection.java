package com.daltonbaird.wnschat;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Dalton on 3/26/2016.
 */
public class ServerConnection implements Closeable
{
    /** The network stream used to access the socket's data */
    protected Object stream;

    /** The server's name */
    protected String serverName;

    /** The user count on the server */
    protected int userCount;

    public ServerConnection(Object client)
    {
        //this.stream = client.getStream();
        this.serverName = String.format("Server@%s", client);
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

    public Object getStream()
    {
        return stream;
    }

    public void setStream(Object stream)
    {
        this.stream = stream;
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
