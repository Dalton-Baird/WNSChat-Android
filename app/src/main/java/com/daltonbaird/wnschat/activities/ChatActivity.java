package com.daltonbaird.wnschat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.daltonbaird.wnschat.NetworkManager;
import com.daltonbaird.wnschat.R;
import com.daltonbaird.wnschat.functional.Action;
import com.daltonbaird.wnschat.functional.Function;
import com.daltonbaird.wnschat.functional.TernaryAction;
import com.daltonbaird.wnschat.functional.UnaryAction;
import com.daltonbaird.wnschat.messages.Message;
import com.daltonbaird.wnschat.packets.PacketSimpleMessage;
import com.daltonbaird.wnschat.viewmodels.ChatClientViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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
        final EditText editTextMessage = (EditText) this.findViewById(R.id.messageBox);
        final Button sendButton = (Button) this.findViewById(R.id.sendButton);

        assert listViewMessages != null : "Message list was null!";
        assert editTextMessage != null : "Message Box was null!";
        assert sendButton != null : "Send button was null!";

        //Hook up stuff
        //listViewMessages.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, chatClient.getMessageLog()));
        //listViewMessages.setAdapter(new MessageLogAdapter(ChatActivity.this, chatClient.getMessageLog()));

        //final List<Message> messages = new ArrayList<Message>();

        //final ArrayAdapter<Message> messageArrayAdapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, messages);
        final ArrayAdapter<Message> messageArrayAdapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, chatClient.getMessageLog());
        listViewMessages.setAdapter(messageArrayAdapter);

        chatClient.getMessageLog().listChanged.add(new Action()
        {
            @Override
            public void invoke()
            {
                messageArrayAdapter.notifyDataSetChanged();
            }
        });

        /*
        chatClient.messageAdded.add(new UnaryAction<Message>()
        {
            @Override
            public void invoke(final Message message)
            {
                ChatActivity.this.runOnUiThread(new Runnable()
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
        */

        chatClient.messageCleared.add(new Action()
        {
            @Override
            public void invoke()
            {
                ChatActivity.this.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        editTextMessage.getText().clear();
                    }
                });
            }
        });

        chatClient.getMessageString = new Function<String>()
        {
            @Override
            public String invoke()
            {
                FutureTask<String> futureResult = new FutureTask<String>(new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        return editTextMessage.getText().toString();
                    }
                });

                ChatActivity.this.runOnUiThread(futureResult);

                try
                {
                    return futureResult.get(); //Return the result
                }
                catch (InterruptedException|ExecutionException e)
                {
                    throw new RuntimeException("Error getting message text!", e);
                }
            }
        };

        //Overwrite this from LoginActivity just in case
        chatClient.runOnUIThread = new UnaryAction<Runnable>()
        {
            @Override
            public void invoke(Runnable runnable)
            {
                ChatActivity.this.runOnUiThread(runnable);
            }
        };

        chatClient.disconnected.add(new TernaryAction<String, Boolean, String>()
        {
            @Override
            public void invoke(final String clientDisconnectReason, final Boolean clientReasonIsBad, final String serverDisconnectReason)
            {
                ChatActivity.this.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (serverDisconnectReason != null)
                        {
                            //Show an error
                            Toast.makeText(ChatActivity.this, String.format("Server closed connection.  Reason: %s", serverDisconnectReason), Toast.LENGTH_LONG).show();
                        }

                        if (clientReasonIsBad && clientDisconnectReason != null)
                        {
                            //Show an error
                            Toast.makeText(ChatActivity.this, String.format("Error: Client had to close connection.  Reason: %s", clientDisconnectReason), Toast.LENGTH_LONG).show();
                        }

                        //Close the activity, automatically going back to the login activity
                        ChatActivity.this.finish();
                    }
                });
            }
        });

        editTextMessage.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                sendButton.setEnabled(ChatActivity.this.canSendMessage());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
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
            chatClient.logoutCommand.execute(null);

            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
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
        return chatClient.sendCommand.canExecute(null);
    }

    public void sendMessage(View view)
    {
        try
        {
            //Find objects
            final EditText editTextMessage = (EditText) this.findViewById(R.id.messageBox);

            assert editTextMessage != null : "Message EditText was null!";

            if (chatClient.sendCommand.canExecute(null)) //Send the message
                chatClient.sendCommand.execute(null);
        }
        catch (Exception e)
        {
            Toast.makeText(ChatActivity.this, String.format("Error sending message: %s", e), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
