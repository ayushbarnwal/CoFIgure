package com.example.cofigure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView nCoronaCases = (TextView) findViewById(R.id.corona_case_detail);
        nCoronaCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(MainActivity.this,CoronaCases.class);
                startActivity(i1);
            }
        });

        TextView vaccineSlot = (TextView) findViewById(R.id.vaccine_slot_availability);
        vaccineSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(MainActivity.this,VaccineSlot.class);
                startActivity(i2);
            }
        });
    }
}