package com.example.project.Controller;
import com.example.project.Data.DataManipulation;
import com.example.project.Model.Booking;
import com.example.project.Model.Vehicle;
import com.example.project.Service.AvailabilityService;
import com.example.project.Service.BookingService;
import com.example.project.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


/* @Author: Ástþór Arnar Bragason
* @Author2: Justé (Methods marked especially)
*
* Availability Controller's main purpose is to receive date input by user and to utilize the Availability Repository
* to check which vehicles are available and unavailable for the selected period.
* After retrieving lists of available and unavailable vehicles, the controller displays html file for the user to select
* a vehicle. User selects vehicle and controller instantiates a booking with dates, regNumber and price set,
* and adds to database.
* */

@Controller
public class AvailabilityController {
    @Autowired
    AvailabilityService availabilityService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    BookingService bookingService;
    @Autowired
    DataManipulation dataManipulation;

    // Availability:
    // Gets
    @GetMapping("/availability")
    public String availability(Model model, String startDate, String endDate){
        model.addAttribute("today", dataManipulation.getTodaysDate());
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

            b.setTotalPrice(dataManipulation.calculateTotalPriceConfirmed(b));

            bookingService.addBooking(b);
            Booking booking = bookingService.findBookingNumber(b);
            int bookingNo = booking.getBookingNo();
            return "redirect:/findCustomerForBooking/"+bookingNo;
        } else {
            return "home/Availability/error";
        }
    }


    /*
        // Justé:
     */


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
