package com.india.gov.helperapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.india.gov.helperapp.service.MyAccountTask;

import okhttp3.Response;

public class MyAccountActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_my_account, null, false);
        drawer.addView(contentView, 0);
        TextView accname = (TextView) findViewById(R.id.accname);
        accname.setText("My name is Shubham");
    }

    @Override
    protected void onResume(){
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    public void BlumenBlatt(Response response){
        Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
    }
}
