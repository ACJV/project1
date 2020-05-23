package com.example.project.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vehicle {
    @Id
    private String regNumber;
    private int categoryID;
    private int year_stmp;
    private int odometer;
    private String transmission;
    private String fuelType;
    private String description;
    private boolean availability;
    private String aComment;

    public Vehicle()
    {
    }

    // Constructor for everything
    public Vehicle(String regNumber, int categoryID, String model, String brand, String category, int year,
                   int odometer, String transmission, String fuelType, int price, String description,
                   boolean operational, String oComment) {
        this.regNumber = regNumber;
        this.categoryID = categoryID;
        this.year_stmp = year_stmp;
        this.odometer = odometer;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.description = description;
        this.availability = availability;
        this.aComment = aComment;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getYear_stmp() {
        return year_stmp;
    }

    public void setYear_stmp(int year_stmp) {
        this.year_stmp = year_stmp;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setavailability(boolean availability) {
        this.availability = availability;
    }

    public String getaComment() {
        return aComment;
    }

    public void setaComment(String aComment) {
        this.aComment = aComment;
    }
}
