package com.example.cofigure;

import static com.example.cofigure.R.id.recycler_view_via_district;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
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
import java.util.List;

public class SlotViaDistrictActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static final String STATE_DETAIL_REQUEST_URL = "https://cdn-api.co-vin.in/api/v2/admin/location/states";
    private static final String BASE_DISTRICT_DETAIL_REQUEST_URL = "https://cdn-api.co-vin.in/api/v2/admin/location/districts/";
    private static final String BASE_VACCINE_SLOT_URL_VIA_DISTRICT_ID = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id=";
    String DATE_SELECTED;

    private ArrayList<StateListDetail> state_list;
    private ArrayList<DistrictListDetail> district_list;
//    private ArrayList<String> state_name_list;
    private StateDetailAdapter madapter;
    private DistrictDetailAdapter m2adapter;
    String STATE_ID;
    String DISTRICT_ID;
    private ArrayList<VaccineDetailViaPin> vaccine_list;
    private RecyclerView recyclerView;
    private Spinner stateSpinner;
    private Spinner districtSpinner;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_via_district);

        state_list = new ArrayList<>();
//        state_name_list = new ArrayList<>();
        district_list = new ArrayList<>();
        vaccine_list = new ArrayList<VaccineDetailViaPin>();
        stateSpinner = (Spinner) findViewById(R.id.spinner_for_state);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_via_district);

        StringRequest request = new StringRequest(Request.Method.GET, STATE_DETAIL_REQUEST_URL, new Response.Listener<String>() {
            private Object ArrayList;

            @Override
            public void onResponse(String response) {

                try{
                    Log.e("TAG", response);

                    JSONObject baseJsonResponse = new JSONObject(response);
                    JSONArray stateArray = baseJsonResponse.getJSONArray("states");
                    for(int i=0;i<stateArray.length();i++){
                        JSONObject currentStateDetail = stateArray.getJSONObject(i);
                        String stateName = currentStateDetail.getString("state_name");
                        int stateId = currentStateDetail.getInt("state_id");

                        Log.e("TAG",stateName);

//                        state_name_list.add(stateName);
                        StateListDetail s = new StateListDetail(stateName,stateId);
                        state_list.add(s);
                        Log.e("TAG",stateName+stateId);
                    }

                    madapter = new StateDetailAdapter(SlotViaDistrictActivity.this,state_list);
                    stateSpinner.setAdapter(madapter);

//                    ArrayAdapter<String> adapter = new ArrayAdapter(SlotViaDistrictActivity.this, android.R.layout.simple_spinner_item,state_name_list);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    stateSpinner.setAdapter(adapter);

                    stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                            if(adapterView.getItemAtPosition(position).equals("Select State Name")){

                            }else{
                                StateListDetail clickedItem = (StateListDetail)adapterView.getItemAtPosition(position);
                                String STATE_NAME = clickedItem.getStateName();
                                STATE_ID = String.valueOf(clickedItem.getStateId());
                                Log.e("TAG",STATE_NAME+STATE_ID);

                                fetchDistrictId(STATE_ID);
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error while parsing",Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(SlotViaDistrictActivity.this);
        queue.add(request);
        Log.e("TAG","Completed");

    }

    public void fetchDistrictId(String state_id) {

        String STATE_ID1=state_id;
        String DISTRICT_URL = BASE_DISTRICT_DETAIL_REQUEST_URL + STATE_ID1;

        districtSpinner = (Spinner) findViewById(R.id.spinner_for_district);

        Log.e("TAG", DISTRICT_URL);

        StringRequest request2 = new StringRequest(Request.Method.GET, DISTRICT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                district_list.clear();

                try{
                    Log.e("TAG", response);

                    JSONObject baseJsonResponse = new JSONObject(response);
                    JSONArray districtArray = baseJsonResponse.getJSONArray("districts");
                    for(int i=0;i<districtArray.length();i++){
                        JSONObject currentDistrictDetail = districtArray.getJSONObject(i);
                        String districtName = currentDistrictDetail.getString("district_name");
                        int districtId = currentDistrictDetail.getInt("district_id");

                        Log.e("TAG", districtName+districtId);

                        DistrictListDetail d = new DistrictListDetail(districtName,districtId);
                        district_list.add(d);

                    }
                    m2adapter = new DistrictDetailAdapter(SlotViaDistrictActivity.this,district_list);
                    districtSpinner.setAdapter(m2adapter);

                    districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            if(adapterView.getItemAtPosition(position).equals("Select District Name")){

                            }else{
                                DistrictListDetail clickedItem = (DistrictListDetail)adapterView.getItemAtPosition(position);
                                String DISTRICT_NAME = clickedItem.getDistrictName();
                                DISTRICT_ID = String.valueOf(clickedItem.getDistrictId());
                                Log.e("TAG",DISTRICT_NAME+DISTRICT_ID);

                                SelectDate();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }catch(JSONException e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error while parsing",Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue2 = Volley.newRequestQueue(SlotViaDistrictActivity.this);
        queue2.add(request2);
    }

    public void SelectDate(){

        searchButton = (Button) findViewById(R.id.search_by_district);
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
        fetchVaccineSlot(DATE_SELECTED);
    }

    public void fetchVaccineSlot(String dateSelected){

        String DateSelected = dateSelected;
        String SLOT_LIST_URL = BASE_VACCINE_SLOT_URL_VIA_DISTRICT_ID + DISTRICT_ID + "&date=" + DateSelected;
        Log.e("TAG",SLOT_LIST_URL);

        StringRequest request3 = new StringRequest(Request.Method.GET, SLOT_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject baseJsonResponse = new JSONObject(response);
                    JSONArray sessionArray = baseJsonResponse.getJSONArray("sessions");
                    for (int i = 0; i < sessionArray.length(); i++) {
                        JSONObject currentVaccineSlot = sessionArray.getJSONObject(i);
                        String blockName = currentVaccineSlot.getString("block_name");
                        String centreNAme = currentVaccineSlot.getString("name");
                        String centreAddress = currentVaccineSlot.getString("address");
                        int dose1Availability = currentVaccineSlot.getInt("available_capacity_dose1");
                        int dose2Availability = currentVaccineSlot.getInt("available_capacity_dose2");
                        int fee = currentVaccineSlot.getInt("fee");
                        String vaccineName = currentVaccineSlot.getString("vaccine");
                        Log.e("TAG", "Hello " + centreNAme + centreAddress + dose1Availability + dose2Availability + fee + vaccineName);
                        VaccineDetailViaPin vaccineSlot = new VaccineDetailViaPin(blockName, centreNAme, centreAddress, dose1Availability, dose2Availability, fee, vaccineName);
                        vaccine_list.add(vaccineSlot);
                    }

                    stateSpinner.setVisibility(View.GONE);
                    districtSpinner.setVisibility(View.GONE);
                    searchButton.setVisibility(View.GONE);


                    VaccineListViaPinAdapter adapter = new VaccineListViaPinAdapter(getApplicationContext(), vaccine_list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error while parsing",Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(SlotViaDistrictActivity.this);
        queue.add(request3);
    }
}