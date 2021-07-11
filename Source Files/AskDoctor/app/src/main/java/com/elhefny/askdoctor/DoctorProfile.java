package com.elhefny.askdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.elhefny.askdoctor.Doctors.Doctor;
import com.elhefny.askdoctor.databinding.ActivityDoctorProfileBinding;

public class DoctorProfile extends AppCompatActivity {
    ActivityDoctorProfileBinding binding;
    Doctor sentDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorProfileBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        sentDoctor = (Doctor) getIntent().getSerializableExtra(getString(R.string.SentDoctor));
        buildDoctorProfile(sentDoctor);
    }

    private void buildDoctorProfile(Doctor sentDoctor) {
        binding.profileDoctorTvName.setText(sentDoctor.getName());
        binding.profileDoctorCardEmailTv.setText(sentDoctor.getEmail());
        binding.profileDoctorCardPhoneTv.setText(sentDoctor.getPhone());
        binding.profileDoctorCardYearsOfExperienceTv.setText(getString(R.string.years_of_experience) + sentDoctor.getYearsOfExperience());
        setDoctorImage(sentDoctor.getName());
    }

    private void setDoctorImage(String name) {
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();
        TextDrawable ic1 = builder.build(name.trim().toUpperCase().substring(0, 1), color1);
        binding.profileDoctorIv.setImageDrawable(ic1);
    }

    public void ShowPrivateMessages(View view) {
        Intent i = new Intent(getBaseContext(),PrivateQuestion.class);
        i.putExtra("EmailOfTheDoctor",sentDoctor.getEmail());
        startActivity(i);
    }
}