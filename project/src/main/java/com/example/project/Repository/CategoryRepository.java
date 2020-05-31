package com.example.project.Repository;

import com.example.project.Model.Booking;
import com.example.project.Model.Category;
import com.example.project.Model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {
    @Autowired
    JdbcTemplate template;

    public List<Category> fetchAll(){
        String sql = "SELECT * FROM category";
        RowMapper<Category> rowMapper = new BeanPropertyRowMapper<>(Category.class);
        return template.query(sql, rowMapper);
    }

    public Category addCategory(Category c) {
        String sql = "INSERT INTO category (cat_price, cat_name, cat_description, model_name, brand) VALUES (?, ?, ?, ?, ?)";
        template.update(sql, c.getCatPrice(), c.getCatName(), c.getCatDescription(), c.getModelName(), c.getBrand());
        return null;
    }

    public Category findCategory(int catId){
        String sql = "SELECT * FROM category WHERE cat_id = ?";
        RowMapper<Category> rowMapper = new BeanPropertyRowMapper<>(Category.class);
        Category category = template.queryForObject(sql, rowMapper, catId);
        return category;
    }

    public Boolean deleteCategory(int catId) {
        String sql = "DELETE FROM category WHERE cat_id = ?";
        return template.update(sql, catId) < 0;
    }

    public Category updateCategory(Category c) {
        String sql = "UPDATE category SET cat_price = ?, cat_name = ?, cat_description = ?, model_name = ?, brand = ? WHERE cat_id = ?";
        template.update(sql, c.getCatPrice(), c.getCatName(), c.getCatDescription(), c.getModelName(), c.getBrand(), c.getCatId());
        return null;
    }

    public List<Category> findByKeyword(@Param("keyword") String keyword) {
        String sql = "SELECT * FROM category WHERE cat_name LIKE '%' ? '%'";
        RowMapper<Category> rowMapper = new BeanPropertyRowMapper<>(Category.class);
        return template.query(sql, rowMapper, keyword);
    }

}
