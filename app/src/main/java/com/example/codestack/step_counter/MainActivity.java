package com.example.codestack.step_counter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xText, yText, zText;
    private TextView xText1, yText1, zText1;
    private TextView Light;

    private Sensor mySensor;            //Accelerameter
    private Sensor mySensor2;           //RGB light sensor
    private Sensor mySensor3;
    private Button musicbutton;
    private SensorManager SM;           //Sensor manager
    private boolean flag = true;       //flag whether count steps

    private BarChart barchart;          //bar chart

    private boolean mInitialized = false;

    private TextView Steps;
    private int stepsCount = 0;

    private final float NOISE = (float) 2.0;
    private double mLastX;
    private double mLastY;
    private double mLastZ;
    BarChart chart;

    private String name;
    private int age;
    private int height;
    private int weight;
    private String gender;

    private int light_value;
    private TextView name_text;
    //private TextView age_text;

    private double[] last_two_value;

    {
        last_two_value = new double[]{0, 0};
    }

    private int gyro_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name_text = (TextView)findViewById(R.id.name);
        //age_text = (TextView)findViewById(R.id.age);


        //Create our Sensor Manager -> This is a manager to all sensors
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        //Accelarometer Sensor -> this is a sensor -> we need SM to set sensor to it
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mySensor2 = SM.getDefaultSensor(Sensor.TYPE_LIGHT);
        mySensor3 = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
        Steps = (TextView)findViewById(R.id.Steps);
        Light = (TextView)findViewById(R.id.Light);
        xText1 = (TextView)findViewById(R.id.xText1);
        yText1 = (TextView)findViewById(R.id.yText1);
        zText1 = (TextView)findViewById(R.id.zText1);

        musicbutton = (Button) findViewById(R.id.button);       //go back button


        chart = (BarChart) findViewById(R.id.bargraph);         //chart

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("figure");
        chart.animateXY(2000, 2000);        //create with a time duration
        chart.invalidate();


    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(stepsCount, 0); // day1
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(stepsCount*height*0.45f/100, 1); // day2
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(weight*stepsCount*height*0.45f/100/1000*1.036f, 2); // day3
        valueSet1.add(v1e3);
        /*
        BarEntry v1e4 = new BarEntry(30.000f, 3); // day4
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // day5
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(stepsCount, 5); // day6
        valueSet1.add(v1e6);
        */

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Your Steps and Data");
        barDataSet1.setColor(Color.rgb(0, 155, 0));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Steps");
        xAxis.add("Distance(m)");
        xAxis.add("Cal");
        //xAxis.add("Day 4");
        // xAxis.add("Day 5");
        //xAxis.add("Day 6");
        return xAxis;
    }



    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if(intent.hasExtra("name"))
        {
            name_text.setText("User: " + intent.getStringExtra("name"));
            //stepsCount = Integer.valueOf(intent.getStringExtra("key2"));
        }
        if(intent.hasExtra("age"))
        {
            //age_text.setText(Integer.toString(intent.getIntExtra("age",0)));
            //stepsCount = Integer.valueOf(intent.getStringExtra("key2"));
            age = intent.getIntExtra("age",0);
        }
        if(intent.hasExtra("height"))
        {
            //age_text.setText(Integer.toString(intent.getIntExtra("age",0)));
            //stepsCount = Integer.valueOf(intent.getStringExtra("key2"));
            height = intent.getIntExtra("height",0);
        }
        if(intent.hasExtra("weight"))
        {
            //age_text.setText(Integer.toString(intent.getIntExtra("age",0)));
            //stepsCount = Integer.valueOf(intent.getStringExtra("key2"));
            weight = intent.getIntExtra("weight",0);
        }


        SM.registerListener(this, mySensor,SensorManager.SENSOR_DELAY_UI);
        SM.registerListener(this,mySensor2, SensorManager.SENSOR_DELAY_UI);
        SM.registerListener(this,mySensor3,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SM.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            Light.setText("Light:" + event.values[0]);
            light_value = (int)event.values[0];
            if(event.values[0] ==0){
                if(flag == true){
                    flag = false;
                }
                else {
                    flag = true;
                }
            }
        }

        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];
            xText1.setText("Gy_xValue: " + String.format("%.2f",x));
            yText1.setText("Gy_yValue: " + String.format("%.2f",y));
            zText1.setText("Gy_zValue: " + String.format("%.2f",z));
            Log.d("gyro",String.format("%.2f",z));

            if(last_two_value[1]>last_two_value[0] && last_two_value[1]> z && flag == true){
                gyro_count++;
            }
            last_two_value[0] = last_two_value[1];
            last_two_value[1] = z;

            /*
            if(light_value == 0 && y > 3){
                stepsCount = 0;
            }
            */
        }


        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //x, y, z value
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];
            // constant filter
            final double alpha = 0.8;
            double[] gravity = {0, 0, 0};

            // Isolate the force of gravity with the low-pass filter.
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            // Remove the gravity contribution with the high-pass filter.
            x = event.values[0] - gravity[0];
            y = event.values[1] - gravity[1];
            z = event.values[2] - gravity[2];

            xText.setText("Ac_xValue: " + String.format("%.2f",x));
            yText.setText("Ac_yValue: " + String.format("%.2f",y));
            zText.setText("Ac_zValue: " + String.format("%.2f",z));

            if (!mInitialized) {
                // sensor is used for the first time, initialize the last read values
                mLastX = x;
                mLastY = y;
                mLastZ = z;
                mInitialized = true;
            } else {
                // sensor is already initialized, and we have previously read values.
                // take difference of past and current values and decide which
                // axis acceleration was detected by comparing values

                double deltaX = Math.abs(mLastX - x);
                double deltaY = Math.abs(mLastY - y);
                double deltaZ = Math.abs(mLastZ - z);
                if (deltaX < NOISE)
                    deltaX = (float) 0.0;
                if (deltaY < NOISE)
                    deltaY = (float) 0.0;
                if (deltaZ < NOISE)
                    deltaZ = (float) 0.0;
                mLastX = x;
                mLastY = y;
                mLastZ = z;

                if (deltaX > deltaY) {
                    // Horizontal shake
                    // do something here if you like

                } else if (deltaY > deltaX) {
                    // Vertical shake
                    // do something here if you like

                } else if ((deltaZ > deltaX) && (deltaZ > deltaY)) {
                    // Z shake
                    if(flag == true) {
                        if(gyro_count>0) {
                            gyro_count--;
                            stepsCount = stepsCount + 1;
                            BarData data = new BarData(getXAxisValues(), getDataSet());
                            chart.setData(data);
                            chart.invalidate();
                        }
                    }

                    if (stepsCount > 0) {
                        Steps.setText(String.valueOf(stepsCount));
                    }
                }

            }
        }

    }


    public void gotogyroscope(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        //intent.putExtra("key",String.valueOf(stepsCount));
        startActivity(intent);
    }

    public void dateAndTime(){

    }

    public void startService(View view){
        //Intent intent = new Intent(this,MyServices.class);
        //startService(intent);
    }
}
