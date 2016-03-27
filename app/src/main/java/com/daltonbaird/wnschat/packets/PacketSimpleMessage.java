package com.daltonbaird.wnschat.packets;

import com.daltonbaird.wnschat.utilities.BinaryHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Dalton on 3/26/2016.
 */
public class PacketSimpleMessage extends Packet
{
    /** The message that the packet contains */
    public String message;

    public PacketSimpleMessage() {}

    public PacketSimpleMessage(String message)
    {
        this.message = message;
    }

    @Override
    public void writeToStream(OutputStream stream) throws IOException
    {
        BinaryHelper.writeString(stream, this.message);
    }

    @Override
    public void readFromStream(InputStream stream) throws IOException
    {
        this.message = BinaryHelper.readString(stream);
    }
}
