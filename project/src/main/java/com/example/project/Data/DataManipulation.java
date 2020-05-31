package com.example.project.Data;

import com.example.project.Model.Booking;
import com.example.project.Repository.AvailabilityRepository;
import com.example.project.Service.AddressService;
import com.example.project.Service.AvailabilityService;
import com.example.project.Service.VehicleService;
import com.sun.jndi.cosnaming.IiopUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DataManipulation {
    @Autowired
    JdbcTemplate template;
    @Autowired
    AvailabilityService availabilityService;
    @Autowired
    AddressService addressService;
    @Autowired
    VehicleService vehicleService;

//----------------------------------------------------------------------------------------------------------------------
    // @Justé
//----------------------------------------------------------------------------------------------------------------------

    // Will return today's date as a String in a format of YYYY-MM-DD
    public String getTodaysDate() {
        Date dateToday = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(dateToday);
        return today;
    }

    // Will return today's year as a String in a format of YYYY
    public String getTodaysYear() {
        Date dateToday = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        String todayYear = dateFormat.format(dateToday);
        return todayYear;
    }

    // Will return a list of all dates in between the specified ones
    // Strings for startDate & endDate has to be in the format of YYYY-MM-DD
    public List<String> getDatesBetween(String startDate, String endDate) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, dateFormat);
        LocalDate end = LocalDate.parse(endDate, dateFormat);

        List<String> daysBetween = new ArrayList<>();
        System.out.println(daysBetween);

        while (!start.isAfter(end)) {
            daysBetween.add(start.toString());
            start = start.plusDays(1);
        }
        return daysBetween;
    }


    // *********************************************************************************
    // *  Calculates the total price of a Booking based on the status of the booking:  *
    // *********************************************************************************

    // ================================= CONFIRMED =====================================
    // Status used right after creating a booking, thus the price is calculated based on the availability during the booked days
    // As well as the price for the additional extras (if included) & the price per km to drop off / pick up locations

    public double calculateTotalPriceConfirmed(Booking b) {
        double totalPrice = calculateTotalPriceForAvailability(b.getPickUpDate(), b.getDropOffDate(), b.getRegNumber());

        if (b.isBikeRack()) {
            totalPrice += 200.; // BIKE RACK - if included, will add additional 200 kr. to the total price
        }

        totalPrice += (b.getBedLinen() * 50.); // BED LINEN - if included, will add additional 50 kr. for every single one to the total price

        totalPrice += (b.getChildSeat() * 100.); // CHILD SEAT - if included, will add additional 100 kr. for every single one to the total price

        totalPrice += (b.getBedLinen() * 50.); // BED LINEN - if included, will add additional 50 kr. for every single one to the total price

        // DISTANCE FROM PICK UP / DROP OFF - adds the additional charge of 5 kr. / per km for the distances to pick up / drop off locations
        totalPrice += (addressService.getDistanceFromId(b.getPickUpId()) + addressService.getDistanceFromId(b.getDropOffId())) * 5.;

        return totalPrice;
    }


    public double calculateTotalPriceForAvailability(String pickUpDate, String dropOffDate, String regNumber) {

        // Gets list of all dates within the selected range
        List<String> list = getDatesBetween(pickUpDate, dropOffDate);

        // Brings back the base daily price for the selected vehicle (base price of it's category)
        String sql = "SELECT category.cat_price FROM category WHERE category.cat_id = (SELECT vehicle.cat_id FROM vehicle WHERE ? = vehicle.reg_number);";
        RowMapper<Double> rowMapper = new BeanPropertyRowMapper<>(Double.class);
        List<Double> price = template.query(sql, rowMapper, regNumber);
        double daysPrice = price.get(0);

        double totalPrice = 0.;
        // Will check the availability percentage for every single day and calculate the price accordingly
        // depending on which 'season' the particular day belongs to
        // and the extras added to the booking (if selected)
        for (int i = 0; i < list.size(); i++) {
            double availability = availabilityService.percentBooked(list.get(i));

            // Checks to which season the day belongs based on the percentage of vehicles booked that day:

            if (availability < 0.3) {           // LOW SEASON - if booked less than 30% of vehicles - base price applies
                totalPrice += daysPrice;
            } else if (availability < 0.6) {    // MID SEASON - if booked more than 30% and less than 60% of vehicles - base price with 30% increase applies
                totalPrice += daysPrice * 1.3;
            } else {                            // HIGH SEASON - if booked more than 60% of vehicles - base price with 60% increase applies
                totalPrice += daysPrice * 1.6;
            }
        }
        return totalPrice;
    }

//------------------------------------------------------------------------------------------------------------------

    // ================================= CANCELLED =====================================
    // Status used after cancelling the booking, thus the price is calculated based on the
    // initial price after the confirmation and the cancellation fee if it applies

    public double calculateTotalPriceCancelled(Booking b) {
        List<String> daysBetween = getDatesBetween(getTodaysDate(), b.getPickUpDate());
        int countDays = daysBetween.size();
        double totalPrice = 0.;

        // Will check the amount of days from cancellation date to the booking start date and apply cancellation fees accordingly

        // If cancelled more than 50 days before the booking start
        if (countDays >= 50) {
            if (b.getTotalPrice() < 1500.) { // If the initial total price was less than 1500 kr., the price after cancellation will be increased to 1500 kr.
                totalPrice = 1500.;
            } else {
                totalPrice = b.getTotalPrice() * 0.2; // If the initial price was more tha 1500 kr., the cancellation fee of 20% applies
            }
        // If cancelled between 15 and 49 days before the booking start
        } else if ((countDays >= 15) && (countDays <= 49)) {
            totalPrice = b.getTotalPrice() * 0.5; // The cancellation fee of 50% applies

        // If cancelled between 1 and 14 days before the booking start
        } else if ((countDays >= 1) && (countDays <= 14)) {
            totalPrice = b.getTotalPrice() * 0.8; // The cancellation fee of 80% applies

        // If cancelled on the day of the booking start
        } else {
            totalPrice = b.getTotalPrice() * 0.95; // The cancellation fee of 95% applies
        }

        return totalPrice;
    }

//------------------------------------------------------------------------------------------------------------------

    // ================================= FINISHED =====================================
    // Status used after the booking is marked as finished, thus the price is calculated based on the
    // initial price after the confirmation and the additional fees if they apply

    public double calculateTotalPriceFinished(Booking b, boolean isLowTank, int newOdometer) {
        int oldOdometer = vehicleService.findVehicle(b.getRegNumber()).getOdometer();
        int amountOfDays = getDatesBetween(b.getPickUpDate(), b.getDropOffDate()).size();
        double totalPrice = b.getTotalPrice();

        // Checks if after drop-off the tank was less thank 1/2 full
        if (isLowTank) {
            totalPrice += 500.; // The additional charge of 500 kr. applies
        }

        // The additional charge of 7 kr. per km. applies if the customer drove more than 400km. per day
        if (((newOdometer - oldOdometer) / amountOfDays) > 400) {
            totalPrice += (((newOdometer - oldOdometer) / amountOfDays) - 400) * 7.;
        }

        return totalPrice;
    }



}
