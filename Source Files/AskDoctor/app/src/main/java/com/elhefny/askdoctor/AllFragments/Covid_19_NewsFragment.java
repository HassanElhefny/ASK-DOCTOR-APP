package com.elhefny.askdoctor.AllFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elhefny.askdoctor.Covid_19_API.CountriesData;
import com.elhefny.askdoctor.Covid_19_API.CountriesRecyclerAdapter;
import com.elhefny.askdoctor.Covid_19_API.Country;
import com.elhefny.askdoctor.Covid_19_API.Covid_19_Client;
import com.elhefny.askdoctor.Covid_19_API.Covid_19_Interface;
import com.elhefny.askdoctor.NetworkUtil;
import com.elhefny.askdoctor.R;
import com.elhefny.askdoctor.WorldHealthOrganization;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Covid_19_NewsFragment extends Fragment {
    RecyclerView rv;
    FloatingActionButton floatingActionButton;
    CountriesRecyclerAdapter adapter;
    ArrayList<Country> countries;
    ArrayList<Integer> resources;
    ArrayList<String> advices;


    public Covid_19_NewsFragment() {
        // Required empty public constructor
    }

    public static Covid_19_NewsFragment newInstance() {
        Covid_19_NewsFragment fragment = new Covid_19_NewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getCountries();
        return inflater.inflate(R.layout.fragment_new_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countries = new ArrayList<>();
        setResourcesAndAdvices();
        adapter = new CountriesRecyclerAdapter(getContext(), new CountriesRecyclerAdapter.GetDetails() {
            @Override
            public void getAdvices() {
                int random = (int) (Math.random() * 8);
                AdviceFragment adviceFragment =
                        AdviceFragment.newInstance(resources.get(random), advices.get(random));
                adviceFragment.show(getFragmentManager(), null);
            }
        });
        rv = view.findViewById(R.id.fragment_new_news_recycler);
        floatingActionButton = view.findViewById(R.id.fragment_new_news_floating_action_btn);
        rv.setAdapter(adapter);
        adapter.setCountries(countries);
        adapter.notifyDataSetChanged();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    floatingActionButton.hide();
                } else {
                    floatingActionButton.show();
                }
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WorldHealthOrganization.class));
            }
        });
    }

    private void setResourcesAndAdvices() {
        advices = new ArrayList<>();
        advices.add(getString(R.string.advice_1));
        advices.add(getString(R.string.advice_2));
        advices.add(getString(R.string.advice_3));
        advices.add(getString(R.string.advice_4));
        advices.add(getString(R.string.advice_5));
        advices.add(getString(R.string.advice_6));
        advices.add(getString(R.string.advice_7));
        advices.add(getString(R.string.advice_8));

        resources = new ArrayList<>();
        resources.add(R.raw.advice_1);
        resources.add(R.raw.advice_2);
        resources.add(R.raw.advice_3);
        resources.add(R.raw.advice_4);
        resources.add(R.raw.advice_5);
        resources.add(R.raw.advice_6);
        resources.add(R.raw.advice_7);
        resources.add(R.raw.advice_8);
    }

    private void getCountries() {
        if (!NetworkUtil.getConnectivityStatusString(getContext()).equals(getString(R.string.no_internet_connection))) {
            Wait_Fragment wait_fragment = Wait_Fragment.newInstance(getString(R.string.getting_data));
            wait_fragment.show(getFragmentManager(), null);
            Covid_19_Interface api = Covid_19_Client.getService();
            api.getData().enqueue(new Callback<CountriesData>() {
                @Override
                public void onResponse(Call<CountriesData> call, Response<CountriesData> response) {
                    if (response.isSuccessful()) {
                        countries.addAll(response.body().getCountries());
                        adapter.notifyDataSetChanged();
                        wait_fragment.dismiss();
                    } else {
                        Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        wait_fragment.dismiss();
                        Error_Fragment error_fragment = Error_Fragment.newInstance(getString(R.string.error_occurred));
                        error_fragment.show(getFragmentManager(), null);
                    }
                }

                @Override
                public void onFailure(Call<CountriesData> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    wait_fragment.dismiss();
                    Error_Fragment error_fragment = Error_Fragment.newInstance("Error Occurred");
                    error_fragment.show(getFragmentManager(), null);
                }
            });
        } else {
            Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }
}