package com.example.cofigure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class VaccineSlot extends AppCompatActivity {

    int stateCode;
    String DATE_SELECTED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_slot);

        Button button1 = (Button)findViewById(R.id.search_via_pin);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(VaccineSlot.this,SlotViaPinActivity.class);
                startActivity(i1);
            }
        });

        Button button2 = (Button)findViewById(R.id.search_via_district);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(VaccineSlot.this,SlotViaDistrictActivity.class);
                startActivity(i2);
            }
        });





    }
}