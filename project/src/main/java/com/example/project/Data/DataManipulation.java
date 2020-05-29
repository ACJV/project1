package com.example.project.Data;

import com.example.project.Model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DataManipulation {
    @Autowired
    JdbcTemplate template;

//----------------------------------------------------------------------------------------------------------------------
    // @Justé
//----------------------------------------------------------------------------------------------------------------------

    // Will return today's date as a String in a format of YYYY-MM-DD
    public static String getTodaysDate() {
        Date dateToday = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(dateToday);
        return today;
    }

    // Calculates the total price of a Booking based on the status of the booking:
    // Confirmed - status used rigth after creating a booking, thus the price is calculated based on the availability during the booked days
    // Cancelled -

    /*
    public static double calculateTotalPriceConfirmed(Booking b) {

    }

    public static double calculateTotalPriceCancelled(Booking b) {

    }

    public static double calculateTotalPriceFinished(Booking b, @Param("isLowTank") Boolean isLowTank, @Param("kmOverOdometer") int kmOverOdometer) {

    }
     */


}
