package com.example.project.Repository;


import com.example.project.Data.DataManipulation;
import com.example.project.Model.Booking;
import com.example.project.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class AvailabilityRepository {
    @Autowired
    JdbcTemplate template;
    @Autowired
    DataManipulation dataManipulation;

//----------------------------------------------------------------------------------------------------------------------
    //@Ástþór
//----------------------------------------------------------------------------------------------------------------------

    public List<Booking> fetchUnavailableVehicles (@Param("startDate") String startDate, @Param("endDate") String endDate){
        String sql = "SELECT reg_number FROM booking WHERE ? between pick_up_date and drop_off_date or ? between pick_up_date and drop_off_date or ? <= pick_up_date and drop_off_date <= ?";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return template.query(sql, rowMapper, startDate, endDate, startDate, endDate);
    }


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
        String sql = "SELECT vehicle.reg_number, vehicle.year_stmp, vehicle.odometer, vehicle.transmission, vehicle.fuel_type, vehicle.operational, vehicle.o_comment " +
                     "FROM vehicle INNER JOIN booking ON vehicle.reg_number = booking.reg_number WHERE booking.drop_off_date = ?;";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper, today);
    }

    // Returns the list of Vehicles which contains the rest of them (the ones which are not supposed to be dropped off today)
    public List<Vehicle> fetchVehiclesNotEndingToday () {
        String today = dataManipulation.getTodaysDate();
        String sql = "SELECT vehicle.reg_number, vehicle.year_stmp, vehicle.odometer, vehicle.transmission, vehicle.fuel_type, vehicle.operational, vehicle.o_comment \n" +
                     "FROM vehicle LEFT JOIN booking ON vehicle.reg_number = booking.reg_number \n" +
                     "WHERE vehicle.reg_number NOT IN \n" +
                     "(SELECT vehicle.reg_number FROM vehicle INNER JOIN booking ON vehicle.reg_number = booking.reg_number WHERE booking.drop_off_date = ?);";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper, today);
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
