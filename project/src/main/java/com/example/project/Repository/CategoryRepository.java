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

//----------------------------------------------------------------------------------------------------------------------
    // Justé
//----------------------------------------------------------------------------------------------------------------------

    // Fetches all available Categories from the database
    public List<Category> fetchAll(){
        String sql = "SELECT * FROM category";
        RowMapper<Category> rowMapper = new BeanPropertyRowMapper<>(Category.class);
        return template.query(sql, rowMapper);
    }

    // Adds a new Category to the database
    public Category addCategory(Category c) {
        String sql = "INSERT INTO category (cat_price, cat_name, cat_description, model_name, brand) VALUES (?, ?, ?, ?, ?)";
        template.update(sql, c.getCatPrice(), c.getCatName(), c.getCatDescription(), c.getModelName(), c.getBrand());
        return null;
    }

    // Finds Category in the database by catId
    public Category findCategory(int catId){
        String sql = "SELECT * FROM category WHERE cat_id = ?";
        RowMapper<Category> rowMapper = new BeanPropertyRowMapper<>(Category.class);
        Category category = template.queryForObject(sql, rowMapper, catId);
        return category;
    }

    // Deletes a selected Category from the database
    public Boolean deleteCategory(int catId) {
        String sql = "DELETE FROM category WHERE cat_id = ?";
        return template.update(sql, catId) < 0;
    }

    // Updates a selected Category in the database
    public Category updateCategory(Category c) {
        String sql = "UPDATE category SET cat_price = ?, cat_name = ?, cat_description = ?, model_name = ?, brand = ? WHERE cat_id = ?";
        template.update(sql, c.getCatPrice(), c.getCatName(), c.getCatDescription(), c.getModelName(), c.getBrand(), c.getCatId());
        return null;
    }

    // Finds a list of Categories in the database based on the specified keyword
    public List<Category> findByKeyword(@Param("keyword") String keyword) {
        String sql = "SELECT * FROM category WHERE cat_name LIKE '%' ? '%'";
        RowMapper<Category> rowMapper = new BeanPropertyRowMapper<>(Category.class);
        return template.query(sql, rowMapper, keyword);
    }

}
