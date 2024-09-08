package com.example.TelConnect.controller;
import com.example.TelConnect.model.CustomerAadhar;
import com.example.TelConnect.repository.CustomerAadharRepository;

import com.example.TelConnect.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/OCR")
public class OcrController {
    @Autowired
    private OcrService ocrService;

    @Autowired
    private CustomerAadharRepository customerAadharRepository;

    @PostMapping("/")
    public ResponseEntity<String> recognizeText(@RequestParam("file") MultipartFile file) throws IOException {
        String text =  ocrService.recognizeText(file.getInputStream());
        List<String> aadhaarNumbers = extractAadhaarNumbers(text);
        List<CustomerAadhar> verifiedPersons = findVerifiedPersons(aadhaarNumbers);
        String response = generateResponse(verifiedPersons);
        if(response.equals("Invalid"))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
        else
            return ResponseEntity.ok(response);
    }

    private List<String> extractAadhaarNumbers(String text) {
        String regex = "\\b\\d{4} \\d{4} \\d{4}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group();
            if (!result.contains(match)) {
                result.add(match);
            }
        }
        return result;
    }

    private List<CustomerAadhar> findVerifiedPersons(List<String> aadhaarNumbers) {
        return customerAadharRepository.findByIdVerificationIn(aadhaarNumbers);
    }

    private String generateResponse(List<CustomerAadhar> verifiedPersons) {
        if (verifiedPersons.isEmpty()) {
            return "Invalid";
        }

        StringBuilder result = new StringBuilder();
        for (CustomerAadhar customerAadhar : verifiedPersons) {
            result.append("Verified customer: ").append(customerAadhar.getName())
                    .append(" with ID Verification: ").append(customerAadhar.getIdVerification())
                    .append("\n");
        }
        return result.toString().trim();
    }
}