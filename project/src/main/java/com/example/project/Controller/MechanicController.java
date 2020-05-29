package com.example.project.Controller;

import com.example.project.Model.Vehicle;
import com.example.project.Service.AvailabilityService;
import com.example.project.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

    @GetMapping("/mechanic")
    public String mechanic (Model model1, Model model2) {
        List<Vehicle> vehiclesEndingToday = availabilityService.fetchVehiclesEndingToday();
        model1.addAttribute("vehiclesToday", vehiclesEndingToday);
        List<Vehicle> vehiclesNotEndingToday = availabilityService.fetchVehiclesNotEndingToday();
        model2.addAttribute("vehiclesOther", vehiclesNotEndingToday);
        return "home/Index/mechanic";
    }

    @PostMapping("/mechanic")
    public String mechanic (String regNumber, String operational, String oComment) {
        vehicleService.updateVehicleMechanic(regNumber, operational, oComment);

        return "redirect:/mechanic";
    }
}
