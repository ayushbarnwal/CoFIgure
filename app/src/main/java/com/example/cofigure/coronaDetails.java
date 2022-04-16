package com.example.cofigure;

public class coronaDetails {

    private int mconfirmedCases;
    private int mrecovered;
    private int mdeath;
    private String mlastUpdated;
    private String mstateName;

    public coronaDetails(String stateName, String lastUpdated, int confirmedCases,int recovered,int death){
        mconfirmedCases=confirmedCases;
        mstateName=stateName;
        mlastUpdated=lastUpdated;
        mdeath=death;
        mrecovered=recovered;
    }

    public int getConfirmedCases(){ return mconfirmedCases; }
    public int getRecoveredCases(){
        return mrecovered;
    }
    public int getDeathCases(){
        return mdeath;
    }
    public String getStateName(){
        return mstateName;
    }
    public String getLastUpdated(){
        return mlastUpdated;
    }

}
