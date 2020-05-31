package com.example.project.Service;

import com.example.project.Data.DataManipulation;
import com.example.project.Model.Booking;
import com.example.project.Model.Vehicle;
import com.example.project.Repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {
    @Autowired
    AvailabilityRepository availabilityRepository;

    public List<Booking> fetchUnavailableVehicles (String startDate, String endDate) {
        return availabilityRepository.fetchUnavailableVehicles(startDate, endDate);
    }

    public List<Booking> fetchBookingsEndingToday () {
        return availabilityRepository.fetchBookingsEndingToday();
    }

    public List<Booking> fetchCancelledBookings () {
        return availabilityRepository.fetchCancelledBookings();
    }

    public List<Booking> fetchConfirmedBookings () {
        return availabilityRepository.fetchConfirmedBookings();
    }

    public List<Booking> fetchFinishedBookings () {
        return availabilityRepository.fetchFinishedBookings();
    }

    public List<Vehicle> fetchVehiclesEndingToday () {
        return availabilityRepository.fetchVehiclesEndingToday();
    }

    public List<Vehicle> fetchVehiclesNotEndingToday () {
        return availabilityRepository.fetchVehiclesNotEndingToday();
    }

    public double percentBooked (String day) {
        return availabilityRepository.percentBooked(day);
    }


}
