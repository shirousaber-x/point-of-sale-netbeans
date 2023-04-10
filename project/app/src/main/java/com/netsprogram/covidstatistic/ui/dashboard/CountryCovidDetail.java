package com.netsprogram.covidstatistic.ui.dashboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

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
import org.w3c.dom.Text;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

public class CountryCovidDetail extends AppCompatActivity {
    TextView CountryNameDetail,TotalConfirmedDetail, TotalDeathDetail, TotalRecoveredDetail;
    WebView wv;
    PieChart pieChart;
    String FlagUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_covid_detail);

        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        CountryNameDetail = (TextView) findViewById(R.id.CountryNameDetail);
        pieChart = findViewById(R.id.PieChart);
        TotalConfirmedDetail = (TextView) findViewById(R.id.totalConfirmedDetail);
        TotalDeathDetail = (TextView) findViewById(R.id.totalDeathDetail);
        TotalRecoveredDetail = (TextView) findViewById(R.id.totalRecoveredDetail);

        // get passed parameter
        Intent intent = getIntent();
        CountryNameDetail.setText(intent.getStringExtra("country_name"));
        FlagUrl = intent.getStringExtra("country_flag");

        // display flag country
        wv = (WebView) findViewById(R.id.webView1);
        wv.setWebViewClient(new Callback());
        WebSettings webSettings = wv.getSettings();
        webSettings.setBuiltInZoomControls(false);
        wv.loadUrl(FlagUrl);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);

        // call getDetailCovid
        getDetailCovidCountry();
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(18);
        pieChart.setEntryLabelColor(Color.BLACK);
//        pieChart.setCenterText("Country Cases");
//        pieChart.setCenterTextSize(24);
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

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }
    }

    private void getDetailCovidCountry() {
        String URL = "https://disease.sh/v3/covid-19/countries/" +  CountryNameDetail.getText().toString().toLowerCase().trim();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    TotalConfirmedDetail.setText(jsonObject.getString("cases"));
                    TotalDeathDetail.setText(jsonObject.getString("deaths"));
                    TotalRecoveredDetail.setText(jsonObject.getString("recovered"));

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

                    findViewById(R.id.progress_bar).setVisibility(View.GONE);

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