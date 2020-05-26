package com.example.project.Service;

import com.example.project.Model.Booking;
import com.example.project.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

    public List<Booking> fetchAll() {
        return bookingRepository.fetchAll();
    }

    public Booking addBooking(Booking b) {
        return bookingRepository.addBooking(b);
    }

    public Booking findBooking(int bookingNo) {
        return bookingRepository.findBooking(bookingNo);
    }

    public Boolean deleteBooking(int bookingNo) {
        return bookingRepository.deleteBooking(bookingNo);
    }

    public Booking updateBooking(int bookingNo, Booking b) {
        return bookingRepository.updateBooking(bookingNo, b);
    }

}
