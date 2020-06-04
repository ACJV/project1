package com.example.project.Controller;

import com.example.project.Model.Vehicle;
import com.example.project.Service.AvailabilityService;
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
public class MechanicController {
    @Autowired
    AvailabilityService availabilityService;
    @Autowired
    VehicleService vehicleService;

//----------------------------------------------------------------------------------------------------------------------
    //@Juste
//----------------------------------------------------------------------------------------------------------------------

    // Will fetch the list of all Vehicles ending & not ending today from the database and link you to mechanic.html
    @GetMapping("/mechanic")
    public String mechanic (Model model1, Model model2) {
        // Fetches a list of Vehicles ending today
        List<Vehicle> vehiclesEndingToday = availabilityService.fetchVehiclesEndingToday();
        // Adds a list to the model
        model1.addAttribute("vehiclesToday", vehiclesEndingToday);
        // Fetches a list of Vehicles not ending today
        List<Vehicle> vehiclesNotEndingToday = availabilityService.fetchVehiclesNotEndingToday();
        // Adds a list to the model
        model2.addAttribute("vehiclesOther", vehiclesNotEndingToday);

        // Links you to mechanic.html
        return "home/Index/mechanic";
    }


    // Will fetch the list of a specified Vehicle in order to access and modify it's data and link you to updateMechanic.html
    @GetMapping("/updateMechanic/{regNumber}")
    public String updateMechanic (@PathVariable("regNumber") String regNumber, Model model, Model model1, Model model2)
    {
        // Fetches a Vehicle from the database based on it's Registration Number nd adds it to the model
        model.addAttribute("vehicle", vehicleService.findVehicle(regNumber));
        // Fetches a list of Vehicles ending today
        List<Vehicle> vehiclesEndingToday = availabilityService.fetchVehiclesEndingToday();
        // Adds a list to the model
        model1.addAttribute("vehiclesToday", vehiclesEndingToday);
        // Fetches a list of Vehicles not ending today
        List<Vehicle> vehiclesNotEndingToday = availabilityService.fetchVehiclesNotEndingToday();
        // Adds a list to the model
        model2.addAttribute("vehiclesOther", vehiclesNotEndingToday);

        // Links you to updateMechanic.html
        return "home/Vehicle/updateMechanic";
    }


    // Will update the Vehicle in the database and redirect you to /mechanic
    @PostMapping("/saveMechanic")
    public String saveMechanic (@ModelAttribute Vehicle vehicle) {
        vehicleService.updateVehicleMechanic(vehicle);
        return "redirect:/mechanic";
    }


}
