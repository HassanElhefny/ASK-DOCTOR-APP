package com.elhefny.askdoctor.Dedelopers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.elhefny.askdoctor.AllFragments.AboutUsFragment;
import com.elhefny.askdoctor.databinding.ActivityProfileDeveloperBinding;

public class ProfileDeveloperActivity extends AppCompatActivity {
    ActivityProfileDeveloperBinding binding;
    Developer developer;
    private Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileDeveloperBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        developer = (Developer) getIntent().getSerializableExtra(AboutUsFragment.DeveloperTag);
        if (developer != null) {
            binding.profileDeveloperCardPhoneTv.setText(developer.getPhoneNumber());
            binding.profileDeveloperTvName.setText(developer.getName());
            binding.profileDeveloperCardEmailTv.setText(developer.getGoogle());
            binding.profileDeveloperIv.setImageResource(developer.getImage());
            binding.profileDeveloperBtnFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(developer.getFacebook());
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }
            });
            binding.profileDeveloperBtnTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(developer.getTwitter());
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }
            });
            binding.profileDeveloperBtnLinkedIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(developer.getLinkedIn());
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }
            });
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .repeat(1)
                    .playOn(binding.profileDeveloperCardPhone);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(6000);
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(1)
                                        .playOn(binding.profileDeveloperCardEmail);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(700);
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                YoYo.with(Techniques.Wobble)
                                        .duration(3000)
                                        .repeat(1)
                                        .playOn(binding.profileDeveloperTvName);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t2.start();

        }
    }
}