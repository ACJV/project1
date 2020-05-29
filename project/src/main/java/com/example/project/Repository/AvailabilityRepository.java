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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class AvailabilityRepository {
    @Autowired
    JdbcTemplate template;

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

    // Returns the list of Bookings which are supposed to be end today
    // (used for the easier access to staff)
    public List<Booking> fetchBookingsEndingToday () {
        String today = DataManipulation.getTodaysDate();
        String sql = "SELECT FROM booking WHERE drop_off_date = ?";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return template.query(sql, rowMapper, today);
    }

    // Returns the list of Vehicles which are supposed to be dropped off today
    // (used for the easier access to mechanic)
    public List<Vehicle> fetchVehiclesEndingToday () {
        String today = DataManipulation.getTodaysDate();
        String sql = "SELECT vehicle.reg_number, vehicle.year_stmp, vehicle.odometer, vehicle.transmission, vehicle.fuel_type, vehicle.operational, vehicle.o_comment FROM vehicle INNER JOIN booking ON vehicle.reg_number = booking.reg_number WHERE booking.drop_off_date = ?;";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper, today);
    }

    // Returns the list of Vehicles which contains the rest of them (the ones which are not supposed to be dropped off today)
    public List<Vehicle> fetchVehiclesNotEndingToday () {
        String today = DataManipulation.getTodaysDate();
        String sql = "SELECT vehicle.reg_number, vehicle.year_stmp, vehicle.odometer, vehicle.transmission, vehicle.fuel_type, vehicle.operational, vehicle.o_comment FROM vehicle INNER JOIN booking ON vehicle.reg_number = booking.reg_number WHERE booking.drop_off_date != ?;";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper, today);
    }



}
