package org.orangecorn.wine;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
    }

    public void sloshed(View view) {
        Intent intent = new Intent(MainActivity.this, SloshedActivity.class);
        startActivity(intent);

    }

    public void legless(View view) {
        Intent intent = new Intent(MainActivity.this, LeglessActivity.class);
        startActivity(intent);
    }


    public void choice(View view) {
        Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
        startActivity(intent);
    }
}
