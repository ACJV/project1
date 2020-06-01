package com.example.project.Controller;

import com.example.project.Model.Address;
import com.example.project.Data.DataManipulation;
import com.example.project.Model.Booking;
import com.example.project.Model.Customer;
import com.example.project.Service.AddressService;
import com.example.project.Model.Vehicle;
import com.example.project.Service.AvailabilityService;
import com.example.project.Service.BookingService;
import com.example.project.Service.CustomerService;
import com.example.project.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
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
    CustomerService customerService;
    @Autowired
    AddressService addressService;
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

    // WiewBooking includes updating and deleting booking
    @GetMapping("/viewBooking/{bookingNo}")
    public String viewBooking(@PathVariable("bookingNo") int bookingNo, Model model){
        model.addAttribute("booking", bookingService.findBooking(bookingNo));
        return "home/Bookings/viewBooking";
    }
    //Deletes the booking from the database
    @GetMapping("deleteBooking/{bookingNo}")
    public String deleteBooking(@PathVariable("bookingNo") int bookingNo){
        boolean deleted = bookingService.deleteBooking(bookingNo);
        return "redirect:/booking";
    }
    //Saves Booking in database
    @PostMapping("/saveBooking")
    public String saveBooking(@ModelAttribute Booking booking){
        bookingService.updateBooking(booking);
        return "redirect:/booking";
    }

    //------------------------------------------------------------------------------------------------------------------


    // From Availability
    // Displays all customers and possibility to select existing customer for the booking.
    @GetMapping("/findCustomerForBooking/{bookingNo}")
    public String findCustomerForBooking(@PathVariable("bookingNo") int bookingNo, Model model, Model model2){
        // find and get booking from database
        Booking booking = bookingService.findBooking(bookingNo);
        // Model used so href in html can use bookingNo as pathVariable
        model.addAttribute("booking", booking);
        // List of Customers to be displayed and to offer selection
        List<Customer> customerList = customerService.fetchAll();
        // Model used to display customers using thymeleaf
        model2.addAttribute("customers", customerList);
        // Return html page for selecting Customer
        return "home/Bookings/bookingSelectCustomer";
    }

    // After user selects customer, the following GetMapping uses pathVariables to link booking to chosen customer
    @GetMapping("selectCustomerForBooking/{bookingNo}/customer/{customerId}")
    public String selectCustomerForRouting(@PathVariable("bookingNo") int bookingNo,
                                        @PathVariable("customerId") int customerId, Model model, Model model2){
        // Find and Get booking
        Booking booking = bookingService.findBooking(bookingNo);
        // Set booking customerId for selected customer
        booking.setCustomerId(customerId);
        // Update Booking with selected customers' Id
        bookingService.updateBooking(booking);
        // Redirect to display newBooking and option to change defaults such as pickUp/dropOff location and extras
        return "redirect:/newBooking/"+bookingNo;
    }

    // NewBooking displays booking as is and offers user to change some of the fields
    @GetMapping("/newBooking/{bookingNo}")
    public String newBooking(@PathVariable("bookingNo") int bookingNo, Model model, Model model2, Model model3,
                             Model model4, Model model5){
        // Find and Get Booking with PathVariable
        Booking booking = bookingService.findBooking(bookingNo);
        // Set BookingStatus as confirmed
        booking.setBookingStatus("Confirmed");
        // Find and Get Customer
        Customer customer = customerService.findCustomer(booking.getCustomerId());
        Address addressCustomer = addressService.findAddress(customer.getAddressId());
        // Use array of 2 addresses, one for pickUp and one for dropOff:
        Address addressPickUp = addressService.findAddress(booking.getPickUpId());
        Address addressDropOff = addressService.findAddress(booking.getDropOffId());
        //Address[] addressArray = new Address[2];
        //addressArray[0] = addressService.findAddress(booking.getPickUpId());
        //addressArray[1] = addressService.findAddress(booking.getDropOffId());

        // Use model to display booking, customer and address information and options to update
        model.addAttribute("booking", booking);
        model2.addAttribute("customer", customer);
        model3.addAttribute("addressPickUp", addressPickUp);
        model4.addAttribute("addressDropOff", addressDropOff);
        model5.addAttribute("customerAddress", addressCustomer);
        //model3.addAttribute("address", addressArray);
        return "home/Bookings/newBooking";
    }

    //------------------------------------------------------------------------------------------------------------------
    // Following section is for creating new pickUp and dropOff address for booking being created or updated
    @GetMapping("/createBookingAddressPickUp/{bookingNo}")
    public String createBookingAddressPickUp(@PathVariable("bookingNo") int bookingNo, Model model){
        Booking booking = bookingService.findBooking(bookingNo);
        model.addAttribute("booking", booking);
        return "home/Bookings/createBookingAddressPickUp";
    }
    @PostMapping("/createBookingAddressPickUp/{bookingNo}")
    public String createBookingAddressPickUp(@PathVariable("bookingNo") int bookingNo, @ModelAttribute Address address){
        addressService.addAddress(address);
        Address a = addressService.findAddressId(address);
        Booking booking = bookingService.findBooking(bookingNo);
        booking.setPickUpId(a.getAddressId());
        bookingService.updateBooking(bookingNo, booking);
        return "redirect:/newBooking/"+bookingNo;
    }
    @GetMapping("/selectCustomer")
    public String selectCustomer(){

    @GetMapping("/createBookingAddressDropOff/{bookingNo}")
    public String createBookingAddressDropOff(@PathVariable("bookingNo") int bookingNo, Model model){
        Booking booking = bookingService.findBooking(bookingNo);
        model.addAttribute("booking", booking);
        return "home/Bookings/createBookingAddressDropOff";
    }
    @PostMapping("/createBookingAddressDropOff/{bookingNo}")
    public String createBookingAddressDropOff(@PathVariable("bookingNo") int bookingNo, @ModelAttribute Address address){
        addressService.addAddress(address);
        Address a = addressService.findAddressId(address);
        Booking booking = bookingService.findBooking(bookingNo);
        booking.setDropOffId(a.getAddressId());
        bookingService.updateBooking(bookingNo, booking);
        return "redirect:/newBooking/"+bookingNo;
    }
    //------------------------------------------------------------------------------------------------------------------

    @PostMapping("/createBooking")
    public String createBooking(@ModelAttribute Booking b) {
        bookingService.addBooking(b);
        return "redirect:/booking";
    }




}
