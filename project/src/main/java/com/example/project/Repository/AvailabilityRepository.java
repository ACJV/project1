package com.example.project.Repository;


import com.example.project.Data.DataManipulation;
import com.example.project.Model.Booking;
import com.example.project.Model.Vehicle;
import com.example.project.Service.VehicleService;
import com.example.project.Model.Booking;
import com.example.project.Model.BookingAvailability;
import com.example.project.Model.Vehicle;
import com.example.project.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AvailabilityRepository {
    @Autowired
    JdbcTemplate template;
    @Autowired
    DataManipulation dataManipulation;
    @Autowired
    VehicleService vehicleService;

    @Autowired
    VehicleService vehicleService;

    public List<Vehicle> fetchAvailabilityVehicles (@Param("startDate") String startDate, @Param("endDate") String endDate, String sort){
        String sql = "SELECT * FROM booking WHERE ? between pick_up_date and drop_off_date " +
                                            "or ? between pick_up_date and drop_off_date " +
                                            "or ? <= pick_up_date and drop_off_date <= ? " +
                                            "or pick_up_date = ? or drop_off_date = ? " +
                                            "or pick_up_date = ? or drop_off_date = ?";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        List<Booking> bookingsFound = template.query(sql, rowMapper, startDate, endDate, startDate, endDate, startDate, endDate, endDate, startDate);
        List<Vehicle> allVehicles = vehicleService.fetchAll();
        ArrayList[] array = sortVehicles(bookingsFound, allVehicles);
        if(sort.equalsIgnoreCase("Unavailable")){
            return array[0];
        } else {
            return array[1]; // Returns available vehicles.
        }
    }

    public static ArrayList[] sortVehicles (List<Booking> bookingsFound, List<Vehicle> allVehicles){
        // Crete an arrayList to get vehicles with matching regNumbers in bookingsFound - Marked as unavailable
        ArrayList<Vehicle> unavailableVehicles = new ArrayList<Vehicle>();
        //Iterate through bookingsFound
        for(int i = 0; i < bookingsFound.size(); i++){
            // If unavailableVehicles doesn't contain this regNumber already, add it to the list
            if(!unavailableVehicles.contains(bookingsFound.get(i).getRegNumber())){
                unavailableVehicles.add(allVehicles.get(i));
            }
        }
        // Create an arrayList to get vehicles not listed in unavailableVehicles
        ArrayList<Vehicle> availableVehicles = new ArrayList<Vehicle>();
        // Iterate through all vehicles - Provided in parameters
        for(int i = 0; i < allVehicles.size(); i++){
            // If unavailableVehicles doesn't contain this vehicle, list it as available
            if(!unavailableVehicles.contains(allVehicles.get(i))){
                availableVehicles.add(allVehicles.get(i));
            }
        }
        // Create an array to hold both arrayLists
        ArrayList[] array = new ArrayList[2];
        // Add both arrayLists as mentioned below to the array of ArrayLists
        array[0] = unavailableVehicles;
        array[1] = availableVehicles;
        return array;
    }
    //public static List<Vehicle> fetchUnavailableVehicles(){

    //}

    public static boolean isConfirmed (Booking booking) {
        return booking.getBookingStatus().equalsIgnoreCase("confirmed");
    }

    //public static void


//----------------------------------------------------------------------------------------------------------------------
    //@Juste
//----------------------------------------------------------------------------------------------------------------------

    // Returns the list of Bookings which are confirmed and supposed to end today
    // (used for staff Home Page)
    public List<Booking> fetchBookingsEndingToday () {
        String today = dataManipulation.getTodaysDate();
        String sql = "SELECT * FROM booking WHERE drop_off_date = ? AND booking_status = 'Confirmed';";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return template.query(sql, rowMapper, today);
    }

    // Returns the list of Bookings which are cancelled and upcoming (start date is later or equals today)
    // (used for staff Home Page)
    public List<Booking> fetchCancelledBookings () {
        String today = dataManipulation.getTodaysDate();
        String sql = "SELECT * FROM booking WHERE pick_up_date >= ? AND booking_status = 'Cancelled';";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return template.query(sql, rowMapper, today);
    }

    // Returns the list of Bookings which are confirmed and upcoming (start date is later or equals today)
    // (used for staff Home Page)
    public List<Booking> fetchConfirmedBookings () {
        String today = dataManipulation.getTodaysDate();
        String sql = "SELECT * FROM booking WHERE pick_up_date >= ? AND booking_status = 'Confirmed';";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return template.query(sql, rowMapper, today);
    }

    // Returns the list of Bookings which are finished (end date will be later or equals today)
    // (used for staff Home Page)
    public List<Booking> fetchFinishedBookings () {
        String today = dataManipulation.getTodaysDate();
        String sql = "SELECT * FROM booking WHERE drop_off_date = ? AND booking_status = 'Finished';";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return template.query(sql, rowMapper, today);
    }


    // Returns the list of Vehicles which are supposed to be dropped off today
    // (used for the easier access to mechanic)
    public List<Vehicle> fetchVehiclesEndingToday () {
        String today = dataManipulation.getTodaysDate();
        String sql = "SELECT vehicle.reg_number, vehicle.cat_id, vehicle.year_stmp, vehicle.odometer, vehicle.transmission, vehicle.fuel_type, vehicle.operational, vehicle.o_comment " +
                     "FROM vehicle INNER JOIN booking ON vehicle.reg_number = booking.reg_number WHERE booking.drop_off_date = ?;";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper, today);
    }

    // Returns the list of Vehicles which contains the rest of them (the ones which are not supposed to be dropped off today)
    public List<Vehicle> fetchVehiclesNotEndingToday () {
        List<Vehicle> vehiclesToday = fetchVehiclesEndingToday();
        List<Vehicle> vehiclesAll = vehicleService.fetchAll();

        for (int i = 0; i < vehiclesAll.size(); i++) {
            for (int j = 0; j < vehiclesToday.size(); j++) {
                if (vehiclesAll.get(i).getRegNumber().equals(vehiclesToday.get(j).getRegNumber())) {
                    vehiclesAll.remove(vehiclesAll.get(i));
                }
            }
        }

        return vehiclesAll;
    }

    // Returns percentage of booked vehicles on a specified day (expressed as a decimal)
    // Requires day as a String in a format of 'YYYY-MM-DD'
    public double percentBooked (String day) {
        String sql = "SELECT booking.reg_number FROM booking WHERE ? <= booking.drop_off_date AND ? >= booking.pick_up_date;";
        RowMapper<String> rowMapper = new BeanPropertyRowMapper<>(String.class);
        int vehiclesBooked = template.query(sql, rowMapper, day, day).size();

        String sql1 = "SELECT vehicle.reg_number FROM vehicle;";
        RowMapper<String> rowMapper1 = new BeanPropertyRowMapper<>(String.class);
        int vehiclesTotal = template.query(sql1, rowMapper1).size();

        return vehiclesBooked/vehiclesTotal;
    }



}
