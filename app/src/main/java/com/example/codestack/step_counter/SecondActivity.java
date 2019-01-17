package com.example.codestack.step_counter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity implements OnItemSelectedListener {
    private EditText name;
    private EditText age;
    private EditText height;
    private EditText weight;
    //private EditText gender;

    Spinner gender;
    TextView showgender;
    private String[] state = { "Male", "Female"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);


        //showgender = (TextView) findViewById(R.id.showgender);
        gender = (Spinner) findViewById(R.id.gender);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, state);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter_state);
        gender.setOnItemSelectedListener(this);



        name = (EditText)findViewById(R.id.name);
        age = (EditText)findViewById(R.id.age);
        height = (EditText)findViewById(R.id.height);
        weight = (EditText)findViewById(R.id.weight);
        //gender = (EditText)findViewById(R.id.gender);


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

        //on click button
    public void goback(View view) {
        //got to main activity and send data
        String text1 = name.getText().toString();
        int text2 = Integer.parseInt(age.getText().toString());
        int text3 = Integer.parseInt(height.getText().toString());
        int text4 = Integer.parseInt(weight.getText().toString());
        //String text5 = gender.getText().toString();


        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name",text1 );
        intent.putExtra("age", text2);
        intent.putExtra("height", text3);
        intent.putExtra("weight", text4);
        //intent.putExtra("gender", text5);
        startActivity(intent);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender.setSelection(position);
        String selState = (String) gender.getSelectedItem();
        //showgender.setText("Your Gender:" + selState);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
