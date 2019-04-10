package com.example.mysensorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;

import android.widget.TextView;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;


public class MainActivity extends Activity {

    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];
    SensorManager sm = null;
    TextView textView1 = null;
    TextView textView2 = null;
    TextView textView3 = null;
    List<Sensor> list;


    final SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = event.sensor;
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float[] values = event.values;
                textView1.setText("x: " + values[0] + "\ny:" + values[1] + "\nz: " + values[2]);
            }
            if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                float[] values = event.values;
                textView2.setText("x: " + values[0] + "\ny:" + values[1] + "\nz: " + values[2]);
            }
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float[] values = event.values;

                final float alpha = 0.8f;

                gravity[0] = alpha * gravity[0] + (1 - alpha) * values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * values[2];

                linear_acceleration[0] = values[0] - gravity[0];
                linear_acceleration[1] = values[1] - gravity[1];
                linear_acceleration[2] = values[2] - gravity[2];

                textView3.setText("x: " + linear_acceleration[0] + "\ny:" + linear_acceleration[1] + "\nz: " + linear_acceleration[2]);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Sensey.getInstance().init(context);

        /*Get a Sensor Manager Instance*/
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);

        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);



        list = sm.getSensorList(Sensor.TYPE_ALL);
        if(list.size() > 0) {
            sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();

        }
        if(list.size() > 0) {
            sm.registerListener(sel, (Sensor) list.get(2), SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(getBaseContext(), "Error: No Linear Acceleration.", Toast.LENGTH_LONG).show();

        }
        if(list.size() > 0) {
            sm.registerListener(sel, (Sensor) list.get(1), SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(getBaseContext(), "Error: No Gyroscope.", Toast.LENGTH_LONG).show();

        }


    }

    @Override
    protected void onStop() {
        if(list.size()>0) {
            sm.unregisterListener(sel);
        }
        super.onStop();
    }
}
