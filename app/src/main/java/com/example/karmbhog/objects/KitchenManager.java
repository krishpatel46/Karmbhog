package com.example.karmbhog.objects;

public class KitchenManager {
    private String managerName;
    private String managerEmail;
    private String managerCity;
    private String managerPassword;

    public KitchenManager(String name, String city, String email, String pwd) {
        managerName = name;
        managerEmail = email;
        managerCity = city;
        managerPassword = pwd;
    }

    //getters and setters
    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getManagerCity() {
        return managerCity;
    }

    public void setManagerCity(String managerCity) {
        this.managerCity = managerCity;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }
}
