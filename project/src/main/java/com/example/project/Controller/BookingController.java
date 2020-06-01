package com.example.project.Controller;

import com.example.project.Data.DataManipulation;
import com.example.project.Model.Booking;
import com.example.project.Model.Vehicle;
import com.example.project.Service.AvailabilityService;
import com.example.project.Service.BookingService;
import com.example.project.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
// Idea on commenting who did what
//----------------------------------------------------------------------------------------------------------------------
    // @Justé
//----------------------------------------------------------------------------------------------------------------------

@Controller
public class BookingController {
    @Autowired
    BookingService bookingService;
    @Autowired
    AvailabilityService availabilityService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    DataManipulation dataManipulation;


    @GetMapping("/booking")
    public String display(Model model) {
        List<Booking> bookingList = bookingService.fetchAll();
        model.addAttribute("bookingL", bookingList);
        return "home/Bookings/booking";
    }


    @GetMapping("/createBooking")
    public String createBooking(){
        return "home/Bookings/createBooking";
    }


    @GetMapping("/bookingHome")
    public String bookingHome(Model model, Model model1, Model model2, Model model3) {
        List<Booking> bookingsEndingToday = availabilityService.fetchBookingsEndingToday();
        model.addAttribute("bookingsToday", bookingsEndingToday);
        List<Booking> confirmedBookings = availabilityService.fetchConfirmedBookings();
        model1.addAttribute("confirmedBookings", confirmedBookings);
        List<Booking> cancelledBookings = availabilityService.fetchCancelledBookings();
        model2.addAttribute("cancelledBookings", cancelledBookings);
        List<Booking> finishedBookings = availabilityService.fetchFinishedBookings();
        model3.addAttribute("finishedBookings", finishedBookings);

        return "home/Index/staff";
    }


    @GetMapping("/bookingFinish/{bookingNo}")
    public String bookingFinish(@PathVariable("bookingNo") int bookingNo, Model model, Model model1, Model model2, Model model3, Model model4, Model model5){
        Booking b = bookingService.findBooking(bookingNo);
        model.addAttribute("booking", b);
        model1.addAttribute("oldOdometer", vehicleService.findVehicle(b.getRegNumber()).getOdometer());

        List<Booking> bookingsEndingToday = availabilityService.fetchBookingsEndingToday();
        model2.addAttribute("bookingsToday", bookingsEndingToday);
        List<Booking> confirmedBookings = availabilityService.fetchConfirmedBookings();
        model3.addAttribute("confirmedBookings", confirmedBookings);
        List<Booking> cancelledBookings = availabilityService.fetchCancelledBookings();
        model4.addAttribute("cancelledBookings", cancelledBookings);
        List<Booking> finishedBookings = availabilityService.fetchFinishedBookings();
        model5.addAttribute("finishedBookings", finishedBookings);

        return "home/Bookings/bookingFinish";
    }


    @PostMapping("/bookingFinish")
    public String bookingFinish(@ModelAttribute Booking booking, @Param("odometer") int odometer, @Param("isLowTank") boolean isLowTank){
        double newTotalPrice = dataManipulation.calculateTotalPriceFinished(booking.getBookingNo(), isLowTank, odometer);
        booking.setTotalPrice(newTotalPrice);
        booking.setBookingStatus("Finished");
        bookingService.updateBookingFinished(booking);
        String regNumber = bookingService.findBooking(booking.getBookingNo()).getRegNumber();

        Vehicle v = vehicleService.findVehicle(regNumber);
        v.setOdometer(odometer);
        vehicleService.updateVehicle(v);

        return "redirect:/bookingHome";
    }


//----------------------------------------------------------------------------------------------------------------------
    //@Ástþór
//----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/viewBooking/{bookingNo}")
    public String viewBooking(@PathVariable("bookingNo") int bookingNo, Model model){
        model.addAttribute("booking", bookingService.findBooking(bookingNo));
        return "home/Bookings/viewBooking";
    }

    @PostMapping("/saveBooking")
    public String saveBooking(@ModelAttribute Booking booking){
        bookingService.updateBooking(booking);
        return "redirect:/booking";
    }

    @GetMapping("deleteBooking/{bookingNo}")
    public String deleteBooking(@PathVariable("bookingNo") int bookingNo){
        boolean deleted = bookingService.deleteBooking(bookingNo);
        return "redirect:/booking";
    }


    @PostMapping("/createBooking")
    public String createBooking(@ModelAttribute Booking b) {
        bookingService.addBooking(b);
        return "redirect:/booking";
    }




}
