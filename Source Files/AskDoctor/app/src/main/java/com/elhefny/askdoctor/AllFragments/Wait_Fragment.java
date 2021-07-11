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
 * Use the {@link Wait_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Wait_Fragment extends DialogFragment {
    TextView tv_wait_message;


    private static final String WAIT_MESSAGE = "WaitMessage";

    private String waitMessage;

    public Wait_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param waitMessage Parameter 1.
     * @return A new instance of fragment Wait_Fragment.
     */

    public static Wait_Fragment newInstance(String waitMessage) {
        Wait_Fragment fragment = new Wait_Fragment();
        Bundle args = new Bundle();
        args.putString(WAIT_MESSAGE, waitMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            waitMessage = getArguments().getString(WAIT_MESSAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wait_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_wait_message = view.findViewById(R.id.wait_fragment_tv_text_message);
        tv_wait_message.setText(waitMessage);
    }
}