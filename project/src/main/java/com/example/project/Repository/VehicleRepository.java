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

//----------------------------------------------------------------------------------------------------------------------
    // @Christian
//----------------------------------------------------------------------------------------------------------------------

    //this selects all elements from the vehicle table in sql
    public List<Vehicle> fetchAll()
    {
        String sql = "SELECT * FROM vehicle";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        // the template is a "spring-thing" mapping the results from the db into an arraylist
        return template.query(sql, rowMapper);
    }
    //this inserts an element in the vehicle table and includes all the attributes that are present in the vehicle table
    public Vehicle addVehicle(Vehicle v)
    {
        String sql = "INSERT INTO vehicle (reg_number, cat_id, year_stmp, odometer, transmission, fuel_type) VALUES(?, ?, ?, ?, ?, ?)";
        template.update(sql, v.getRegNumber(), v.getCatId(), v.getYearStmp(), v.getOdometer(), v.getTransmission(), v.getFuelType());
        return null;
    }
    //this searches the sql table vehicle for a specific reg_number, which is an attribute in the table.
    public Vehicle findVehicle(String regNumber)
    {
        String sql = "SELECT * FROM vehicle WHERE reg_number = ?";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        Vehicle v = template.queryForObject(sql, rowMapper, regNumber);
        return v;
    }
    //deletes a vehicle by using reg_number to find the vehicle
    public Boolean deleteVehicle(String regNumber)
    {
        String sql = "DELETE FROM vehicle WHERE reg_number = ?";
        return template.update(sql, regNumber) < 0;
    }
    //updates all vehicle columns, matching the row with the reg_number
    public Vehicle updateVehicle(Vehicle vehicle)
    {
        String sql = "UPDATE vehicle SET reg_number = ?, cat_id = ?, year_stmp = ?, odometer = ?, transmission = ?, fuel_type = ?, operational = ?, o_comment = ? WHERE reg_number = ?";
        template.update(sql, vehicle.getRegNumber(), vehicle.getCatId(), vehicle.getYearStmp(), vehicle.getOdometer(), vehicle.getTransmission(), vehicle.getFuelType(), vehicle.isOperational(), vehicle.getoComment(), vehicle.getRegNumber());
        return null;
    }
    //finds a vehicle by keyword, searching the reg_number column for matches
    public List<Vehicle> findByKeyword(@Param("keyword") String keyword)
    {
        String sql = "SELECT * FROM vehicle WHERE reg_number LIKE '%' ? '%'";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        return template.query(sql, rowMapper, keyword);
    }


//----------------------------------------------------------------------------------------------------------------------
    // @Justé
//----------------------------------------------------------------------------------------------------------------------

    public void updateVehicleMechanic(Vehicle vehicle) {
        String sql = "UPDATE vehicle SET operational = ?, o_comment = ? WHERE reg_number = ?";
        template.update(sql, vehicle.isOperational(), vehicle.getoComment(), vehicle.getRegNumber());
    }
}
