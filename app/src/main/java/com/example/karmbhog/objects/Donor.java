package com.example.karmbhog.objects;

public class Donor {
    private String donorName;
    private String donorMobNo;
    private String donorAddress;
    private String donorPaymentRefNumber;

    public Donor() {
    }

    public Donor(String donorName, String donorMobNo, String donorAddress, String donorPaymentRefNumber) {
        this.donorName = donorName;
        this.donorMobNo = donorMobNo;
        this.donorAddress = donorAddress;
        this.donorPaymentRefNumber = donorPaymentRefNumber;
    }

    //getters and setters
    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorMobNo() {
        return donorMobNo;
    }

    public void setDonorMobNo(String donorMobNo) {
        this.donorMobNo = donorMobNo;
    }

    public String getDonorAddress() {
        return donorAddress;
    }

    public void setDonorAddress(String donorAddress) {
        this.donorAddress = donorAddress;
    }

    public String getDonorPaymentRefNumber() {
        return donorPaymentRefNumber;
    }

    public void setDonorPaymentRefNumber(String donorPaymentRefNumber) {
        this.donorPaymentRefNumber = donorPaymentRefNumber;
    }
}
