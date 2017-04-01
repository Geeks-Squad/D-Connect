package com.india.gov.helperapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.Bind;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by vyas on 3/25/17.
 */

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_username) EditText _usernameText;
    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_address) EditText _addressText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_mobile) EditText _mobileText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;
    @Bind(R.id.input_adhaar) TextView _adhaarText;
    @Bind(R.id.input_city) TextView _cityText;
    @Bind(R.id.input_dob) TextView _dobText;
    @Bind(R.id.input_pan) TextView _panText;
    @Bind(R.id.input_gname) TextView _gnameText;
    @Bind(R.id.etype_spinner) Spinner _etypeSpinner;

    String username;
    String name;
    String address;
    String email;
    String mobile;
    String password;
    String reEnterPassword;
    Integer adhaar;
    String city;
    int pan;
    String gname;
    Date dob;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

/*
        if (!validate()) {
            onSignupFailed();
            return;
        }
*/

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        username = _usernameText.getText().toString();
        name = _nameText.getText().toString();
        address = _addressText.getText().toString();
        email = _emailText.getText().toString();
        mobile = _mobileText.getText().toString();
        password = _passwordText.getText().toString();
        reEnterPassword = _reEnterPasswordText.getText().toString();
        adhaar = Integer.parseInt(_adhaarText.getText().toString());
        city = _cityText.getText().toString();
        pan = Integer.parseInt(_panText.getText().toString());
        gname = _gnameText.getText().toString();

        SimpleDateFormat parser=new SimpleDateFormat("d m yyyy");
        try {
            dob = parser.parse(_dobText.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(this,"Invalid date format. Use d-m-yyyy.",Toast.LENGTH_LONG).show();
            onSignupFailed();
            return;
        }
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        String result;
                        MediaType JSON
                                = MediaType.parse("application/json; charset=utf-8");

                        OkHttpClient client = new OkHttpClient();
                        String json = "{username:\""+username+"\",password:\""+password+"\"}";
                        String url = "192.168.43.143:8080";
                        RequestBody body = RequestBody.create(JSON, json);
                        Request request = new Request.Builder()
                                .url(url)
                                .post(body)
                                .build();
                        Response response = null;
                        try {
                            response = client.newCall(request).execute();
                            result = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}