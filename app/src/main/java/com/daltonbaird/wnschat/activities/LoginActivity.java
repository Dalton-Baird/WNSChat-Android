package com.daltonbaird.wnschat.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daltonbaird.wnschat.Constants;
import com.daltonbaird.wnschat.R;
import com.daltonbaird.wnschat.functional.Function;
import com.daltonbaird.wnschat.utilities.TextValidator;
import com.daltonbaird.wnschat.viewmodels.ChatClientViewModel;

import java.net.InetAddress;

public class LoginActivity extends AppCompatActivity
{
    private boolean usernameValid = false;
    private boolean serverIPAddressValid = false;
    private boolean serverPortValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Find objects
        final EditText editTextUsername = (EditText) this.findViewById(R.id.editTextUsername);
        final EditText editTextServerIP = (EditText) this.findViewById(R.id.editTextServerIP);
        final EditText editTextServerPort = (EditText) this.findViewById(R.id.editTextServerPort);
        final Button buttonConnect = (Button) this.findViewById(R.id.buttonConnect);

        assert editTextUsername != null : "Username EditText was null!";
        assert editTextServerIP != null : "Server IP EditText was null!";
        assert editTextServerPort != null : "Server Port EditText was null!";
        assert buttonConnect != null : "Connect Button was null!";

        //Hook up validation code
        editTextUsername.addTextChangedListener(new TextValidator(editTextUsername)
        {
            //private final int textViewBackgroundColor = editTextUsername.getDrawingCacheBackgroundColor();

            @Override
            public void validate(TextView textView, String text)
            {
                //Log.d("LOGIN DEBUG", String.format("EditText Username validate() called! text is \"%s\"", text));

                if (text != null && text.matches(Constants.UsernameRegexStr))
                {
                    LoginActivity.this.usernameValid = true;
                    //textView.setBackgroundColor(textViewBackgroundColor);
                    textView.setError(null);
                    //Log.d("LOGIN DEBUG", "\tvalidation SUCCESS");
                }
                else
                {
                    LoginActivity.this.usernameValid = false;
                    //textView.setBackgroundColor(ContextCompat.getColor(LoginActivity.this.getBaseContext(), R.color.colorErrorBackground));
                    textView.setError(String.format("Invalid username, only alphanumeric characters, _, and - are allowed.  Username must match the regex string \"%s\".", Constants.UsernameRegexStr));
                    //Log.e("LOGIN DEBUG", "\tvalidation FAIL");
                }

                buttonConnect.setEnabled(LoginActivity.this.canLogin()); //Update the connect button's enabled status
            }
        });

        editTextServerIP.addTextChangedListener(new TextValidator(editTextServerIP)
        {
            //private final int textViewBackgroundColor = editTextServerIP.getDrawingCacheBackgroundColor();

            @Override
            public void validate(TextView textView, String text)
            {
                try
                {
                    if (text == null || text.isEmpty())
                        throw new Exception("IP address cannot be blank!");

                    InetAddress.getByName(text);
                    LoginActivity.this.serverIPAddressValid = true;
                    textView.setError(null);
                    //textView.setBackgroundColor(textViewBackgroundColor);
                }
                catch (Exception e)
                {
                    LoginActivity.this.serverIPAddressValid = false;
                    //textView.setBackgroundColor(ContextCompat.getColor(LoginActivity.this.getBaseContext(), R.color.colorErrorBackground));
                    textView.setError("Invalid IP address: Host lookup failed.");
                }

                buttonConnect.setEnabled(LoginActivity.this.canLogin()); //Update the connect button's enabled status
            }
        });

        editTextServerPort.addTextChangedListener(new TextValidator(editTextServerPort)
        {
            //private final int textViewBackgroundColor = editTextServerPort.getDrawingCacheBackgroundColor();

            @Override
            public void validate(TextView textView, String text)
            {
                try
                {
                    short port = Short.parseShort(text);

                    if (port < 0)
                        throw new NumberFormatException("Port cannot be negative.");

                    LoginActivity.this.serverPortValid = true;
                    textView.setError(null);
                    //textView.setBackgroundColor(textViewBackgroundColor);
                }
                catch (NumberFormatException e)
                {
                    LoginActivity.this.serverPortValid = false;
                    //textView.setBackgroundColor(ContextCompat.getColor(LoginActivity.this.getBaseContext(), R.color.colorErrorBackground));
                    textView.setError(String.format("Invalid server port: %s", e));
                }

                buttonConnect.setEnabled(LoginActivity.this.canLogin()); //Update the connect button's enabled status
            }
        });

        //Load saved data
        this.loadSettings();
    }

    @Override
    protected void onStop()
    {
        this.saveSettings();

        super.onStop();
    }

    /**
     * Saves the settings for the activity
     */
    protected void saveSettings()
    {
        Log.i(this.getClass().getSimpleName(), "Saving settings...");

        //Find objects
        final EditText editTextUsername = (EditText) this.findViewById(R.id.editTextUsername);
        final EditText editTextServerIP = (EditText) this.findViewById(R.id.editTextServerIP);
        final EditText editTextServerPort = (EditText) this.findViewById(R.id.editTextServerPort);
        final Button buttonConnect = (Button) this.findViewById(R.id.buttonConnect);

        assert editTextUsername != null : "Username EditText was null!";
        assert editTextServerIP != null : "Server IP EditText was null!";
        assert editTextServerPort != null : "Server Port EditText was null!";
        assert buttonConnect != null : "Connect Button was null!";

        //Save settings
        SharedPreferences preferences = this.getSharedPreferences("Login", 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("Username", editTextUsername.getText().toString());
        editor.putString("ServerIP", editTextServerIP.getText().toString());
        editor.putString("ServerPort", editTextServerPort.getText().toString());

        editor.apply();
    }

    /**
     * Loads the settings for the activity
     */
    protected void loadSettings()
    {
        Log.i(this.getClass().getSimpleName(), "Loading settings...");

        //Find objects
        final EditText editTextUsername = (EditText) this.findViewById(R.id.editTextUsername);
        final EditText editTextServerIP = (EditText) this.findViewById(R.id.editTextServerIP);
        final EditText editTextServerPort = (EditText) this.findViewById(R.id.editTextServerPort);
        final Button buttonConnect = (Button) this.findViewById(R.id.buttonConnect);

        assert editTextUsername != null : "Username EditText was null!";
        assert editTextServerIP != null : "Server IP EditText was null!";
        assert editTextServerPort != null : "Server Port EditText was null!";
        assert buttonConnect != null : "Connect Button was null!";

        //Load settings
        SharedPreferences preferences = this.getSharedPreferences("Login", 0);

        editTextUsername.setText(preferences.getString("Username", "Android"));
        editTextServerIP.setText(preferences.getString("ServerIP", "0.0.0.0"));
        editTextServerPort.setText(preferences.getString("ServerPort", "9001"));
    }

    private boolean canLogin()
    {
        return this.usernameValid && this.serverIPAddressValid && this.serverPortValid;
    }
    
    public void login(View view)
    {
        //Toast.makeText(LoginActivity.this, "login() called! Not yet implemented.", Toast.LENGTH_SHORT).show();

        try
        {
            //Find objects
            final EditText editTextUsername = (EditText) this.findViewById(R.id.editTextUsername);
            final EditText editTextServerIP = (EditText) this.findViewById(R.id.editTextServerIP);
            final EditText editTextServerPort = (EditText) this.findViewById(R.id.editTextServerPort);
            final Button buttonConnect = (Button) this.findViewById(R.id.buttonConnect);

            assert editTextUsername != null : "Username EditText was null!";
            assert editTextServerIP != null : "Server IP EditText was null!";
            assert editTextServerPort != null : "Server Port EditText was null!";
            assert buttonConnect != null : "Connect Button was null!";

            //Create chat client
            ChatActivity.chatClient = new ChatClientViewModel(
                    editTextUsername.getText().toString(),
                    InetAddress.getByName(editTextServerIP.getText().toString()),
                    Short.parseShort(editTextServerPort.getText().toString()));

            new Thread() //Connect to the server on a new thread, since network I/O isn't allowed on the UI thread
            {
                @Override
                public void run()
                {
                    ChatActivity.chatClient.connectToServer(new Function<String>()
                    {
                        @Override
                        public String invoke()
                        {
                            Toast.makeText(LoginActivity.this, "Server requires a password, giving it an empty one since getting a password is not yet implemented.", Toast.LENGTH_LONG).show();
                            return "";
                        }
                    });
                }
            }.start();

            //Start the ChatActivity
            Intent intent = new Intent(this, ChatActivity.class);
            this.startActivity(intent);

        }
        catch (Exception e)
        {
            Toast.makeText(LoginActivity.this, String.format("Error connecting: %s", e), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the menu items for use in the action bar
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_activity_login, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
