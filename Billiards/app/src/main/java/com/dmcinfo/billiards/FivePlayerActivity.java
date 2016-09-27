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
    private Spinner player_low_low, player_low, player_mid, player_high, player_high_high;
    private PoolBall[] balls;
    private ArrayList players, player_group;
    private DBPlayer db_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_player);

        // Get refs to the player/ball assignments spinners
        player_low_low = (Spinner) findViewById(R.id.player_low_low);
        player_low = (Spinner) findViewById(R.id.player_low);
        player_mid = (Spinner) findViewById(R.id.player_mid);
        player_high = (Spinner) findViewById(R.id.player_high);
        player_high_high = (Spinner) findViewById(R.id.player_high_high);

        // Get refs to the player order spinners
        player_first = (Spinner) findViewById(R.id.first_player);
        player_second = (Spinner) findViewById(R.id.second_player);
        player_third = (Spinner) findViewById(R.id.third_player);
        player_fourth = (Spinner) findViewById(R.id.fourth_player);
        player_fifth = (Spinner) findViewById(R.id.fifth_player);

        // Initialize the pool ball array
        initBalls();
        loadPlayers();

        player_low_low.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CheckPlayerStatus(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        player_low.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CheckPlayerStatus(3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        player_mid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CheckPlayerStatus(6);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        player_high.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CheckPlayerStatus(9);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        player_high_high.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CheckPlayerStatus(12);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        CheckPlayerStatus(ball_num);
    }

    private void loadPlayers() {
        db_player = new DBPlayer(this);
        this.players = new ArrayList();

        players = db_player.getAllPlayers();

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

    private void AddPlayerToGame(){
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
        player_low_low.setAdapter(adapter);
        player_low.setAdapter(adapter);
        player_mid.setAdapter(adapter);
        player_high.setAdapter(adapter);
        player_high_high.setAdapter(adapter);
    }

    private void CheckPlayerStatus(int ball_num){
        String player;
        int ball_count;

        // Check if the player's balls were just eliminated, or if they are now back in the game,
        // If either case, get the player's name for the ball that was just toggled
        switch(ball_num) {
            case(0):
            case(1):
            case(2):
            {
                ball_count = PlayerBallCount(0);
                // A ball from the low group was toggled
                if (ball_count == 1) {
                    player = player_low_low.getSelectedItem().toString();
                    if(player != null && !player.isEmpty() ) {
                        FindPlayerOrderSpinner(player).setVisibility(View.VISIBLE);
                    }
                } else if (ball_count == 0) {
                    player = player_low_low.getSelectedItem().toString();
                    if(player != null && !player.isEmpty() ) {
                        FindPlayerOrderSpinner(player).setVisibility(View.INVISIBLE);
                    }
                }
                break;
            }
            case(3):
            case(4):
            case(5):
            {
                ball_count = PlayerBallCount(1);
                if (ball_count == 1) {
                    player = player_low.getSelectedItem().toString();
                    if(player != null && !player.isEmpty() ) {
                        FindPlayerOrderSpinner(player).setVisibility(View.VISIBLE);
                    }
                } else if (ball_count == 0) {
                    player = player_low.getSelectedItem().toString();
                    if(player != null && !player.isEmpty() ) {
                        FindPlayerOrderSpinner(player).setVisibility(View.INVISIBLE);
                    }
                }
                break;
            }
            case(6):
            case(7):
            case(8):
            {
                ball_count = PlayerBallCount(2);
                if (ball_count == 1) {
                    player = player_mid.getSelectedItem().toString();
                    if(player != null && !player.isEmpty() ) {
                        FindPlayerOrderSpinner(player).setVisibility(View.VISIBLE);
                    }
                } else if (ball_count == 0) {
                    player = player_mid.getSelectedItem().toString();
                    if(player != null && !player.isEmpty() ) {
                        FindPlayerOrderSpinner(player).setVisibility(View.INVISIBLE);
                    }
                }
                break;
            }
            case(9):
            case(10):
            case(11):
            {
                ball_count = PlayerBallCount(3);
                if (ball_count == 1) {
                    player = player_high.getSelectedItem().toString();
                    if(player != null && !player.isEmpty() ) {
                        FindPlayerOrderSpinner(player).setVisibility(View.VISIBLE);
                    }
                } else if (ball_count == 0) {
                    player = player_high.getSelectedItem().toString();
                    if(player != null && !player.isEmpty() ) {
                        FindPlayerOrderSpinner(player).setVisibility(View.INVISIBLE);
                    }
                }
                break;
            }
            case(12):
            case(13):
            case(14):
            {
                ball_count = PlayerBallCount(4);
                if (ball_count == 1) {
                    player = player_high_high.getSelectedItem().toString();
                    if(player != null && !player.isEmpty() ) {
                        FindPlayerOrderSpinner(player).setVisibility(View.VISIBLE);
                    }
                } else if (ball_count == 0) {
                    player = player_high_high.getSelectedItem().toString();
                    if(player != null && !player.isEmpty() ) {
                        FindPlayerOrderSpinner(player).setVisibility(View.INVISIBLE);
                    }
                }
                break;
            }
        }
    }

    // group should be 0=Lows, 1=Mids, 2=Highs
    private int PlayerBallCount(int group){
        int active_ball_count = 0;

        for(int i = (group * 3); i <= ((group * 3)+2); i++){
            if(balls[i].IsInPlay){
                active_ball_count++;
            }
        }
        return active_ball_count;
    }

    private Spinner FindPlayerOrderSpinner(String player){
        if(player_first.getSelectedItem().toString() == player){
            return player_first;
        }
        else if(player_second.getSelectedItem().toString() == player){
            return player_second;
        }
        else if(player_third.getSelectedItem().toString() == player){
            return player_third;
        }
        else if(player_fourth.getSelectedItem().toString() == player){
            return player_fourth;
        }
        else if(player_fifth.getSelectedItem().toString() == player){
            return player_fifth;
        }
        else{
            return null;
        }
    }
}
