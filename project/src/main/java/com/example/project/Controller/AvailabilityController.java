package com.example.project.Controller;



import com.example.project.Model.Vehicle;
import com.example.project.Service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
public class AvailabilityController {
    @Autowired
    AvailabilityService availabilityService;

    @GetMapping("/availability")
    public String availability(Model model, String startDate, String endDate){
        if(startDate != null && endDate != null) {
            if(isDate(startDate, endDate)) {
                model.addAttribute("AvailableVehicles", availabilityService.fetchAvailabilityVehicles(startDate, endDate, "Available"));
                model.addAttribute("UnAvailableVehicles", availabilityService.fetchAvailabilityVehicles(startDate, endDate, "Unavailable"));
            }
            return "home/Availability/availability";
        } else {
          return "home/Availability/availability";
        }
    }
    /*
    @GetMapping("/availablilty/{regNumber}")
    public String selectedRegNumber(@PathVariable("regNumber") String regNumber, Model model){

    }

    /*
    @GetMapping("/getAvailability")
    public String getAvailability(Model model, @ModelAttribute SearchingDates searchingDates){
        List<BookingAvailability> unAvailable = availabilityService.fetchUnavailableVehicles(searchingDates);
        return "home/Availability/availability";
    }*/

    public boolean isDate (String startDate, String endDate) {
        boolean dates = true;
        try{
            LocalDate sd = LocalDate.parse(startDate);
            LocalDate ed = LocalDate.parse(endDate);
        } catch (DateTimeParseException e) {
            dates = false;
        }

        return dates;
    }


}
