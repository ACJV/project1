package com.example.project.Service;

import com.example.project.Model.Address;
import com.example.project.Model.Customer;
import com.example.project.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public List<Address> fetchAll() {
        return addressRepository.fetchAll();
    }

    public Address addAddress(Address a) {
        return addressRepository.addAddress(a);
    }

    public Address findAddressById(int id) {
        return addressRepository.findAddressById(id);
    }

    public Boolean deleteAddress(int id) {
        return addressRepository.deleteAddress(id);
    }

    public Address updateAddress(int id, Address a) {
        return addressRepository.updateAddress(id, a);
    }

    public List<Address> findByKeyword(String keyword){
        return addressRepository.findByKeyword(keyword);
    }

}
