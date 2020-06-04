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

//----------------------------------------------------------------------------------------------------------------------
    // @Juste
//----------------------------------------------------------------------------------------------------------------------

    // Will fetch the list of all bookings from the database and link you to bookings.html
    @GetMapping("/booking")
    public String display(Model model) {
        // Fetches all bookings from database through BookingRepository
        List<Booking> bookingList = bookingService.fetchAll();
        // bookingList added as an attribute to a model
        model.addAttribute("bookingL", bookingList);
        // Returns html file booking.html
        return "home/Bookings/booking";
    }

    // Will link you to createBooking.html
    @GetMapping("/createBooking")
    public String createBooking(){
        return "home/Bookings/createBooking";
    }


    // Will fetch lists of different category bookings, place them into models and lnk you to staff.html
    @GetMapping("/bookingHome")
    public String bookingHome(Model model, Model model1, Model model2, Model model3) {
        // Fetches all Bookings ending today and places them in a List of Booking Objects
        List<Booking> bookingsEndingToday = availabilityService.fetchBookingsEndingToday();
        // Adds the list to a model
        model.addAttribute("bookingsToday", bookingsEndingToday);
        // Fetches all Confirmed Bookings and places them in a List of Booking Objects
        List<Booking> confirmedBookings = availabilityService.fetchConfirmedBookings();
        // Adds the list to a model
        model1.addAttribute("confirmedBookings", confirmedBookings);
        // Fetches all Cancelled Bookings and places them in a List of Booking Objects
        List<Booking> cancelledBookings = availabilityService.fetchCancelledBookings();
        // Adds the list to a model
        model2.addAttribute("cancelledBookings", cancelledBookings);
        // Fetches all Finshed Bookings and places them in a List of Booking Objects
        List<Booking> finishedBookings = availabilityService.fetchFinishedBookings();
        // Adds the list to a model
        model3.addAttribute("finishedBookings", finishedBookings);

        // Will link you to staff.html
        return "home/Index/staff";
    }


    // Will fetch lists of different category bookings, place them into models and lnk you to bookingFinish.html
    @GetMapping("/bookingFinish/{bookingNo}")
    public String bookingFinish(@PathVariable("bookingNo") int bookingNo, Model model, Model model1, Model model2, Model model3, Model model4, Model model5){
        // Fetches a booking found by its bookingNo
        Booking b = bookingService.findBooking(bookingNo);
        // Adds the booking to a model
        model.addAttribute("booking", b);
        // Fetches the odometer parameters and adds them to the model
        model1.addAttribute("oldOdometer", vehicleService.findVehicle(b.getRegNumber()).getOdometer());

        // Fetches all Bookings ending today and places them in a List of Booking Objects
        List<Booking> bookingsEndingToday = availabilityService.fetchBookingsEndingToday();
        // Adds the list to a model
        model2.addAttribute("bookingsToday", bookingsEndingToday);
        // Fetches all Confirmed Bookings and places them in a List of Booking Objects
        List<Booking> confirmedBookings = availabilityService.fetchConfirmedBookings();
        // Adds the list to a model
        model3.addAttribute("confirmedBookings", confirmedBookings);
        // Fetches all Cancelled Bookings and places them in a List of Booking Objects
        List<Booking> cancelledBookings = availabilityService.fetchCancelledBookings();
        // Adds the list to a model
        model4.addAttribute("cancelledBookings", cancelledBookings);
        // Fetches all Finished Bookings and places them in a List of Booking Objects
        List<Booking> finishedBookings = availabilityService.fetchFinishedBookings();
        // Adds the list to a model
        model5.addAttribute("finishedBookings", finishedBookings);

        // Will link you to bookingFinish.html
        return "home/Bookings/bookingFinish";
    }


    // Will update a Booking after its Cancellation and redirect you back to /bookingHome
    @GetMapping("/bookingCancelled/{bookingNo}")
    public String bookingCancelled(@PathVariable("bookingNo") int bookingNo){
        // Fetched a booking found by its bookingNo
        Booking b = bookingService.findBooking(bookingNo);
        // Calculates the total price after the applied cancellation fee and stores it as a double
        double newTotalPrice = dataManipulation.calculateTotalPriceCancelled(b);
        // Updates Booking's total price to newly calculated one after the Cancellation fee has been applied
        b.setTotalPrice(newTotalPrice);
        // Updates Booking's status to "Canceled"
        b.setBookingStatus("Cancelled");
        // Updates Booking in the database
        bookingService.updateBookingStatus(b);

        // Redirects you back to /bookingHome
        return "redirect:/bookingHome";
    }


    // Will update a Booking after its been marked as Finished and redirect you back to /bookingHome
    @PostMapping("/bookingFinish")
    public String bookingFinish(@ModelAttribute Booking booking, @Param("odometer") int odometer, @Param("isLowTank") boolean isLowTank){
        // Calculates the total price after marking the Booking as finished and providing additional information about new odometer parameters and whether the tank was less than 1/2 full
        double newTotalPrice = dataManipulation.calculateTotalPriceFinished(booking.getBookingNo(), isLowTank, odometer);
        // Updates Booking's total price to newly calculated one after the additional fees based on odometer and fuel tank after drop off
        booking.setTotalPrice(newTotalPrice);
        // Updates Booking's status to "Finished"
        booking.setBookingStatus("Finished");
        // Updates Booking in the database
        bookingService.updateBookingStatus(booking);
        // Fetches the dropped off Vehicle's Registration Number
        String regNumber = bookingService.findBooking(booking.getBookingNo()).getRegNumber();

        // Finds the Vehicle based on it's Registration Number
        Vehicle v = vehicleService.findVehicle(regNumber);
        // Sets Vehicle's odometer to the updated one after the drop off
        v.setOdometer(odometer);
        // Updates Vehicle in the database
        vehicleService.updateVehicle(v);

        // Redirects you back to /bookingHome
        return "redirect:/bookingHome";
    }


//----------------------------------------------------------------------------------------------------------------------
    //@Ástþór
//----------------------------------------------------------------------------------------------------------------------
    // General methods not being used by gui in current version
        // Kept here as they are to be used in future iterations after a few changes for better functionality

    // WiewBooking includes updating and deleting booking - Currently mapped with GUI, but confirmed tested and works
    @GetMapping("/viewBooking/{bookingNo}")
    public String viewBooking(@PathVariable("bookingNo") int bookingNo, Model model){
        model.addAttribute("booking", bookingService.findBooking(bookingNo));
        return "home/Bookings/viewBooking";
    }
    //Deletes the booking from the database - Currently not mapped with GUI, but confirmed tested and works
    @GetMapping("deleteBooking/{bookingNo}")
    public String deleteBooking(@PathVariable("bookingNo") int bookingNo){
        boolean deleted = bookingService.deleteBooking(bookingNo);
        return "redirect:/booking";
    }

    //------------------------------------------------------------------------------------------------------------------
    // Method below is used to save booking - Last step for creating new booking.

    //Saves Booking in database - called by newBooking.
    @PostMapping("/saveBooking")
    public String saveBooking(@ModelAttribute Booking booking){ // Instantiates Booking with ModelAttribute
        //Sets booking price by calling dataManipulation to calculate
        booking.setTotalPrice(dataManipulation.calculateTotalPriceConfirmed(booking));
        // Updates booking through BookingService to BookingRepository
        bookingService.updateBooking(booking);
        // Redirects to Booking Page
        return "redirect:/booking";
    }


    //------------------------------------------------------------------------------------------------------------------
    // Methods below are used to finding and selecting customer for the booking.
        // Future iterations would allow for creating new customer using same methodology as create PickUp
        // and DropOff addresses.

    // From Availability
    // Displays all customers and possibility to select existing customer for the booking.
    // Method that calls this one is in AvailabilityController and passes on the BookingNumber after instantiating
    // booking by using @PathVariable
    @GetMapping("/findCustomerForBooking/{bookingNo}")
    public String findCustomerForBooking(@PathVariable("bookingNo") int bookingNo, Model model, Model model2){
        // Using PathVariable to find and get booking from database with booking number
        Booking booking = bookingService.findBooking(bookingNo);
        // Model used so href in html can use bookingNo as pathVariable
            // Could be further optimized by skipping "findBooking" and using bookingNo PathVariable
            // directly as booking is not needed.
        model.addAttribute("booking", booking);
        // List of Customers to be displayed and to offer selection
            // Future iterations could and would include:
            // Could be further optimized to include search in this method as is in regular customer page
            // + Filtering out customers who are already assigned to a confirmed booking.
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
        // Find and Get booking using PathVariable bookingNo
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
        //  Fetch pickUp and dropOff address to display (Default is Office address, ID = 1)
            // In html file user is offered to change pickUp and dropOff address.
        Address addressPickUp = addressService.findAddress(booking.getPickUpId());
        Address addressDropOff = addressService.findAddress(booking.getDropOffId());

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
        // Future iterations would include similar methodology to create new customer for booking.

    // Using PathVariable to pass on Booking Number, to be used in PostMapping Method below
    @GetMapping("/createBookingAddressPickUp/{bookingNo}")
    public String createBookingAddressPickUp(@PathVariable("bookingNo") int bookingNo, Model model){
        // Fetching Booking info to pass on booking numeber
            // Future iterations would improve upon this by either utilizing booking information
            // or to take the find booking method out use model for bookingNo only.
        Booking booking = bookingService.findBooking(bookingNo);
        model.addAttribute("booking", booking);
        // Returns createBookingAddressPickUp as GUI
        return "home/Bookings/createBookingAddressPickUp";
    }
    // Using PostMapping to instantiate an address object and PathVariable for Booking Number to link with Booking.
    @PostMapping("/createBookingAddressPickUp/{bookingNo}")
    public String createBookingAddressPickUp(@PathVariable("bookingNo") int bookingNo, @ModelAttribute Address address){
        // Add address to database
        addressService.addAddress(address);
        // Instantiate another address with the addressId generated by mysql (auto_increment)
        Address a = addressService.findAddressId(address);
        // Instantiate a booking using findBooking to get booking information from database
        Booking booking = bookingService.findBooking(bookingNo);
        // Set pickUp Id fetched from database above
        booking.setPickUpId(a.getAddressId());
        // Setting totalPrice generated within DataManipulation class.
        booking.setTotalPrice(dataManipulation.calculateTotalPriceConfirmed(booking));
        // Updating booking in database after setting changing above mentioned attributes.
        bookingService.updateBooking(booking);
        // Redirect back to newBooking with booking number as PathVariable.
        return "redirect:/newBooking/"+bookingNo;
    }

    // Copy paste of methods above except for DropOff location and attribute instead of PickUp.
    @GetMapping("/createBookingAddressDropOff/{bookingNo}")
    public String createBookingAddressDropOff(@PathVariable("bookingNo") int bookingNo, Model model) {
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
        booking.setTotalPrice(dataManipulation.calculateTotalPriceConfirmed(booking));
        bookingService.updateBooking(booking);
        return "redirect:/newBooking/"+bookingNo;
    }
    //------------------------------------------------------------------------------------------------------------------


}
