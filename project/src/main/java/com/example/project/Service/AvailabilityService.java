package com.example.project.Service;

import com.example.project.Model.Vehicle;
import com.example.project.Repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {
    @Autowired
    AvailabilityRepository availabilityRepository;

    public List<Vehicle> fetchAvailabilityVehicles (String startDate, String endDate, String sort) {
        return availabilityRepository.fetchAvailabilityVehicles(startDate, endDate, sort);
    }


}
