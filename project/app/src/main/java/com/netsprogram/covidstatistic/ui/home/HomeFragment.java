package com.netsprogram.covidstatistic.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.netsprogram.covidstatistic.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    private TextView TotalConfirmed, TotalDeath, TotalRecovered, TxtLastUpdated;
    private PieChart pieChart;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home,container,false);

        pieChart = root.findViewById(R.id.PieChart);
        TotalConfirmed = root.findViewById(R.id.totalConfirmed);
        TotalDeath = root.findViewById(R.id.totalDeath);
        TotalRecovered = root.findViewById(R.id.totalRecovered);
        TxtLastUpdated = root.findViewById(R.id.LastUpdated);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        root.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        getDataApiGlobalCases();
        requireActivity().findViewById(R.id.country_search_container).setVisibility(View.INVISIBLE);
    }

    private String getLastUpdated(Long miliseconds) {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aaa");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliseconds);
        return formatDate.format(calendar.getTime());
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(16);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Global Cases");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextColor(Color.WHITE);
        l.setTextSize(16f);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void getDataApiGlobalCases() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://disease.sh/v3/covid-19/all";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    TotalConfirmed.setText(jsonObject.getString("cases"));
                    TotalDeath.setText(jsonObject.getString("deaths"));
                    TotalRecovered.setText(jsonObject.getString("recovered"));
                    TxtLastUpdated.setText(getLastUpdated(jsonObject.getLong("updated")));

                    setupPieChart();
                    ArrayList<PieEntry> entries = new ArrayList<>();
                    entries.add(new PieEntry(Integer.parseInt(jsonObject.getString("recovered")), "Recovered"));
                    entries.add(new PieEntry(Integer.parseInt(jsonObject.getString("cases")), "Cases"));
                    entries.add(new PieEntry(Integer.parseInt(jsonObject.getString("deaths")), "Deaths"));

                    ArrayList<Integer> colors = new ArrayList<>();
                    for (int color: ColorTemplate.MATERIAL_COLORS) {
                        colors.add(color);
                    }

                    for (int color: ColorTemplate.VORDIPLOM_COLORS) {
                        colors.add(color);
                    }

                    PieDataSet dataSet = new PieDataSet(entries, "");
                    dataSet.setColors(colors);

                    PieData data = new PieData(dataSet);
                    data.setDrawValues(true);
                    data.setValueFormatter(new PercentFormatter(pieChart));
                    data.setValueTextSize(14f);
                    data.setValueTextColor(Color.BLACK);

                    pieChart.setData(data);
                    pieChart.invalidate();

                    pieChart.animateY(1400, Easing.EaseInOutQuad);

                    root.findViewById(R.id.progress_bar).setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error response",error.toString());
            }
        });
        queue.getCache().clear();
        queue.add(stringRequest);
    }
}