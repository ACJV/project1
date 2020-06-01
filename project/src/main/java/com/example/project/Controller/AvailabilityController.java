package com.example.project.Controller;



import com.example.project.Model.Booking;
import com.example.project.Model.BookingAvailability;
import com.example.project.Model.Vehicle;
import com.example.project.Service.AvailabilityService;
import com.example.project.Service.BookingService;
import com.example.project.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.print.Book;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AvailabilityController {
    @Autowired
    AvailabilityService availabilityService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    BookingService bookingService;

    @GetMapping("/availability")
    public String availability(Model model, String startDate, String endDate){
        if(startDate != null && endDate != null) {
            if(isDate(startDate, endDate)) {
                model.addAttribute("AvailableVehicles", availabilityService.fetchAvailabilityVehicles(startDate, endDate, "Available"));
                model.addAttribute("UnAvailableVehicles", availabilityService.fetchAvailabilityVehicles(startDate, endDate, "Unavailable"));
                //Vehicle chosen = vehicleService.findVehicle(regNumber);
                Booking tester = new Booking();
                tester.setPickUpDate(startDate);
                tester.setDropOffDate(endDate);
                //tester.setVehicleRegNumber(chosen.getRegNumber());
                List<Booking> selected = new ArrayList<>();
                selected.add(tester);
                model.addAttribute("datesL", selected);
            }
            return "home/Availability/availability";
        } else {
          return "home/Availability/availability";
        }
    }
    @GetMapping("/dates/start/{startDate}/end/{endDate}/reg/{regNumber}")
    public String dates (@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate, @PathVariable("regNumber") String regNumber){
        Vehicle chosen = vehicleService.findVehicle(regNumber);
        if(!chosen.equals(null)){
            Booking b = new Booking();
            b.setRegNumber(regNumber);
            b.setPickUpDate(startDate);
            b.setDropOffDate(endDate);
            b.setPickUpId(1);
            b.setDropOffId(1);
            bookingService.addBooking(b);
            Booking booking = bookingService.findBookingNumber(b);
            int bookingNo = booking.getBookingNo();
            return "redirect:/selectCustomerBooking/"+bookingNo;
        } else {
            return "home/Availability/error";
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
