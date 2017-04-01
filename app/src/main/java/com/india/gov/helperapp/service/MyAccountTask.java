package com.india.gov.helperapp.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import com.india.gov.helperapp.MyAccountActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Shubham Maheshwari on 31-03-2017.
 */

public class MyAccountTask extends AsyncTask<Void,Void,Boolean> {

    ProgressDialog pg;
    OkHttpClient okHttpClient = new OkHttpClient();
    Response response;
    String url = "http://172.104.51.13:8080/candidate/id/";
    Context context;
    String aid,apass;
    public MyAccountTask(String lname,String lpass,Context context) {
        aid = lname;
        apass = lpass;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        pg = new ProgressDialog(context);
        pg.setMessage("Loading...");
        pg.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        Request request = new Request.Builder().url(url+aid).addHeader("Skip","yes").build();
        //Request request = new Request.Builder().url(url).addHeader("Authorization", "Basic " + Base64.encodeToString((aid+":"+apass).getBytes(),Base64.DEFAULT)).build();
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.body().string() == "1") {
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Details Couldn't be retrieved", Toast.LENGTH_LONG).show();
        }

        return false;
    }

    @Override
    public void onPostExecute(Boolean Status) {
        try {
            if (Status == true) {
                //String result = response.body().string();
                //result = result.replace("\"","");
                //JSONObject json = new JSONObject(result);
                Intent intent = new Intent(context,MyAccountActivity.class);
                //intent.putExtra("Candidate_Name",json.getString("Name"));
                context.startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
