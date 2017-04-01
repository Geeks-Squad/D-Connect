package com.india.gov.helperapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.india.gov.helperapp.service.MyAccountTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static long back_pressed_time;
    private static long PERIOD = 2000;
    TextView textView;
    DrawerLayout drawer;
    Toolbar toolbar;
    ConstraintLayout constraintLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView  = (TextView) findViewById(R.id.textView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(1).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (back_pressed_time + PERIOD > System.currentTimeMillis()) super.onBackPressed();
            else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
            back_pressed_time = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_my_acc) {
            SharedPreferences prefs = getSharedPreferences("IDPass", Context.MODE_PRIVATE);
            String lname = prefs.getString("aadhar",null);
            String lpass = prefs.getString("aadhar_pass",null);
            MyAccountTask myAccountTask = new MyAccountTask(lname,lpass,this);
            myAccountTask.execute();
        } else if (id == R.id.nav_dashboard) {
            Intent dashintent = new Intent(getApplicationContext(),UpdateListActivity.class);
            startActivity(dashintent);
        } else if (id == R.id.nav_recs) {
            Intent recsintent = new Intent(getApplicationContext(),RecommendationsActivity.class);
            startActivity(recsintent);
        } else if (id == R.id.nav_bookmarks) {
            Intent bookmarkintent = new Intent(getApplicationContext(),BookmarksActivity.class);
            startActivity(bookmarkintent);
        } else if (id == R.id.nav_share) {
            Intent shareintent = new Intent(Intent.ACTION_SEND);
            shareintent.setType("Text/html");
            shareintent.putExtra(Intent.EXTRA_TEXT, "The Playstore Link to this app is: @playstore");
            startActivity(Intent.createChooser(shareintent,"Share using"));
        } else if (id == R.id.nav_about) {
            Intent aboutintent = new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(aboutintent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
