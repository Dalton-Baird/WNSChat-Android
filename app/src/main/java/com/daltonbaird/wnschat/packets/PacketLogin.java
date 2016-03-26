package com.daltonbaird.wnschat.packets;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by Dalton on 3/26/2016.
 */
public class PacketLogin extends Packet
{
    /** The version of the network protocol */
    public int protocolVersion;
    /** The client's username */
    public String username;
    /** The client's password hash */
    public String passwordHash;

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
