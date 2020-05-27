package com.example.project.Repository;


import com.example.project.Model.BookingAvailability;
import com.example.project.Model.SearchingDates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AvailabilityRepository {
    @Autowired
    JdbcTemplate template;

    public List<BookingAvailability> fetchUnavailableVehicles (@Param("startDate") String startDate, @Param("endDate") String endDate){
        String sql = "SELECT vehicleRegNumber FROM bookingTester WHERE ? between pickUpDate and dropOffDate or ? between pickUpDate and dropOffDate or ? <= pickUpDate and dropOffDate <= ?";
        RowMapper<BookingAvailability> rowMapper = new BeanPropertyRowMapper<>(BookingAvailability.class);
        return template.query(sql, rowMapper, startDate, endDate, startDate, endDate);
    }

}
