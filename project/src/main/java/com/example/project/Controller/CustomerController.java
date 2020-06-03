package com.example.project.Controller;

import com.example.project.Model.Address;
import com.example.project.Model.Category;
import com.example.project.Model.Customer;
import com.example.project.Service.AddressService;
import com.example.project.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    AddressService addressService;

//----------------------------------------------------------------------------------------------------------------------
    //@Ástþór
//----------------------------------------------------------------------------------------------------------------------

    // Gets all customers and searches for keyword in customer name if not null
    @GetMapping("/customer")
    public String customer(Model model, String keyword) {
        // Fetches all customers
        List<Customer> customerList = customerService.fetchAll();
        // if keyword is not null
        if(keyword != null){
            // Search customer name for keyword and display customer matching keyword input by user
            model.addAttribute("customers", customerService.findByKeyword(keyword));
        } else {
            // if keyword is null - display all customers
            model.addAttribute("customers", customerList);
        }
        // returns customer page
        return "home/Customers/customer";
    }

    // Gets view customer page with pathVariable customerId to fetch customer in database.
    @GetMapping("/viewCustomer/{customerId}")
    public String viewCustomer(@PathVariable("customerId") int customerId, Model model) {
        model.addAttribute("customer", customerService.findCustomer(customerId));
        return "home/Customers/viewCustomer";
    }

    // delete customer using pathvariable to locate customer in database
    @GetMapping("/deleteCustomer/{customerId}")
    public String delete(@PathVariable("customerId") int customerId){
        boolean deleted = customerService.deleteCustomer(customerId);
        // redirects to customer page
        return "redirect:/customer";
    }

    // updated create customer page, that creates address at the same time and links customer to address table in db
    @GetMapping("/createAddressAndCustomer")
    public String createAddressAndCustomer(){
        return "home/Customers/createAddressAndCustomer";
    }

    // Using model attributes to instantiate customer and address, adding to database and linking together
    @PostMapping("/createAddressAndCustomer")
    public String createAddressAndCustomer(@ModelAttribute Address address, @ModelAttribute Customer customer){
        // add address to database
        addressService.addAddress(address);
        // find address id
        Address a = addressService.findAddressId(address);
        int addressId = a.getAddressId();
        // set address id to customer, to link customer to correct address.
        customer.setAddressId(addressId);
        // add customer to database
        customerService.addCustomer(customer);
        // redirecting to customer page
        return "redirect:/customer";
    }

    // saving customer used in view customer page to update customer
    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute Customer customer){
        customerService.updateCustomer(customer.getCustomerId(), customer);
        return "redirect:/customer";
    }

    //------------------------------------------------------------------------------------------------------------------
    // Methods not in use in current system

    // updateCustomer page which is not in use
    @GetMapping("/updateCustomer/{customerId}")
    public String updateCategory(@PathVariable("customerId") int customerId, Model model)
    {
        Customer customer = customerService.findCustomer(customerId);
        model.addAttribute("customer", customer);
        return "home/Customers/updateCustomer";
    }
    // Gets create customer html page
    @GetMapping("/create")
    public String create(){
        return "home/Customers/create";
    }

    // uses model attribute to instantiate customer and save to database
    @PostMapping("/create")
    public String create(@ModelAttribute Customer customer){
        customerService.addCustomer(customer);
        // redirects to customer page
        return "redirect:/customer";
    }

}
