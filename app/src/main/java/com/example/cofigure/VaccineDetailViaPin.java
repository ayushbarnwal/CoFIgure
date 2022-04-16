package com.example.cofigure;

public class VaccineDetailViaPin {
    private String mcenterName;
    private String mcentreAddress;
    private int mdose1Availability;
    private int mdose2Availability;
    private int mfee;
    private String mVaccineName;
    private String mBlockName;

    public VaccineDetailViaPin(String centerName,String centreAddress,int dose1Availability,int dose2Availability,int fee,String VaccineName){
        mcenterName=centerName;
        mcentreAddress=centreAddress;
        mdose1Availability=dose1Availability;
        mdose2Availability=dose2Availability;
        mfee=fee;
        mVaccineName = VaccineName;
    }

    public VaccineDetailViaPin(String blockName,String centerName,String centreAddress,int dose1Availability,int dose2Availability,int fee,String VaccineName){
        mBlockName = blockName;
        mcenterName=centerName;
        mcentreAddress=centreAddress;
        mdose1Availability=dose1Availability;
        mdose2Availability=dose2Availability;
        mfee=fee;
        mVaccineName = VaccineName;
    }

    public String getcenterName(){return mcenterName;}
    public String getcentreAddress(){return mcentreAddress;}
    public int getdose1Availability(){return mdose1Availability;}
    public int getdose2Availability(){return mdose2Availability;}
    public int getfee(){return mfee;}
    public String getVaccineName(){return mVaccineName;}
    public String getBlockName(){return mBlockName;}

}
