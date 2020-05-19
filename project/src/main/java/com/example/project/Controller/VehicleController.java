package com.example.project.Controller;

import com.example.project.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

}
