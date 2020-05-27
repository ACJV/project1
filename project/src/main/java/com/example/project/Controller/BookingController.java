package com.example.project.Controller;

import com.example.project.Model.Address;
import com.example.project.Model.Booking;
import com.example.project.Service.AddressService;
import com.example.project.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookingController {
    @Autowired
    BookingService bookingService;

    @GetMapping("/booking/")
    public String display(Model model) {
        List<Booking> bookingList = bookingService.fetchAll();
        model.addAttribute("bookingL", bookingList);
        return "home/booking-test";
    }

    @PostMapping("/booking/")
    public String create(@ModelAttribute Booking b) {
        bookingService.addBooking(b);
        return "redirect:/booking/";
    }



}
