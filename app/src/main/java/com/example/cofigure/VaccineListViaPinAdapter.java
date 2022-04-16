package com.example.cofigure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class VaccineListViaPinAdapter extends RecyclerView.Adapter<VaccineListViaPinAdapter.VaccineViewHolder> {

    private LayoutInflater inflater;
    private List<VaccineDetailViaPin> vaccine_slot_list;

    public VaccineListViaPinAdapter(Context mcontext, List<VaccineDetailViaPin> vaccine_slot_list) {
        this.inflater = LayoutInflater.from(mcontext);
        this.vaccine_slot_list = vaccine_slot_list;
    }

    @NonNull
    @Override
    public VaccineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.vaccine_list_via_pin,parent,false);
        return new VaccineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccineViewHolder holder, int position) {

        holder.blockName.setText(vaccine_slot_list.get(position).getBlockName());
        holder.centerName.setText(vaccine_slot_list.get(position).getcenterName());
        holder.centerAddress.setText(vaccine_slot_list.get(position).getcentreAddress());
        holder.vaccineName.setText(vaccine_slot_list.get(position).getVaccineName());
        holder.vaccineFee.setText(String.valueOf(vaccine_slot_list.get(position).getfee()));
        holder.dose1Availability.setText(String.valueOf(vaccine_slot_list.get(position).getdose1Availability()));
        holder.dose2Availability.setText(String.valueOf(vaccine_slot_list.get(position).getdose2Availability()));

    }


    @Override
    public int getItemCount() {
        return vaccine_slot_list.size();
    }

    public static class VaccineViewHolder extends RecyclerView.ViewHolder{

        TextView blockName;
        TextView centerName;
        TextView centerAddress;
        TextView vaccineName;
        TextView vaccineFee;
        TextView dose1Availability;
        TextView dose2Availability;

        public VaccineViewHolder(@NonNull View itemView) {
            super(itemView);

            blockName = itemView.findViewById(R.id.block_name);
            centerName = itemView.findViewById(R.id.centre_name);
            centerAddress = itemView.findViewById(R.id.centre_address);
            vaccineName = itemView.findViewById(R.id.vaccine_name);
            vaccineFee = itemView.findViewById(R.id.vaccine_charge);
            dose1Availability = itemView.findViewById(R.id.dose1_availability);
            dose2Availability = itemView.findViewById(R.id.dose2_availability);
        }
    }


}
