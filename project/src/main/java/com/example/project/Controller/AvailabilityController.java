package com.example.project.Controller;



import com.example.project.Model.BookingAvailability;
import com.example.project.Model.SearchingDates;
import com.example.project.Service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class AvailabilityController {
    @Autowired
    AvailabilityService availabilityService;

    @GetMapping("/availability")
    public String availability(Model model, String startDate, String endDate){
        if(startDate != null && endDate != null) {
            model.addAttribute("vehicles", availabilityService.fetchUnavailableVehicles(startDate, endDate));
            return "home/Availability/availability";
        } else {
          return "home/Availability/availability";
        }
    }

    /*
    @GetMapping("/getAvailability")
    public String getAvailability(Model model, @ModelAttribute SearchingDates searchingDates){
        List<BookingAvailability> unAvailable = availabilityService.fetchUnavailableVehicles(searchingDates);
        return "home/Availability/availability";
    }*/


}
