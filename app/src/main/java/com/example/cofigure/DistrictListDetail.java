package com.example.cofigure;

public class DistrictListDetail {

    private String mDistrictName;
    private int mDistrictId;


    public DistrictListDetail(String districtName,int districtId){
        mDistrictName = districtName;
        mDistrictId = districtId;
    }

    public String getDistrictName(){
        return mDistrictName;
    }

    public int getDistrictId(){
        return mDistrictId;
    }

}
