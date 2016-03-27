package com.daltonbaird.wnschat.packets;

import com.daltonbaird.wnschat.commands.PermissionLevel;
import com.daltonbaird.wnschat.utilities.BinaryHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Dalton on 3/26/2016.
 */
public class PacketUserInfo extends Packet
{
    /** The user's username */
    public String username;
    /** The user's permmission level */
    public PermissionLevel permissionLevel;

    @Override
    public void writeToStream(OutputStream stream) throws IOException
    {
        BinaryHelper.writeString(stream, this.username);
        BinaryHelper.writeInt32(stream, this.permissionLevel.id);
    }

    @Override
    public void readFromStream(InputStream stream) throws IOException
    {
        this.username = BinaryHelper.readString(stream);
        this.permissionLevel = PermissionLevel.values()[BinaryHelper.readInt32(stream)];
    }
}
