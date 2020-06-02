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

    // Should Be done.
    @GetMapping("/customer")
    public String customer(Model model, String keyword) {
        List<Customer> customerList = customerService.fetchAll();

        if(keyword != null){
            model.addAttribute("customers", customerService.findByKeyword(keyword));
        } else {
            model.addAttribute("customers", customerList);
        }

        return "home/Customers/customer";
    }



    @GetMapping("/create")
    public String create(){
        return "home/Customers/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Customer customer){
        customerService.addCustomer(customer);
        return "redirect:/customer";
    }

    @GetMapping("/viewCustomer/{customerId}")
    public String viewCustomer(@PathVariable("customerId") int customerId, Model model) {
        System.out.println("GetMapping /viewCustomer, test ID=5: " + customerId);
        model.addAttribute("customer", customerService.findCustomer(customerId));
        return "home/Customers/viewCustomer";
    }

    @GetMapping("/updateCustomer/{customerId}")
    public String updateCategory(@PathVariable("customerId") int customerId, Model model)
    {
        Customer customer = customerService.findCustomer(customerId);
        model.addAttribute("customer", customer);
        return "home/Customers/updateCustomer";
    }


    @GetMapping("/deleteCustomer/{customerId}")
    public String delete(@PathVariable("customerId") int customerId){

        boolean deleted = customerService.deleteCustomer(customerId);
        if(deleted){
            return "redirect:/customer";
        } else {
            return "redirect:/customer";
        }
    }

    @GetMapping("/createAddressAndCustomer")
    public String createAddressAndCustomer(){
        return "home/Customers/createAddressAndCustomer";
    }

    @PostMapping("/createAddressAndCustomer")
    public String createAddressAndCustomer(@ModelAttribute Address address, @ModelAttribute Customer customer){
        addressService.addAddress(address);
        Address a = addressService.findAddressId(address);
        int addressId = a.getAddressId();
        customer.setAddressId(addressId);
        customerService.addCustomer(customer);
        return "redirect:/customer";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute Customer customer){
        customerService.updateCustomer(customer.getCustomerId(), customer);
        return "redirect:/customer";
    }

}
