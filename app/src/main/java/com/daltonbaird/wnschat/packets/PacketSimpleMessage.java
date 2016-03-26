package com.daltonbaird.wnschat.packets;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by Dalton on 3/26/2016.
 */
public class PacketSimpleMessage extends Packet
{
    /** The message that the packet contains */
    public String message;

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
