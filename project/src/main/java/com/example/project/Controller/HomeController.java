package com.example.project.Controller;

import com.example.project.Model.Booking;
import com.example.project.Model.Vehicle;
import com.example.project.Service.AvailabilityService;
import com.example.project.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class HomeController {

//----------------------------------------------------------------------------------------------------------------------
    // @Justé
//----------------------------------------------------------------------------------------------------------------------

    // Will link you to index.html
    @GetMapping("/start")
    public String index() {
       return "home/Index/index";
    }

    // Will redirect you accordingly depending on the position selected
    @GetMapping("/")
    public String indexByPosition(@Param("position") String position) {
        // If logged in as a staff member - will redirect you to /bookingHome
        if (position.equals("staff")) {
            return "redirect:/bookingHome";
        // If logged in as a mechanic - will redirect you to /mechanic
        } else if (position.equals("mechanic")) {
            return "redirect:/mechanic";
        // If logged in as an owner - will redirect you to /vehicle
        } else if (position.equals("owner")) {
            return "redirect:/vehicle";
        // If nothing selected - will redirect you to /start
        } else return "redirect:/start";
    }

}
