package com.example.project.Controller;

import com.example.project.Model.Address;
import com.example.project.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping("/")
    public String index(Model model) {
        List<Address> addressList = addressService.fetchAll();
        model.addAttribute("addressL", addressList);
        return "home/index";
    }

}
