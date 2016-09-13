package com.dmcinfo.billiards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class FivePlayerActivity extends AppCompatActivity {

    private Spinner player_first, player_second, player_third, player_fourth, player_fifth;
    private Spinner player_1, player_2, player_3, player_4, player_5;
    private PoolBall[] balls;
    private ArrayList players, player_group;
    private DBPlayer db_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_player);

        // Get refs to the player/ball assignments spinners
        player_1 = (Spinner) findViewById(R.id.player_1);
        player_2 = (Spinner) findViewById(R.id.player_2);
        player_3 = (Spinner) findViewById(R.id.player_3);
        player_4 = (Spinner) findViewById(R.id.player_4);
        player_5 = (Spinner) findViewById(R.id.player_5);

        // Get refs to the player order spinners
        player_first = (Spinner) findViewById(R.id.first_player);
        player_second = (Spinner) findViewById(R.id.second_player);
        player_third = (Spinner) findViewById(R.id.third_player);
        player_fourth = (Spinner) findViewById(R.id.fourth_player);
        player_fifth = (Spinner) findViewById(R.id.fifth_player);

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
        // get the ball number (as a string) from the ball ID
        String ball_string = ball.getResources().getResourceName(ball.getId()).split("_")[1];
        // convert the ball number string to an integer, decrement because the array is 0 indexed
        int ball_num = Integer.parseInt(ball_string) - 1;
        // index the balls array using the ball number, and toggle the ball state
        balls[ball_num].ToggleBall();
        //CheckPlayerStatus();

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
        player_fourth.setAdapter(adapter);
        player_fifth.setAdapter(adapter);

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

        player_fourth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AddPlayerToGame();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        player_fifth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        this.player_group = new ArrayList();
        this.player_group.clear();
        this.player_group.add(""); // Add blank player to list
        if (player_first.getSelectedItem() != "") {
            this.player_group.add(player_first.getSelectedItem());
        }
        if (player_second.getSelectedItem() != "") {
            this.player_group.add(player_second.getSelectedItem());
        }
        if (player_third.getSelectedItem() != "") {
            this.player_group.add(player_third.getSelectedItem());
        }
        if (player_fourth.getSelectedItem() != "") {
            this.player_group.add(player_fourth.getSelectedItem());
        }
        if (player_fifth.getSelectedItem() != "") {
            this.player_group.add(player_fifth.getSelectedItem());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,
                                com.dmcinfo.billiards.R.layout.player_dropdown_item,
                                player_group);
        player_1.setAdapter(adapter);
        player_2.setAdapter(adapter);
        player_3.setAdapter(adapter);
        player_4.setAdapter(adapter);
        player_5.setAdapter(adapter);
    }

    private void CheckPlayerStatus(){
        if(balls[0].IsInPlay || balls[1].IsInPlay || balls[2].IsInPlay){
            player_first.setBackgroundColor(0xFFAAAAAA);
            //player_first.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        }
        else {
            player_first.setBackgroundColor(getResources().getColor(R.color.black_overlay));
            //player_first.setBackgroundColor(0xFFAAAAAA);
        }
    }
}
