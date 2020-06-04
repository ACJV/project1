package com.example.project.Controller;

import com.example.project.Model.Address;
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
    @Autowired //tells where an injection needs to occur
    AddressService addressService; //create addressSerivce object
    CustomerService customerService; //create cusotmerSerivce object

    @GetMapping("/address") //listen to this route, via the http get request
    public String index(Model model) {
        List<Address> addressList = addressService.fetchAll(); //create a list of addresses
        model.addAttribute("addressL", addressList); // creates a model attribute called addressL and passes for web rendering.
        return "home/Address/addressIndex"; //renders addressIndex.html
    }

    @GetMapping("/createAddress")//listen to this route, via the http get request
    public String create() {
        return "home/Address/createAddress"; //render the create address page
    }

    @PostMapping("/createAddress")//listen to this route, via the http post request
    public String create(@ModelAttribute Address address) // tells java to treat the incoming object in
                                                            // the post requests as a model (address)
    {
        addressService.addAddress(address);
        return "redirect:/address"; //after creating the address go back to the main page
    }

    @GetMapping("/viewOneAddress/{addressID}")
    public String viewAddress(@PathVariable("addressID") int addressID, Model model) //@PathVariable passes the addressId
                                                                                    // in the path to int addressID
    {
        model.addAttribute("address", addressService.findAddress(addressID));
        return "home/Address/viewOneAddress"; //render
    }

    @GetMapping("/deleteAddress/{addressID}")
    public String delete(@PathVariable("addressID") int addressID)
    {
        boolean del = addressService.deleteAddress(addressID);
        if (del) { //upon successful deletion
            return "redirect:/address";
        } else {   // upon unsuccessful deletion
            return "redirect:/address";
        }
    }

    @GetMapping("/updateAddress/{addressID}")
    //@PathVariable passes the addressId
    // in the path to int addressID
    public String update(@PathVariable("addressID") int addressID, Model model){
        // creates a model attribute called address and passes for web rendering.
        model.addAttribute("address", addressService.findAddress(addressID));
        return "home/Address/updateAddress";
    }

    @PostMapping("/updateAddress")
    public String updateAddress(@ModelAttribute Address address){
        // tells java to treat the incoming object in
        // the post requests as a model
        addressService.updateAddress(address.getAddressId(),address);
        return "redirect:/address";
    }

}
