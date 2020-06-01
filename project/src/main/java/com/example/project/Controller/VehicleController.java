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
public class VehicleController
{
    @Autowired
    VehicleService vehicleService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    DataManipulation dataManipulation;

//----------------------------------------------------------------------------------------------------------------------
    // @Christian
//----------------------------------------------------------------------------------------------------------------------

    @GetMapping("/vehicle")
    public String vehicle(Model model, String keyword)
    {
        List<Vehicle> vehicleList = vehicleService.fetchAll();
        if (keyword != null)
        {
            model.addAttribute("vehicles", vehicleService.findByKeyword(keyword));
        } else
            {
            model.addAttribute("vehicles", vehicleList);
            }

        return "home/Vehicle/vehicle";
    }


    @GetMapping("/createVehicle")
    public String createVehicle(Model model1, Model model2, Model model3)
    {
        model1.addAttribute("year", dataManipulation.getTodaysYear());

        List<Category> categoryList = categoryService.fetchAll();
        model2.addAttribute("numberOfCategories", categoryList.size());

        List<Vehicle> vehicleList = vehicleService.fetchAll();
        model3.addAttribute("vehicles", vehicleService.fetchAll());

        return "home/Vehicle/createVehicle";
    }


    @PostMapping("/createVehicle")
    public String createVehicle(@ModelAttribute Vehicle vehicle)
    {
        vehicle.setRegNumber(vehicle.getRegNumber().toUpperCase());
        vehicleService.addVehicle(vehicle);
        return "redirect:/vehicle";
    }


    @GetMapping("/viewVehicle/{regNumber}")
    public String viewVehicle(@PathVariable("regNumber") String regNumber, Model model)
    {
        model.addAttribute("vehicle", vehicleService.findVehicle(regNumber));
        return "home/Vehicle/viewVehicle";
    }


    @GetMapping("/deleteVehicle/{regNumber}")
    public String delete(@PathVariable("regNumber") String regNumber)
    {
        boolean del = vehicleService.deleteVehicle((regNumber));
        if (del) {
            return "redirect:/vehicle";
        } else {
            return "redirect:/vehicle";
        }
    }


    @GetMapping("/updateVehicle/{regNumber}")
    public String updateVehicle(@PathVariable("regNumber") String regNumber, Model model, Model model1, Model model2, Model model3)
    {
        model.addAttribute("year", dataManipulation.getTodaysYear());

        List<Category> categoryList = categoryService.fetchAll();
        model1.addAttribute("numberOfCategories", categoryList.size());

        List<Vehicle> vehicleList = vehicleService.fetchAll();
        model2.addAttribute("vehicles", vehicleList);

        model3.addAttribute("vehicle", vehicleService.findVehicle(regNumber));
        return "home/Vehicle/updateVehicle";
    }


    @PostMapping("/saveVehicle")
    public String saveVehicle(@ModelAttribute Vehicle vehicle)
    {
        vehicle.setRegNumber(vehicle.getRegNumber().toUpperCase());
        vehicleService.updateVehicle(vehicle);
        return "redirect:/vehicle";
    }


    @GetMapping("/vehicleFull")
    public String vehicleFull(Model model, Model model1)
    {
        List<Vehicle> vehicleList = vehicleService.fetchAll();
        model.addAttribute("vehicles", vehicleList);
        List<Category> categoryList = categoryService.fetchAll();
        model1.addAttribute("categories", categoryList);

        return "home/Vehicle/vehicleFull";
    }

}

