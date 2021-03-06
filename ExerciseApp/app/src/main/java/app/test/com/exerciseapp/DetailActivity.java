package app.test.com.exerciseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.test.com.exerciseapp.common.IntentHelper;
import app.test.com.exerciseapp.fragments.ExerciseFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flDetailContainer,
                            ExerciseFragment.newInstance(getIntent().getIntExtra(IntentHelper.EXTRA_EXERCISE_NUM, 0)))
                    .commit();
        }
    }
}
