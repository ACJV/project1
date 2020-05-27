package com.example.project.Service;

import com.example.project.Model.BookingAvailability;
import com.example.project.Model.SearchingDates;
import com.example.project.Repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {
    @Autowired
    AvailabilityRepository availabilityRepository;

    public List<BookingAvailability> fetchUnavailableVehicles (String startDate, String endDate) {
        return availabilityRepository.fetchUnavailableVehicles(startDate, endDate);
    }


}
