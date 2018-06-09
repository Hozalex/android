package app.test.com.exerciseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import app.test.com.exerciseapp.adapters.ExerciseAdapter;
import app.test.com.exerciseapp.common.IntentHelper;
import app.test.com.exerciseapp.fragments.ExerciseFragment;
import app.test.com.exerciseapp.models.Exercise;

public class MainActivity extends AppCompatActivity {
    private boolean twoPane=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twoPane=findViewById(R.id.flRightContainer)!=null;

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new ExerciseAdapter(Exercise.allExercises, new ExerciseAdapter.OnExerciseClickListener() {
            @Override
            public void onExerciseClick(int exerciseNum) {
                if(!twoPane) {
                    startActivity(new Intent(MainActivity.this, DetailActivity.class)
                            .putExtra(IntentHelper.EXTRA_EXERCISE_NUM, exerciseNum));
                }
                else
                    showDetailFragment(exerciseNum);
            }
        }));
        if(twoPane&savedInstanceState==null)
            showDetailFragment(0);

    }
    private void showDetailFragment(int exerciseNum)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flRightContainer,
                        ExerciseFragment.newInstance(exerciseNum))
                //.addToBackStack(null)
                .commit();
    }
}
