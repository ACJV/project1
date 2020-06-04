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
    //creates a rowmapper (arraylist) for vehicle
    //then return it in the spring template with those params: sql and rowmapper
    public List<Vehicle> fetchAll()
    {
        String sql = "SELECT * FROM vehicle";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        // the template is a "spring-thing" mapping the results from the db into an arraylist
        return template.query(sql, rowMapper);
    }
    //this inserts an element in the vehicle table and includes all the attributes that are present in the vehicle table
    //then uses the spring template to update the model
    public Vehicle addVehicle(Vehicle v)
    {
        String sql = "INSERT INTO vehicle (reg_number, cat_id, year_stmp, odometer, transmission, fuel_type) VALUES(?, ?, ?, ?, ?, ?)";
        template.update(sql, v.getRegNumber(), v.getCatId(), v.getYearStmp(), v.getOdometer(), v.getTransmission(), v.getFuelType());
        return null;
    }
    //this searches the sql table vehicle for a specific reg_number, which is an attribute in the table.
    //creates a rowmapper (arraylist) for vehicle
    //creates object v and equates it to the spring template with params: sql, rowmapper and regnumber
    //then returns v, so this result can be used by another method
    public Vehicle findVehicle(String regNumber)
    {
        String sql = "SELECT * FROM vehicle WHERE reg_number = ?";
        RowMapper<Vehicle> rowMapper = new BeanPropertyRowMapper<>(Vehicle.class);
        Vehicle v = template.queryForObject(sql, rowMapper, regNumber);
        return v;
    }

    public Boolean deleteVehicle(String regNumber)
    {
        String sql = "DELETE FROM vehicle WHERE reg_number = ?";
        return template.update(sql, regNumber) < 0;
    }

    public Vehicle updateVehicle(Vehicle vehicle)
    {
        String sql = "UPDATE vehicle SET reg_number = ?, cat_id = ?, year_stmp = ?, odometer = ?, transmission = ?, fuel_type = ?, operational = ?, o_comment = ? WHERE reg_number = ?";
        template.update(sql, vehicle.getRegNumber(), vehicle.getCatId(), vehicle.getYearStmp(), vehicle.getOdometer(), vehicle.getTransmission(), vehicle.getFuelType(), vehicle.isOperational(), vehicle.getoComment(), vehicle.getRegNumber());
        return null;
    }

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
