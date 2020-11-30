package com.example.miniprojectrunningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity2Summery extends AppCompatActivity {
    public  double distanceRan;
    Button doGoBack ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_summery);
        TextView tvDisplayDate = (TextView) findViewById(R.id.tvDisplayDate);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String simpleDateFormat = "";
        String date = String.format(String.valueOf(calendar.getTime()));
        tvDisplayDate.setText(date);


    }

    public void doGoBack(View view) {
        Intent intent = new Intent( MainActivity2Summery.this , MainActivity.class);

        // start the activity connect to the specified class
        startActivity(intent);
    }
}