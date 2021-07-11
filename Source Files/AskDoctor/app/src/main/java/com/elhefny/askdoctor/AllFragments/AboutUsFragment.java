package com.elhefny.askdoctor.AllFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elhefny.askdoctor.Dedelopers.Developer;
import com.elhefny.askdoctor.Dedelopers.DeveloperRecyclerAdapter;
import com.elhefny.askdoctor.Dedelopers.ProfileDeveloperActivity;
import com.elhefny.askdoctor.R;

import org.jetbrains.annotations.NotNull;

public class AboutUsFragment extends Fragment {
    RecyclerView rvDevelopers;
    DeveloperRecyclerAdapter adapter;
    public static final String DeveloperTag = "SENDING CURRENT DEVELOPER";

    public AboutUsFragment() {
        // Required empty public constructor
    }

    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        Adding the layout to the fragment
         */
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        building the recycler by The Developer Data
         */
        rvDevelopers = view.findViewById(R.id.about_us_fragment_recycler);
        adapter = new DeveloperRecyclerAdapter(getContext(), new DeveloperRecyclerAdapter.getDeveloper() {
            @Override
            public void getClickedDeveloper(Developer developer) {
                Intent i = new Intent(getContext(), ProfileDeveloperActivity.class);
                i.putExtra(DeveloperTag, developer);
                startActivity(i);
            }
        });
        /*
        Developer 1 "Hassan Reda Elhefny"
         */
        Developer Hassan = new Developer(
                R.drawable.hassan,
                "https://www.linkedin.com/in/hassan-reda-9391361aa/",
                "https://twitter.com/hassan_elhefny",
                "hrelhefny@gmail.com",
                "https://www.facebook.com/7assanElhefny",
                "Hassan Reda EL_hefny",
                "01032066499"
        );
        /*
        Developer 2 "Islam Hamam"
         */
        Developer Islam = new Developer(
                R.drawable.islam,
                "https://www.linkedin.com/in/islam-hmam/",
                "http://Facebook.com/islamhmam22",
                "Eng.islamhmam@gmail.com",
                "http://Facebook.com/islamhmam22",
                "Islam Hamam Mostafa",
                "01065761668"
        );
        adapter.addDeveloper(Hassan);
        adapter.addDeveloper(Islam);
        rvDevelopers.setAdapter(adapter);
        rvDevelopers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDevelopers.setHasFixedSize(true);
    }
}