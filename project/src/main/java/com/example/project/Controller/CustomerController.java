package com.example.project.Controller;

import com.example.project.Model.Customer;
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

    @GetMapping("/viewCustomer/{customerID}")
    public String viewCustomer(@PathVariable("customerID") int customerID, Model model) {
        System.out.println("GetMapping /viewCustomer, test ID=5: " + customerID);
        model.addAttribute("customer", customerService.findCustomer(customerID));
        return "home/Customers/viewCustomer";
    }

    @GetMapping("/deleteCustomer/{customerID}")
    public String delete(@PathVariable("customerID") int customerID){

        boolean deleted = customerService.deleteCustomer(customerID);
        if(deleted){
            return "redirect:/customer";
        } else {
            return "redirect:/customer";
        }
    }
    

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute Customer customer){
        customerService.updateCustomer(customer.getCustomerID(), customer);
        return "redirect:/customer";
    }

}
