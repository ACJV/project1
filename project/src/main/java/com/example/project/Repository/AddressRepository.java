package com.example.project.Repository;

import com.example.project.Model.Address;
import com.example.project.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
        String sql = "SELECT * FROM address";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        return template.query(sql, rowMapper);
    }

    public Address addAddress(Address a) {
        String sql = "INSERT INTO address (id, address, zip, city, country, distance) VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql,a.getAddressID(), a.getAddress(), a.getZip(), a.getCity(), a.getCountry(),a.getDistance());
        return null;
    }
    public Address findAddress(int id) {
        String sql = "SELECT * FROM address WHERE id = ?";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        Address a = template.queryForObject(sql, rowMapper, id);
        return a;
    }
    public Boolean deleteAddress(int id){
        return null;
    }

    public Address updateAddress(int id, Address a){
        String sql = "UPDATE address SET address =?, zip =?, city=?, country=?, distance=? WHERE id = ?";
        template.update(sql, a.getAddress(), a.getZip(), a.getCity(), a.getCountry(),a.getDistance());
        return null;
    }

    public List<Address> findByKeyword(@Param("keyword") String keyword)
    {
        String sql = "SELECT * FROM address WHERE address LIKE '%' ? '%'";
        RowMapper<Address> rowMapper = new BeanPropertyRowMapper<>(Address.class);
        return template.query(sql, rowMapper, keyword);
    }

}
