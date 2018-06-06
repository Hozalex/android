package org.orangecorn.corn;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    String name;
    int salt = 0;
    int sugar = 0;
    boolean isSugar;
    boolean isSalt;

    String emailTo = "ahozyainov@acrontrans.spb.ru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void displayQuantity(int quantity) {
        TextView quantityTextView = findViewById(R.id.text_view_0);
        quantityTextView.setText("" + quantity);

    }

    public void sendOrder(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);

        EditText nameText = findViewById(R.id.edit_text_name);
        name = nameText.getText().toString();
        String price = NumberFormat.getCurrencyInstance().format(calculatePrice(quantity));
        String order = getString(R.string.order_name, name) + "\n" +
                getString(R.string.order_quantity, quantity) + "\n" +
                getString(R.string.order_sugar, isSugar) + "\n" +
                getString(R.string.order_salt, isSalt) + "\n" +
                getString(R.string.order_price, price) + "\n" +
                getString(R.string.thanks);
        intent.setType("message/email");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.order_summary));
        intent.putExtra(Intent.EXTRA_TEXT, order);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    private int calculatePrice(int quantity) {
        CheckBox checkBoxSalt = findViewById(R.id.check_box_salt);
        CheckBox checkBoxSugar = findViewById(R.id.check_box_sugar);
        if (checkBoxSalt.isChecked()) {
            isSalt = true;
            salt = 2;
        } else salt = 0;
        if (checkBoxSugar.isChecked()) {
            isSugar = true;
            sugar = 3;
        } else sugar = 0;
        int price = quantity * 5 + sugar + salt;
        return price;
    }

    public void clickMinus(View view) {
        if (quantity == 1) {
            return;
        }
        displayQuantity(quantity -= 1);
    }

    public void clickPlus(View view) {
        if (quantity > 100) {
            return;
        }
        displayQuantity(quantity += 1);
    }
}
