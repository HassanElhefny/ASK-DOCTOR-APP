package com.elhefny.askdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.elhefny.askdoctor.AllFragments.Error_Fragment;
import com.elhefny.askdoctor.AllFragments.Wait_Fragment;
import com.elhefny.askdoctor.DepartmentsRecyclerClasses.DepartmentDetails;
import com.elhefny.askdoctor.DepartmentsRecyclerClasses.DepartmentRecyclerAdapterSignUp;
import com.elhefny.askdoctor.Doctors.Doctor;
import com.elhefny.askdoctor.Patients.Patient;
import com.elhefny.askdoctor.databinding.ActivitySignUp2Binding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Sign_Up_2 extends AppCompatActivity {
    ActivitySignUp2Binding binding;
    ArrayList<DepartmentDetails> departmentDetails = new ArrayList<>();
    DepartmentRecyclerAdapterSignUp adapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference, collectionReference2 = db.collection("all doctors".toUpperCase());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUp2Binding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        initialNumberPicker();
        createDepartments();
        buildRecycler();
    }

    private void buildRecycler() {
        adapter = new DepartmentRecyclerAdapterSignUp(this, departmentDetails, new DepartmentRecyclerAdapterSignUp.onDepartmentClicked() {
            @Override
            public void getClickedDepartment(DepartmentDetails clickedDepartment) {
                binding.signUp2Tv3.setText(clickedDepartment.getDepartmentName());
            }
        });
        binding.signUp2RvDepartments.setAdapter(adapter);
        binding.signUp2RvDepartments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.signUp2RvDepartments.setHasFixedSize(true);
    }

    private void initialNumberPicker() {
        binding.signUp2NumberPicker.setMaxValue(40);
        binding.signUp2NumberPicker.setMinValue(1);
    }

    private void createDepartments() {
        DepartmentDetails department_1 = new DepartmentDetails(
                R.drawable.department_1
                , getString(R.string.department_1)
                , getString(R.string.department_1_Detail)
        );
        departmentDetails.add(department_1);

        DepartmentDetails department_2 = new DepartmentDetails(
                R.drawable.department_2
                , getString(R.string.department_2)
                , getString(R.string.department_2_Detail)
        );
        departmentDetails.add(department_2);

        DepartmentDetails department_3 = new DepartmentDetails(
                R.drawable.department_3
                , getString(R.string.department_3)
                , getString(R.string.department_3_Detail)
        );
        departmentDetails.add(department_3);

        DepartmentDetails department_4 = new DepartmentDetails(
                R.drawable.department_4
                , getString(R.string.department_4)
                , getString(R.string.department_4_Detail)
        );
        departmentDetails.add(department_4);

        DepartmentDetails department_5 = new DepartmentDetails(
                R.drawable.department_5
                , getString(R.string.department_5)
                , getString(R.string.department_5_Detail)
        );
        departmentDetails.add(department_5);

        DepartmentDetails department_6 = new DepartmentDetails(
                R.drawable.department_6
                , getString(R.string.department_6)
                , getString(R.string.department_6_Detail)
        );
        departmentDetails.add(department_6);

        DepartmentDetails department_7 = new DepartmentDetails(
                R.drawable.department_7
                , getString(R.string.department_7)
                , getString(R.string.department_7_Detail)
        );
        departmentDetails.add(department_7);

        DepartmentDetails department_8 = new DepartmentDetails(
                R.drawable.department_8
                , getString(R.string.department_8)
                , getString(R.string.department_8_Detail)
        );
        departmentDetails.add(department_8);

        DepartmentDetails department_9 = new DepartmentDetails(
                R.drawable.department_9
                , getString(R.string.department_9)
                , getString(R.string.department_9_Detail)
        );
        departmentDetails.add(department_9);

        DepartmentDetails department_10 = new DepartmentDetails(
                R.drawable.department_10
                , getString(R.string.department_10)
                , getString(R.string.department_10_Detail)
        );
        departmentDetails.add(department_10);

        DepartmentDetails department_11 = new DepartmentDetails(
                R.drawable.department_11
                , getString(R.string.department_11)
                , getString(R.string.department_11_Detail)
        );
        departmentDetails.add(department_11);
        DepartmentDetails department_12 = new DepartmentDetails(
                R.drawable.department_12
                , getString(R.string.department_12)
                , getString(R.string.department_12_Detail)
        );
        departmentDetails.add(department_12);
    }

    public void Sign_Up(View view) {
        if (!NetworkUtil.getConnectivityStatusString(this).equals(getString(R.string.no_internet_connection))) {
            if (binding.signUp2Tv3.getText().equals(getString(R.string.not_selected_yet))) {
                Toast.makeText(this, getString(R.string.select_the_department), Toast.LENGTH_SHORT).show();
            } else {
                Wait_Fragment wait_fragment = Wait_Fragment.newInstance(getString(R.string.loading));
                wait_fragment.show(getSupportFragmentManager(), null);
                Patient patient = (Patient) getIntent().getSerializableExtra(Sign_Up_1.newPatientTag);
                Doctor newDoctor = new Doctor(
                        patient.getEmail(),
                        patient.getName(),
                        binding.signUp2Tv3.getText().toString(),
                        patient.getCountry(),
                        patient.getGender(),
                        patient.getPhone(),
                        patient.getAge(),
                        String.valueOf(binding.signUp2NumberPicker.getValue())
                );
                mAuth.createUserWithEmailAndPassword(patient.getEmail(), getIntent().getStringExtra(Sign_Up_1.passwordTag)).
                        addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                collectionReference = db.collection(binding.signUp2Tv3.getText().toString());
                                collectionReference.add(newDoctor);
                                collectionReference2.add(newDoctor);
                                Intent i = new Intent(Sign_Up_2.this, Login_In.class);
                                startActivity(i);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        wait_fragment.dismiss();
                        Error_Fragment error_fragment = Error_Fragment.newInstance(e.getMessage());
                        error_fragment.show(getSupportFragmentManager(), null);
                    }
                });
            }
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }
}