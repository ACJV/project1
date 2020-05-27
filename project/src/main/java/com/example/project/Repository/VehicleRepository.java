package com.example.project.Repository;

import com.example.project.Model.Booking;
import com.example.project.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VehicleRepository {
    @Autowired
    JdbcTemplate template;

    public List<Vehicle> fetchAll(){
        return null;
    }
    public Vehicle addVehicle(Vehicle v){
        return null;
    }

    // Note - I'm not sure but I think the "regNumber" might have to be the same as in the database.
    // - It doesn't make any sense but I had problems with this when following Cay's videos because I didn't name
    // - Them the same... - Just a note in case that something goes wrong - And to test it asap.
    public Vehicle findVehicle(int regNumber){
        return null;
    }
    public Boolean deleteVehicle(int regNumber){
        return null;
    }
    public Vehicle updateVehicle(int regNumber, Vehicle v){
        return null;
    }





}