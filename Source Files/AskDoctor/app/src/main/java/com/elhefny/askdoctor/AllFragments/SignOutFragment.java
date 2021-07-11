package com.elhefny.askdoctor.AllFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.elhefny.askdoctor.R;
import com.google.android.material.button.MaterialButton;

public class SignOutFragment extends Fragment {
    MaterialButton signOut;
    static SignOutInterface signOutInterface;

    public SignOutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        signOutInterface = (SignOutInterface) context;
    }

    public static SignOutFragment newInstance() {
        SignOutFragment fragment = new SignOutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_out, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signOut = view.findViewById(R.id.sign_out_btn);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutInterface.signOut();
            }
        });
    }

    public interface SignOutInterface {
        void signOut();
    }
}