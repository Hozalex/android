package org.orangecorn.wine;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LeglessActivity extends AppCompatActivity {

    Intent intent = new Intent(Intent.ACTION_SEND);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_legless);
    }

    public void leglessWineOrder(View view) {
        TextView text = findViewById(R.id.leglessWineText);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hozalex@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT, text.getText());
        startActivity(Intent.createChooser(intent, "Куда послать??"));

    }

    public void leglessBeerOrder(View view) {
        TextView text = findViewById(R.id.leglessWineText);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hozalex@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT, text.getText());
        startActivity(Intent.createChooser(intent, "Куда послать??"));
    }
}
