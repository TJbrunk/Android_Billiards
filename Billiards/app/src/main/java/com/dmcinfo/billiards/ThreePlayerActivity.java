package com.dmcinfo.billiards;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class ThreePlayerActivity extends Activity {

    private Spinner player_low, player_mid, player_high;
    private Spinner player_first, player_second, player_third;
    private PoolBall[] balls;
    private ArrayList players, groups;
    private DBPlayer db_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_three_player);

        // Get refs to the player/ball assignment spinners
        player_low = (Spinner) findViewById(R.id.player_low);
        player_mid = (Spinner) findViewById(R.id.player_mid);
        player_high = (Spinner) findViewById(R.id.player_high);

        // Get refs to the player order spinners at the bottom of the screen
        player_first = (Spinner) findViewById(R.id.first_player);
        player_second = (Spinner) findViewById(R.id.second_player);
        player_third = (Spinner) findViewById(R.id.third_player);

        // Initialize the pool ball array
        initBalls();
        loadPlayers();
    }

    private void initBalls() {
        balls = new PoolBall[15];
        for (int i = 0; i < 15; i++) {
            String ball_id_string = "ball_" + (i + 1);
            int ball_id = getResources().getIdentifier(ball_id_string,
                    "id",
                    getApplicationContext().getPackageName());
            TextView ball_view = (TextView) findViewById(ball_id);

            balls[i] = new PoolBall(ball_view, i + 1, ball_id_string);
        }
    }

    // The pool ball images call this function when they're clicked
    public void toggle(View ball) {
        String ball_string = ball.getResources().getResourceName(ball.getId()).split("_")[1];
        int ball_num = Integer.parseInt(ball_string) - 1;
        balls[ball_num].ToggleBall();
        CheckPlayerStatus(ball_num);
    }

    private void loadPlayers() {
        db_player = new DBPlayer(this);
        int i = 1;
        this.players = new ArrayList();
        this.players.clear();

        this.players.add(""); // Add blank player so that none can be selected
        while (db_player.getPlayer(i) != "none") {
            this.players.add(this.db_player.getPlayer(i));
            i += 1;
        }
        // order the players alphabetically
        Collections.sort(players);

        // Add a few temp guest players
        this.players.add("Guest 1");
        this.players.add("Guest 2");
        this.players.add("Guest 3");

        ArrayAdapter adapter = new ArrayAdapter(this,
                                                com.dmcinfo.billiards.R.layout.player_dropdown_item,
                                                this.players);
        player_first.setAdapter(adapter);
        player_second.setAdapter(adapter);
        player_third.setAdapter(adapter);

        player_first.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AddPlayerToGame();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        player_second.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AddPlayerToGame();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        player_third.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AddPlayerToGame();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void AddPlayerToGame()
    {
        this.groups = new ArrayList();
        this.groups.clear();
        this.groups.add(""); // Add blank player to list
        if (player_first.getSelectedItem() != "") {
            this.groups.add(player_first.getSelectedItem());
        }
        if (player_second.getSelectedItem() != "") {
            this.groups.add(player_second.getSelectedItem());
        }
        if (player_third.getSelectedItem() != "") {
            this.groups.add(player_third.getSelectedItem());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, com.dmcinfo.billiards.R.layout.player_dropdown_item, groups);
        player_low.setAdapter(adapter);
        player_mid.setAdapter(adapter);
        player_high.setAdapter(adapter);
    }

    private void CheckPlayerStatus(int ball_num){
        String player;

        // Check if the player's balls were just eliminated, or if they are now back in the game,
        // If either case, get the player's name for the ball that was just toggled
        if(ball_num <= 4 ){
            // A ball from the low group was toggled
            if(PlayerBallCount(0) == 1){
                player = player_low.getSelectedItem().toString();
                FindPlayerOrderSPinner(player).setVisibility(View.VISIBLE);
            }
            else if(PlayerBallCount(0) == 0){
                player = player_low.getSelectedItem().toString();
                FindPlayerOrderSPinner(player).setVisibility(View.INVISIBLE);
            }
        }
        else if(ball_num <= 9){
            if(PlayerBallCount(1) == 1){
                player = player_mid.getSelectedItem().toString();
                FindPlayerOrderSPinner(player).setVisibility(View.VISIBLE);
            }
            else if(PlayerBallCount(1) == 0){
                player = player_mid.getSelectedItem().toString();
                FindPlayerOrderSPinner(player).setVisibility(View.INVISIBLE);
            }
        }
        else{
            if(PlayerBallCount(2) == 1){
                player = player_high.getSelectedItem().toString();
                FindPlayerOrderSPinner(player).setVisibility(View.VISIBLE);
            }
            else if(PlayerBallCount(2) == 0){
                player = player_high.getSelectedItem().toString();
                FindPlayerOrderSPinner(player).setVisibility(View.INVISIBLE);
            }
        }
    }

    // group should be 0=Lows, 1=Mids, 2=Highs
    private int PlayerBallCount(int group){
        int active_ball_count = 0;

        for(int i = (group * 5); i <= ((group * 5)+4); i++){
            if(balls[i].IsInPlay){
                active_ball_count++;
            }
        }
        return active_ball_count;
    }

    private Spinner FindPlayerOrderSPinner(String player){
        if(player_first.getSelectedItem().toString() == player){
            return player_first;
        }
        else if(player_second.getSelectedItem().toString() == player){
            return player_second;
        }
        else if(player_third.getSelectedItem().toString() == player){
            return player_third;
        }
        else{
            return null;
        }
    }
}