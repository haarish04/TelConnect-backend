package com.example.TelConnect.service;

import com.example.TelConnect.model.Verification;
import com.example.TelConnect.DTO.VerificationRequestDTO;
import com.example.TelConnect.model.Document;
import com.example.TelConnect.repository.VerificationRepository;
import com.example.TelConnect.service.DocumentService;
import com.example.TelConnect.service.VerificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VerificationServiceTest {

    @Mock
    private VerificationRepository verificationRepository;

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private VerificationService verificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Test saving new verification request
    @Test
    void testSaveVerification() {
        VerificationRequestDTO requestDTO = new VerificationRequestDTO();
        requestDTO.setCustomerId(1L);
        requestDTO.setDocumentId(2L);

        verificationService.saveVerification(requestDTO);

        verify(verificationRepository, times(1)).save(any(Verification.class));
    }

    //Test retrieving verification status
    @Test
    void testGetVerificationStatus() {
        List<Verification> mockVerifications = new ArrayList<>();
        Verification verification = new Verification();
        verification.setCustomerId(1L);
        verification.setDocumentId(2L);
        verification.setRequestStatus("failed");
        mockVerifications.add(verification);

        Document mockDocument = new Document();
        mockDocument.setDocumentId(2L);
        mockDocument.setDocumentType("Aadhar");

        when(verificationRepository.findByCustomerId(1L)).thenReturn(mockVerifications);
        when(documentService.getByDocumentId(2L)).thenReturn(mockDocument);

        String result = verificationService.getVerificationStatus(1L);

        assertEquals("Document Type: Aadhar, Status: failed", result);
    }

    //Test retrieving non-existent verification request
    @Test
    void testGetVerificationStatus_Empty() {
        when(verificationRepository.findByCustomerId(1L)).thenReturn(new ArrayList<>());

        String result = verificationService.getVerificationStatus(1L);

        assertEquals("", result);
    }

    //Test updating verification request status
    @Test
    void testUpdateVerificationStatus() {
        List<Verification> mockVerifications = new ArrayList<>();
        Verification verification = new Verification();
        verification.setCustomerId(1L);
        verification.setDocumentId(2L);
        mockVerifications.add(verification);

        Document mockDocument = new Document();
        mockDocument.setDocumentId(2L);
        mockDocument.setDocumentType("Aadhar");

        when(verificationRepository.findByCustomerId(1L)).thenReturn(mockVerifications);
        when(documentService.getByDocumentId(2L)).thenReturn(mockDocument);

        verificationService.updateVerificationStatus(1L, "success");

        verify(verificationRepository, times(1)).save(verification);
        assertEquals("success", verification.getRequestStatus());
    }

    //Test updating verification status of non-existent document
    @Test
    void testUpdateVerificationStatus_NoMatchingDocument() {
        List<Verification> mockVerifications = new ArrayList<>();
        Verification verification = new Verification();
        verification.setVerificationId(3L);
        verification.setCustomerId(3L);
        verification.setDocumentId(3L);
        verification.setRequestStatus("failed");
        mockVerifications.add(verification);

        Document mockDocument = new Document();
        mockDocument.setDocumentId(3L);
        mockDocument.setDocumentType("Passport");

        when(verificationRepository.findByCustomerId(3L)).thenReturn(mockVerifications);
        when(documentService.getByDocumentId(2L)).thenReturn(mockDocument);

        verificationService.updateVerificationStatus(2L, "success");

        verify(verificationRepository, never()).save(any());
        assertEquals("failed", verification.getRequestStatus());
    }

    //Test retrieve all verification attempts
    @Test
    void testGetAllVerificationAttempts() {
        List<Verification> mockVerifications = new ArrayList<>();
        Verification verification = new Verification();
        verification.setCustomerId(1L);
        mockVerifications.add(verification);

        when(verificationRepository.findAll()).thenReturn(mockVerifications);

        List<Verification> result = verificationService.getAllVerificationAttempts();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getCustomerId());
    }
}
