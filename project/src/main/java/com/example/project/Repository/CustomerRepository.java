package com.example.project.Repository;

import com.example.project.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {
    @Autowired
    JdbcTemplate template;

    public List<Customer> fetchAll() {
        return null;
    }

    public Customer addCustomer(Customer c) {
        return null;
    }

    public Customer findCustomer(int customerID) {
        return null;
    }

    public Boolean deleteCustomer(int customerID) {
        return null;
    }

    public Customer updateCustomer(int customerID, Customer c) {
        return null;
    }

}
