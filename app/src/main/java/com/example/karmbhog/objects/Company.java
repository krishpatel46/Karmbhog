package com.example.karmbhog.objects;

public class Company {
    private String companyName;
    private String companyEmail;
    private String companyCity;
    private String companyPassword;

    public Company() {
    }

    public Company(String name, String city, String email, String pwd) {
        companyName = name;
        companyEmail = email;
        companyCity = city;
        companyPassword = pwd;
    }

    //getters and setters

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyPassword() {
        return companyPassword;
    }

    public void setCompanyPassword(String companyPassword) {
        this.companyPassword = companyPassword;
    }
}
