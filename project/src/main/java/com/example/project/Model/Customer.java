package com.example.project.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Customer {
    @Id
    private int customerID; // NOT IN CONSTRUCTOR
    private String name;
    private String driverLicNo;
    private Date dob;
    private String phoneNo;
    private String email;
    private int addressID; // NOT IN CONSTRUCTOR

    public Customer() {
    }

    // Constructor for all except for ID's - Since at first creation, we don't have the SQL ID
    public Customer(String name, String driverLicNo, Date dob, String phoneNo, String email) {
        this.name = name;
        this.driverLicNo = driverLicNo;
        this.dob = dob;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriverLicNo() {
        return driverLicNo;
    }

    public void setDriverLicNo(String driverLicNo) {
        this.driverLicNo = driverLicNo;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }
}
