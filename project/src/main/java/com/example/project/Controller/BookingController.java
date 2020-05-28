package com.example.project.Controller;

import com.example.project.Model.Booking;
import com.example.project.Service.BookingService;
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
    @GetMapping("/newBooking")
    public String newBooking(){
        return "home/Bookings/newBooking";
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
