package com.daltonbaird.wnschat;

import com.daltonbaird.wnschat.utilities.BinaryHelper;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static org.junit.Assert.*;

/**
 * Created by Dalton on 3/26/2016.
 */
public class TestBinaryHelper
{
    @Test
    public void testInt32() throws Exception
    {
        //Create Byte Array I/O streams so we can connect them
        ByteArrayInputStream inByte = null;
        ByteArrayOutputStream outByte = new ByteArrayOutputStream(1024);

        //Create abstract I/O streams to make sure it works with them
        InputStream in = null;
        OutputStream out = outByte;

        //Write to input stream
        final int number1 = 123456789;
        final int number2 = -123456789;

        BinaryHelper.writeInt32(out, number1);
        BinaryHelper.writeInt32(out, number2);

        //Create input stream from output stream
        inByte = new ByteArrayInputStream(outByte.toByteArray());
        in = inByte;

        //Read from output stream
        int number1Read = BinaryHelper.readInt32(in);
        int number2Read = BinaryHelper.readInt32(in);

        //Test the results
        assertEquals(number1, number1Read);
        assertEquals(number2, number2Read);
        assertStreamEmpty(in);
    }

    @Test
    public void testUInt32() throws Exception
    {
        //NOTE: the Java int's here are being treated as unsigned

        //Create Byte Array I/O streams so we can connect them
        ByteArrayInputStream inByte = null;
        ByteArrayOutputStream outByte = new ByteArrayOutputStream(1024);

        //Create abstract I/O streams to make sure it works with them
        InputStream in = null;
        OutputStream out = outByte;

        //Write to input stream
        final int number1 = 123456789;
        final int number2 = -123456789;

        BinaryHelper.writeUInt32(out, number1);
        BinaryHelper.writeInt32(out, number2);

        //Create input stream from output stream
        inByte = new ByteArrayInputStream(outByte.toByteArray());
        in = inByte;

        //Read from output stream
        int number1Read = BinaryHelper.readUInt32(in);
        int number2Read = BinaryHelper.readInt32(in);

        //Test the results
        assertEquals(number1, number1Read);
        assertEquals(number2, number2Read);
        assertStreamEmpty(in);
    }

    @Test
    public void testInt64() throws Exception
    {
        //Create Byte Array I/O streams so we can connect them
        ByteArrayInputStream inByte = null;
        ByteArrayOutputStream outByte = new ByteArrayOutputStream(1024);

        //Create abstract I/O streams to make sure it works with them
        InputStream in = null;
        OutputStream out = outByte;

        //Write to input stream
        final long number1 = 4815162342L;
        final long number2 = -4815162342L;

        BinaryHelper.writeInt64(out, number1);
        BinaryHelper.writeInt64(out, number2);

        //Create input stream from output stream
        inByte = new ByteArrayInputStream(outByte.toByteArray());
        in = inByte;

        //Read from output stream
        long number1Read = BinaryHelper.readInt64(in);
        long number2Read = BinaryHelper.readInt64(in);

        //Test the results
        assertEquals(number1, number1Read);
        assertEquals(number2, number2Read);
        assertStreamEmpty(in);
    }

    @Test
    public void testString() throws Exception
    {
        //Create Byte Array I/O streams so we can connect them
        ByteArrayInputStream inByte = null;
        ByteArrayOutputStream outByte = new ByteArrayOutputStream(1024);

        //Create abstract I/O streams to make sure it works with them
        InputStream in = null;
        OutputStream out = outByte;

        //Write to input stream
        final String string1 = "Hello World!";
        final String string2 = "This is a test.";
        final String string3 = "This is a really long string.  It needs to be so long, that it has more than 128 or 256 characters.  I wonder how much longer I have to type until it reaches that length.  Apparently I am over half way there when I began typing this sentence.  Almost over the required limit, just a few more words...";

        BinaryHelper.writeString(out, string1);
        BinaryHelper.writeString(out, string2);
        BinaryHelper.writeString(out, string3);

        //Create input stream from output stream
        inByte = new ByteArrayInputStream(outByte.toByteArray());
        in = inByte;

        //Read from output stream
        String string1Read = BinaryHelper.readString(in);
        String string2Read = BinaryHelper.readString(in);
        String string3Read = BinaryHelper.readString(in);

        //Test the results
        assertEquals(string1, string1Read);
        assertEquals(string2, string2Read);
        assertEquals(string3, string3Read);
        assertStreamEmpty(in);
    }

    @Test
    public void testBoolean() throws Exception
    {
        //Create Byte Array I/O streams so we can connect them
        ByteArrayInputStream inByte = null;
        ByteArrayOutputStream outByte = new ByteArrayOutputStream(1024);

        //Create abstract I/O streams to make sure it works with them
        InputStream in = null;
        OutputStream out = outByte;

        //Write to input stream
        final boolean bool1 = false;
        final boolean bool2 = true;

        BinaryHelper.writeBoolean(out, bool1);
        BinaryHelper.writeBoolean(out, bool2);

        //Create input stream from output stream
        inByte = new ByteArrayInputStream(outByte.toByteArray());
        in = inByte;

        //Read from output stream
        boolean bool1Read = BinaryHelper.readBoolean(in);
        boolean bool2Read = BinaryHelper.readBoolean(in);

        //Test the results
        assertEquals(bool1, bool1Read);
        assertEquals(bool2, bool2Read);
        assertStreamEmpty(in);
    }

    @Test
    public void testByte() throws Exception
    {
        //Create Byte Array I/O streams so we can connect them
        ByteArrayInputStream inByte = null;
        ByteArrayOutputStream outByte = new ByteArrayOutputStream(1024);

        //Create abstract I/O streams to make sure it works with them
        InputStream in = null;
        OutputStream out = outByte;

        //Write to input stream
        final byte number1 = (byte) 192;
        final byte number2 = (byte) -192;

        BinaryHelper.writeByte(out, number1);
        BinaryHelper.writeByte(out, number2);

        //Create input stream from output stream
        inByte = new ByteArrayInputStream(outByte.toByteArray());
        in = inByte;

        //Read from output stream
        byte number1Read = BinaryHelper.readByte(in);
        byte number2Read = BinaryHelper.readByte(in);

        //Test the results
        assertEquals(number1, number1Read);
        assertEquals(number2, number2Read);
        assertStreamEmpty(in);
    }

    @Test
    public void test7BitIntEncoding() throws Exception
    {
        //Create Byte Array I/O streams so we can connect them
        ByteArrayInputStream inByte = null;
        ByteArrayOutputStream outByte = new ByteArrayOutputStream(1024);

        //Create abstract I/O streams to make sure it works with them
        InputStream in = null;
        OutputStream out = outByte;

        //Write to input stream
        int[] numbers = new int[] {0, 1, 4, 8, 15, 16, 23, 42, 9001, 1000000, Integer.MAX_VALUE};

        for (int number : numbers)
                BinaryHelper.write7BitEncodedInt(out, number);

        //Create input stream from output stream
        inByte = new ByteArrayInputStream(outByte.toByteArray());
        in = inByte;

        //Read from output stream
        int[] numbersRead = new int[numbers.length];

        for (int i = 0; i < numbersRead.length; i++)
            numbersRead[i] = BinaryHelper.read7BitEncodedInt(in);

        //Test the results
        for (int i = 0; i < numbers.length; i++)
            assertEquals(numbers[i], numbersRead[i]);

        assertStreamEmpty(in);
    }

    /** Asserts that the InputStream is now empty */
    public void assertStreamEmpty(InputStream in) throws Exception
    {
        int bytesRead = in.read();

        assertEquals(-1, bytesRead);
    }
}
