package com.example.project.Controller;

import com.example.project.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class VehicleController
{
    @Autowired
    VehicleService vehicleService;

    @GetMapping("/vehicle")
    public String vehicle(Model model)
    {
        List<Vehicle> vehicleList = vehicleService.fetchAll();
        model.addAttribute("vehicles", vehicleList);
        return "home/vehicle/vehicle";
    }

    @GetMapping("/create")
    public String create()
    {
        return "home/vehicle/create";
    }

    @PostMapping
        public String create(@ModelAttribute Vehicle vehicle)
        {
            vehicleService.addVehicle(vehicle);
            return "redirect:/vehicle";
        }

    @GetMapping ("/viewVehicle/{regNumber}")
    public String viewVehicle(@PathVariable("regNumber") String regNumber, Model model)
    {
        model.addAttribute("vehicle", vehicleService.findVehicle(regNumber));
        return "home/vehicle/viewVehicle";
    }
}
