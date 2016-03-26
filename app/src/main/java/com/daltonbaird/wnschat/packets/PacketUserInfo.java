package com.daltonbaird.wnschat.packets;

import com.daltonbaird.wnschat.commands.PermissionLevel;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
