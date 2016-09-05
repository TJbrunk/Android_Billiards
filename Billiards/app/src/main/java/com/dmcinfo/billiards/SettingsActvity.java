package com.dmcinfo.billiards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActvity extends AppCompatActivity {

    private DBPlayer player_db;
    private String first_name, last_name;
    Boolean fname_valid, lname_valid;
    Button add_player_button;
    EditText f_name, l_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initPlayerControls();
    }

    private void initPlayerControls(){
        fname_valid = false;
        lname_valid = false;

        // disable the add player button
        add_player_button = (Button) findViewById(R.id.add_player);
        add_player_button.setEnabled(false);

        // add event listener to first and last name fields
        f_name = (EditText) findViewById(R.id.first_name);
        l_name = (EditText) findViewById(R.id.last_name);

        f_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0){
                    fname_valid = true;
                    if(lname_valid){
                        add_player_button.setEnabled(true);
                    }
                }
                else{
                    fname_valid = false;
                    add_player_button.setEnabled(false);
                }
                Log.v("First name:", s.toString());
            }
        });

        l_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0 ){
                    lname_valid = true;
                    if(fname_valid){
                        add_player_button.setEnabled(true);
                    }
                }
                else{
                    lname_valid = false;
                    add_player_button.setEnabled(false);
                }
                Log.v("Last name:", s.toString());;
            }
        });

    }

    public void addPlayerToDB(View v) {
        try {
            player_db = new DBPlayer(this);

            first_name = ((EditText) findViewById(R.id.first_name)).getText().toString();
            last_name = ((EditText) findViewById(R.id.last_name)).getText().toString();

            if (player_db.addPlayer(first_name, last_name)){

                Toast.makeText(getApplicationContext(), "player added", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "player already exists", Toast.LENGTH_LONG).show();
            }

            ((EditText) findViewById(R.id.first_name)).setText("");
            ((EditText) findViewById(R.id.last_name)).setText("");
            return;
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error adding player", Toast.LENGTH_LONG).show();
        }
    }

    public void initDB(View v){
        player_db = new DBPlayer(this);

        player_db.addPlayer("Boris", "Cherkasskiy");
        player_db.addPlayer("Devon", "Fritz");
        player_db.addPlayer("Jimmy", "Condon");
        player_db.addPlayer("Nick", "Aroneseno");
        player_db.addPlayer("Otto", "Gottlieb");
        player_db.addPlayer("Ryan", "Lake");
        player_db.addPlayer("Sully", "John");
        player_db.addPlayer("Tim", "Gee");
        player_db.addPlayer("Tyler", "Brink");
        player_db.addPlayer("Cameron", "Fyfe");

    }
}
