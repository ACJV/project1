package com.example.project.Model;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Customer {
    @Id
    private int customerID; // NOT IN CONSTRUCTOR
    private String fullName;
    private String driverLicNo;
    private String dob;
    private String phoneNo;
    private String email;
    private int addressID; // NOT IN CONSTRUCTOR




    /*
    public Customer(int customerID, String name, String driverLicNo, String dob, String phoneNo, String email) {
        this.customerID = customerID;
        this.name = name;
        this.driverLicNo = driverLicNo;
        this.dob = dob;
        this.phoneNo = phoneNo;
        this.email = email;
        //this.addressID = 500;
        //this.addressID = addressID;
    }*/


    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDriverLicNo() {
        return driverLicNo;
    }

    public void setDriverLicNo(String driverLicNo) {
        this.driverLicNo = driverLicNo;
    }


    public String getDob() {
        return dob;
    }

    public void setDob(String dob) throws ParseException {
        //String d = dob;
        //Date date = new SimpleDateFormat("yyyy-MM-dd").parse(d);
        //System.out.println(dob);
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
