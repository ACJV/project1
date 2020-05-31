package com.example.project.Controller;

import com.example.project.Model.Address;
import com.example.project.Model.Booking;
import com.example.project.Model.Customer;
import com.example.project.Model.Vehicle;
import com.example.project.Service.AddressService;
import com.example.project.Service.BookingService;
import com.example.project.Service.CustomerService;
import com.example.project.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.print.Book;
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
    VehicleService vehicleService;
    CustomerService customerService;
    AddressService addressService;

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
        bookingService.updateBooking(booking.getBookingNo(), booking);
        return "redirect:/booking";
    }
    @GetMapping("/newBooking/{bookingNo}")
    public String newBooking(@PathVariable("bookingNo") int bookingNo, Model model){
        Booking booking = bookingService.findBooking(bookingNo);
        booking.setBookingStatus("Confirmed");
        model.addAttribute("booking", booking);
        return "home/Bookings/newBooking";
    }
    @GetMapping("/selectCustomerBooking/{bookingNo}")
    public String selectCustomerBooking(@PathVariable("bookingNo") String bookingNo, @ModelAttribute Customer customer, @ModelAttribute Address address){
        Customer c = customerService.addCustomer(customer);
        Address a = addressService.addAddress(address);
        return null;

    }
/*
    @GetMapping("/vehicleForBooking/{regNumber}")
    public String vehicleForBooking (@PathVariable("regNumber") String regNumber, Model model){
        //Vehicle chosen = model.addAttribute("vfb", vehicleService.findVehicle(regNumber));
        Vehicle chosen = vehicleService.findVehicle(regNumber);
        if(!chosen.equals(null)){
            Booking b = new Booking();
            b.setRegNumber(chosen.getRegNumber());
            bookingService.addBooking(b);
        }
        return "redirect:/newBooking";
    }*/
    @GetMapping("/selectCustomer")
    public String selectCustomer(){

        return null;
    }

    @GetMapping("deleteBooking/{bookingNo}")
    public String deleteBooking(@PathVariable("bookingNo") int bookingNo){
        boolean deleted = bookingService.deleteBooking(bookingNo);
        return "redirect:/booking";
    }


    @PostMapping("/createBooking")
    public String createBooking(@ModelAttribute Booking b) {
        //bookingService.addBooking(b);
        //return "redirect:/booking";
        return "home/Bookings/createBooking";
    }




}
