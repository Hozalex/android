package app.test.com.exerciseapp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.test.com.exerciseapp.R;
import app.test.com.exerciseapp.common.IntentHelper;
import app.test.com.exerciseapp.models.Exercise;

public class ExerciseFragment extends Fragment {

    public static ExerciseFragment newInstance(int exerciseNum) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putInt(IntentHelper.EXTRA_EXERCISE_NUM, exerciseNum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvTitle=view.findViewById(R.id.tvTitle);
        TextView tvDescription=view.findViewById(R.id.tvDescription);
        ImageView imageView=view.findViewById(R.id.imageView);

        if(getArguments()==null) return;
        Exercise exercise=Exercise.allExercises[getArguments().getInt(IntentHelper.EXTRA_EXERCISE_NUM)];
        tvTitle.setText(exercise.name);
        tvDescription.setText(exercise.description);
        imageView.setImageResource(exercise.image);
    }
}
