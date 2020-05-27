package com.example.project.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class Booking {
    @Id
    private int bookingNo;
    private String vehicleRegNumber; // Added - Missing in DCD
    private String pickUpDate;
    private String dropOffDate;
    private String bookingStatus;
    private int customerID;
    private int pickUpLocID;
    private int dropOffLocID;
    private boolean bikeRack;
    private int bedLinen;
    private int childSeat;
    private double totalPrice;

    public Booking() {
    }

    // Kept ID's as part of constructor - //
    // Considering whether we should keep the total price in the constructor -> Since we will most likely be using
    // the constructor to create the object, and then use JAVA to calculate the total price.
    public Booking(int bookingNo, String vehicleRegNumber, String pickUpDate, String dropOffDate, String bookingStatus,
                   int customerID, int pickUpLocID, int dropOffLocID, boolean bikeRack, int bedLinen,
                   int childSeat, double totalPrice) {
        this.bookingNo = bookingNo;                     // CHECK
        this.vehicleRegNumber = vehicleRegNumber;       // CHECK
        this.pickUpDate = pickUpDate;
        this.dropOffDate = dropOffDate;
        this.bookingStatus = bookingStatus;
        this.customerID = customerID;
        this.pickUpLocID = pickUpLocID;
        this.dropOffLocID = dropOffLocID;
        this.bikeRack = bikeRack;
        this.bedLinen = bedLinen;
        this.childSeat = childSeat;
        this.totalPrice = totalPrice;                   // CHECK -> TAKE FROM CONSTRUCTOR?
    }

    public int getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(int bookingNo) {
        this.bookingNo = bookingNo;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(String dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getPickUpLocID() {
        return pickUpLocID;
    }

    public void setPickUpLocID(int pickUpLocID) {
        this.pickUpLocID = pickUpLocID;
    }

    public int getDropOffLocID() {
        return dropOffLocID;
    }

    public void setDropOffLocID(int dropOffLocID) {
        this.dropOffLocID = dropOffLocID;
    }

    public boolean isBikeRack() {
        return bikeRack;
    }

    public void setBikeRack(boolean bikeRack) {
        this.bikeRack = bikeRack;
    }

    public int getBedLinen() {
        return bedLinen;
    }

    public void setBedLinen(int bedLinen) {
        this.bedLinen = bedLinen;
    }

    public int getChildSeat() {
        return childSeat;
    }

    public void setChildSeat(int childSeat) {
        this.childSeat = childSeat;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
