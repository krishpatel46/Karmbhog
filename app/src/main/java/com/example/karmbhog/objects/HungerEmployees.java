package com.example.karmbhog.objects;

public class HungerEmployees {
    private String userName;
    private String employeeName;
    private String employeeMobNo;
    private Integer employeeAge;
    private String employeeAddress;
    private String employeeAssignedCompany;
    private String employeeWorkName;

    public HungerEmployees() {
    }

    public HungerEmployees(String userName, String employeeName, String employeeMobNo, Integer employeeAge, String employeeAddress, String employeeAssignedCompany, String employeeWorkName) {
        this.userName = userName;
        this.employeeName = employeeName;
        this.employeeMobNo = employeeMobNo;
        this.employeeAge = employeeAge;
        this.employeeAddress = employeeAddress;
        this.employeeAssignedCompany = employeeAssignedCompany;
        this.employeeWorkName = employeeWorkName;
    }

    //getters and setters

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeMobNo() {
        return employeeMobNo;
    }

    public void setEmployeeMobNo(String employeeMobNo) {
        this.employeeMobNo = employeeMobNo;
    }

    public Integer getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(Integer employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeAssignedCompany() {
        return employeeAssignedCompany;
    }

    public void setEmployeeAssignedCompany(String employeeAssignedCompany) {
        this.employeeAssignedCompany = employeeAssignedCompany;
    }

    public String getEmployeeWorkName() {
        return employeeWorkName;
    }

    public void setEmployeeWorkName(String employeeWorkName) {
        this.employeeWorkName = employeeWorkName;
    }
}
