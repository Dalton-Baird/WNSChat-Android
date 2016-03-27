package com.daltonbaird.wnschat.utilities;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by Dalton on 3/27/2016.
 */
public class MathUtils
{
    /**
     * Computes the SHA-1 hash of the given string
     * @param string The string to hash
     * @return The hashed string
     */
    public static String sha1Hash(String string)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(string.getBytes(Charset.forName("ascii")));

            Formatter formatter = new Formatter();

            for (byte b : digest.digest())
                formatter.format("%02x", b);

            return formatter.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Error computing SHA-1 hash", e);
        }
    }
}
