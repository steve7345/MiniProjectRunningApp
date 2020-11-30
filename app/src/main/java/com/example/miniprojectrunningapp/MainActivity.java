package com.example.miniprojectrunningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    // experimental values for hi and lo magnitude limits
    private final double HI_STEP = 10.5;     // upper mag limit
    private final double LO_STEP = 8.5;      // lower mag limit
    boolean highLimit = false;      // detect high limit
    int step_counter = 0;                // step counter

    TextView tvx, tvy, tvz, tvMag, tvStepsCount;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    
    
    CountUpTimer timer;
    TextView counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        tvStepsCount = findViewById(R.id.tvStepCount);
        counter = findViewById(R.id.tvSecondsCount);

        timer = new CountUpTimer(30000000) {  // should be high for the run (ms)
            public void onTick(int second) {
                counter.setText(String.valueOf(second));
            }
        };
    }
    public void doStart(View view) {

        timer.start();
        Toast.makeText(this, "Tracking Started", Toast.LENGTH_LONG).show();
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener((SensorEventListener) this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void doStop(View view) {
        timer.cancel();
        Toast.makeText(this, "Tracking Stopped", Toast.LENGTH_LONG).show();
    }

    public void doReset(View view) {
        counter.setText("0");
        Toast.makeText(this, "Tracking Reset", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // get a magnitude number using Pythagorus's Theorem
        double mag = round(Math.sqrt((x*x) + (y*y) + (z*z)), 2);



        if ((mag > HI_STEP) && (highLimit == false)) {
            highLimit = true;
        }
        if ((mag < LO_STEP) && (highLimit == true)) {
            // we have a step
            step_counter++;
            tvStepsCount.setText(String.valueOf(step_counter));
            highLimit = false;
        }
}
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void doSummery(View view) {
        Intent intent = new Intent( MainActivity.this , MainActivity2Summery.class);

        // start the activity connect to the specified class
        startActivity(intent);
    }
}
