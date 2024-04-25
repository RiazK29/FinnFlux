package com.android.riazk29.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.rifatk29.Models.AreaModel;
import com.android.rifatk29.Models.CitizenshipData;
import com.android.rifatk29.DataRetrievers.CitizenshipRetriever;
import com.android.rifatk29.Interfaces.CitizenshipInterface;
import com.android.rifatk29.Adapters.PieChartAdapter;
import com.android.rifatk29.databinding.FragmentIlastokeskusBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentTilastokeskus extends Fragment implements CitizenshipInterface {
    private FragmentIlastokeskusBinding binding;
    private ArrayList<CitizenshipData> citizenshipDataList;
    private ArrayList<AreaModel> areaModelArrayList=new ArrayList<>();
    private String areaCode="";
    private CitizenshipRetriever citizenshipRetriever;

    /**Set area and codes*/
 private void setAreaData(){
     areaModelArrayList.add(new AreaModel("MA1","MAINLAND FINLAND"));
     areaModelArrayList.add(new AreaModel("MK01","Uusimaa"));
     areaModelArrayList.add(new AreaModel("MK02 ","Southwest Finland"));
     areaModelArrayList.add(new AreaModel("MK05 ","Kanta-Häme"));
     areaModelArrayList.add(new AreaModel("MK06","Pirkanmaa"));
     areaModelArrayList.add(new AreaModel("MK07","Päijät-Häme"));
     areaModelArrayList.add(new AreaModel("MK08","Kymenlaakso"));
     areaModelArrayList.add(new AreaModel("MK09","South Karelia"));
     areaModelArrayList.add(new AreaModel("MK10","South Savo"));
     areaModelArrayList.add(new AreaModel("MK11","North Savo"));
     areaModelArrayList.add(new AreaModel("MK12","North Karelia"));
     areaModelArrayList.add(new AreaModel("MK13","Central Finland"));
     areaModelArrayList.add(new AreaModel("MK14 ","South Ostrobothnia"));  areaModelArrayList.add(new AreaModel("MK15","Ostrobothnia"));
     areaModelArrayList.add(new AreaModel("MK16","Central Ostrobothnia"));
     areaModelArrayList.add(new AreaModel("MK17","North Ostrobothnia"));
     areaModelArrayList.add(new AreaModel("MK18","Kainuu"));
     areaModelArrayList.add(new AreaModel("MK19","Lapland"));
     areaModelArrayList.add(new AreaModel("MA2","ÅLAND"));
     areaModelArrayList.add(new AreaModel("MK21 ","Åland"));

 }


 /**Create arraylist from json object
  * @param areaCode  is area code
  * @param jsonResponse is api response
  * @return is arrylist
  * */
    public ArrayList<CitizenshipData> parseCitizenshipData(JSONObject jsonResponse, String areaCode) {
        ArrayList<CitizenshipData> dataList = new ArrayList<>();
        try {
            JSONObject dimensions = jsonResponse.getJSONObject("dimension");
            JSONObject categoryVuosi = dimensions.getJSONObject("Vuosi").getJSONObject("category");
            JSONObject categoryMaakunta = dimensions.getJSONObject("Maakunta").getJSONObject("category");
            JSONObject categorySukupuoli = dimensions.getJSONObject("Sukupuoli").getJSONObject("category");

            JSONArray values = jsonResponse.getJSONArray("value");
            JSONObject years = categoryVuosi.getJSONObject("label");
            String regionLabel = categoryMaakunta.getJSONObject("label").getString(areaCode);
            JSONObject genders = categorySukupuoli.getJSONObject("label");

            int yearCount = categoryVuosi.getJSONObject("index").length();
            int genderCount = categorySukupuoli.getJSONObject("index").length();

            // Loop through each gender and year
            for (int i = 0; i < values.length(); i++) {
                int yearIndex = i / genderCount % yearCount;
                int genderIndex = i % genderCount;
                int year = Integer.parseInt(years.getString(String.valueOf(2010 + yearIndex)));
                String genderLabel = genders.getString(String.valueOf(genderIndex + 1)); // Assuming gender indexes are 1 and 2 for Males and Females
                int count = values.getInt(i);
                dataList.add(new CitizenshipData(regionLabel.replace(areaCode,""), year, genderLabel,
                        count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
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
        binding=FragmentIlastokeskusBinding.inflate(getLayoutInflater());
        setAreaData();
        citizenshipRetriever=new CitizenshipRetriever(getActivity(),this::setData);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                 areaCode=findAreaCodeByAreaName(s);
                if (areaCode!=null){
                    citizenshipRetriever.fetchData(areaCode);
                }else {
                    Toast.makeText(getActivity(), "Region not found!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return binding.getRoot();
    }


    /**
     * Find area code viea areanama
     * @param areaName is area name from user
     * @return is area code*/
    public  String findAreaCodeByAreaName(String areaName) {
        for (AreaModel areaModel :areaModelArrayList ) {
            if (areaModel.getArea().equalsIgnoreCase(areaName)) {
                return areaModel.getCode();
            }
        }
        return null;
    }

    @Override
    public void setData(JSONObject response) {
        citizenshipDataList = parseCitizenshipData(response,areaCode);
        for (CitizenshipData citizenshipData:citizenshipDataList){
            Log.i("DATA",
                    "Region: "+citizenshipData.getRegion()+" *** Year: "+citizenshipData.getYear()+" *** Count: "+citizenshipData.getCount()+" *** Gender"+citizenshipData.getGender());
        }

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerview.setAdapter(new PieChartAdapter(getActivity(),
                citizenshipDataList));
    }
}
