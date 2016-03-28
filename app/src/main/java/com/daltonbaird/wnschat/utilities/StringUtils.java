package com.daltonbaird.wnschat.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Dalton on 3/27/2016.
 */
public class StringUtils
{
    /**
     * Gets the stack trace from a throwable and returns it as a string.
     * @param t The throwable to get the stack trace from
     * @return The stack trace, formatted as a string
     */
    public static String getStackTrace(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);

        return sw.toString();
    }
}
