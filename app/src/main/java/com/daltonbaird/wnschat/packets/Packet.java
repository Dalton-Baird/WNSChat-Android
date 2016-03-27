package com.daltonbaird.wnschat.packets;

import java.io.IOException;
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
     */
    public abstract void writeToStream(OutputStream stream) throws IOException;

    /**
     * Reads the packet from the input stream
     * @param stream The raw InputStream
     */
    public abstract void readFromStream(InputStream stream) throws IOException;
}
