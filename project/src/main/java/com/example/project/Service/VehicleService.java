package com.example.project.Service;

import com.example.project.Model.Vehicle;
import com.example.project.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;

    public List<Vehicle> fetchAll()
    {
        return vehicleRepository.fetchAll();
    }

    public Vehicle addVehicle(Vehicle v)
    {
        return vehicleRepository.addVehicle(v);
    }

    public Vehicle findVehicle(String regNumber)
    {
        return vehicleRepository.findVehicle(regNumber);
    }

    public Boolean deleteVehicle(String regNumber)
    {
        return vehicleRepository.deleteVehicle(regNumber);
    }

    public Vehicle updateVehicle(String regNumber, Vehicle v)
    {
        return vehicleRepository.updateVehicle(regNumber, v);
    }

    public List<Vehicle> findByKeyword(String keyword)
    {
        return vehicleRepository.findByKeyword(keyword);
    }
}
