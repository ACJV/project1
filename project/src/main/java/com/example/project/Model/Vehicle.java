package com.example.project.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vehicle {
    @Id
    private String regNumber;
    private int categoryId;
    private int yearStmp;
    private int odometer;
    private String transmission;
    private String fuelType;
    private String descriptionX;
    private boolean operational;
    private String oComment;

    public Vehicle() {
    }

    // Constructor for everything
    public Vehicle(String regNumber, int categoryID, int yearStmp,
                   int odometer, String transmission, String fuelType, String descriptionX,
                   boolean operational, String oComment) {
        this.regNumber = regNumber;
        this.categoryId = categoryID;
        this.yearStmp = yearStmp;
        this.odometer = odometer;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.descriptionX = descriptionX;
        this.operational = operational;
        this.oComment = oComment;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public String getDescriptionX() {
        return descriptionX;
    }

    public void setDescriptionX(String descriptionX) {
        this.descriptionX = descriptionX;
    }

    public boolean getOperational() {
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
