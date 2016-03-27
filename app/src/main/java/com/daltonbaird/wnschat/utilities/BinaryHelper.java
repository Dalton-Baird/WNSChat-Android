package com.daltonbaird.wnschat.utilities;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * A helper class to read and write objects to a stream in the same format as the .NET BinaryReader
 * and BinaryWriter.
 *
 * Created by Dalton on 3/26/2016.
 *
 * Based on https://github.com/vrecan/Thaw-Giant/blob/master/src/main/java/com/vreco/thawgiant/BinaryUtil.java
 */
public class BinaryHelper
{
    /**
     * Reads an Int32 from the stream and returns it as a Java int.
     * @param in The input stream
     * @return An Int32 as a Java int.
     * @throws IOException If an IO error occurs
     */
    public static int readInt32(final InputStream in) throws IOException
    {
        byte[] buffer = new byte[4];
        ByteBuffer bb = ByteBuffer.wrap(buffer);

        if (in.read(buffer) < 0) //Read the stream into the buffer
            throw new EOFException();

        //Switch the byte ordering to little endian, which is what .NET uses
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.position(0);

        //System.out.println("Int32:");
        //printBytes(buffer);

        return bb.getInt();
    }

    /**
     * Reads a UInt32 from the stream and returns it as a Java int.
     * @param in The input stream
     * @return A UInt32 as a Java int.
     * @throws IOException If an IO error occurs
     */
    public static int readUInt32(final InputStream in) throws IOException
    {
        byte[] buffer = new byte[4];
        ByteBuffer bb = ByteBuffer.wrap(buffer);

        if (in.read(buffer) < 0) //Read the stream into the buffer
            throw new EOFException();

        //Switch the byte ordering to little endian, which is what .NET uses
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.position(0);

        return bb.getInt();
    }

    /**
     * Reads an Int64 from the stream and returns it as a Java long.
     * @param in The input stream
     * @return An Int64 as a Java long.
     * @throws IOException If an IO error occurs
     */
    public static long readInt64(final InputStream in) throws IOException
    {
        byte[] buffer = new byte[8];
        ByteBuffer bb = ByteBuffer.wrap(buffer);

        if (in.read(buffer) < 0) //Read the stream into the buffer
            throw new EOFException();

        //Switch the byte ordering to little endian, which is what .NET uses
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.position(0);

        return bb.getLong();
    }

    /**
     * Reads a .NET String from the stream and returns it as a Java String.
     * @param in The input stream
     * @return A String as a Java String
     * @throws IOException If an IO error occurs
     */
    public static String readString(final InputStream in) throws IOException
    {
        int length = read7BitEncodedInt(in);

        byte[] buffer = new byte[length];

        if (in.read(buffer) < 0)
            throw new EOFException();

        //System.out.printf("String: (Length: %s)\n", length);
        //printBytes(buffer);

        return new String(buffer);
    }

    /**
     * Reads the integer from the stream as a 7 bit encoded integer
     * @param in The input stream
     * @return The integer read
     * @throws IOException If an IO error occurs
     */
    public static int read7BitEncodedInt(final InputStream in) throws IOException
    {
        //Based on the .NET implementation.  See http://referencesource.microsoft.com/#mscorlib/system/io/binaryreader.cs,569

        //Read out an Int32 7 bits at a time.  The high bit of the byte when on means to continue reading more bytes.
        int count = 0;
        int shift = 0;
        byte b;

        do
        {
            //Check for a corrupted stream.  Read a max of 5 bytes.
            if (shift == 5 * 7) //5 bytes max per Int32, shift += 7
                throw new IOException("Bad 7 bit encoded integer");

            byte[] bArray = new byte[1];
            if (in.read(bArray) < 0)
                throw new EOFException();

            b = bArray[0];

            //b = (byte) in.read();

            //if (b < 0) //in.read() returns -1 if no bytes were read
            //    throw new EOFException();

            count |= (b & 0x7F) << shift;
            shift += 7;
        }
        while ((b & 0x80) != 0);

        return count;
    }

    /**
     * Reads a boolean from the stream and returns it as a Java boolean.
     * @param in The input stream
     * @return A boolean as a Java boolean.
     * @throws IOException If an IO error occurs
     */
    public static boolean readBoolean(final InputStream in) throws IOException
    {
        byte[] buffer = new byte[1];
        ByteBuffer bb = ByteBuffer.wrap(buffer);

        if (in.read(buffer) < 0) //Read the stream into the buffer
            throw new EOFException();

        //Switch the byte ordering to little endian, which is what .NET uses
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.position(0);

        return (bb.get() & 0x10000000) > 0;
    }

    /**
     * Reads a byte from the stream and returns it as a Java byte.
     * @param in The input stream
     * @return A byte as a Java byte.
     * @throws IOException If an IO error occurs
     */
    public static byte readByte(final InputStream in) throws IOException
    {
        byte[] buffer = new byte[1];
        ByteBuffer bb = ByteBuffer.wrap(buffer);

        if (in.read(buffer) < 0) //Read the stream into the buffer
            throw new EOFException();

        //Switch the byte ordering to little endian, which is what .NET uses
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.position(0);

        return bb.get();
    }

    /**
     * Writes an Int32 to the stream
     * @param out The output stream
     * @param value The int to write.  Will be written as a .NET Int32.
     * @throws IOException If an IO error occurs
     */
    public static void writeInt32(final OutputStream out, int value) throws IOException
    {
        byte[] buffer = new byte[4];
        ByteBuffer bb = ByteBuffer.wrap(buffer);

        //Switch the byte ordering to little endian, which is what .NET uses
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.position(0);

        bb.putInt(value); //Write the int to the buffer

        out.write(buffer); //Write the buffer to the stream

        //System.out.println("Int32:");
        //printBytes(buffer);
    }

    /**
     * Writes a UInt32 to the stream
     * @param out The output stream
     * @param value The int to write.  Will be written as a .NET UInt32.
     * @throws IOException If an IO error occurs
     */
    public static void writeUInt32(final OutputStream out, int value) throws IOException
    {
        byte[] buffer = new byte[4];
        ByteBuffer bb = ByteBuffer.wrap(buffer);

        //Switch the byte ordering to little endian, which is what .NET uses
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.position(0);

        bb.putInt(value); //Write the int to the buffer

        out.write(buffer); //Write the buffer to the stream
    }

    /**
     * Writes an Int64 to the stream
     * @param out The output stream
     * @param value The long to write.  Will be written as a .NET Int64.
     * @throws IOException If an IO error occurs
     */
    public static void writeInt64(final OutputStream out, long value) throws IOException
    {
        byte[] buffer = new byte[8];
        ByteBuffer bb = ByteBuffer.wrap(buffer);

        //Switch the byte ordering to little endian, which is what .NET uses
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.position(0);

        bb.putLong(value); //Write the long to the buffer

        out.write(buffer); //Write the buffer to the stream
    }

    /**
     * Writes a string to the stream formatted as a .NET string.
     * @param out The output stream
     * @param value The string to write
     * @throws IOException if an IO error occurs
     */
    public static void writeString(final OutputStream out, String value) throws IOException
    {
        if (value == null) //Null check
            throw new NullPointerException("String cannot be null");

        //System.out.printf("String: (Length: %s)\n", value.length());

        write7BitEncodedInt(out, value.length()); //Write the length of the string

        byte[] bytes = value.getBytes(Charset.forName("utf-8"));

        out.write(bytes); //Write  the string bytes

        //printBytes(bytes);
    }

    /**
     * Writes the integer to the stream as a 7 bit encoded integer
     * @param out The output stream
     * @param value The integer to write
     * @throws IOException if an IO error occurs
     */
    public static void write7BitEncodedInt(final OutputStream out, int value) throws IOException
    {
        //Based on the .NET implementation.  See http://referencesource.microsoft.com/#mscorlib/system/io/binarywriter.cs,407

        //Write out an int 7 bits at a time.  The high bit of the byte, when on, tells reader to continue reading more bytes.
        int v = value;

        while (v >= 0x80)
        {
            out.write(new byte[] {(byte) (v | 0x80)});
            v >>= 7;
        }

        out.write(new byte[] {(byte) v});
    }

    /**
     * Writes a boolean to the stream
     * @param out The output stream
     * @param value The boolean to write.  Will be written as a .NET boolean.
     * @throws IOException If an IO error occurs
     */
    public static void writeBoolean(final OutputStream out, boolean value) throws IOException
    {
        byte[] buffer = new byte[1];
        ByteBuffer bb = ByteBuffer.wrap(buffer);

        //Switch the byte ordering to little endian, which is what .NET uses
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.position(0);

        bb.put((byte)(value ? 0b10000000 : 0)); //Write the byte to the buffer

        out.write(buffer); //Write the buffer to the stream
    }

    /**
     * Writes a byte to the stream
     * @param out The output stream
     * @param value The byte to write.  Will be written as a .NET byte.
     * @throws IOException If an IO error occurs
     */
    public static void writeByte(final OutputStream out, byte value) throws IOException
    {
        byte[] buffer = new byte[1];
        ByteBuffer bb = ByteBuffer.wrap(buffer);

        //Switch the byte ordering to little endian, which is what .NET uses
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.position(0);

        bb.put(value); //Write the byte to the buffer

        out.write(buffer); //Write the buffer to the stream
    }

    /**
     * Prints the byte array to System.out.
     * @param bytes The bytes to print
     */
    public static void printBytes(byte[] bytes)
    {
        System.out.printf("Bytes: %s", bytes.length);

        for (byte b : bytes)
        {
            System.out.print("\t");

            for (int i = 1; i < 256; i <<= 1)
                System.out.printf(" %d", (b & i) > 0 ? 1 : 0);
        }

        System.out.println("\n");
    }
}
