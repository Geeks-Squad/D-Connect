package com.india.gov.helperapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
    }

    public void candidatefunc(View view)
    {
        Intent selectChoice = new Intent(StartingActivity.this,UserAuthentication.class);
        startActivity(selectChoice);
    }
}
