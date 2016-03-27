package com.daltonbaird.wnschat.packets;

import com.daltonbaird.wnschat.utilities.BinaryHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by Dalton on 3/26/2016.
 */
public class PacketDisconnect extends Packet
{
    /** The reason for disconnecting */
    public String reason;

    public PacketDisconnect() {}

    public PacketDisconnect(String reason)
    {
        this.reason = reason;
    }

    @Override
    public void writeToStream(OutputStream stream) throws IOException
    {
        BinaryHelper.writeString(stream, this.reason);
    }

    @Override
    public void readFromStream(InputStream stream) throws IOException
    {
        this.reason = BinaryHelper.readString(stream);
    }
}
