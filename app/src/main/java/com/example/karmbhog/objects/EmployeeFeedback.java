package com.example.karmbhog.objects;

public class EmployeeFeedback {
    private String workerName;
    private String workerMobNo;
    private String rating;

    //constructors
    public EmployeeFeedback() {
    }

    public EmployeeFeedback(String workerName, String workerMobNo, String rating) {
        this.workerName = workerName;
        this.workerMobNo = workerMobNo;
        this.rating = rating;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
