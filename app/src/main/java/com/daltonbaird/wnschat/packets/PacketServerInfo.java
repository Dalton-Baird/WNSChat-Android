package com.daltonbaird.wnschat.packets;

import com.daltonbaird.wnschat.utilities.BinaryHelper;

import java.io.IOException;
import java.io.InputStream;
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
    public void writeToStream(OutputStream stream) throws IOException
    {
        BinaryHelper.writeUInt32(stream, this.protocolVersion);
        BinaryHelper.writeInt32(stream, this.userCount);
        BinaryHelper.writeBoolean(stream, this.passwordRequired);
        BinaryHelper.writeString(stream, this.serverName);
    }

    @Override
    public void readFromStream(InputStream stream) throws IOException
    {
        this.protocolVersion = BinaryHelper.readUInt32(stream);
        this.userCount = BinaryHelper.readInt32(stream);
        this.passwordRequired = BinaryHelper.readBoolean(stream);
        this.serverName = BinaryHelper.readString(stream);
    }
}
