package com.android.riazk29.DataRetrievers;

import android.app.Activity;

import com.android.rifatk29.Interfaces.WikipediaInterfaces;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WikipediaRetriever {
   private Activity activity;
    private RequestQueue wikipediaQueue;
private WikipediaInterfaces wikipediaInterfaces;
    public WikipediaRetriever(Activity activity, WikipediaInterfaces wikipediaInterfaces) {
        this.activity = activity;
        this.wikipediaInterfaces = wikipediaInterfaces;
        //create volley request
        wikipediaQueue = Volley.newRequestQueue(activity);
    }

    /**
     * Get data from wikipedia
     * @param query is search title
     * */
    public void fetchWikiData(String query) {
        String url = "https://en.wikipedia.org/api/rest_v1/page/summary/"+query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            wikipediaInterfaces.setWikiData(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                wikipediaInterfaces.setWikiData("Not found!");
            }
        });

        wikipediaQueue.add(stringRequest);
    }
}
