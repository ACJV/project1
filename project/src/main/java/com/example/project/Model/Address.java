package com.example.project.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {
    @Id
    private int addressID;
    private String address;
    private String zip;
    private String city;
    private String country;
    private int distance;

    public Address() {
    }

    public Address(String address, String zip, String city, String country, int distance) {
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.country = country;
        this.distance = distance;
    }

    public Address(int addressID, String address, String zip, String city, String country, int distance) {
        this.addressID = addressID;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.country = country;
        this.distance = distance;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressId) {
        this.addressID = addressID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
