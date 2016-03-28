package com.daltonbaird.wnschat;

import android.util.SparseArray;

import com.daltonbaird.wnschat.packets.Packet;
import com.daltonbaird.wnschat.packets.PacketDisconnect;
import com.daltonbaird.wnschat.packets.PacketLogin;
import com.daltonbaird.wnschat.packets.PacketPing;
import com.daltonbaird.wnschat.packets.PacketServerInfo;
import com.daltonbaird.wnschat.packets.PacketSimpleMessage;
import com.daltonbaird.wnschat.packets.PacketUserInfo;
import com.daltonbaird.wnschat.utilities.BinaryHelper;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

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
    private SparseArray<Class<? extends Packet>> packetMap;

    /** The last used packet ID */
    private byte packetID;

    private NetworkManager()
    {
        this.packetID = 0;
        this.packetMap = new SparseArray<Class<? extends Packet>>(); //Max should be 256

        //Register packets
        this.registerPacketType(PacketSimpleMessage.class);
        this.registerPacketType(PacketLogin.class);
        this.registerPacketType(PacketServerInfo.class);
        this.registerPacketType(PacketDisconnect.class);
        this.registerPacketType(PacketPing.class);
        this.registerPacketType(PacketUserInfo.class);
    }

    /**
     * Registers a packet type
     * @param packetClass The class of the packet to register
     */
    public void registerPacketType(Class<? extends Packet> packetClass)
    {
        if (this.packetID >= Byte.MAX_VALUE)
            throw new IndexOutOfBoundsException("The maximum amount of packet types has been exceeded.");

        this.packetMap.append(this.packetID++, packetClass);
    }

    /**
     * Writes a packet to the stream
     * @param socket The socket to write to
     * @param packet The packet to write to the stream
     */
    public void writePacket(Socket socket, Packet packet)
    {
        try
        {
            //Find the packet ID for the given packet class
            byte packetID = (byte) this.packetMap.keyAt(this.packetMap.indexOfValue(packet.getClass()));

            BinaryHelper.writeByte(socket.getOutputStream(), packetID);

            packet.writeToStream(socket.getOutputStream()); //Tell the packet to write itself to the stream
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error writing packet!", e);
        }
    }

    /**
     * Reads and returns a packet from the stream.  Blocks until a packet comes in.
     * @param socket The socket to read from
     * @return The packet read from the stream
     */
    public Packet readPacket(Socket socket)
    {
        short packetID = -1;

        try
        {
            packetID = BinaryHelper.readByte(socket.getInputStream());

            Class<? extends Packet> packetClass = this.packetMap.get(packetID);

            Packet packet;

            try
            {
                packet = packetClass.newInstance(); //Create the packet object
            }
            catch (InstantiationException|IllegalAccessException|NullPointerException e)
            {
                throw new RuntimeException(String.format("Error instantiating packet class for packet type \"%s\" with ID %d", packetClass, packetID), e);
            }

            packet.readFromStream(socket.getInputStream()); //Have the packet read from the stream
            return packet;
        }
        catch (IOException e)
        {
            if (e instanceof EOFException)
                throw new RuntimeException("Connection closed!", e);

            throw new RuntimeException("Error reading packet!", e);
        }
    }
}
