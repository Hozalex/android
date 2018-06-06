package org.orangecorn.alarm;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class AlarmActivity extends AppCompatActivity {

    TextToSpeech tts;
    EditText cancelEditText;
    Button alarmButton;
    private final String DEFAULT_ALARM = "Ты че? решил ко мне лезьть? а ну вставай, говно, мать твою...";
    String toSpeak;
    String alarmText = "";
    String cancel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        cancelEditText = findViewById(R.id.cancel_text);
        alarmText = getIntent().getExtras().getString("alarmText");
        alarmButton = findViewById(R.id.alarm_button);
        alarmStart();
        //        checkCancel();
        cancelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cancel = cancelEditText.getText().toString();
            }
        });

    }


    private void alarmStart() {

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                    tts.setSpeechRate(0);
                    if (alarmText.length() != 0 && alarmText != null) {
                        toSpeak = alarmText;
                    } else toSpeak = DEFAULT_ALARM;
//                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();

                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, "alarmSpeak");

                }
            }

        });

    }


}
