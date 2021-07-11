package com.elhefny.askdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.elhefny.askdoctor.AllFragments.AboutUsFragment;
import com.elhefny.askdoctor.AllFragments.Covid_19_NewsFragment;
import com.elhefny.askdoctor.AllFragments.HomeFragment;
import com.elhefny.askdoctor.AllFragments.LastNewsFragment;
import com.elhefny.askdoctor.AllFragments.SignOutFragment;
import com.elhefny.askdoctor.AllFragments.Wait_Fragment;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SignOutFragment.SignOutInterface {
    MeowBottomNavigation bottomNavigation;
    String TAG = "MAIN ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkNetwork();
        initializeViews();
        buildBottomNavigation();
    }

    private void checkNetwork() {
        if (NetworkUtil.getConnectivityStatusString(this).equals(getString(R.string.no_internet_connection))) {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void buildBottomNavigation() {
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_about_app));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_news));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_covid_19));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_person));
        bottomNavigation.add(new MeowBottomNavigation.Model(6, R.drawable.ic_sign_out));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment selectedFragment = null;
                switch (item.getId()) {
                    case 1:
                        selectedFragment = PaperOnboardingFragment.newInstance(getDataForOnBoarding());
                        break;
                    case 2:
                        selectedFragment = LastNewsFragment.newInstance();
                        break;
                    case 3:
                        selectedFragment = Covid_19_NewsFragment.newInstance();
                        break;
                    case 4:
                        selectedFragment = HomeFragment.newInstance();
                        break;
                    case 5:
                        selectedFragment = AboutUsFragment.newInstance();
                        break;
                    case 6:
                        selectedFragment = SignOutFragment.newInstance();
                        break;
                }
                loadFragment(selectedFragment);
            }
        });

        bottomNavigation.show(4, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case 1:
                        Log.e(TAG, "About Application");
                        break;
                    case 2:
                        Log.e(TAG, "Last News");
                        break;
                    case 3:
                        Log.e(TAG, "New News About Covid_19");
                        break;
                    case 4:
                        Log.e(TAG, "Home");
                        break;
                    case 5:
                        Log.e(TAG, "About Us");
                        break;
                    case 6:
                        Log.e(TAG, "Sign Out");
                        break;
                }
            }
        });

    }

    private ArrayList<PaperOnboardingPage> getDataForOnBoarding() {
        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        PaperOnboardingPage page1 = new PaperOnboardingPage(getString(R.string.sign_up0_patient),
                getString(R.string.patient_role),
                getColor(R.color.packet_color_1),
                R.drawable.ic_patient,
                R.drawable.ic_patient);
        PaperOnboardingPage page2 = new PaperOnboardingPage(getString(R.string.sign_up0_doctor),
                getString(R.string.doctor_role),
                getColor(R.color.packet_color_2),
                R.drawable.ic_doctor,
                R.drawable.ic_doctor);
        PaperOnboardingPage page3 = new PaperOnboardingPage(getString(R.string.COVID_19),
                getString(R.string.COVID_19_role),
                getColor(R.color.packet_color_3),
                R.drawable.ic_covid_19,
                R.drawable.ic_covid_19);
        PaperOnboardingPage page4 = new PaperOnboardingPage(getString(R.string.medicine_new_news),
                getString(R.string.medicine_new_news_role),
                getColor(R.color.teal_200),
                R.drawable.ic_baseline_fiber_new_24,
                R.drawable.ic_news);
        PaperOnboardingPage page5 = new PaperOnboardingPage(getString(R.string.covid_19_advices),
                getString(R.string.covid_19_advices_role),
                getColor(R.color.purple_200),
                R.drawable.ic_covid_19,
                R.drawable.ic_covid_19);
        PaperOnboardingPage page6 = new PaperOnboardingPage(getString(R.string.vaccine),
                getString(R.string.vaccine_role),
                getColor(R.color.purple_500),
                R.drawable.ic_vaccine,
                R.drawable.ic_vaccine);
        elements.add(page1);
        elements.add(page2);
        elements.add(page3);
        elements.add(page4);
        elements.add(page5);
        elements.add(page6);
        return elements;
    }

    private void loadFragment(Fragment selectedFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_container, selectedFragment)
                .commit();
    }

    private void initializeViews() {
        bottomNavigation = findViewById(R.id.activity_main_bottom_navigation_bar);
    }

    @Override
    public void signOut() {
        Wait_Fragment wait_fragment = Wait_Fragment.newInstance("Signing Out ..");
        wait_fragment.show(getSupportFragmentManager(), null);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        startActivity(new Intent(this, Login_In.class));
        finish();
    }
}