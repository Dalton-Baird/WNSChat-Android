package com.daltonbaird.wnschat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.daltonbaird.wnschat.NetworkManager;
import com.daltonbaird.wnschat.R;
import com.daltonbaird.wnschat.functional.UnaryAction;
import com.daltonbaird.wnschat.messages.Message;
import com.daltonbaird.wnschat.packets.PacketSimpleMessage;
import com.daltonbaird.wnschat.viewmodels.ChatClientViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity
{
    /** The chat client. It is static so the login activity can access it. */
    public static ChatClientViewModel chatClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Find objects
        final ListView listViewMessages = (ListView) this.findViewById(R.id.messageList);

        assert listViewMessages != null : "Message list was null!";

        //Hook up stuff
        //listViewMessages.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, chatClient.getMessageLog()));
        //listViewMessages.setAdapter(new MessageLogAdapter(ChatActivity.this, chatClient.getMessageLog()));

        final List<Message> messages = new ArrayList<Message>();

        final ArrayAdapter<Message> messageArrayAdapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, messages);
        listViewMessages.setAdapter(messageArrayAdapter);

        chatClient.messageAdded.add(new UnaryAction<Message>()
        {
            @Override
            public void invoke(final Message message)
            {
                listViewMessages.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        messages.add(message);
                        messageArrayAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the menu items for use in the action bar
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_activity_chat, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_logout)
        {
            Toast.makeText(this, "Logout selected", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (item.getItemId() == R.id.action_settings)
        {
            Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    public boolean canSendMessage()
    {
        return true; //TODO: implement this!
    }

    public void sendMessage(View view)
    {
        try
        {
            //Find objects
            final EditText editTextMessage = (EditText) this.findViewById(R.id.messageBox);

            assert editTextMessage != null : "Message EditText was null!";

            //TODO: Do this with commands instead!
            NetworkManager.INSTANCE.writePacket(chatClient.getServer().getSocket(), new PacketSimpleMessage(editTextMessage.getText().toString()));
        }
        catch (Exception e)
        {
            Toast.makeText(ChatActivity.this, String.format("Error sending message: %s", e), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
