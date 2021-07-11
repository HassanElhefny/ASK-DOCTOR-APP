package com.elhefny.askdoctor;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.elhefny.askdoctor.AllFragments.Wait_Fragment;
import com.elhefny.askdoctor.Patients.PublicQuestion;
import com.elhefny.askdoctor.databinding.ActivityPublicQuestionBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class PublicQuestionActivity extends AppCompatActivity {
    ActivityPublicQuestionBinding binding;
    PublicQuestion publicQuestion;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference;
    String Department;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPublicQuestionBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        mAuth = FirebaseAuth.getInstance();
        Department = getIntent().getStringExtra("CurrentDepartment");
        id = getIntent().getStringExtra("ID");
        collectionReference = db.collection(Department + " " + getString(R.string.publicQuestions));
        publicQuestion = (PublicQuestion) getIntent().getSerializableExtra("SEND_PUBLIC_QUESTION");
        if (publicQuestion != null) {
            binding.publicAnswerEtLayout.setVisibility(View.VISIBLE);
            binding.publicQuestionEtLayout.setEnabled(false);
            binding.publicQuestionBtnSave.setText(getString(R.string.answer_the_question));
            binding.publicQuestionTvDate.setText(publicQuestion.getDate());
            binding.publicQuestionTvPatient.setText(publicQuestion.getPatientEmail());
            binding.publicQuestionEtQuestionBody.setText(publicQuestion.getQuestionBody());
        } else {
            binding.publicQuestionEtLayout.setEnabled(true);
            binding.publicQuestionBtnSave.setText(getString(R.string.send_this_question));
            binding.publicAnswerEtLayout.setVisibility(View.GONE);
            binding.publicQuestionTvDate.setText(Calendar.getInstance().getTime().toString());
            binding.publicQuestionTvPatient.setText(mAuth.getCurrentUser().getEmail());
        }

    }

    public void sendThisPublicQuestion(View view) {
        if (!NetworkUtil.getConnectivityStatusString(this).equals(getString(R.string.no_internet_connection))) {
            if (binding.publicQuestionEtQuestionBody.getText().toString().trim().length() <= 6) {
                binding.publicQuestionEtQuestionBody.setError(getString(R.string.required_more_than_5_charachters));
                binding.publicQuestionEtQuestionBody.requestFocus();
            } else {
                Wait_Fragment wait_fragment = Wait_Fragment.newInstance(getString(R.string.saving));
                wait_fragment.show(getSupportFragmentManager(), null);
                if (binding.publicQuestionBtnSave.getText().toString().equals(getString(R.string.send_this_question))) {
                    PublicQuestion publicQuestion = new PublicQuestion(
                            binding.publicQuestionEtQuestionBody.getText().toString().trim(),
                            Calendar.getInstance().getTime().toString(),
                            "",
                            mAuth.getCurrentUser().getEmail()
                    );
                    collectionReference.add(publicQuestion).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            wait_fragment.dismiss();
                            finish();
                        }
                    });
                } else if (binding.publicQuestionBtnSave.getText().toString().equals(getString(R.string.answer_the_question))) {
                    collectionReference.whereEqualTo("questionBody", publicQuestion.getQuestionBody())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                        DocumentSnapshot snapshot = task.getResult().getDocuments().get(0);
                                        String id = snapshot.getId();
                                        collectionReference.document(id).update("answer", binding.publicQuestionEtAnswerBody.getText().toString().trim())
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        wait_fragment.dismiss();
                                                        finish();
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}