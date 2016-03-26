package com.daltonbaird.wnschat.messages;

import java.util.Date;

/**
 * Created by Dalton on 3/26/2016.
 */
public abstract class Message
{
    protected Date createdDate;

    public Message()
    {
        //this.createdDate = NOW; //TODO: How do I find this?
    }

    @Override
    public abstract String toString(); //You must implement this for the console log

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
}
