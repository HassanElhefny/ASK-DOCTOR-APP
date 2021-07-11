package com.elhefny.askdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.elhefny.askdoctor.AllFragments.Error_Fragment;
import com.elhefny.askdoctor.AllFragments.Wait_Fragment;
import com.elhefny.askdoctor.databinding.ActivityLoginInBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_In extends AppCompatActivity {
    private ActivityLoginInBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(View view) {
        Intent i = new Intent(this, Sign_Up_0.class);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void logIn(View view) {
        if (!NetworkUtil.getConnectivityStatusString(this).equals(getString(R.string.no_internet_connection))) {
            if (binding.logInTextInputEditTextEmail.getText().length() > 0 && binding.logInTextInputEditTextPassword.getText().length() > 0) {
                Wait_Fragment wait_fragment = Wait_Fragment.newInstance(getString(R.string.loading) + " ...");
                wait_fragment.show(getSupportFragmentManager(), null);
                if (NetworkUtil.getConnectivityStatusString(this).equals(getString(R.string.wifi_connection))
                        || NetworkUtil.getConnectivityStatusString(this).equals(getString(R.string.mobile_data_connection))) {
                    if (!TextUtils.isEmpty(binding.logInTextInputEditTextEmail.getText())
                            && !TextUtils.isEmpty(binding.logInTextInputEditTextPassword.getText())) {
                        mAuth.signInWithEmailAndPassword(binding.logInTextInputEditTextEmail.getText().toString().trim(),
                                binding.logInTextInputEditTextPassword.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Intent i = new Intent(Login_In.this, MainActivity.class);
                                wait_fragment.dismiss();
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
                    wait_fragment.dismiss();
                    Error_Fragment error_fragment = Error_Fragment.newInstance(getString(R.string.no_internet));
                    error_fragment.show(getSupportFragmentManager(), null);
                }
            } else {
                if (binding.logInTextInputEditTextPassword.getText().length() <= 0) {
                    binding.logInTextInputEditTextPassword.setError(getString(R.string.required));
                    binding.logInTextInputEditTextPassword.requestFocus();
                } else if (binding.logInTextInputEditTextEmail.getText().length() <= 0) {
                    binding.logInTextInputEditTextEmail.setError(getString(R.string.required));
                    binding.logInTextInputEditTextEmail.requestFocus();
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    public void forgetPassword(View view) {
        startActivity(new Intent(Login_In.this, ForgetPassword.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Wait_Fragment wait_fragment = Wait_Fragment.newInstance(getString(R.string.loading) + " ...");
        wait_fragment.show(getSupportFragmentManager(), null);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            wait_fragment.dismiss();
        }
    }
}