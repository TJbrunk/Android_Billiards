package com.dmcinfo.billiards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void Start_2P_Game(View v)
    {
        Intent intent = new Intent(this, TwoPlayerActivity.class);
        startActivity(intent);
    }

    public void Start_3P_Game(View v)
    {
        Intent intent = new Intent(this, ThreePlayerActivity.class);
        startActivity(intent);
    }

    public void Start_5P_Game(View v)
    {
        Intent intent = new Intent(this, FivePlayerActivity.class);
        startActivity(intent);
    }

    public void openSettings(View v){
        Intent intent = new Intent(this, SettingsActvity.class);
        startActivity(intent);
    }
}
