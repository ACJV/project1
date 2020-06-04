package com.example.project.Repository;


import com.example.project.Data.DataManipulation;
import com.example.project.Model.Booking;
import com.example.project.Model.Vehicle;
import com.example.project.Service.VehicleService;
import com.example.project.Model.Booking;
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

//----------------------------------------------------------------------------------------------------------------------
    //@Ástþór
//----------------------------------------------------------------------------------------------------------------------
    // Fetches bookings that match dates selected by user as @Param values and String sort to check return either
    // an array of available or unavailable vehicles.
    public List<Vehicle> fetchAvailabilityVehicles (@Param("startDate") String startDate, @Param("endDate") String endDate, String sort){
        // String checks if either of the dates are equal to or in between dates registered for bookings in database
        // or booking pickup and dropOff date is in between the startDate and endDate.
        // Sql query returns all bookings that would contain a vehicle that is unavailable.
        String sql = "SELECT * FROM booking WHERE ? between pick_up_date and drop_off_date " +
                                            "or ? between pick_up_date and drop_off_date " +
                                            "or ? <= pick_up_date and drop_off_date <= ? " +
                                            "or pick_up_date = ? or drop_off_date = ? " +
                                            "or pick_up_date = ? or drop_off_date = ?";
        // RowMapper used as a type of "ResultSet"
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        // List of Bookings to receive bookings found.
        List<Booking> bookingsFound = template.query(sql, rowMapper, startDate, endDate, startDate, endDate, startDate, endDate, endDate, startDate);
        // Fetches all vehicles in the database
        List<Vehicle> allVehicles = vehicleService.fetchAll();
        // Array of ArrayList made, one arrayList for available vehicles and another for unavailable vehicles.
        // Using method below named sortVehicles to sort them as available or unavailable.
        ArrayList[] array = sortVehicles(bookingsFound, allVehicles);
        // Depending on the method called intended to fetch unavailable of available vehicles, will return separate lists.
        if(sort.equalsIgnoreCase("Unavailable")){
            return array[0];
        } else {
            return array[1]; // Returns available vehicles.
        }
    }

    // Method that check's whether the vehicles are unavailable or available, returns array of arrayList.
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

    // Method to be used in future iterations, checking if booking is confirmed to see if vehicles are available due to
    // a cancelled booking
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
        // Calls a method in dataManipulation class which returns today's date as a String ("YYYY-MM-DD")
        String today = dataManipulation.getTodaysDate();
        // A query is formed to return a List of all Confirmed bookings ending today
        String sql = "SELECT * FROM booking WHERE drop_off_date = ? AND booking_status = 'Confirmed';";
        // RowMapper forms a Result Set of Booking Objects
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        // Query results from the database are being stored in the ResultSet
        // which then gets returned as a List of Booking Objects
        return template.query(sql, rowMapper, today);
    }


    // Returns the list of Bookings which are cancelled and upcoming (start date is later or equals today)
    // (used for staff Home Page)
    public List<Booking> fetchCancelledBookings () {
        // Calls a method in dataManipulation class which returns today's date as a String ("YYYY-MM-DD")
        String today = dataManipulation.getTodaysDate();
        // A query is formed to return a List of all Cancelled bookings starting later or on the same day as today
        String sql = "SELECT * FROM booking WHERE pick_up_date >= ? AND booking_status = 'Cancelled';";
        // RowMapper forms a Result Set of Booking Objects
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        // Query results from the database are being stored in the ResultSet
        // which then gets returned as a List of Booking Objects
        return template.query(sql, rowMapper, today);
    }


    // Returns the list of Bookings which are confirmed and upcoming (start date is later or equals today)
    // (used for staff Home Page)
    public List<Booking> fetchConfirmedBookings () {
        // Calls a method in dataManipulation class which returns today's date as a String ("YYYY-MM-DD")
        String today = dataManipulation.getTodaysDate();
        // A query is formed to return a List of all Confirmed bookings starting later or on the same day as today
        String sql = "SELECT * FROM booking WHERE pick_up_date >= ? AND booking_status = 'Confirmed';";
        // RowMapper forms a Result Set of Booking Objects
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        // Query results from the database are being stored in the ResultSet
        // which then gets returned as a List of Booking Objects
        return template.query(sql, rowMapper, today);
    }


    // Returns the list of Bookings which are finished (end date will be later or equals today)
    // (used for staff Home Page)
    public List<Booking> fetchFinishedBookings () {
        // Calls a method in dataManipulation class which returns today's date as a String ("YYYY-MM-DD")
        String today = dataManipulation.getTodaysDate();
        // A query is formed to return a List of all Finished bookings which ended today
        String sql = "SELECT * FROM booking WHERE drop_off_date = ? AND booking_status = 'Finished';";
        // RowMapper forms a Result Set of Booking Objects
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        // Query results from the database are being stored in the ResultSet
        // which then gets returned as a List of Booking Objects
        return template.query(sql, rowMapper, today);
    }


    // Returns the list of Vehicles which are supposed to be dropped off today
    // (used for the easier access to mechanic)
    public List<Vehicle> fetchVehiclesEndingToday () {
        // Calls a method in dataManipulation class which returns today's date as a String ("YYYY-MM-DD")
        String today = dataManipulation.getTodaysDate();
        // A query is formed to return a List of all vehicles which were supposed to be dropped off today
        String sql = "SELECT vehicle.reg_number, vehicle.cat_id, vehicle.year_stmp, vehicle.odometer, vehicle.transmission, vehicle.fuel_type, vehicle.operational, vehicle.o_comment " +
                     "FROM vehicle INNER JOIN booking ON vehicle.reg_number = booking.reg_number WHERE booking.drop_off_date = ?;";
        // RowMapper forms a Result Set of Vehicle Objects
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        // Query results from the database are being stored in the ResultSet
        // which then gets returned as a List of Vehicle Objects
        return template.query(sql, rowMapper, today);
    }


    // Returns the list of Vehicles which contains the rest of them (the ones which are not supposed to be dropped off today)
    public List<Vehicle> fetchVehiclesNotEndingToday () {
        // Returns the list of Vehicles which are supposed to be dropped off today
        List<Vehicle> vehiclesToday = fetchVehiclesEndingToday();
        // Returns the list of Vehicles of all Vehicles stored in the database
        List<Vehicle> vehiclesAll = vehicleService.fetchAll();

        // for loop goes through the list of all Vehicles
        for (int i = 0; i < vehiclesAll.size(); i++) {
            // another nested for loop goes through the list of Vehicles which are supposed to be dropped off today
            for (int j = 0; j < vehiclesToday.size(); j++) {
                // checks if the Vehicles' registration numbers match in both list
                if (vehiclesAll.get(i).getRegNumber().equals(vehiclesToday.get(j).getRegNumber())) {
                    // if they do, the matching one (included in the list of vehicles dropped off today) gets removed from the list
                    // containing all of them, leaving only the ones which are not supposed to be dropped off today
                    vehiclesAll.remove(vehiclesAll.get(i));
                }
            }
        }
        // returns the list of the Vehicles which are not supposed to be dropped off today
        return vehiclesAll;
    }


    // Returns percentage of booked vehicles on a specified day (expressed as a decimal)
    // Requires day as a String in a format of 'YYYY-MM-DD'
    public double percentBooked (String day) {
        // A query is formed to return a List of registration numbers from booking table where the day specified is included
        // in the booking's range (from pick up to drop off)
        String sql = "SELECT booking.reg_number FROM booking WHERE ? <= booking.drop_off_date AND ? >= booking.pick_up_date;";
        // RowMapper forms a Result Set of Strings
        RowMapper<String> rowMapper = new BeanPropertyRowMapper<>(String.class);
        // Query results from the database are being stored in the ResultSet
        // and the size of that ResultSet is being defined as an int specifying amount of vehicles booked on that day
        int vehiclesBooked = template.query(sql, rowMapper, day, day).size();

        // Another query is formed to return a List of all registration numbers from vehicle table
        String sql1 = "SELECT vehicle.reg_number FROM vehicle;";
        // RowMapper forms a Result Set of Strings
        RowMapper<String> rowMapper1 = new BeanPropertyRowMapper<>(String.class);
        // Query results from the database are being stored in the ResultSet
        // and the size of that ResultSet is being defined as an int specifying amount of vehicles in total in the database
        int vehiclesTotal = template.query(sql1, rowMapper1).size();

        // A difference between amount of booked and all vehicles on a specified day
        // expressed as a decimal is being returned
        return vehiclesBooked/vehiclesTotal;
    }



}
