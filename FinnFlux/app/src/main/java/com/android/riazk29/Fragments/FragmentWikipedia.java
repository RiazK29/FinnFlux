package com.android.riazk29.Fragments;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.rifatk29.DataRetrievers.WikipediaRetriever;
import com.android.rifatk29.Interfaces.WikipediaInterfaces;
import com.android.rifatk29.databinding.FragmentWikipediaBinding;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentWikipedia extends Fragment implements WikipediaInterfaces {
    FragmentWikipediaBinding binding;
    WikipediaRetriever wikipediaRetriever;
    String title;
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
        binding=FragmentWikipediaBinding.inflate(getLayoutInflater());

        //create wikipedia retrievers
        wikipediaRetriever =new WikipediaRetriever(getActivity(),this::setWikiData);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                wikipediaRetriever.fetchWikiData(query);
                title=query;
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
     * Sets the Wikipedia data in the TextView using HTML formatting.
     *
     * @param wikiData The Wikipedia data to be displayed.
     */
    @Override
    public void setWikiData(String wikiData) {

        try {
            JSONObject jsonResponse = new JSONObject(wikiData);
            JSONObject originalImageObj = jsonResponse.getJSONObject("originalimage");
            String originalimage = originalImageObj.getString("source");
            String extract = jsonResponse.getString("extract_html");
            JSONObject contentUrls = jsonResponse.getJSONObject("content_urls").getJSONObject("desktop");
            String pageUrl = contentUrls.getString("page");
            String editUrl = contentUrls.getString("edit");
            String talkUrl = contentUrls.getString("talk");

            JSONObject coordinates = jsonResponse.getJSONObject("coordinates");
            double lat = coordinates.getDouble("lat");
            double lon = coordinates.getDouble("lon");

            String formattedText = "<b>Description:</b> " + extract + "<br/>" +
                    "<b>Page URL:</b> <a href='" + pageUrl + "'>View Page</a><br/>" +
                    "<b>Edit URL:</b> <a href='" + editUrl + "'>Edit Page</a><br/>" +
                    "<b>Talk URL:</b> <a href='" + talkUrl + "'>Discussion Page</a><br/>" +
                    "<b>Coordinates:</b> <a href='geo:" + lat + "," + lon + "'>Latitude: " + lat + ", Longitude: " + lon + "</a><br/>";

            binding.tvResult.setText(Html.fromHtml(formattedText, Html.FROM_HTML_MODE_LEGACY));
            binding.tvResult.setMovementMethod(LinkMovementMethod.getInstance());

            Glide.with(getActivity()).load(originalimage).into(binding.imageView);
        } catch (JSONException e) {

        }

    }
}
