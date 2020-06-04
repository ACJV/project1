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
* Contributing author for method called below: Justé
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

//----------------------------------------------------------------------------------------------------------------------
    //@Ástþór Arnar (unless otherwise mentioned for specific method)
//----------------------------------------------------------------------------------------------------------------------


    // Availability: Create Booking Step 1
    @GetMapping("/availability")
    public String availability(Model model, String startDate, String endDate){
        // @Juste:
        model.addAttribute("today", dataManipulation.getTodaysDate());
        // @Ástþór:
        // Checks if there is startDate and endDate have been selected by user,
        if(startDate != null && endDate != null) {
            // Uses isDate method (Bottom of this class - returns boolean value)
            // to use try catch method and parse string to LocalDate to see if they are dates.
            if(isDate(startDate, endDate)) {
                // uses modelAttribute to display array of available and unavailable vehicles.
                model.addAttribute("AvailableVehicles", availabilityService.fetchAvailabilityVehicles(startDate, endDate, "Available"));
                model.addAttribute("UnAvailableVehicles", availabilityService.fetchAvailabilityVehicles(startDate, endDate, "Unavailable"));
                // Instantiate booking object, setting pickUp and dropOff date:
                Booking tester = new Booking();
                tester.setPickUpDate(startDate);
                tester.setDropOffDate(endDate);
                // Creates list for single booking to use dates selected as PathVariable for GetMapping method below.
                List<Booking> selected = new ArrayList<>();
                selected.add(tester);
                // Model to display and use selected dates.
                model.addAttribute("datesL", selected);
            }
            // Returns availability page
            return "home/Availability/availability";
        } else { // if not dates - Will call simple error page and link to go back to page - should never happen
          return "home/Availability/availability";
        }
    }

    // Create booking step 2:
    // uses startDate, endDate and regNumber from available vehicle as PathVariables to instantiate booking.
    @GetMapping("/dates/start/{startDate}/end/{endDate}/reg/{regNumber}")
    public String dates (@PathVariable("startDate") String startDate,
                         @PathVariable("endDate") String endDate,
                         @PathVariable("regNumber") String regNumber){
        // Although it shouldn't be possible for repository to return a null value,
        // this was added just in case - would be removed in future iterations while condoning unit testing.
        Vehicle chosen = vehicleService.findVehicle(regNumber);
        if(!chosen.equals(null)){
            // Instantiate booking, set dates and office addressId for pickUp and dropOff locations
            Booking b = new Booking();
            b.setRegNumber(regNumber);
            b.setPickUpDate(startDate);
            b.setDropOffDate(endDate);
            b.setPickUpId(1);
            b.setDropOffId(1);
            // Calculate total price
            b.setTotalPrice(dataManipulation.calculateTotalPriceConfirmed(b));
            // Add booking to database
            bookingService.addBooking(b);
            // Find bookingNumber by checking for values set
            Booking booking = bookingService.findBookingNumber(b);
            // Booking Number used to pass on booking information for next method, using PathVariable
            int bookingNo = booking.getBookingNo();
            // redirects to findCustomerForBooking - Method found in BookingController.
            return "redirect:/findCustomerForBooking/"+bookingNo;
        } else {
            return "home/Availability/error";
        }
    }
    // isDate takes two strings, uses try/catch and returns boolean value set to default true except if exception occurs
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
