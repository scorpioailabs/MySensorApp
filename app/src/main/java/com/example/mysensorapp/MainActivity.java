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

    SensorManager sm = null;
    TextView textView1 = null;
    TextView textView2 = null;
    TextView textView3 = null;
    List<Sensor> list;
    List<Sensor> list1;
    List<Sensor> list2;



    SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = event.sensor;
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float[] values = event.values;
                textView1.setText("x: "+values[0]+"\ny:"+values[1]+"\nz: "+values[2]);
            }
            if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                float[] values = event.values;
                textView2.setText("x: "+values[0]+"\ny:"+values[1]+"\nz: "+values[2]);
            }
            if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                float[] values = event.values;
                textView3.setText("x: "+values[0]+"\ny:"+values[1]+"\nz: "+values[2]);
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

        list = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        list1 = sm.getSensorList(Sensor.TYPE_GYROSCOPE);
        list2 = sm.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION);
        if(list.size() > 0) {
            sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();

        }
        if(list1.size() > 0) {
            sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();

        }
        if(list2.size() > 0) {
            sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();

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
