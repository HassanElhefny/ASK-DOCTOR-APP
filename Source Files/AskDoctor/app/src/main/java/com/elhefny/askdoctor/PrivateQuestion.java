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

import com.elhefny.askdoctor.PrivateQuestions.PrivateQuestionAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class PrivateQuestion extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference;
    RecyclerView rv;
    FloatingActionButton fab;
    PrivateQuestionAdapter adapter;
    private FirebaseAuth mAuth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_question);
        mAuth = FirebaseAuth.getInstance();
        email = getIntent().getStringExtra("EmailOfTheDoctor");
        collectionReference = db.collection(email);
        initializeViews();
        initializeRecycler();
        addActionForFloatingActionButton();
    }

    private void addActionForFloatingActionButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddPrivateQuestion.class);
                i.putExtra("DoctorEmail", email);
                Log.e("Private Question", "addActionForFloatingActionButton: " + email);
                startActivity(i);
            }
        });
    }

    private void initializeViews() {
        fab = findViewById(R.id.private_question_fab_add);
        rv = findViewById(R.id.private_question_rv);
    }

    private void initializeRecycler() {
        Query query = collectionReference.orderBy("questionReceiver", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<com.elhefny.askdoctor.PrivateQuestions.PrivateQuestion> options =
                new FirestoreRecyclerOptions.Builder<com.elhefny.askdoctor.PrivateQuestions.PrivateQuestion>()
                        .setQuery(query, com.elhefny.askdoctor.PrivateQuestions.PrivateQuestion.class)
                        .build();
        adapter = new PrivateQuestionAdapter(this, options, new PrivateQuestionAdapter.getClickedQuestion() {
            @Override
            public void getQuestion(DocumentSnapshot dataSnapshot, int position) {
                if (mAuth.getCurrentUser().getEmail().equals(email)) {
                    Intent i = new Intent(getBaseContext(), AddPrivateQuestion.class);
                    i.putExtra("SEND PRIVATE QUESTION", dataSnapshot.toObject(com.elhefny.askdoctor.PrivateQuestions.PrivateQuestion.class));
                    i.putExtra("PRIVATE QUESTION ID", dataSnapshot.getId());
                    i.putExtra("DoctorEmail", email);
                    startActivity(i);
                } else {
                    Toast.makeText(PrivateQuestion.this, getString(R.string.only_doctor), Toast.LENGTH_SHORT).show();
                }
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}