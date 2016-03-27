package com.daltonbaird.wnschat.packets;

import com.daltonbaird.wnschat.utilities.BinaryHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dalton on 3/26/2016.
 */
public class PacketPing extends Packet
{
    /** The username of the user who sent the ping request */
    public String sendingUsername;
    /** The username of the user pinged */
    public String destinationUsername;
    /** The ping packet state */
    public State packetState;

    /** A list of timestamps */
    public List<Timestamp> timestamps;

    @Override
    public void writeToStream(OutputStream stream) throws IOException
    {
        BinaryHelper.writeString(stream, this.sendingUsername);
        BinaryHelper.writeString(stream, this.destinationUsername);
        BinaryHelper.writeInt32(stream, this.packetState.id);

        BinaryHelper.writeInt32(stream, this.timestamps.size()); //Write the length of the timestamp list

        //Write the timestamps to the stream
        for (Timestamp timestamp : this.timestamps)
        {
            BinaryHelper.writeString(stream, timestamp.username);
            BinaryHelper.writeInt64(stream, timestamp.time.getTime()); //TODO: is this correct?
        }
    }

    @Override
    public void readFromStream(InputStream stream) throws IOException
    {
        this.sendingUsername = BinaryHelper.readString(stream);
        this.destinationUsername = BinaryHelper.readString(stream);
        this.packetState = State.values()[BinaryHelper.readInt32(stream)];

        int listSize = BinaryHelper.readInt32(stream);
        this.timestamps = new ArrayList<Timestamp>(listSize);

        //Read the timestamps from the stream
        for (int i = 0; i < listSize; i++)
        {
            String username = BinaryHelper.readString(stream);
            Date time = new Date(BinaryHelper.readInt64(stream));
            this.timestamps.add(new Timestamp(username, time));
        }
    }

    /**
     * Adds a timestamp with the specified username
     * @param username The user that added the timestamp
     */
    public void addTimestamp(String username)
    {
        if (this.timestamps == null) //Make the list if it is null
            this.timestamps = new ArrayList<Timestamp>();

        this.timestamps.add(new Timestamp(username, null)); //TODO: find out how to find the current date
    }

    /**
     * Returns the traced ping data formatted as a string
     * @return A string representing the traced ping data
     */
    public String trace()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Ping Trace: \n");

        Date firstTime = this.timestamps != null && this.timestamps.size() > 0 ? this.timestamps.get(0).time : null;

        sb.append(String.format("\t%-20s Timestamp\n\n", "User"));

        for (Timestamp timestamp : this.timestamps)
        {
            Object timeOffset = null; //TODO: Find out how to do this

            if (firstTime != null)
                timeOffset = null; //TODO: Find out how to do this

            sb.append(String.format("\t%-20s %d ms\n", timestamp.username, 0)); //TODO: find out how to do this
        }

        Date lastTime = null; //TODO: find out how to do this

        if (firstTime != null && lastTime != null)
            sb.append(String.format("\t%d ms\n", 0)); //TODO: find out how to do this

        return sb.toString();
    }

    /**
     * A timestamp class because Java doesn't have tuples
     */
    public static class Timestamp
    {
        /** The username that created this timestamp */
        public String username;
        /** The time that this timestamp was created */
        public Date time;

        public Timestamp(String username, Date time)
        {
            this.username = username;
            this.time = time;
        }
    }

    /**
     * An enum to represent the ping packet state
     */
    public static enum State
    {
        GOING_TO(0),
        GOING_BACK(1);

        public final int id;

        State(int id)
        {
            this.id = id;
        }
    }
}
