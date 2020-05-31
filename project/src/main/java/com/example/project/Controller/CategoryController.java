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

    @GetMapping("/category")
    public String category(Model model, String keyword) {
        List<Category> categoryList = categoryService.fetchAll();
        if (keyword != null) {
            model.addAttribute("categories", categoryService.findByKeyword(keyword));
        } else {
            model.addAttribute("categories", categoryList);
        }
        return "home/Category/category";
    }


    @GetMapping("/createCategory")
    public String createCategory(Model model) {
        List<Category> categoryList = categoryService.fetchAll();
        model.addAttribute("categories", categoryList);
        return "home/Category/createCategory";
    }


    @PostMapping("/createCategory")
    public String createCategory(@ModelAttribute Category category) {
        categoryService.addCategory(category);
        return "redirect:/category";
    }


    @GetMapping("/deleteCategory/{catId}")
    public String deleteCategory(@PathVariable("catId") int catId) {
        categoryService.deleteCategory(catId);
        return "redirect:/category";
    }


    @GetMapping("/updateCategory/{catId}")
    public String updateCategory(@PathVariable("catId") int catId, Model model, Model model1, Model model2, Model model3)
    {
        List<Category> categoryList = categoryService.fetchAll();
        model.addAttribute("categories", categoryList);

        model1.addAttribute("category", categoryService.findCategory(catId));
        return "home/Category/updateCategory";
    }


    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category) {
        categoryService.updateCategory(category);
        return "redirect:/category";
    }

    /*

    @GetMapping("/chooseCategory")
    public String chooseCategory(@ModelAttribute Vehicle vehicle, Model model, Model model1, String keyword) {
        model.addAttribute("vehicle", vehicle);

        List<Category> categoryList = categoryService.fetchAll();
        if (keyword != null) {
            model1.addAttribute("categories", categoryService.findByKeyword(keyword));
        } else {
            model1.addAttribute("categories", categoryList);
        }
        return "home/Category/category";
    }


    @PostMapping("/chooseCategory/{catId}")
    public String chooseCategory(@PathVariable("catId") int catId, @ModelAttribute Vehicle vehicle, Model model) {
        vehicle.setCatId(catId);
        vehicleService.addVehicle(vehicle);

        model.addAttribute(vehicle);
        return "redirect:/updateVehicle/" + vehicle.getRegNumber();
    }

     */

}
