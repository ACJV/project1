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
        String sql = "SELECT * FROM nmr_db.booking";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return template.query(sql, rowMapper);
    }

    public Booking addBooking(Booking b) {
        String sql = "INSERT INTO nmr_db.booking (vehicle_reg_number, pick_up_date, drop_off_date, customer_id, pick_up_loc_id, drop_off_loc_id, bike_rack, bed_linen, child_seat, total_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, b.getVehicleRegNumber(), b.getPickUpDate(), b.getDropOffDate(), b.getCustomerID(), b.getPickUpLocID(), b.getDropOffLocID(), b.isBikeRack(), b.getBedLinen(), b.getChildSeat(), b.getTotalPrice());
        return null;
    }

    public Booking findBooking(int bookingNo){
        return null;
    }

    public Boolean deleteBooking(int bookingNo) {
        return null;
    }

    public Booking updateBooking(int bookingNo, Booking b){
        return null;
    }
}
