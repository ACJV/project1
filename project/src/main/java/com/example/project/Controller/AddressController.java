package com.example.project.Controller;

import com.example.project.Model.Address;
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
public class AddressController {
    @Autowired
    AddressService addressService;
    CustomerService customerService;

    @GetMapping("/address") //listen to this route
    public String index(Model model) {
        List<Address> addressList = addressService.fetchAll();
        model.addAttribute("addressL", addressList);
        return "home/Address/addressIndex"; //render

    }

    @GetMapping("/createAddress")
    public String create() {
        return "home/Address/createAddress"; //render the create address page
    }

    @PostMapping("/createAddress")
    public String create(@ModelAttribute Address address)
    {
        addressService.addAddress(address);
        return "redirect:/address"; //after creating the address go back to the main page
    }



    @GetMapping("/viewOneAddress/{addressID}")
    public String viewAddress(@PathVariable("addressID") int addressID, Model model)
    {
        model.addAttribute("address", addressService.findAddressById(addressID));
        return "home/Address/viewOneAddress";
    }

    @GetMapping("/deleteAddress/{addressID}")
    public String delete(@PathVariable("addressID") int addressID)
    {
        boolean del = addressService.deleteAddress(addressID);
        if (del) {
            return "redirect:/address";
        } else {
            return "redirect:/address";
        }
    }

    @GetMapping("/updateAddress/{addressID}")
    public String update(@PathVariable("addressID") int addressID, Model model){
        model.addAttribute("address", addressService.findAddressById(addressID));
        return "home/Address/updateAddress";
    }

    @PostMapping("/updateAddress")
    public String updateAddress(@ModelAttribute Address address){
        addressService.updateAddress(address.getAddressId(),address);
        return "redirect:/address";
    }

    @GetMapping("/address2") //listen to this route
    public String index2(Model model) {

        return "home/Address/addressIndex2"; //render

    }

}
