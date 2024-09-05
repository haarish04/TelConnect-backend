package com.example.TelConnect.controller;
import com.example.TelConnect.model.CustomerAadhar;
import com.example.TelConnect.repository.CustomerAadharRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer/Aadhar")
public class CustomerAadharController {

    @Autowired
    private CustomerAadharRepository  customerAadharRepository;

    @GetMapping("/getAll")
    public List<CustomerAadhar> getAllPersons() {
        return customerAadharRepository.findAll();
    }


}