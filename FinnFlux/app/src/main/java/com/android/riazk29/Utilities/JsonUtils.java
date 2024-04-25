package com.android.riazk29.Utilities;

import android.content.Context;

import com.android.rifatk29.Models.TrueOrFalseModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JsonUtils {

    /**
     * Reads a JSON file from the assets folder, parses it, and converts it into a list of TrueOrFalseModel objects.
     * @param context Application context
     * @param fileName Name of the JSON file in the assets folder
     * @return A list of TrueOrFalseModel objects
     */
    public static ArrayList<TrueOrFalseModel> loadQuestionsFromJson(Context context, String fileName) {
        ArrayList<TrueOrFalseModel> questionsList = new ArrayList<>();
        try {
            // Load the JSON file as a string
            String jsonString = loadJSONFromAsset(context, fileName);
            // Convert the JSON string into a JSONArray object
            JSONArray jsonArray = new JSONArray(jsonString);

            // Iterate through each object in the JSONArray
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject questionObj = jsonArray.getJSONObject(i);
                String question = questionObj.getString("question");
                String content = questionObj.getString("content");
                boolean answer = questionObj.getBoolean("answer");

                // Create a new TrueOrFalseModel object and add it to the list
                TrueOrFalseModel model = new TrueOrFalseModel();
                model.setQuestion(question);
                model.setContent(content);
                model.setAnswer(answer);
                questionsList.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return questionsList;
    }

    /**
     * Helper method to load a JSON file from the assets directory.
     * @param context Application context
     * @param fileName Name of the file in the assets directory
     * @return The file content as a string
     */
    private static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            // Open the file and load it into an InputStream
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the byte array to a String
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle the error
            return null;
        }
        return json;
    }
}
