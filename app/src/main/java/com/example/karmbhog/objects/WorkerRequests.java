package com.example.karmbhog.objects;

public class WorkerRequests {
    private String workManagerName;
    private String workManagerMobNo;
    private String workAddress;
    private String workName;
    private Integer workersNeeded;
    private Integer workersAvailable;


    //constructors
    public WorkerRequests() {
    }

    public WorkerRequests(String workManagerName, String workManagerMobNo, String workAddress, String workName, Integer workersNeeded) {
        this.workManagerName = workManagerName;
        this.workManagerMobNo = workManagerMobNo;
        this.workAddress = workAddress;
        this.workName = workName;
        this.workersNeeded = workersNeeded;
    }

    public WorkerRequests(String workManagerName, String workManagerMobNo, String workAddress, String workName, Integer workersNeeded, Integer workersAvailable) {
        this.workManagerName = workManagerName;
        this.workManagerMobNo = workManagerMobNo;
        this.workAddress = workAddress;
        this.workName = workName;
        this.workersNeeded = workersNeeded;
        this.workersAvailable = workersAvailable;
    }


    //getters and setters
    public String getWorkManagerName() {
        return workManagerName;
    }

    public void setWorkManagerName(String workManagerName) {
        this.workManagerName = workManagerName;
    }

    public String getWorkManagerMobNo() {
        return workManagerMobNo;
    }

    public void setWorkManagerMobNo(String workManagerMobNo) {
        this.workManagerMobNo = workManagerMobNo;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public Integer getWorkersNeeded() {
        return workersNeeded;
    }

    public void setWorkersNeeded(Integer workersNeeded) {
        this.workersNeeded = workersNeeded;
    }

    public Integer getWorkersAvailable() {
        return workersAvailable;
    }

    public void setWorkersAvailable(Integer workersAvailable) {
        this.workersAvailable = workersAvailable;
    }
}
