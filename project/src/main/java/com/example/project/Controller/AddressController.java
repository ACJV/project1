package com.example.project.Controller;

import com.example.project.Model.Address;
import com.example.project.Model.Vehicle;
import com.example.project.Service.AddressService;
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

    /*
    @GetMapping("/address")
    public String index(Model model) {
        List<Address> addressList = addressService.fetchAll();
        model.addAttribute("addressL", addressList);
        return "home/Address/address";

    }*/

    /*
    @GetMapping("/createAddress")
    public String create() {
        return "home/Address/createAddress";
    }

    @PostMapping("/createAddress")
    public String create(@ModelAttribute Address address)
    {
        addressService.addAddress(address);
        return "redirect:/address";
    }

    @GetMapping("/viewAddress/{addressID}")
    public String viewAddress(@PathVariable("addressID") int addressID, Model model)
    {
        model.addAttribute("address", addressService.findAddress(addressID));
        return "home/Address/viewAddress";
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
        model.addAttribute("address", addressService.findAddress(addressID));
        return "home/updateAddress";
    }

    @PostMapping("/updateAddress")
    public String updateAddress(@ModelAttribute Address address){
        addressService.updateAddress(address.getAddressID(),address);
        return "redirect:/address";
    }*/






    @GetMapping("/createAddressBooking")
    public String createAddressBooking(){
        return "home/Address/createAddressBooking";
    }

}
