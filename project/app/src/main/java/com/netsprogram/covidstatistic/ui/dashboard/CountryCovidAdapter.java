package com.netsprogram.covidstatistic.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.netsprogram.covidstatistic.R;

import java.util.ArrayList;
import java.util.Locale;

public class CountryCovidAdapter extends RecyclerView.Adapter<CountryCovidAdapter.ViewHolder> {
    ArrayList<CountryCovid> countriescovid;
    ArrayList<CountryCovid> arrayList;

    Context context;
    public CountryCovidAdapter(ArrayList<CountryCovid> countriescovid,Context context)
    {
        this.countriescovid= countriescovid;
//        this.context = context;

        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(countriescovid);
    }
    @NonNull
    @Override
    public CountryCovidAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_covid,parent,false);
        context = parent.getContext();
        return  new ViewHolder(layout);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CountryCovid countryCovid = countriescovid.get(position);
          // show flag icon
        Glide.with(context).load(countryCovid.getmFlag()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                return false;
            }
        }).into(holder.imgFlagCountry);

        holder.cardView.setBackgroundResource(position % 2 != 0 ?
                R.color.item_ganjil : R.color.item_genap);
        holder.tvCountryName.setText(countryCovid.getmCountry());
        holder.tvTotalCase.setText(countryCovid.getmCases());
        holder.tvTotalDeath.setText(countryCovid.getmDeaths());
        holder.tvTotalRecovery.setText(countryCovid.getmRecovered());

        // set event click on country name
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,countryCovid.getmCountry(),Toast.LENGTH_SHORT).show();

                ListCountryDirections.ListCountryToCountryCovidDetail action
                        = ListCountryDirections.listCountryToCountryCovidDetail(countryCovid.getmCountry(),
                        countryCovid.getmFlag());
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
    @Override
    public int getItemCount() {
        return countriescovid.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView tvCountryName, tvTotalCase, tvTotalDeath, tvTotalRecovery;
       ImageView imgFlagCountry;
       ConstraintLayout cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            tvTotalCase = itemView.findViewById(R.id.tvTotalCases);
            tvTotalDeath = itemView.findViewById(R.id.tvDeath);
            tvTotalRecovery = itemView.findViewById(R.id.tvRecovery);
            imgFlagCountry = itemView.findViewById(R.id.imgFlagCountry);
            cardView = itemView.findViewById(R.id.cardCountry);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        countriescovid.clear();
        if (charText.length() == 0) {
            countriescovid.addAll(arrayList);
        } else {
            for (CountryCovid wp : arrayList) {
                if (wp.getmCountry().toLowerCase(Locale.getDefault()).contains(charText)) {
                    countriescovid.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}