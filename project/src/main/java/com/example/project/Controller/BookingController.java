package com.example.project.Controller;

import com.example.project.Model.Address;
import com.example.project.Model.Booking;
import com.example.project.Model.Customer;
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
    @Autowired
    CustomerService customerService;
    @Autowired
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
    public String newBooking(@PathVariable("bookingNo") int bookingNo, Model model, Model model2){
        Booking booking = bookingService.findBooking(bookingNo);
        booking.setBookingStatus("Confirmed");
        Customer customer = customerService.findCustomer(booking.getCustomerId());
        model.addAttribute("booking", booking);
        model2.addAttribute("customer", customer);
        return "home/Bookings/newBooking";
    }
    @GetMapping("/selectCustomerBooking/{bookingNo}")
    public String selectCustomerBooking(@PathVariable("bookingNo") int bookingNo, Model model, Model model2){
        Booking booking = bookingService.findBooking(bookingNo);
        model.addAttribute("booking", booking);
        List<Customer> customerList = customerService.fetchAll();
        model2.addAttribute("customers", customerList);
        return "home/Bookings/bookingSelectCustomer";

    }

    @GetMapping("selectCustomerBooking/{bookingNo}/customer/{customerId}")
    public String selectCustomerRouting(@PathVariable("bookingNo") int bookingNo, @PathVariable("customerId") int customerId, Model model, Model model2){
        Booking booking = bookingService.findBooking(bookingNo);
        Customer customer = customerService.findCustomer(customerId);
        booking.setCustomerId(customerId);
        bookingService.updateBooking(bookingNo, booking);
        return "redirect:/newBooking/"+bookingNo;
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
