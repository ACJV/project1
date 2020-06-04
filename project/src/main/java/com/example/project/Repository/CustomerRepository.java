package com.example.project.Repository;

import com.example.project.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {
    @Autowired
    JdbcTemplate template;

//----------------------------------------------------------------------------------------------------------------------
    //@Ástþór
//----------------------------------------------------------------------------------------------------------------------

    public List<Customer> fetchAll(){
        String sql = "SELECT * FROM customer";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }

    public Customer addCustomer(Customer c){
        String sql = "INSERT INTO customer (full_name, driver_lic_no, dob, phone_no, email, address_id) VALUES(?, ?, ?, ?, ?, ?)";
        template.update(sql, c.getFullName(), c.getDriverLicNo(), c.getDob(), c.getPhoneNo(), c.getEmail(), c.getAddressId());
        return null;
    }
    public Customer findCustomer(int customerID){
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer c = template.queryForObject(sql, rowMapper, customerID);
        return c;
    }
    public Boolean deleteCustomer(int customerID){
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        return template.update(sql, customerID) < 0;
        //return null;
    }
    public Customer updateCustomer(int customerID, Customer customer){
        String sql = "UPDATE customer SET full_name = ?, driver_lic_no = ?, dob = ?, phone_no = ?, email = ?, address_id = ? WHERE customer_id = ?";
        template.update(sql, customer.getFullName(), customer.getDriverLicNo(), customer.getDob(), customer.getPhoneNo(), customer.getEmail(), customer.getAddressId(), customer.getCustomerId());
        return null;
    }

    // Using @Param to get keyword input by user to check if keyword matches any characters within customers name.
    public List<Customer> findByKeyword(@Param("keyword") String keyword){
        // Following a video on youtube, where this method was used but as it did not work for me, the methodology was
            //@Query(value = "select * from customer c where c.fullName like %:keyword%", nativeQuery = true);
        // implemented with the methods that have been working for me so far
        // gets all information from customer where full name has characters in keyword anywhere within the full name.
        String sql = "SELECT * FROM customer WHERE full_name LIKE '%' ? '%'";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper, keyword);


    }

}
