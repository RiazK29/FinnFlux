package com.android.riazk29.DataRetrievers;

import android.app.Activity;

import com.android.rifatk29.Configs;
import com.android.rifatk29.Interfaces.WeatherInterfaces;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherRetriever {
    private Activity activity;
    private RequestQueue weatherQueue;
    private WeatherInterfaces weatherInterfaces;
    public WeatherRetriever(Activity activity, WeatherInterfaces weatherInterfaces) {
        this.activity = activity;
        this.weatherInterfaces = weatherInterfaces;
        //create volley request
        weatherQueue = Volley.newRequestQueue(activity);
    }
    /**Get weather for city
     * @param city is city name
     * */
    public void getWeatherForecast(String city) {
        String url =
                "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid="+ Configs.WEATHER_API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            weatherInterfaces.setWeather(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                weatherInterfaces.setWeather("Failed to get weather data!");
            }
        });

        weatherQueue.add(stringRequest);

    }

    /**
     * set date format
     * @param timestamp is time*/
    private String formatDate(long timestamp) {
        Date date = new Date(timestamp * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
