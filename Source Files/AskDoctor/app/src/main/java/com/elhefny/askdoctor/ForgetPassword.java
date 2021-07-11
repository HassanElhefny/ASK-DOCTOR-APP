package com.elhefny.askdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.elhefny.askdoctor.AllFragments.Error_Fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    TextInputEditText email;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        email = findViewById(R.id.forget_password_text_input_editText_email);
        auth = FirebaseAuth.getInstance();
    }

    public void changePassword(View view) {
        if (!NetworkUtil.getConnectivityStatusString(this).equals(getString(R.string.no_internet_connection))) {
            if (!TextUtils.isEmpty(email.getText()) && validateEmail()) {
                auth.sendPasswordResetEmail(email.getText().toString().trim())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ForgetPassword.this, getString(R.string.password_set_send), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetPassword.this, Login_In.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForgetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Error_Fragment.newInstance(getString(R.string.error_occurred)).show(getSupportFragmentManager(), null);
                    }
                });
            }
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateEmail() {
        String val = email.getText().toString().trim();
        String check = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            email.setError(getString(R.string.required));
            return false;
        } else if (!val.matches(check)) {
            email.setError(getString(R.string.invalid_email));
            return false;
        } else {
            email.setError(null);
            return true;
        }

    }
}