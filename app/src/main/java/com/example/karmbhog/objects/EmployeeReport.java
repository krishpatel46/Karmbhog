package com.example.karmbhog.objects;

public class EmployeeReport {
    private String workerName;
    private String workerMobNo;
    private String daysOfWork;
    private String avgWorkPerDay;
    private String extraHours;

    //constructors
    public EmployeeReport() {
    }

    public EmployeeReport(String workerName, String workerMobNo, String daysOfWork, String avgWorkPerDay, String extraHours) {
        this.workerName = workerName;
        this.workerMobNo = workerMobNo;
        this.daysOfWork = daysOfWork;
        this.avgWorkPerDay = avgWorkPerDay;
        this.extraHours = extraHours;
    }

    //getters and setters
    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerMobNo() {
        return workerMobNo;
    }

    public void setWorkerMobNo(String workerMobNo) {
        this.workerMobNo = workerMobNo;
    }

    public String getDaysOfWork() {
        return daysOfWork;
    }

    public void setDaysOfWork(String daysOfWork) {
        this.daysOfWork = daysOfWork;
    }

    public String getAvgWorkPerDay() {
        return avgWorkPerDay;
    }

    public void setAvgWorkPerDay(String avgWorkPerDay) {
        this.avgWorkPerDay = avgWorkPerDay;
    }

    public String getExtraHours() {
        return extraHours;
    }

    public void setExtraHours(String extraHours) {
        this.extraHours = extraHours;
    }

}
