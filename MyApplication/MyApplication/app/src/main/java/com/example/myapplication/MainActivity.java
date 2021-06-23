package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;;
import android.widget.TextView;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textViewStepCounter, textViewStepDetector;
    private SensorManager sensorManager;
    private Sensor mStepCounter, mStepDetector;
    private boolean isCounterSensorPresent, isDetectorSensorPresent;
    int stepCount = 0, stepDetect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        textViewStepCounter = findViewById(R.id.textViewStepCounter);
        textViewStepDetector = findViewById(R.id.textViewStepDetector);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;

        }else
        {
            textViewStepCounter.setText("Counter Sensor is not Present");
            isCounterSensorPresent = false;

        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null)
        {
            mStepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            isDetectorSensorPresent = true;

        }else {
            textViewStepDetector.setText("Detector Sensor is not Present");
            isDetectorSensorPresent = false;

        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
    if(sensorEvent.sensor == mStepCounter){
        stepCount = (int) sensorEvent.values[0];
        textViewStepCounter.setText(String.valueOf(stepCount));
    }
    if(sensorEvent.sensor == mStepDetector){
        stepDetect = (int) (stepDetect + sensorEvent.values[0]);
        textViewStepDetector.setText(String.valueOf(stepDetect));
        stepDetect++;
    }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
            sensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null) {
            sensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
            sensorManager.unregisterListener(this, mStepCounter);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null)
            sensorManager.unregisterListener(this, mStepDetector);
    }
}