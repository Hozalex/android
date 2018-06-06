package org.orangecorn.wine;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ChoiceActivity extends AppCompatActivity {

    int wineQuantity = 0;
    int beerQuantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_choice);
    }

    public void wineMinus(View view) {
        wineQuantity -= 1;
        if (wineQuantity < 0) {
            wineQuantity = 0;
        } else {
            displayWineQuantity(wineQuantity);
        }
    }

    public void winePlus(View view) {
        wineQuantity += 1;
        displayWineQuantity(wineQuantity);
    }

    public void beerPlus(View view) {
        beerQuantity += 1;
        displayBeerQuantity(beerQuantity);
    }

    public void beerMinus(View view) {
        beerQuantity -= 1;
        if (beerQuantity < 0) {
            beerQuantity = 0;
        } else {
            displayBeerQuantity(beerQuantity);
        }
    }

    private void displayWineQuantity(int wineQuantity) {
        TextView textView = findViewById(R.id.textViewQuantityWine);
        textView.setText("" + wineQuantity);
    }

    private void displayBeerQuantity(int beerQuantity) {
        TextView textView = findViewById(R.id.textViewQuantityBeer);
        textView.setText("" + beerQuantity);
    }

    public void choiceOrder(View view) {
        EditText text = findViewById(R.id.edit_text_choice);
        String tx = "Вино - " + wineQuantity + "\n" +
                "Пиво - " + beerQuantity + "\n" +
                "Закуски - " + "\n" +
                text.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hozalex@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Бухнем?");
        intent.putExtra(Intent.EXTRA_TEXT, tx);
        startActivity(Intent.createChooser(intent, "Куда послать??"));


    }
}
