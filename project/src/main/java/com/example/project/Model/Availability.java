package com.example.project.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class Availability {

    @Autowired
    JdbcTemplate template;

    public static boolean checkIfAvailable (String startDate, String endDate, String regNum) {


        String sql = "SELECT ? FROM availability WHERE ";



        return template.query(sql, regNum, );
    }



}
