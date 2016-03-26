package com.daltonbaird.wnschat;

import android.util.SparseArray;

import com.daltonbaird.wnschat.packets.Packet;

/**
 * Created by Dalton on 3/25/2016.
 */
public class NetworkManager
{
    /** The network protocol version */
    public static final int PROTOCOL_VERSION = 3;

    /** The single instance of the NetowrkManager */
    public static final NetworkManager INSTANCE = new NetworkManager();

    /** The packet map */
    private SparseArray<Class<Packet>> packetMap;

    /** The last used packet ID */
    private byte packetID;

    private NetworkManager()
    {
        this.packetID = 0;
        this.packetMap = new SparseArray<Class<Packet>>();

        //Register packets
        //this.registerPacketType();
        //TODO: register packets!
    }

    /**
     * Registers a packet type
     * @param packetClass The class of the packet to register
     */
    public void registerPacketType(Class<Packet> packetClass)
    {
        if (this.packetID >= Byte.MAX_VALUE)
            throw new IndexOutOfBoundsException("The maximum amount of packet types has been exceeded.");

        this.packetMap.append(this.packetID++, packetClass);
    }

    /**
     * Writes a packet to the stream
     * @param stream The stream to write to
     * @param packet The packet to write to the stream
     */
    public void writePacket(Object stream, Packet packet)
    {
        //TODO: Implement this!
    }

    /**
     * Reads and returns a packet from the stream.  Blocks until a packet comes in.
     * @param stream The stream to read from
     * @return The packet read from the stream
     */
    public Packet readPacket(Object stream)
    {
        return null; //TODO: Implement this!
    }
}
