package com.example.project.Controller;

import com.example.project.Model.Vehicle;
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

    @GetMapping("/create")
    public String create() {
        return "home/Vehicle/createVehicle";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Vehicle vehicle)
    {
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

  /*  @GetMapping("updateVehicle/{regNumber}")
    public String updateVehicle(@PathVariable("regNumber") String regNumber, Model model)
    {
        model.addAttribute("vehicle", vehicleService.findVehicle(regNumber));
        return "home/Vehicle/updateVehicle";
    }*/

    @PostMapping("/saveVehicle")
    public String saveVehicle(@ModelAttribute Vehicle vehicle)
    {
        vehicleService.updateVehicle(vehicle.getRegNumber(), vehicle);
        return "redirect:/vehicle";
    }

}

