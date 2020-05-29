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
        // the template is a "spring-thing" mapping the results from the db into an arraylist
        return template.query(sql, rowMapper);
    }

    public Vehicle addVehicle(Vehicle v)
    {
        String sql = "INSERT INTO vehicle (regNumber, category_id, year_stmp, odometer, transmission, fuel_type, description_x, operational, o_comment) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, v.getRegNumber(), v.getCategoryId(), v.getYearStmp(), v.getOdometer(), v.getTransmission(), v.getFuelType(), v.getDescriptionX(), v.getOperational(), v.getoComment());
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
        String sql = "DELETE FROM vehicle WHERE regNumber = ?";
        return template.update(sql, regNumber) < 0;
    }

    public Vehicle updateVehicle(String regNumber, Vehicle vehicle)
    {
        String sql = "UPDATE vehicle SET yearStmp = ?, odometer = ?, transmission = ?, fuel_type = ?, description_x = ?, operational = ?, o_comment = ? WHERE regNumber = ?";
        template.update(sql, vehicle.getYearStmp(), vehicle.getOdometer(), vehicle.getTransmission(), vehicle.getFuelType(), vehicle.getDescriptionX(), vehicle.getOperational(), vehicle.getoComment(), vehicle.getRegNumber());
        return null;
    }

    public List<Vehicle> findByKeyword(@Param("keyword") String keyword)
    {
        String sql = "SELECT * FROM vehicle WHERE regNumber LIKE '%' ? '%'";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper, keyword);
    }
}
