package com.example.project.Repository;

import com.example.project.Model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingRepository {
    @Autowired
    JdbcTemplate template;

//----------------------------------------------------------------------------------------------------------------------
    // Ástþór & Justé
//----------------------------------------------------------------------------------------------------------------------

    // Fetches all available vehices from database
    public List<Booking> fetchAll(){
        String sql = "SELECT * FROM booking";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return template.query(sql, rowMapper);
    }

    // Adds booking to database
    public Booking addBooking(Booking b) {
        String sql = "INSERT INTO booking (reg_number, pick_up_date, drop_off_date, booking_status, customer_id, pick_up_id, drop_off_id, bike_rack, bed_linen, child_seat, total_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, b.getRegNumber(), b.getPickUpDate(), b.getDropOffDate(), b.getBookingStatus(), b.getCustomerId(), b.getPickUpId(), b.getDropOffId(), b.isBikeRack(), b.getBedLinen(), b.getChildSeat(), b.getTotalPrice());
        return null;
    }

    // finds booking in database by bookingNo
    public Booking findBooking(int bookingNo){
        String sql = "SELECT * FROM booking WHERE booking_no = ?";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        Booking booking = template.queryForObject(sql, rowMapper, bookingNo);
        return booking;
    }

    // deletes booking using booking number to identify booking/row
    public Boolean deleteBooking(int bookingNo) {
        String sql = "DELETE FROM booking WHERE booking_no = ?";
        return template.update(sql, bookingNo) < 0;
    }

    // updates all booking columns (except for booking_no) matching row with correct booking number
    public Booking updateBooking(Booking b){
        String sql = "UPDATE booking SET pick_up_date = ?, drop_off_date = ?, booking_status = ?, customer_id = ?, pick_up_id = ?, drop_off_id = ?, " +
                "bike_rack = ?, bed_linen = ?, child_seat = ?, total_price = ? WHERE booking_no = ?";
        template.update(sql, b.getPickUpDate(), b.getDropOffDate(), b.getBookingStatus(), b.getCustomerId(), b.getPickUpId(), b.getDropOffId(),
                b.isBikeRack(), b.getBedLinen(), b.getChildSeat(), b.getTotalPrice(), b.getBookingNo());
        return null;
    }

    // Finds booking booking_no by checking for reg_number and dates
    public Booking findBookingNumber (Booking b){
        String sql = "SELECT * FROM booking WHERE reg_number = ? AND pick_up_date = ? AND drop_off_date = ?";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        Booking booking = template.queryForObject(sql, rowMapper, b.getRegNumber(), b.getPickUpDate(), b.getDropOffDate());
        return booking;
    }

    // updates booking status, total price by finding correct booking_no
    public void updateBookingStatus (Booking b) {
        String sql = "UPDATE booking SET booking_status = ?, total_price = ? WHERE booking_no = ?";
        template.update(sql, b.getBookingStatus(), b.getTotalPrice(), b.getBookingNo());
    }
}
