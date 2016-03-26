package com.daltonbaird.wnschat.packets;

/**
 * Created by Dalton on 3/25/2016.
 */
public abstract class Packet
{
    public abstract void writeToStream();

    public abstract void readFromStream();
}
