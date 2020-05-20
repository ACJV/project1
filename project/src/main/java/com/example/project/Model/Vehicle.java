package com.example.project.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vehicle {
    @Id
    private String regNumber;
    private int categoryID;                                        
    private String model;
    private String brand;
    private String category;
    private int year;
    private int odometer;
    private String transmission;
    private String fuelType;
    private int price;
    private String description;
    private boolean operational;
    private String oComment;

    public Vehicle() {
    }

    // Constructor for everything
    public Vehicle(String regNumber, int categoryID, String model, String brand, String category, int year,
                   int odometer, String transmission, String fuelType, int price, String description,
                   boolean operational, String oComment) {
        this.regNumber = regNumber;
        this.categoryID = categoryID;
        this.model = model;
        this.brand = brand;
        this.category = category;
        this.year = year;
        this.odometer = odometer;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.price = price;
        this.description = description;
        this.operational = operational;
        this.oComment = oComment;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
