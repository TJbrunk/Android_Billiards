package com.dmcinfo.billiards;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TwoPlayerActivity extends Activity {

    private Spinner player_first, player_second;
    private int player1_pk, player2_pk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);

        // Get refs to the player order spinners at the bottom of the screen
        player_first = (Spinner) findViewById(R.id.player_one);
        player_second = (Spinner) findViewById(R.id.player_two);

        loadPlayers();
    }

    private void loadPlayers() {
        final DBPlayer db_player;
        ArrayList players;

        db_player = new DBPlayer(this);
        players = db_player.getAllPlayers();

        ArrayAdapter adapter = new ArrayAdapter(this,
                com.dmcinfo.billiards.R.layout.player_dropdown_item,
                players);

        player_first.setAdapter(adapter);
        player_second.setAdapter(adapter);

        player_first.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(player_first.getSelectedItem() != ""){
                    Map<String, Double> stats = new HashMap();
                    stats = db_player.getPlayerStats(player_first.getSelectedItem().toString());
                    TextView mu_1 = (TextView) findViewById(R.id.player1_mu);
                    mu_1.setText(stats.get("mu").toString());
                    TextView sigma_1 = (TextView) findViewById(R.id.player1_sigma);
                    sigma_1.setText(stats.get("sigma").toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        player_second.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(player_second.getSelectedItem() != ""){
                    Map<String, Double> stats = new HashMap();
                    stats = db_player.getPlayerStats(player_second.getSelectedItem().toString());
                    TextView mu = (TextView) findViewById(R.id.player2_mu);
                    mu.setText(stats.get("mu").toString());
                    TextView sigma = (TextView) findViewById(R.id.player2_sigma);
                    sigma.setText(stats.get("sigma").toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
