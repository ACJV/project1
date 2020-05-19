package com.example.project.Repository;

import com.example.project.Model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRepository {
    @Autowired
    JdbcTemplate template;

    public List<Address> fetchAll() {

        // Comment back once we finish testing data retrieval

        String sql = "SELECT * FROM address";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        return template.query(sql, rowMapper);
        //return null;
    }

    public Address addAddress(Address a) {
        //String sql = "INSERT INTO address () VALUES ()";
        //template.update(sql,...)
        return null;
    }
    public Address findAddress(int id) {
        //String sql = "SELECT * FROM address WHERE ,, = ?";
        //RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        //Address a = template.queryForObject(sql, rowMapper, ...);
        return null;
    }
    public Boolean deleteAddress(int id){
        return null;
    }
    public Address updateAddress(int id, Address a){
        return null;
    }

}
