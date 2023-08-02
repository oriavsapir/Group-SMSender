package com.example.group_smsender;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class URLMSGFragment extends Fragment {

    EditText urlEditText;
    EditText msgEditText;
    Button sendButton;
    TextView responseTextView;
    TextView letterCounterTextView;
    FragmentNavigation fragmentNavigation;
    TextView instructionTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_u_r_l_m_s_g, container, false);

        urlEditText = view.findViewById(R.id.urlEditText);
        msgEditText = view.findViewById(R.id.msgEditText);
        sendButton = view.findViewById(R.id.sendButton);
        responseTextView = view.findViewById(R.id.responseTextView);
        letterCounterTextView = view.findViewById(R.id.letterCounterTextView);
        fragmentNavigation = (FragmentNavigation) getActivity();

        // Set hints and text from resources
        urlEditText.setHint(getString(R.string.url_hint));
        msgEditText.setHint(getString(R.string.msg_hint));
        sendButton.setText(getString(R.string.send_button));

        // Get the saved token and protocol from SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlEditText.getText().toString();
                String msg = msgEditText.getText().toString();
                ((MainActivity)getActivity()).sendPostRequest(url, token, msg);
            }
        });

        msgEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int currentCount = s.length();
                letterCounterTextView.setText(getString(R.string.counter_text, currentCount));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        instructionTextView = view.findViewById(R.id.instructionTextView);
        instructionTextView.setText(R.string.url_msg_instruction);
        return view;
    }
}
