package com.elhefny.askdoctor;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elhefny.askdoctor.AllFragments.Wait_Fragment;
import com.elhefny.askdoctor.PrivateQuestions.PrivateQuestion;
import com.elhefny.askdoctor.databinding.ActivityAddPrivateQuestionBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class AddPrivateQuestion extends AppCompatActivity {
    String email;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ActivityAddPrivateQuestionBinding binding;
    PrivateQuestion sentQuestion;
    String questionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPrivateQuestionBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        sentQuestion = (PrivateQuestion) getIntent().getSerializableExtra("SEND PRIVATE QUESTION");
        questionID = getIntent().getStringExtra("PRIVATE QUESTION ID");
        email = getIntent().getStringExtra("DoctorEmail");
        initializeViews();
    }

    private void initializeViews() {
        if (sentQuestion != null) {
            binding.privateQuestionBtnSave.setText(getString(R.string.send_the_answer));
            binding.privateQuestionEtAnswerBody.setVisibility(View.VISIBLE);
            binding.privateQuestionEtQuestionBody.setText(sentQuestion.getQuestionBody());
            binding.privateAnswerEtLayout.setVisibility(View.VISIBLE);
            binding.privateQuestionTvDate.setText(sentQuestion.getQuestionTime());
        } else {
            binding.privateQuestionEtAnswerBody.setVisibility(View.GONE);
            binding.privateQuestionTvDate.setText(Calendar.getInstance().getTime().toString());
        }
        binding.privateQuestionTvTo.setText(email);
        binding.privateQuestionBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtil.getConnectivityStatusString(getBaseContext()).equals(getString(R.string.no_internet_connection))) {
                    if (binding.privateQuestionBtnSave.getText().toString().equals(getString(R.string.send_the_answer))) {
                        if (binding.privateQuestionEtAnswerBody.getText().length() == 0) {
                            binding.privateQuestionEtAnswerBody.setError(getString(R.string.not_empty));
                            binding.privateQuestionEtAnswerBody.requestFocus();
                        } else {
                            Wait_Fragment wait_fragment = Wait_Fragment.newInstance(getString(R.string.please_wait));
                            wait_fragment.show(getSupportFragmentManager(), null);
                            db.collection(email).document(questionID).update("questionAnswer", binding.privateQuestionEtAnswerBody.getText().toString().trim())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            wait_fragment.dismiss();
                                            finish();
                                        }
                                    });
                        }
                    } else {
                        if (binding.privateQuestionEtQuestionBody.getText().length() == 0) {
                            binding.privateQuestionEtQuestionBody.setError(getString(R.string.not_empty));
                            binding.privateQuestionEtQuestionBody.requestFocus();
                        } else {
                            Wait_Fragment wait_fragment = Wait_Fragment.newInstance(getString(R.string.please_wait));
                            wait_fragment.show(getSupportFragmentManager(), null);
                            PrivateQuestion p = new PrivateQuestion(
                                    mAuth.getCurrentUser().getEmail(),
                                    email,
                                    binding.privateQuestionEtQuestionBody.getText().toString().trim(),
                                    binding.privateQuestionTvDate.getText().toString(),
                                    ""

                            );
                            db.collection(email).add(p)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            wait_fragment.dismiss();
                                            finish();
                                        }
                                    });
                        }
                    }
                } else {
                    Toast.makeText(AddPrivateQuestion.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}