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

    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    LinearLayout mainLayout;
    Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        popUp = new PopupWindow(this);
        popUp = new PopupWindow(this);
        layout = new LinearLayout(this);
        mainLayout = new LinearLayout(this);
        tv = new TextView(this);
        but = new Button(this);
        but.setText("Click Me");

        setContentView(R.layout.activity_main);
    }

    public void Start_2P_Game(View v)
    {
       /* Intent intent = new Intent(this, .class);
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/
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
