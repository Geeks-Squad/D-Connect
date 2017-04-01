package com.india.gov.helperapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.india.gov.helperapp.service.LoginTask;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by vyas on 3/25/17.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    SharedPreferences sharedPref;
    @Bind(R.id.input_aadharno) EditText _aadharnoText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
            }
        });
        sharedPref = this.getSharedPreferences("IDPass",Context.MODE_PRIVATE);
    }

    public void login() {
        Log.d(TAG, "Login");

    /*    if (!validate()) {
            onLoginFailed();
            return;
        }*/

        _loginButton.setEnabled(false);

        String username = _aadharnoText.getText().toString();
        String password = _passwordText.getText().toString();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("aadhar",username);
        editor.putString("aadhar_pass",password);
        editor.commit();
        LoginTask loginTask = new LoginTask(username,password,getApplicationContext(),this);
        loginTask.execute();
        _loginButton.setEnabled(true);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String password = _passwordText.getText().toString();

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Your password should be 4 to 10 alphanumeric characters long");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}