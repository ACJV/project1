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

    @GetMapping("/start")
    public String index() {
       return "home/Index/index";
    }

    @GetMapping("/")
    public String indexByPosition(@Param("position") String position) {
        if (position.equals("staff")) {
            return "redirect:/bookingHome";
        } else if (position.equals("mechanic")) {
            return "redirect:/mechanic";
        } else if (position.equals("owner")) {
            return "redirect:/vehicle";
        } else return "redirect:/start";
    }

}
