package com.example.group_smsender;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FragmentNavigation {

    private static final int PERMISSION_REQUEST_SEND_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                int itemId = item.getItemId();
                if (itemId == R.id.URL) {
                    fragment = new URLFragment();
                } else if (itemId == R.id.navigation_notifications) {
                    fragment = new URLMSGFragment();
                } else if (itemId == R.id.navigation_home) {
                    fragment = new SettingFragment();
                } else {
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                return true;
            }
        });
        navigateToURLInputFragment(); // default fragment
    }

    @Override
    public void navigateToURLInputFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new URLFragment())
                .commit();
    }

    @Override
    public void navigateToURLMsgInputFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new URLMSGFragment())
                .commit();
    }

    public void sendPostRequest(String url, String token, final String userMsg) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<String> recipients;
                        String msg;

                        // Check if response includes ":" which indicates it's of the first type
                        if(response.contains(":")) {
                            // Parse response into recipients and messages
                            String[] splitResponse = response.split(",");
                            recipients = new ArrayList<>();
                            for(String item : splitResponse) {
                                String[] splitItem = item.split(":");
                                recipients.add(splitItem[0].trim());
                                msg = splitItem[1].trim();
                                // Send SMS for each recipient and message pair
                                sendSMS(Arrays.asList(splitItem[0].trim()), splitItem[1].trim());
                            }
                        } else {
                            // If it's the second type, just split by comma
                            recipients = Arrays.asList(response.split(","));
                            msg = userMsg;
                            // Send SMS to all recipients with the same message
                            if(!msg.isEmpty()) {
                                sendSMS(recipients, msg);
                            }
                        }

                        // Show response in a Toast
                        Toast.makeText(MainActivity.this, "Response: " + response, Toast.LENGTH_SHORT).show();

                        // Show success toast
                        Toast.makeText(MainActivity.this, "POST request was successful.", Toast.LENGTH_SHORT).show();

                        if(!userMsg.isEmpty()) {
                            navigateToURLMsgInputFragment();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));

                        // Show failure toast
                        Toast.makeText(MainActivity.this, "POST request failed: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                return params;
            }
        };
        queue.add(postRequest);
    }



    private void sendSMS(List<String> recipients, String message) {
        if (checkPermission()) {
            for (String recipient : recipients) {
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(recipient, null, message, null, null);
                    Toast.makeText(MainActivity.this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "SMS sending failed", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        } else {
            requestPermission();
        }
    }

    private List<String> parseRecipientsFromResponse(String response) {
        String[] recipientsArray = response.split(",");
        List<String> recipients = Arrays.asList(recipientsArray);
        return recipients;
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_SEND_SMS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
