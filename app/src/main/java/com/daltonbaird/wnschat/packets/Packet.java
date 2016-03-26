package com.daltonbaird.wnschat.packets;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by Dalton on 3/25/2016.
 */
public abstract class Packet
{
    /**
     * Writes the packet to the output stream.
     * @param stream The raw OutputStream
     * @param writer The more advanced ObjectOutputStream, use this for writing
     */
    public abstract void writeToStream(OutputStream stream, ObjectOutputStream writer);

    /**
     * Reads the packet from the input stream
     * @param stream The raw InputStream
     * @param writer The ObjectInputStream, use this for reading
     */
    public abstract void readFromStream(InputStream stream, ObjectInputStream writer);
}
