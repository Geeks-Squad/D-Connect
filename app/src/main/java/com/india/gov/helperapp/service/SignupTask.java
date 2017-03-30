package com.india.gov.helperapp.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.india.gov.helperapp.R;
import com.india.gov.helperapp.UpdateListActivity;
import com.india.gov.helperapp.model.Signup;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by Shubham Maheshwari on 31-03-2017.
 */

public class SignupTask extends AsyncTask<Void, Void, Boolean> {

    ProgressDialog pg;
    Context context;
    Signup signup;

    public SignupTask(Signup signup, Context context){
        this.context = context;
        this.signup = signup;
    }

    @Override
    protected void onPreExecute() {
        pg = new ProgressDialog(context);
        pg.setMessage("Loading...");
        pg.show();
    }


    @Override
    protected Boolean doInBackground(Void... params) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        String url = context.getString(R.string.url);;
        Gson gson = new GsonBuilder().create();
        String body = gson.toJson(signup);

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody
                        .create(MediaType
                                        .parse("application/json"),
                                gson.toJson(signup)
                        ))
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.body().string() == "1") {
                return true;
            }
        } catch (IOException e) {
            Toast.makeText(context,"Error Signing up",Toast.LENGTH_LONG).show();
        }

        return false;
    }

    protected void onPostExecute(Boolean Status) {
        if (Status == true) {
        }
        else {
            Toast.makeText(context,"Error Logging in",Toast.LENGTH_LONG).show();
        }
    }



}
