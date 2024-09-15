package com.example.TelConnect.service;

import com.example.TelConnect.model.Verification;
import com.example.TelConnect.DTO.VerificationRequestDTO;
import com.example.TelConnect.repository.VerificationRepository;
import com.example.TelConnect.model.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final DocumentService documentService;

    public VerificationService(VerificationRepository verificationRepository,DocumentService documentService){
        this.verificationRepository= verificationRepository;
        this.documentService=documentService;
    }

    //Save verification status as failed by default for a customer and document
    public void saveVerification(VerificationRequestDTO newVerificationRequest){
        Verification verification= new Verification();

        verification.setCustomerId(newVerificationRequest.getCustomerId());
        verification.setDocumentId(newVerificationRequest.getDocumentId());
        verification.setRequestDate(LocalDateTime.now());
        verification.setRequestStatus("failed");

        verificationRepository.save(verification);
    }

    //Get verification status of a customer
    public String getVerificationStatus(Long customerId) {

        // Get the list of verifications for the given customer ID
        List<Verification> verifications = verificationRepository.findByCustomerId(customerId);
        List<String> results = new ArrayList<>();

        for (Verification verification : verifications) {
            // Extract document ID and status from each verification
            Long documentId = verification.getDocumentId();
            String status = verification.getRequestStatus();

            // Get the document type using the document service
            Document document = documentService.getByDocumentId(documentId);
            String documentType = document.getDocumentType();

            // Combine the document type and status into a result string
            results.add("Document Type: " + documentType + ", Status: " + status);
        }
        if(results.isEmpty())
            return "";

        // Join all results into a single string for output
        return String.join("\n", results);
    }

    //Update verification status of a customer
    public void updateVerificationStatus(Long customerId, String status) {
        String documentType= "Aadhar";
        // Get the list of verifications for the given customer ID
        List<Verification> verifications = verificationRepository.findByCustomerId(customerId);

        // Use streams to find the verification that matches the given document type
        verifications.stream()
                .filter(verification -> {
                    // Retrieve the document using the document service
                    Document document = documentService.getByDocumentId(verification.getDocumentId());
                    // Check if the document type matches
                    return document != null && documentType.equals(document.getDocumentType());
                })
                .findFirst() // Get the first matching verification, if any
                .ifPresent(verification -> {
                    // Update the status of the found verification
                    verification.setRequestStatus(status);
                    verificationRepository.save(verification); // Save the updated verification
                });
    }
}
