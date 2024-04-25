package com.android.riazk29.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.rifatk29.Fragments.FragmentQuiz;
import com.android.rifatk29.Fragments.FragmentTilastokeskus;
import com.android.rifatk29.Fragments.FragmentWeather;
import com.android.rifatk29.Fragments.FragmentWikipedia;
import com.android.rifatk29.R;
import com.android.rifatk29.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    /**
     * Called when the activity is starting. This is where most initialization should go: calling setContentView(int) to inflate the activity's UI, using findViewById(int) to programmatically interact with widgets in the UI, and binding data to UI elements.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                new FragmentWikipedia(),"WIKIPEDIA").commit();

        // Set menu listener
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_wikipedia) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new FragmentWikipedia(),"WIKIPEDIA").commit();
                return true;
            } else if (id == R.id.navigation_weather) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new FragmentWeather(),"WEATHER").commit();
                return true;
            } else if (id == R.id.navigation_population) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new FragmentTilastokeskus(),"POPULATION").commit();
                return true;
            } else if (id == R.id.navigation_quiz) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new FragmentQuiz(),"QUIZ").commit();
                return true;
            }
            return false;
        });
    }

}