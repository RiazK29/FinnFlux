package com.android.riazk29.DataRetrievers;

import static com.android.rifatk29.Configs.API_URL;

import android.app.Activity;
import android.util.Log;

import com.android.rifatk29.Interfaces.CitizenshipInterface;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CitizenshipRetriever {
    private RequestQueue requestQueue;
    private Activity activity;
    private CitizenshipInterface  citizenshipInterface;
    private static final String TAG = "FragmentTilastokeskus";

    public CitizenshipRetriever(Activity activity,CitizenshipInterface  citizenshipInterface) {
        requestQueue = Volley.newRequestQueue(activity);
        this.activity = activity;
        this.citizenshipInterface = citizenshipInterface;
    }
    /**
     * Method to fetch data from the API.
     *
     * @param areaCode The area code to fetch data for.
     */
    public void fetchData(String areaCode) {
        try {
            JSONObject jsonQuery = new JSONObject();
            JSONArray query = new JSONArray();

            // Maakunta selection (Specific Area)
            JSONObject maakunta = new JSONObject();
            maakunta.put("code", "Maakunta");
            JSONObject maakuntaSelection = new JSONObject();
            maakuntaSelection.put("filter", "item");
            JSONArray maakuntaValues = new JSONArray();
            maakuntaValues.put(areaCode);  // Add only the specific area code
            maakuntaSelection.put("values", maakuntaValues);
            maakunta.put("selection", maakuntaSelection);
            query.put(maakunta);

            // Vuosi selection (Year)
            JSONObject vuosi = new JSONObject();
            vuosi.put("code", "Vuosi");
            JSONObject vuosiSelection = new JSONObject();
            vuosiSelection.put("filter", "item");
            JSONArray vuosiValues = new JSONArray();
// Adding years from 2010 to 2022
            for (int year = 2010; year <= 2022; year++) {
                vuosiValues.put(String.valueOf(year));
            }
            vuosiSelection.put("values", vuosiValues);
            vuosi.put("selection", vuosiSelection);
            query.put(vuosi);


            // Sukupuoli selection (Gender)
            JSONObject sukupuoli = new JSONObject();
            sukupuoli.put("code", "Sukupuoli");
            JSONObject sukupuoliSelection = new JSONObject();
            sukupuoliSelection.put("filter", "item");
            JSONArray sukupuoliValues = new JSONArray(new String[]{"1", "2"});  // '1' for males and '2' for females
            sukupuoliSelection.put("values", sukupuoliValues);
            sukupuoli.put("selection", sukupuoliSelection);
            query.put(sukupuoli);


            jsonQuery.put("query", query);
            JSONObject response = new JSONObject();
            response.put("format", "json-stat2");
            jsonQuery.put("response", response);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_URL, jsonQuery,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "Response for area " + areaCode + ": " + response.toString());
                            citizenshipInterface.setData(response);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: " + error.toString());
                }
            });

            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            Log.e(TAG, "JSON Error: " + e.getMessage());
        }
    }

}
