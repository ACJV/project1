package com.example.project.Controller;

import com.example.project.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    @Autowired
    BookingService bookingService;



}
