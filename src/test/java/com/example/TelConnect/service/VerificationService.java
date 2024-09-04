package com.example.TelConnect.service;

import com.example.TelConnect.model.Document;
import com.example.TelConnect.model.Verification;
import com.example.TelConnect.repository.VerificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class VerificationServiceTest {

    @Mock
    private VerificationRepository verificationRepository;

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private VerificationService verificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveVerification() {
        Long customerId = 12345L;
        Long documentId = 67890L;

        // Mocking the save operation
        when(verificationRepository.save(any(Verification.class))).thenReturn(new Verification());

        verificationService.saveVerification(customerId, documentId);

        // Verifying that save method is called once
        verify(verificationRepository, times(1)).save(any(Verification.class));
    }

    @Test
    void testGetVerificationStatus() {
        Long customerId = 12345L;
        Long documentId = 67890L;
        Verification verification = new Verification();
        verification.setDocumentId(documentId);
        verification.setRequestStatus("Approved");

        Document document = new Document();
        document.setDocumentId(documentId);
        document.setDocumentType("ID Card");

        List<Verification> verifications = new ArrayList<>();
        verifications.add(verification);

        // Mocking repository and service methods
        when(verificationRepository.findByCustomerId(customerId)).thenReturn(verifications);
        when(documentService.getByDocumentId(documentId)).thenReturn(document);

        String result = verificationService.getVerificationStatus(customerId);

        assertEquals("Document Type: ID Card, Status: Approved", result);
    }

    @Test
    void testUpdateVerificationStatus() {
        Long customerId = 12345L;
        String documentType = "ID Card";
        String newStatus = "Verified";
        Long documentId = 67890L;

        Verification verification = new Verification();
        verification.setDocumentId(documentId);
        verification.setRequestStatus("Pending");

        Document document = new Document();
        document.setDocumentId(documentId);
        document.setDocumentType(documentType);

        List<Verification> verifications = new ArrayList<>();
        verifications.add(verification);

        // Mocking repository and service methods
        when(verificationRepository.findByCustomerId(customerId)).thenReturn(verifications);
        when(documentService.getByDocumentId(documentId)).thenReturn(document);
        when(verificationRepository.save(any(Verification.class))).thenReturn(verification);

        verificationService.updateVerificationStatus(customerId, documentType, newStatus);

        assertEquals(newStatus, verification.getRequestStatus());
        verify(verificationRepository, times(1)).save(verification);
    }
}
