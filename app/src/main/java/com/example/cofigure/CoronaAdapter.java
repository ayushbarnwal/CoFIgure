package com.example.cofigure;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CoronaAdapter extends ArrayAdapter<coronaDetails> {

    public CoronaAdapter(Activity context,ArrayList<coronaDetails> covidList){

        super(context,0,covidList);
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.corona_detail_list, parent, false);
        }

        coronaDetails covidDetails = getItem(position);

        TextView stateName = (TextView)listItemView.findViewById(R.id.state_name);
        stateName.setText(covidDetails.getStateName());

        TextView lastUpdated = (TextView)listItemView.findViewById(R.id.last_updated_value);
        lastUpdated.setText(covidDetails.getLastUpdated());

        TextView confirmedCases = (TextView)listItemView.findViewById(R.id.confirmed_cases_value);
        confirmedCases.setText(String.valueOf(covidDetails.getConfirmedCases()));

        TextView dischargedCases = (TextView)listItemView.findViewById(R.id.discharged_cases_value);
        dischargedCases.setText(String.valueOf(covidDetails.getRecoveredCases()));

        TextView deathCase = (TextView)listItemView.findViewById(R.id.death_cases_value);
        deathCase.setText(String.valueOf(covidDetails.getDeathCases()));

        return listItemView;

    }
}
