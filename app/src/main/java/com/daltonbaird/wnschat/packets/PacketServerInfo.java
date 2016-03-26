package com.daltonbaird.wnschat.packets;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by Dalton on 3/26/2016.
 */
public class PacketServerInfo extends Packet
{
    /** The server's protocol version */
    public int protocolVersion;
    /** The server's user count */
    public int userCount;
    /** If true, the server requires a password */
    public boolean passwordRequired;
    /** The server's name */
    public String serverName;

    @Override
    public void writeToStream(OutputStream stream, ObjectOutputStream writer)
    {
        //TODO: implement this!
    }

    @Override
    public void readFromStream(InputStream stream, ObjectInputStream writer)
    {
        //TODO: implement this!
    }
}
