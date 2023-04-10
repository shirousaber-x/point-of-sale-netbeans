package com.netsprogram.covidstatistic.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.netsprogram.covidstatistic.R;

import java.util.ArrayList;
import java.util.Locale;

public class ContinentCovidAdapter extends RecyclerView.Adapter<ContinentCovidAdapter.ViewHolder> {
    ArrayList<ContinentCovid> continentscovid;
    ArrayList<ContinentCovid> arrayList;

    Context context;
    public ContinentCovidAdapter(ArrayList<ContinentCovid> continentscovid, Context context)
    {
        this.continentscovid= continentscovid;
        this.context = context;

        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(continentscovid);
    }

    @NonNull
    @Override
    public ContinentCovidAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_continent,parent,false);
        return  new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ContinentCovid continentCovid = continentscovid.get(position);

        holder.tvContinentName.setText(continentCovid.getmContinent());
        holder.tvTotalCase.setText(continentCovid.getmCases());
        holder.tvTotalDeath.setText(continentCovid.getmDeaths());
        holder.tvTotalRecovery.setText(continentCovid.getmRecovered());

        // ganti color item
        holder.cardView.setBackgroundResource(position % 2 != 0 ?
                R.color.item_ganjil : R.color.item_genap);

        // set event click on continent name
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,continentCovid.getmContinent(),Toast.LENGTH_SHORT).show();
                DashboardFragmentDirections.ContinentToCountry action
                        = DashboardFragmentDirections.continentToCountry(continentscovid.get(position).mContinent);
//              action.setNamaContinent(continentscovid.get(position).mContinent);
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return continentscovid.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView tvContinentName, tvTotalCase, tvTotalDeath, tvTotalRecovery;
       LinearLayout cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContinentName = itemView.findViewById(R.id.tvContinentName);
            tvTotalCase = itemView.findViewById(R.id.tvTotalCases);
            tvTotalRecovery = itemView.findViewById(R.id.tvRecovery);
            tvTotalDeath = itemView.findViewById(R.id.tvDeath);
            cardView = itemView.findViewById(R.id.cardContinent);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        continentscovid.clear();
        if (charText.length() == 0) {
            continentscovid.addAll(arrayList);
        } else {
            for (ContinentCovid wp : arrayList) {
                if (wp.getmContinent().toLowerCase(Locale.getDefault()).contains(charText)) {
                    continentscovid.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}