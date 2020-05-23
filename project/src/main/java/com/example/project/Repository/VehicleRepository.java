package com.example.project.Repository;

import com.example.project.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleRepository
{
    @Autowired
    JdbcTemplate template;

    public List<Vehicle> fetchAll()
    {
        String sql = "SELECT * FROM vehicle";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper);
    }

    public Vehicle addVehicle(Vehicle v)
    {
        String sql = "INSERT INTO vehicle (reg_number, cat_id, year_stmp, odometer, transmission, fuel_type, availability, a_comments) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, v.getRegNumber(), v.getCatID(), v.getYearStmp(), v.getOdometer(), v.getTransmission(), v.getFuelType(), v.getAvailability(), v.getAComments());
        return null;
    }

    public findVehicle(String regNumber)
    {
        String sql = "SELECT * FROM vehicle WHERE reg_number = ?";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        Vehicle v = template.queryForObject(sql, rowMapper, regNumber);
        return v;
    }

    public Boolean deleteVehicle(String regNumber)
    {
        return null;
    }

    public Vehicle updateVehicle(String regNumber, Vehicle v)
    {
        //string sql = ""
        return null;
    }
}
