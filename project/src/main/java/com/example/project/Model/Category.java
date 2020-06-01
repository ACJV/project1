package com.example.project.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Category {
    @Id
    int catId;
    double catPrice;
    String catName;
    String catDescription;
    String modelName;
    String brand;

    public Category() {
    }

    public Category(int catId, double catPrice, String catName, String catDescription, String modelName, String brand) {
        this.catId = catId;
        this.catPrice = catPrice;
        this.catName = catName;
        this.catDescription = catDescription;
        this.modelName = modelName;
        this.brand = brand;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public double getCatPrice() {
        return catPrice;
    }

    public void setCatPrice(double catPrice) {
        this.catPrice = catPrice;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDescription() {
        return catDescription;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
