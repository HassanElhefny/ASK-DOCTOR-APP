package com.elhefny.askdoctor.Covid_19_API;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.elhefny.askdoctor.R;

import java.util.ArrayList;

public class CountriesRecyclerAdapter extends RecyclerView.Adapter<CountriesRecyclerAdapter.CountryViewHolder> {
    private Context context;
    private ArrayList<Country> countries;
    GetDetails getDetails;

    public CountriesRecyclerAdapter(Context context, GetDetails getDetails) {
        this.context = context;
        this.getDetails = getDetails;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override

    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountryViewHolder(LayoutInflater.from(context).inflate(R.layout.covid_19_countries_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country currentCountry = countries.get(position);
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left));
        holder.country_name.setText(currentCountry.getCountry());
        holder.death.setText(currentCountry.getTotalDeaths().toString());
        holder.infected.setText(currentCountry.getTotalConfirmed().toString());

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();
        TextDrawable ic1 = builder.build(currentCountry.getCountry().substring(0, 1), color1);
        holder.countryIV.setImageDrawable(ic1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetails.getAdvices();
            }
        });

    }

    @Override
    public int getItemCount() {
        return countries.size() == 0 ? 0 : countries.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {
        TextView country_name, infected, death;
        ImageView countryIV;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            country_name = itemView.findViewById(R.id.covid_19_countries_design_tv_country_Name);
            infected = itemView.findViewById(R.id.covid_19_countries_design_tv_new_infected2);
            death = itemView.findViewById(R.id.covid_19_countries_design_tv_new_death2);
            countryIV = itemView.findViewById(R.id.covid_19_countries_design_iv);
        }
    }

    public static interface GetDetails {
        void getAdvices();
    }
}
