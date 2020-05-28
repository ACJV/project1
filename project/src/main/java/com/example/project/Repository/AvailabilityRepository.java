package com.example.project.Repository;


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

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AvailabilityRepository {
    @Autowired
    JdbcTemplate template;

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

}
