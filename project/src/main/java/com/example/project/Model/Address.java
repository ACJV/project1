package com.example.project.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {
    @Id
    private int addressId;
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

    public Address(int addressId, String address, String zip, String city, String country, int distance) {
        this.addressId = addressId;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.country = country;
        this.distance = distance;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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
