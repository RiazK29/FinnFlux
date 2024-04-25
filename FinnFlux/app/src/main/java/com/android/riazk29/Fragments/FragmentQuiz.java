package com.android.riazk29.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.rifatk29.Utilities.JsonUtils;
import com.android.rifatk29.Models.TrueOrFalseModel;
import com.android.rifatk29.databinding.FragmentQuizBinding;

import java.util.ArrayList;

public class FragmentQuiz extends Fragment {
    FragmentQuizBinding binding;
    ArrayList<TrueOrFalseModel> trueOrFalseModelArrayList;
    int position=0;
    int trueAnswers;

    /**
     * Called when the Fragment's onCreateView method is called to create and return the layout for the fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentQuizBinding.inflate(getLayoutInflater());
        trueOrFalseModelArrayList= JsonUtils.loadQuestionsFromJson(getActivity(),"quiz.json");
        return binding.getRoot();
    }
    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view. It is followed by onStart().
     *
     * @param view               The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvContent.setText(trueOrFalseModelArrayList.get(0).getQuestion());
        binding.tvSeeAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Answer");
                builder.setMessage(trueOrFalseModelArrayList.get(position).getContent());
                builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        binding.tvTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position<trueOrFalseModelArrayList.size()){
                    if (trueOrFalseModelArrayList.get(position).isAnswer()==true){
                        rotateViewVertically(binding.cardview);
                    }else {
                        shakeView(binding.cardview);
                    }
                }



            }
        });
        binding.tvFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position<trueOrFalseModelArrayList.size()){
                    if (trueOrFalseModelArrayList.get(position).isAnswer()==false){
                        rotateViewVertically(binding.cardview);
                    }else {
                        shakeView(binding.cardview);
                    }
                }


            }
        });
    }

    /**
     * Rotates the view vertically.
     *
     * @param view The view to rotate.
     */
    public void rotateViewVertically(final View view) {
        // Set the camera distance for depth effects
        final float distance = 8000;
        final float scale = getResources().getDisplayMetrics().density * distance;
        view.setCameraDistance(scale);

        // First half of the rotation (0 to 180 degrees)
        ObjectAnimator firstHalfAnimator = ObjectAnimator.ofFloat(view, "rotationY", 0.0f, 180.0f);
        firstHalfAnimator.setDuration(1); // half duration for the first half of the rotation
        firstHalfAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        // Second half of the rotation (180 to 360 degrees)
        final ObjectAnimator secondHalfAnimator = ObjectAnimator.ofFloat(view, "rotationY", 180.0f, 360.0f);
        secondHalfAnimator.setDuration(500); // half duration for the second half of the rotation
        secondHalfAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        firstHalfAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Start the second half of the rotation when the first half ends
                secondHalfAnimator.start();
            }
        });
        secondHalfAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                    position++;
                    if (position<trueOrFalseModelArrayList.size()){

                        binding.tvContent.setText(trueOrFalseModelArrayList.get(position).getQuestion());
                        trueAnswers++;
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Quiz Over");
                        builder.setMessage("True: "+trueAnswers+"\n False: "+String.valueOf(10-trueAnswers));
                        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }


            }
        });
        // Start the first half of the rotation
        firstHalfAnimator.start();
    }
    /**
     * Shakes the given view horizontally.
     *
     * @param view The view to shake.
     */
    public void shakeView(View view) {
        // Create an ObjectAnimator instance for horizontal shake effect
        ObjectAnimator shakeAnimator = ObjectAnimator.ofFloat(view, "translationX", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f);
        shakeAnimator.setDuration(500); // Set the duration of the animation (in milliseconds)
        shakeAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); // Set the interpolator for smooth animation

        shakeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                // Optional: Do something when the animation starts
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // Optional: Do something when the animation ends
                position++;
                rotateViewVertically(binding.cardview);
            }
        });

        shakeAnimator.start(); // Start the animation
    }

}
