package com.android.riazk29.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.rifatk29.Adapters.WeatherAdapter;
import com.android.rifatk29.DataRetrievers.WeatherRetriever;
import com.android.rifatk29.Interfaces.WeatherInterfaces;
import com.android.rifatk29.Models.WeatherModel;
import com.android.rifatk29.databinding.FragmentWeatherBinding;
import com.android.rifatk29.databinding.FragmentWikipediaBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FragmentWeather extends Fragment implements WeatherInterfaces {
    private FragmentWeatherBinding binding;
    private WeatherRetriever weatherRetriever;
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
        binding=FragmentWeatherBinding.inflate(getLayoutInflater());

        //create weather reterievers
        weatherRetriever =new WeatherRetriever(getActivity(),this::setWeather);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               weatherRetriever.getWeatherForecast(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return binding.getRoot();
    }


    /**
     * Sets the weather information in the TextView.
     *
     * @param weather The weather information to be displayed.
     */
    @Override
    public void setWeather(String weather) {
        WeatherAdapter weatherAdapter=new WeatherAdapter(getActivity(),processWeatherData(weather));
        binding.recyclerView.setAdapter(weatherAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public ArrayList<WeatherModel> processWeatherData(String weatherJson) {
        ArrayList<WeatherModel> weatherList = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(weatherJson);
            JSONArray forecastArray = jsonResponse.getJSONArray("list");

            for (int i = 0; i < forecastArray.length(); i++) {
                JSONObject forecast = forecastArray.getJSONObject(i);

                // get time
                String dateTime = forecast.getString("dt_txt");
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM EEEE HH:mm", Locale.getDefault());

                Date date = null;
                try {
                    date = inputFormat.parse(dateTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String formattedDate = outputFormat.format(date);

                // temp and weather status
                JSONObject main = forecast.getJSONObject("main");
                double tempK = main.getDouble("temp");
                double feelsLikeK = main.getDouble("feels_like");
                double tempMinK = main.getDouble("temp_min");
                double tempMaxK = main.getDouble("temp_max");

                // Convert kelvin to celsius
                double temp = Math.round((tempK - 273.15) * 10) / 10.0;
                double feelsLike = Math.round((feelsLikeK - 273.15) * 10) / 10.0;
                double tempMin = Math.round((tempMinK - 273.15) * 10) / 10.0;
                double tempMax = Math.round((tempMaxK - 273.15) * 10) / 10.0;

                int pressure = main.getInt("pressure");
                int humidity = main.getInt("humidity");


                // weather status and icon
                JSONObject weather = forecast.getJSONArray("weather").getJSONObject(0);
                String description = weather.getString("description");
                String iconCode = weather.getString("icon");
                String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + ".png";

                // wind
                JSONObject wind = forecast.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                int windDeg = wind.getInt("deg");

                // visibility distance
                int visibility = forecast.getInt("visibility");

                // set data to html format
                String otherDetails = "<b>Date Time:</b> " + formattedDate + "<br>" +
                        "<b>Temperature:</b> " + temp + " °C (Feels like: " + feelsLike + " °C)<br>" +
                        "<b>Min/Max Temp:</b> " + tempMin + " °C / " + tempMax + " °C<br>" +
                        "<b>Pressure:</b> " + pressure + " hPa<br>" +
                        "<b>Humidity:</b> " + humidity + "%<br>" +
                        "<b>Weather:</b> " + description + "<br>" +
                        "<b>Wind Speed:</b> " + windSpeed + " m/s (Direction: " + windDeg + " degrees)<br>" +
                        "<b>Visibility:</b> " + visibility + " meters<br>";

                // create model and add to list
                WeatherModel model = new WeatherModel(iconUrl, otherDetails);
                weatherList.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherList;
    }
}
