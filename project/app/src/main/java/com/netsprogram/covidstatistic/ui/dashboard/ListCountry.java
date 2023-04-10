package com.netsprogram.covidstatistic.ui.dashboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.netsprogram.covidstatistic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListCountry extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<CountryCovid> countriescovid;
    CountryCovidAdapter countryCovidAdapter;
    public String namaContinent;
    TextView countryListTitle;
    MenuProvider menuProvider;
    Toolbar myToolbar;
    TextInputLayout countrySearchContainer;
    TextInputEditText countrySearch;
    View root;

    private static final String TAG = ListCountry.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.activity_list_country, container, false);

        // get nama continent dari dashboard fragment
        namaContinent = ListCountryArgs.fromBundle(getArguments()).getNamaContinent();

        countryListTitle = root.findViewById(R.id.countryListTitle);
        countryListTitle.setText(namaContinent);

        mRecyclerView = root.findViewById(R.id.rvCountries);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SetupMenuBar();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        root.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

        countrySearch = requireActivity().findViewById(R.id.country_search);

        getDataApiCountriesCasesSortAsc();

        countrySearchContainer = requireActivity().findViewById(R.id.country_search_container);
        countrySearchContainer.setVisibility(View.VISIBLE);
    }

    private void SetupMenuBar() {
        menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.sortAsc:
                        root.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                        countriescovid.clear();
                        getDataApiCountriesCasesSortAsc();
                        Toast.makeText(requireContext(), "Sort Ascending by Name", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sortDesc:
                        root.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                        countriescovid.clear();
                        getDataApiCountriesCasesSortDesc();
                        Toast.makeText(requireContext(), "Sort Descending by Name", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        };

        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner());
    }

    private void ShowRecyclerView() {
        countryCovidAdapter = new CountryCovidAdapter(countriescovid, getActivity());
        mRecyclerView.setAdapter(countryCovidAdapter);

        // Setup search TextInputEditText Listener
        countryCovidAdapter.filter(countrySearch.getText().toString());
        countrySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                countryCovidAdapter.filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countryCovidAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getDataApiCountriesCasesSortAsc() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://disease.sh/v3/covid-19/countries";
        countriescovid = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse" + response);
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            JSONObject countryinfo = data.getJSONObject("countryInfo");
                            if (data.getString("continent").equals(namaContinent)) {
                                countriescovid.add(new CountryCovid(countryinfo.getString("flag"),
                                        data.getString("country"), data.getString("cases"),
                                        data.getString("deaths"), data.getString("recovered")));
                            }

                        }

                        Collections.sort(countriescovid, new Comparator<CountryCovid>() {
                            @Override
                            public int compare(CountryCovid a, CountryCovid b) {
                                return a.getmCountry().compareToIgnoreCase(b.getmCountry());
                            }
                        });

                        ShowRecyclerView();
                        root.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onResponse" + error);

            }
        });
        queue.getCache().clear();
        queue.add(stringRequest);
    }


    private void getDataApiCountriesCasesSortDesc() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://disease.sh/v3/covid-19/countries";
        countriescovid = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse" + response);
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            JSONObject countryinfo = data.getJSONObject("countryInfo");
                            if (data.getString("continent").equals(namaContinent)) {
                                countriescovid.add(new CountryCovid(countryinfo.getString("flag"),
                                        data.getString("country"), data.getString("cases"),
                                        data.getString("deaths"), data.getString("recovered")));
                            }
                        }

                        // sort desc
                        Collections.sort(countriescovid, new Comparator<CountryCovid>() {
                            @Override
                            public int compare(CountryCovid a, CountryCovid b) {
                                return b.getmCountry().compareToIgnoreCase(a.getmCountry());
                            }
                        });

                        ShowRecyclerView();
                        root.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onResponse" + error);

            }
        });
        queue.getCache().clear();
        queue.add(stringRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}