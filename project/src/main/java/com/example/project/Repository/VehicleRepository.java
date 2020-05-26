package com.example.project.Repository;

import com.example.project.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleRepository {
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
        String sql = "INSERT INTO vehicle (reg_number, cat_id, year_stmp, odometer, transmission, fuel_type, description, availability, a_comments) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, v.getRegNumber(), v.getCategoryID(), v.getYear_stmp(), v.getOdometer(), v.getTransmission(), v.getFuelType(), v.getDescription(), v.isAvailability(), v.getaComment());
        return null;
    }

    public Vehicle findVehicle(String regNumber)
    {
        String sql = "SELECT * FROM vehicle WHERE reg_number = ?";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        Vehicle v = template.queryForObject(sql, rowMapper, regNumber);
        return v;
    }

    public Boolean deleteVehicle(String regNumber)
    {
        String sql = "SELECT * FORM vehicle WHERE reg_number = ?";
        return template.update(sql, regNumber) < 0;
    }

    public Vehicle updateVehicle(String regNumber, Vehicle vehicle)
    {
        String sql = "UPDATE vehicle SET year_stmp = ?, odometer = ?, transmission = ?, fuel_type = ?, availability = ?, a_comments = ? WHERE reg_number = ?";
        template.update(sql, vehicle.getYear_stmp(), vehicle.getOdometer(), vehicle.getTransmission(), vehicle.getFuelType(), vehicle.isAvailability(), vehicle.getaComment());
        return null;
    }

    public List<Vehicle> findByKeyword(@Param("keyword") String keyword)
    {
        String sql = "SELECT * FROM vehicle WHERE reg_number LIKE '%' ? '%'";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper, keyword);
    }
}
