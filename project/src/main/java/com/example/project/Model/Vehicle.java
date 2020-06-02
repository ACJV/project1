package com.example.project.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vehicle {
    @Id
    private String regNumber;
    private int catId;
    private int yearStmp;
    private int odometer;
    private String transmission;
    private String fuelType;
    //private String descriptionX;
    private boolean operational;
    private String oComment;

    public Vehicle() {
    }

    public Vehicle(String regNumber, int catId, int yearStmp, int odometer, String transmission, String fuelType, boolean operational, String oComment) {
        this.regNumber = regNumber;
        this.catId = catId;
        this.yearStmp = yearStmp;
        this.odometer = odometer;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.operational = operational;
        this.oComment = oComment;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getYearStmp() {
        return yearStmp;
    }

    public void setYearStmp(int yearStmp) {
        this.yearStmp = yearStmp;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public boolean isOperational() {
        return operational;
    }

    public void setOperational(boolean operational) {
        this.operational = operational;
    }

    public String getoComment() {
        return oComment;
    }

    public void setoComment(String oComment) {
        this.oComment = oComment;
    }
}
