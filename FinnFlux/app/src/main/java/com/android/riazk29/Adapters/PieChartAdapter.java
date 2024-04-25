package com.android.riazk29.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rifatk29.Models.CitizenshipData;
import com.android.rifatk29.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class PieChartAdapter extends RecyclerView.Adapter<PieChartAdapter.ViewHolder> {

    private Context context;
    private List<CitizenshipData> dataList;

    public PieChartAdapter(Context context, List<CitizenshipData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_population, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set data for male and female
        int malePosition = position * 2;
        int femalePosition = malePosition + 1;
        if (femalePosition < dataList.size()) {
            CitizenshipData maleData = dataList.get(malePosition);
            CitizenshipData femaleData = dataList.get(femalePosition);
            holder.setChartData(maleData, femaleData);
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return dataList.size() / 2;
    }

    /**
     * ViewHolder class to hold the views for each item in RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        PieChart pieChart;

        ViewHolder(View itemView) {
            super(itemView);
            pieChart = itemView.findViewById(R.id.pieChart);
        }

        /**
         * Method to set chart data for male and female.
         *
         * @param maleData   The CitizenshipData object representing male data.
         * @param femaleData The CitizenshipData object representing female data.
         */
        void setChartData(CitizenshipData maleData, CitizenshipData femaleData) {
            List<PieEntry> entries = new ArrayList<>();
            // Add male data
            entries.add(new PieEntry(maleData.getCount(), "Males"));
            // Add female data
            entries.add(new PieEntry(femaleData.getCount(), "Females"));

            PieDataSet dataSet = new PieDataSet(entries, "Citizenship Counts");
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);

            PieData pieData = new PieData(dataSet);
            pieChart.setData(pieData);
            pieChart.invalidate();
            pieChart.getDescription().setEnabled(false);
            pieChart.setCenterText(maleData.getRegion() + " " + maleData.getYear());
        }

    }
}
