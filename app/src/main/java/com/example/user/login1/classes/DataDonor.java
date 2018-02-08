package com.example.user.login1.classes;

/**
 * Created by User on 1/1/2018.
 */

public class DataDonor {
    private String user;
    private String name;
    private String phone;
    private String location;
    private String clocation;
    private String distance;

    public DataDonor(String user,String name,String phone, String location, String clocation, String distance) {
        this.user = user;
        this.phone = phone;
        this.name = name;
        this.location = location;
        this.clocation = clocation;
        this.distance = distance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getClocation() {
        return clocation;
    }

    public void setClocation(String clocation) {
        this.clocation = clocation;
    }

    public String getDistance() {

        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}

