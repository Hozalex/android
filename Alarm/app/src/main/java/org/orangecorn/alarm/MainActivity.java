package org.orangecorn.alarm;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {


    private EditText dayEditText;
    private EditText timeEditText;
    private EditText alarmText;
    String day;
    String time;
    String alarmT = "";
    private TextView test;
    Button btn;
    Intent intent;
    //Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        intent = new Intent(this, AlarmActivity.class);
        btn = findViewById(R.id.button);
        alarmText = findViewById(R.id.alarmText);
        dayEditText = findViewById(R.id.dayText);
        timeEditText = findViewById(R.id.clockText);
        test = findViewById(R.id.test);


        alarmText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                alarmT = alarmText.getText().toString();

            }
        });
        dayEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                day = dayEditText.getText().toString();
            }
        });
        timeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                time = timeEditText.getText().toString();
            }
        });


    }


    public void press(View view) {

        test.setText(time + day + alarmT);


    }

    public void recallAlarm(View view) {
        intent.putExtra("alarmText", alarmT);
        startActivity(intent);


    }
}
