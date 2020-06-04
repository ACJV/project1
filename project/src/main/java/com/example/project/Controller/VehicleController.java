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
        //fetches all vehicles from db using vehicle.repo
        List<Vehicle> vehicleList = vehicleService.fetchAll();
        if (keyword != null)
        {
            //model showing vehicles found by keyword (based on reg_number)
            model.addAttribute("vehicles", vehicleService.findByKeyword(keyword));
        } else
            {
                // model that shows vehiclelist
            model.addAttribute("vehicles", vehicleList);
            }
        // returns to the vehicle.html
        return "home/Vehicle/vehicle";
    }

    //creates vehicle page, that opens for the category and vehicles list
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

    // using model attributes from addvehicle in vehicle repo, to create a new vehicle with all the attributes. sets PK in db (reg_number) to UPPERCASE
    @PostMapping("/createVehicle")
    public String createVehicle(@ModelAttribute Vehicle vehicle)
    {
        vehicle.setRegNumber(vehicle.getRegNumber().toUpperCase());
        vehicleService.addVehicle(vehicle);
        return "redirect:/vehicle";
    }

    // gets view vehicle page wtih pathvar reg_number to find vehicle in db
    @GetMapping("/viewVehicle/{regNumber}")
    public String viewVehicle(@PathVariable("regNumber") String regNumber, Model model)
    {
        model.addAttribute("vehicle", vehicleService.findVehicle(regNumber));
        return "home/Vehicle/viewVehicle";
    }

    // deletes vehicle using regnumber as pathvar to find vehicle in db (reg_number = PK)
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

    //update vehicle page
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

    //saves vehicle ised in view vehicle page to update vehicle
    @PostMapping("/saveVehicle")
    public String saveVehicle(@ModelAttribute Vehicle vehicle)
    {
        vehicle.setRegNumber(vehicle.getRegNumber().toUpperCase());
        vehicleService.updateVehicle(vehicle);
        return "redirect:/vehicle";
    }

    //displays html with all vehicles and their caterogies
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

