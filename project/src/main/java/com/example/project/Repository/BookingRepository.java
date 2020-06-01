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

    public List<Booking> fetchAll(){
        String sql = "SELECT * FROM booking";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return template.query(sql, rowMapper);
    }

    public Booking addBooking(Booking b) {
        String sql = "INSERT INTO booking (reg_number, pick_up_date, drop_off_date, booking_status, customer_id, pick_up_id, drop_off_id, bike_rack, bed_linen, child_seat, total_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, b.getRegNumber(), b.getPickUpDate(), b.getDropOffDate(), b.getBookingStatus(), b.getCustomerId(), b.getPickUpId(), b.getDropOffId(), b.isBikeRack(), b.getBedLinen(), b.getChildSeat(), b.getTotalPrice());
        return null;
    }

    public Booking findBooking(int bookingNo){
        String sql = "SELECT * FROM booking WHERE booking_no = ?";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        Booking booking = template.queryForObject(sql, rowMapper, bookingNo);
        return booking;
    }

    public Boolean deleteBooking(int bookingNo) {
        String sql = "DELETE FROM booking WHERE booking_no = ?";
        return template.update(sql, bookingNo) < 0;
    }

    public Booking updateBooking(Booking b){
        String sql = "UPDATE booking SET pick_up_date = ?, drop_off_date = ?, booking_status = ?, customer_id = ?, pick_up_id = ?, drop_off_id = ?, " +
                "bike_rack = ?, bed_linen = ?, child_seat = ?, total_price = ? WHERE booking_no = ?";
        template.update(sql, b.getPickUpDate(), b.getDropOffDate(), b.getBookingStatus(), b.getCustomerId(), b.getPickUpId(), b.getDropOffId(),
                b.isBikeRack(), b.getBedLinen(), b.getChildSeat(), b.getTotalPrice(), b.getBookingNo());
        return null;
    }

    public void updateBookingFinished(Booking b) {
        String sql = "UPDATE booking SET booking_status = ?, total_price = ? WHERE booking_no = ?";
        template.update(sql, b.getBookingStatus(), b.getTotalPrice(), b.getBookingNo());
    }
}
