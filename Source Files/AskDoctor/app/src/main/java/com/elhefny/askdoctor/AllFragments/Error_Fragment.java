package com.elhefny.askdoctor.AllFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.elhefny.askdoctor.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Error_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Error_Fragment extends DialogFragment {
    TextView tv_error_message;

    private static final String ERROR_MESSAGE = "ERROR MESSAGE";

    private String error_message;

    public Error_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param error_message Parameter 1.
     * @return A new instance of fragment Error_Fragment.
     */
    public static Error_Fragment newInstance(String error_message) {
        Error_Fragment fragment = new Error_Fragment();
        Bundle args = new Bundle();
        args.putString(ERROR_MESSAGE, error_message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            error_message = getArguments().getString(ERROR_MESSAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_error_message = view.findViewById(R.id.error_fragment_tv_text_message);
        tv_error_message.setText(error_message);
    }
}