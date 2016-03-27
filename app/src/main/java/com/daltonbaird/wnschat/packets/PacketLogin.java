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
public class PacketLogin extends Packet
{
    /** The version of the network protocol */
    public int protocolVersion;
    /** The client's username */
    public String username;
    /** The client's password hash */
    public String passwordHash;

    public PacketLogin() {}

    public PacketLogin(int protocolVersion, String username, String passwordHash)
    {
        this.protocolVersion = protocolVersion;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    @Override
    public void writeToStream(OutputStream stream) throws IOException
    {
        BinaryHelper.writeUInt32(stream, this.protocolVersion);
        BinaryHelper.writeString(stream, this.username);
        BinaryHelper.writeString(stream, this.passwordHash);
    }

    @Override
    public void readFromStream(InputStream stream) throws IOException
    {
        this.protocolVersion = BinaryHelper.readUInt32(stream);
        this.username = BinaryHelper.readString(stream);
        this.passwordHash = BinaryHelper.readString(stream);
    }
}
