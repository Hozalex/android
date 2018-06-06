package org.orangecorn.wine;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SloshedActivity extends AppCompatActivity {

    Intent intent = new Intent(Intent.ACTION_SEND);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sloshed);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void sloshedWineOrder(View view) {
        TextView text = findViewById(R.id.sloshedWineText);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hozalex@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT, text.getText());
        startActivity(Intent.createChooser(intent, "Куда послать??"));
    }

    public void sloshedBeerOrder(View view) {
        TextView text = findViewById(R.id.sloshedBeerText);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hozalex@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT, text.getText());
        startActivity(Intent.createChooser(intent, "Куда послать??"));
    }
}

