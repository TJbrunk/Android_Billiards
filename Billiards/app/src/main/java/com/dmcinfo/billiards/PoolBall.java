package com.dmcinfo.billiards;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by tylerb on 6/14/2016.
 */
public class PoolBall extends Activity
{

    TextView PlayerBalls;
    Boolean IsInPlay;
    Integer InPlayImage;
    Integer PocketedImage;
    String BallID;

    private static Integer in_play_images[] = {R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.drawable.seven,
            R.drawable.eight,
            R.drawable.nine,
            R.drawable.ten,
            R.drawable.eleven,
            R.drawable.twelve,
            R.drawable.thirteen,
            R.drawable.fourteen,
            R.drawable.fifteen};

    private static Integer pocketed_images[] = { R.drawable.one_out,
            R.drawable.two_out,
            R.drawable.three_out,
            R.drawable.four_out,
            R.drawable.five_out,
            R.drawable.six_out,
            R.drawable.seven_out,
            R.drawable.eight_out,
            R.drawable.nine_out,
            R.drawable.ten_out,
            R.drawable.eleven_out,
            R.drawable.twelve_out,
            R.drawable.thirteen_out,
            R.drawable.fourteen_out,
            R.drawable.fifteen_out};

    public PoolBall(TextView ball_view, int ball_number, String ball_id)
    {
        //decrement the ball number so API calls can be used as 1-15
        ball_number --;
        PlayerBalls = ball_view;
        IsInPlay = true;
        BallID = ball_id;
        InPlayImage = in_play_images[ball_number];
        PocketedImage = pocketed_images[ball_number];
    }

    public void ToggleBall()
    {
        if(this.IsInPlay){
            this.PlayerBalls.setBackgroundResource(this.PocketedImage);

            this.PlayerBalls.setActivated(false);

            this.IsInPlay = false;
        }
        else {
            this.PlayerBalls.setBackgroundResource(this.InPlayImage);

            this.PlayerBalls.setActivated(true);

            this.IsInPlay = true;
        }
    }

}
