package com.example.project.Repository;

import com.example.project.Model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingRepository {
    @Autowired
    JdbcTemplate template;

    public List<Booking> fetchAll() {
        return null;
    }

    public Booking addBooking(Booking b) {
        return null;
    }

    public Booking findBooking(int bookingNo) {
        return null;
    }

    public Boolean deleteBooking(int bookingNo) {
        return null;
    }

    public Booking updateBooking(int bookingNo, Booking b) {
        return null;
    }
}
