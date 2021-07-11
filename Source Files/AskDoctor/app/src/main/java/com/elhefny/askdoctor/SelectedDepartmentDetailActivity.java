package com.elhefny.askdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.elhefny.askdoctor.AllFragments.Error_Fragment;
import com.elhefny.askdoctor.DepartmentsRecyclerClasses.DepartmentDetails;
import com.elhefny.askdoctor.Doctors.DepartmentDoctorsAdapter;
import com.elhefny.askdoctor.Doctors.Doctor;
import com.elhefny.askdoctor.Patients.DepartmentsPublicQuestionsAdapter;
import com.elhefny.askdoctor.Patients.PublicQuestion;
import com.elhefny.askdoctor.databinding.ActivitySelectedDepartmentDetailBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class SelectedDepartmentDetailActivity extends AppCompatActivity {
    private FirebaseFirestore fs = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CollectionReference doctorRef;
    private CollectionReference publicQuestionsRef;
    private ActivitySelectedDepartmentDetailBinding binding;
    private DepartmentDetails currentDepartment;
    private MaterialTextView tv_description;
    private RecyclerView rv_doctors, rv_public_messages;
    private DepartmentDoctorsAdapter adapter;
    private DepartmentsPublicQuestionsAdapter adapter2;
    private boolean isDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectedDepartmentDetailBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        initializeViews();
        checkIfDoctor();
        setRecyclerAdapter();
        setSupportActionBar(binding.toolbar);
    }

    private void setRecyclerAdapter() {
        Query query = doctorRef.orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Doctor>()
                .setQuery(query, Doctor.class)
                .build();
        adapter = new DepartmentDoctorsAdapter(this, new DepartmentDoctorsAdapter.DoctorInterface() {
            @Override
            public void getClickedDoctor(Doctor clickedDoctor) {
                Intent i = new Intent(SelectedDepartmentDetailActivity.this, DoctorProfile.class);
                i.putExtra(getString(R.string.SentDoctor), clickedDoctor);
                startActivity(i);
            }
        }, options);
        rv_doctors.setHasFixedSize(true);
        rv_doctors.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rv_doctors.setAdapter(adapter);


        Query query2 = publicQuestionsRef.orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions options2 = new FirestoreRecyclerOptions.Builder<PublicQuestion>()
                .setQuery(query2, PublicQuestion.class)
                .build();
        adapter2 = new DepartmentsPublicQuestionsAdapter(this, options2, new DepartmentsPublicQuestionsAdapter.getClickedPublicQuestion() {
            @Override
            public void getPublicQuestion(DocumentSnapshot snapshot, int position) {
                if (isDoctor) {
                    Intent i = new Intent(SelectedDepartmentDetailActivity.this, PublicQuestionActivity.class);
                    i.putExtra("SEND_PUBLIC_QUESTION", snapshot.toObject(PublicQuestion.class));
                    i.putExtra("ID", snapshot.getId());
                    i.putExtra("CurrentDepartment", currentDepartment.getDepartmentName());
                    startActivity(i);
                } else {
                    Toast.makeText(SelectedDepartmentDetailActivity.this, getString(R.string.only_doctors), Toast.LENGTH_SHORT).show();
                }
            }
        });
        rv_public_messages.setHasFixedSize(true);
        rv_public_messages.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));


        rv_public_messages.setAdapter(adapter2);
    }

    private void initializeViews() {
        currentDepartment = (DepartmentDetails) getIntent().getSerializableExtra(getString(R.string.sendDepartmentDetails));
        tv_description = findViewById(R.id.content_scroll_department_description);
        rv_doctors = findViewById(R.id.content_scroll_doctors_rv);
        rv_public_messages = findViewById(R.id.content_scroll_department_questions);
        tv_description.setText(currentDepartment.getDepartmentDescription().trim());
        if (!currentDepartment.getDepartmentName().equals(getString(R.string.public_department))) {
            doctorRef = fs.collection(currentDepartment.getDepartmentName());
        } else {
            doctorRef = fs.collection("all doctors".toUpperCase());
        }
        publicQuestionsRef = fs.collection(currentDepartment.getDepartmentName() + " " + getString(R.string.publicQuestions));
        binding.departmentDetailActivityIv.setImageResource(currentDepartment.getDepartmentImageID());
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(currentDepartment.getDepartmentName());
        binding.departmentDetailActivityFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectedDepartmentDetailActivity.this, PublicQuestionActivity.class);
                i.putExtra("CurrentDepartment", currentDepartment.getDepartmentName());
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
    }

    private void checkIfDoctor() {
        doctorRef.whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().size() == 0) {
                            isDoctor = false;
                        } else {
                            isDoctor = true;
                        }
                        if (isDoctor) {
                            binding.departmentDetailActivityFloatingActionButton.setVisibility(View.GONE);
                        } else {
                            binding.departmentDetailActivityFloatingActionButton.setVisibility(View.VISIBLE);
                        }
                        Log.e(null, "onSuccess: " + isDoctor);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Error_Fragment error_fragment = Error_Fragment.newInstance(e.getMessage());
                        error_fragment.show(getSupportFragmentManager(), null);
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter2.stopListening();
    }
}