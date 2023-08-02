package com.example.group_smsender;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {

    private Button saveTokenButton;
    private EditText tokenEditText;
    private Spinner languageSpinner;
    private SharedPreferences preferences;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        saveTokenButton = view.findViewById(R.id.saveButton);
        tokenEditText = view.findViewById(R.id.tokenEditText);

        // Retrieve the saved values
        preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedToken = preferences.getString("token", "");
        tokenEditText.setText(savedToken);



        saveTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = tokenEditText.getText().toString();

                // Save the token
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("token", token);
                editor.apply();

                // Display a toast message
                Toast.makeText(getActivity(), "Token Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}
