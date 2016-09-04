package com.dmcinfo.billiards;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ThreePlayerActivity extends Activity {

    private Spinner player_low, player_mid, player_high;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_three_player);

        player_low = (Spinner)findViewById(R.id.player_low);
        player_mid = (Spinner)findViewById(R.id.player_mid);
        player_high = (Spinner)findViewById(R.id.player_high);
    }
}