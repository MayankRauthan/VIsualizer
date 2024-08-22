package com.example.Visualizer.Sorting;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.example.Visualizer.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;
import java.util.List;

public class SortingFragment extends Fragment {

    private List<BarEntry> entries;
    private BarChart barChart;

    public SortingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sorting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBar(view);
        ImageButton ib = view.findViewById(R.id.startSorting);
        ib.setOnClickListener(view1 -> {
            new Thread(() -> {
                Log.i("SortingFragment", "Sorting started");
                for (int i = 0; i < entries.size(); i++) {
                    for (int j = i + 1; j < entries.size(); j++) {
                        if (entries.get(i).getY() > entries.get(j).getY()) {
                            BarEntry temp = entries.get(i);
                            entries.set(i, entries.get(j));
                            entries.set(j, temp);
                        }
                    }
                    // Update the chart on the main thread
                    getActivity().runOnUiThread(() -> {
                        BarDataSet set = new BarDataSet(entries, "BarDataSet");
                        BarData data = new BarData(set);
                        data.setBarWidth(0.9f); // Set custom bar width
                        barChart.clear();
                        barChart.setData(data);
                        barChart.setFitBars(true); // Make the x-axis fit exactly all bars
                        barChart.invalidate(); // Refresh the chart
                    });

                    try {
                        Thread.sleep(500); // Add delay to visualize sorting process
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("SortingFragment", "Sorting finished");
            }).start();
        });
    }

    private void initBar(View view) {
        barChart = view.findViewById(R.id.mpchart);
        entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        entries.add(new BarEntry(4f, 70f));
        entries.add(new BarEntry(5f, 60f));
        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // Set custom bar width
        barChart.setData(data);
        barChart.setFitBars(true); // Make the x-axis fit exactly all bars
        barChart.invalidate(); // Refresh the chart
    }
}
