package mao.com.customviewgather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mao.com.customviewgather.base.MoveWaysActivity;
import mao.com.customviewgather.slide.ScrollerActivity;
import mao.com.customviewgather.slide.ScrollerWithGestureActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void moveViewWays(View v) {
        startActivity(new Intent(this, MoveWaysActivity.class));
    }

    public void scroller_move(View v) {
        startActivity(new Intent(this, ScrollerActivity.class));
    }

    public void scroller_gesture_move(View v) {
        startActivity(new Intent(this, ScrollerWithGestureActivity.class));
    }
}
