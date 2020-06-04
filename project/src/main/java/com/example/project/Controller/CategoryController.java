package com.example.project.Controller;

import com.example.project.Data.DataManipulation;
import com.example.project.Model.Category;
import com.example.project.Model.Vehicle;
import com.example.project.Service.CategoryService;
import com.example.project.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

//----------------------------------------------------------------------------------------------------------------------
    // @Juste
//----------------------------------------------------------------------------------------------------------------------

    // Gets all Categories and searches for keyword in category name if the keyword's field is not null
    @GetMapping("/category")
    public String category(Model model, String keyword) {
        // Fetches a list of all Categories form the database
        List<Category> categoryList = categoryService.fetchAll();
        // Checks whether the keyword (search field provided in UI) was filled or left empty
        // If there was some input (keyword) in the search field
        if (keyword != null) {
            // Then a list of Categories filtered by the entered keyword will be fetched and added to a model
            model.addAttribute("categories", categoryService.findByKeyword(keyword));
        // If there was no input in the search field
        } else {
            // Then a regular list of all existing Categories is being fetched from the database and added to a model
            model.addAttribute("categories", categoryList);
        }

        // Will link you to category.html
        return "home/Category/category";
    }


    // Fetches all Categories from the database, adds them to the model and links you to createCategory.html
    @GetMapping("/createCategory")
    public String createCategory(Model model) {
        List<Category> categoryList = categoryService.fetchAll();
        model.addAttribute("categories", categoryList);
        return "home/Category/createCategory";
    }


    // Adds a newly created Category object to a database table and redirects you to /category
    @PostMapping("/createCategory")
    public String createCategory(@ModelAttribute Category category) {
        categoryService.addCategory(category);
        return "redirect:/category";
    }


    // Deletes a selected Category by it's catId from the database and redirects you to /category
    @GetMapping("/deleteCategory/{catId}")
    public String deleteCategory(@PathVariable("catId") int catId) {
        categoryService.deleteCategory(catId);
        return "redirect:/category";
    }


    // Fetches all Categories from the database, as well as finds a specified category. Adds them both to two separate
    // models and links you to updateCategory.html
    @GetMapping("/updateCategory/{catId}")
    public String updateCategory(@PathVariable("catId") int catId, Model model, Model model1)
    {
        List<Category> categoryList = categoryService.fetchAll();
        model.addAttribute("categories", categoryList);

        model1.addAttribute("category", categoryService.findCategory(catId));
        return "home/Category/updateCategory";
    }


    // Updates a selected Category in the database and redirects you to /category
    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category) {
        categoryService.updateCategory(category);
        return "redirect:/category";
    }

}
