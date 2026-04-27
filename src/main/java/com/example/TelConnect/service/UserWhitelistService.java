package com.example.TelConnect.service;

import com.example.TelConnect.DTO.UserWhitelistDTO;
import com.example.TelConnect.enums.Status;
import com.example.TelConnect.model.UserWhitelist;
import com.example.TelConnect.repository.UserWhitelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserWhitelistService {

    @Autowired
    private UserWhitelistRepository userWhitelistRepository;

    public List<UserWhitelist> getUserWhitelist() {
        List<UserWhitelist> userWhiteList= userWhitelistRepository.findAll();
        return new ArrayList<>(userWhiteList);
    }

    public UserWhitelist createuserWhitelist(UserWhitelist userWhitelist) {

        UserWhitelist uwl = new UserWhitelist();
        uwl.setCustomerId(userWhitelist.getCustomerId());
        uwl.setEnrollmentDate(userWhitelist.getEnrollmentDate());
        uwl.setStatus(userWhitelist.getStatus());
        return userWhitelistRepository.save(uwl);
    }

    public UserWhitelist updateUserWhitelist(Long customer_id, UserWhitelistDTO dto) {

        UserWhitelist existing = userWhitelistRepository.findById(customer_id).orElse(null);
        if(existing==null)
            return null;

        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        if (dto.getEnrollmentDate() != null) {
            existing.setEnrollmentDate(dto.getEnrollmentDate());
        }
        return userWhitelistRepository.save(existing);
    }

    public void deleteUserWhitelist(Long customer_id) {
        UserWhitelist existing = userWhitelistRepository.findById(customer_id)
                .orElseThrow(() -> new RuntimeException("Incident not found"));
        userWhitelistRepository.delete(existing);
    }

    public UserWhitelist updateStatus(Long customerId, String status) {
        UserWhitelist existing = userWhitelistRepository.findById(customerId).orElse(null);
        if(existing==null)
            return null;

        existing.setStatus(status);
        return userWhitelistRepository.save(existing);
    }
}
