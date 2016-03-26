package com.daltonbaird.wnschat.messages;

/**
 * Created by Dalton on 3/26/2016.
 */
public class MessageText extends Message
{
    protected String text;

    public MessageText()
    {
        this(null);
    }

    public MessageText(String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return this.text;
    }
}
