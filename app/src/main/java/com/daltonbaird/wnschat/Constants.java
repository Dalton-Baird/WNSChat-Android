package com.daltonbaird.wnschat;

/**
 * Created by Dalton on 3/26/2016.
 */
public class Constants
{
    /** The regular expression that defines username format */
    public static final String UsernameRegexStr = "^\\w(?:\\w|-){1,50}\\w$";
    /** Same as the UsernameRegexStr, but without the ^ and $ beginning and end of string requirements */
    public static final String UsernameRegexStrInline = "\\w(?:\\w|-){1,50}\\w";
}
