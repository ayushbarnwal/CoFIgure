package com.example.cofigure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SlotViaPinActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private RecyclerView recyclerview;
    private EditText editText;
    private Button searchButton;
    private ArrayList<VaccineDetailViaPin> vaccine_list;
    private static String VACCINE_SLOT_REQUEST_URL_VIA_PIN_CODE = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_via_pin);

        editText = (EditText) findViewById(R.id.pin_code);

        recyclerview = (RecyclerView) findViewById(R.id.vaccine_List);

        vaccine_list = new ArrayList<VaccineDetailViaPin>();

        searchButton = (Button) findViewById(R.id.search_pin_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment dp = new PickDate();
                dp.show(getSupportFragmentManager(), "pick a date");
            }
        });
    }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

            Calendar k = Calendar.getInstance();
            k.set(Calendar.YEAR, year);
            k.set(Calendar.MONTH, month);
            k.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateFormat.setTimeZone(k.getTimeZone());
            String DATE_SELECTED = dateFormat.format(k.getTime());
            Log.e("TAG", DATE_SELECTED);
            setup(DATE_SELECTED);

        }

        private void setup(String date_selected) {

            String PIN_CODE = editText.getText().toString();
            String DATE_SELECTED = date_selected;
            String URL = VACCINE_SLOT_REQUEST_URL_VIA_PIN_CODE + PIN_CODE + "&date=" + DATE_SELECTED;

            Log.e("TAG", URL);

            StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try{
                        Log.e("TAG", response);

                        JSONObject baseJsonResponse = new JSONObject(response);
                        JSONArray sessionArray = baseJsonResponse.getJSONArray("sessions");
                        for(int i=0;i<sessionArray.length();i++){
                            JSONObject currentVaccineSlot = sessionArray.getJSONObject(i);
                            String centreNAme = currentVaccineSlot.getString("name");
                            String centreAddress = currentVaccineSlot.getString("address");
                            int dose1Availability = currentVaccineSlot.getInt("available_capacity_dose1");
                            int dose2Availability = currentVaccineSlot.getInt("available_capacity_dose2");
                            int fee = currentVaccineSlot.getInt("fee");
                            String vaccineName = currentVaccineSlot.getString("vaccine");
                            Log.e("TAG","Hello " + centreNAme+centreAddress+dose1Availability+dose2Availability+fee+vaccineName);

                            VaccineDetailViaPin vaccineSlot = new VaccineDetailViaPin(centreNAme,centreAddress,dose1Availability,dose2Availability,fee,vaccineName);
                            vaccine_list.add(vaccineSlot);

                        }

                        editText.setVisibility(View.GONE);
                        searchButton.setVisibility(View.GONE);
                        VaccineListViaPinAdapter adapter = new VaccineListViaPinAdapter(getApplicationContext(),vaccine_list);
                        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }catch(JSONException e) {
                        e.printStackTrace();
                    }
                }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"error while parsing",Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(SlotViaPinActivity.this);
            queue.add(request);
    }

}