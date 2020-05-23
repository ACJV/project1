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
    public Vehicle findVehicle(int regNumber)
    {
        return vehicleRepository.findVehicle(regNumber);
    }
    public Boolean deleteVehicle(int regNumber)
    {
        return vehicleRepository.deleteVehicle(regNumber);
    }
    public Vehicle updateVehicle(int regNumber, Vehicle v)
    {
        return vehicleRepository.updateVehicle(regNumber, v);
    }
}
