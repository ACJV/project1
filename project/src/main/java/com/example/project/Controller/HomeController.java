package com.example.project.Controller;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    @GetMapping("/")
    public String vehicle()
        return "home/vehicle";
}
