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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class DashboardFragment extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<ContinentCovid> continentscovid;
    ContinentCovidAdapter continentCovidAdapter;
    MenuProvider menuProvider;
    Toolbar myToolbar;
    TextInputLayout countrySearchContainer;
    TextInputEditText countrySearch;
    View root;

    private static final String TAG = DashboardFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mRecyclerView = root.findViewById(R.id.rvContinent);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SetupMenuBar();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        root.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

        countrySearch = requireActivity().findViewById(R.id.country_search);

        getDataApiContinentsCasesSortAsc();

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
                        continentscovid.clear();
                        getDataApiContinentsCasesSortAsc();
                        Toast.makeText(requireContext(), "Sort Ascending by Name", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sortDesc:
                        root.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                        continentscovid.clear();
                        getDataApiContinentsCasesSortDesc();
                        Toast.makeText(requireContext(), "Sort Descending by Name", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        };

        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner());
    }

    private void ShowRecyclerView() {
        continentCovidAdapter = new ContinentCovidAdapter(continentscovid, getActivity());
        mRecyclerView.setAdapter(continentCovidAdapter);

        // Setup search TextInputEditText Listener
        continentCovidAdapter.filter(countrySearch.getText().toString());
        countrySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                continentCovidAdapter.filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                continentCovidAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getDataApiContinentsCasesSortAsc() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://disease.sh/v3/covid-19/continents";
        continentscovid = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse" + response);
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            continentscovid.add(new ContinentCovid(data.getString("continent"),
                                    data.getString("cases"), data.getString("deaths"),
                                    data.getString("recovered")));
                        }

                        Collections.sort(continentscovid, new Comparator<ContinentCovid>() {
                            @Override
                            public int compare(ContinentCovid a, ContinentCovid b) {
                                return a.getmContinent().compareToIgnoreCase(b.getmContinent());
                            }
                        });
                        ShowRecyclerView();
                        root.findViewById(R.id.progress_bar).setVisibility(View.GONE);
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

    private void getDataApiContinentsCasesSortDesc() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://disease.sh/v3/covid-19/continents";
        continentscovid = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse" + response);
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            continentscovid.add(new ContinentCovid(data.getString("continent"),
                                    data.getString("cases"), data.getString("deaths"), data.getString("recovered")));
                        }
                        Collections.sort(continentscovid, new Comparator<ContinentCovid>() {
                            @Override
                            public int compare(ContinentCovid a, ContinentCovid b) {
                                return b.getmContinent().compareToIgnoreCase(a.getmContinent());
                            }
                        });
                        ShowRecyclerView();
                        root.findViewById(R.id.progress_bar).setVisibility(View.GONE);
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