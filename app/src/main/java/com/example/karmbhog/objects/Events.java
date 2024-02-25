package com.example.karmbhog.objects;

import java.time.LocalDate;

public class Events {
    private String eventName;
    private String organizerMobNo;
    private String eventAddress;
    private String eventCity;
    private long eventDate;

    //constructors
    public Events() {
    }

    public Events(String eventName, String organizerMobNo, String eventAddress, String eventCity, long eventDate) {
        this.eventName = eventName;
        this.organizerMobNo = organizerMobNo;
        this.eventAddress = eventAddress;
        this.eventCity = eventCity;
        this.eventDate = eventDate;
    }


    //getters and setters
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getOrganizerMobNo() {
        return organizerMobNo;
    }

    public void setOrganizerMobNo(String organizerMobNo) {
        this.organizerMobNo = organizerMobNo;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventCity() {
        return eventCity;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public long getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }
}
