package com.example.group_smsender;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // member variables for views
    EditText urlEditText;
    EditText msgEditText;
    Button sendButton;
    TextView responseTextView;

    private static final int PERMISSION_REQUEST_SEND_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize views
        urlEditText = findViewById(R.id.urlEditText);
        msgEditText = findViewById(R.id.msgEditText);
        sendButton = findViewById(R.id.sendButton);
        responseTextView = findViewById(R.id.responseTextView);

        // set click listener for send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlEditText.getText().toString();
                String msg = msgEditText.getText().toString();
                sendGetRequest(url, msg);
            }
        });
    }

    // function for sending a GET request
    private void sendGetRequest(String url, final String msg) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // handle successful response
                        responseTextView.setText(response);

                        // Use the response directly as the list of recipients
                        List<String> recipients = parseRecipientsFromResponse(response);

                        // Send SMS with the received list of recipients
                        sendSMS(recipients, msg);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error response
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity", "Error: " + error.getMessage());
                    }
                });

        // add request to queue
        queue.add(request);
    }

    // function for sending an SMS
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

    // function for parsing the recipients from the response
    private List<String> parseRecipientsFromResponse(String response) {
        // TODO: Implement your logic to parse the recipients from the response
        // Here, you can parse the response JSON or extract recipients from any specific format

        // For demonstration purposes, assuming the response is a comma-separated string
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
                String url = urlEditText.getText().toString();
                String msg = msgEditText.getText().toString();
                sendGetRequest(url, msg);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
