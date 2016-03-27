package com.daltonbaird.wnschat.utilities;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.daltonbaird.wnschat.activities.ChatActivity;
import com.daltonbaird.wnschat.messages.Message;

import java.util.List;

/**
 * Created by Dalton on 3/27/2016.
 *
 * Based on http://stackoverflow.com/questions/20886795/populate-listview-from-listobject
 */
public class MessageLogAdapter extends ArrayAdapter<Message>
{
    protected List<Message> messages;

    public MessageLogAdapter(Context context, List<Message> list)
    {
        super(context, 0, list);
        this.messages = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //TODO: finish this

        return super.getView(position, convertView, parent);
    }
}
