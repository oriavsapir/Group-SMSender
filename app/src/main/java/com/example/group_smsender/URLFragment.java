package com.example.group_smsender;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class URLFragment extends Fragment {
    EditText urlEditText;
    Button sendButton;
    TextView responseTextView;
    FragmentNavigation fragmentNavigation;
    TextView instructionTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_u_r_l, container, false);

        urlEditText = view.findViewById(R.id.urlEditText);
        sendButton = view.findViewById(R.id.sendButton);
        responseTextView = view.findViewById(R.id.responseTextView);
        fragmentNavigation = (FragmentNavigation) getActivity();

        // Get the saved token and protocol from SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlEditText.getText().toString();
                ((MainActivity)getActivity()).sendPostRequest(url, token, "");

            }
        });
        instructionTextView = view.findViewById(R.id.instructionTextView);
        instructionTextView.setText(R.string.url_instruction);
        return view;
    }
}
