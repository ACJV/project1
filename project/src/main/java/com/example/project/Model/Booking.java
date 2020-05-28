package com.example.project.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Booking {
    @Id
    private int bookingNo;
    private String regNumber;
    private String pickUpDate;
    private String dropOffDate;
    private String bookingStatus;
    private int customerId;
    private int pickUpId;
    private int dropOffId;
    private boolean bikeRack;
    private int bedLinen;
    private int childSeat;
    private double totalPrice;

    public Booking() {
    }

    public Booking(int bookingNo, String vehicleRegNumber, String pickUpDate, String dropOffDate, String bookingStatus, int customerID, int pickUpId, int dropOffId, boolean bikeRack, int bedLinen, int childSeat, double totalPrice) {
        this.bookingNo = bookingNo;
        this.regNumber = vehicleRegNumber;
        this.pickUpDate = pickUpDate;
        this.dropOffDate = dropOffDate;
        this.bookingStatus = bookingStatus;
        this.customerId = customerID;
        this.pickUpId = pickUpId;
        this.dropOffId = dropOffId;
        this.bikeRack = bikeRack;
        this.bedLinen = bedLinen;
        this.childSeat = childSeat;
        this.totalPrice = totalPrice;
    }

    public int getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(int bookingNo) {
        this.bookingNo = bookingNo;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPickUpId() {
        return pickUpId;
    }

    public void setPickUpId(int pickUpId) {
        this.pickUpId = pickUpId;
    }

    public int getDropOffId() {
        return dropOffId;
    }

    public void setDropOffId(int dropOffId) {
        this.dropOffId = dropOffId;
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
