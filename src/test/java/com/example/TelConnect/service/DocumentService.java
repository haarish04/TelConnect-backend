package com.example.TelConnect.service;

import com.example.TelConnect.model.Document;
import com.example.TelConnect.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDocument() {
        Long customerId = 12345L;
        Document document = new Document();
        document.setCustomerId(customerId);
        document.setUploadDate(LocalDate.now());
        document.setDocumentType("ID_CARD");

        // Mocking the save operation
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        documentService.saveDocument(customerId, "Aadhar");

        // Verifying that save method is called once
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @Test
    void testSaveDocument_NullValues() {
        Long customerId = null;
        String documentType = null;

        documentService.saveDocument(customerId, documentType);

        // Verifying that save method is called even with null values
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @Test
    void testGetByCustomerId() {
        Long customerId = 12345L;
        List<Document> documents = new ArrayList<>();
        Document document1 = new Document();
        document1.setDocumentId(1L);
        document1.setCustomerId(customerId);
        document1.setDocumentType("ID_CARD");
        Document document2 = new Document();
        document2.setDocumentId(2L);
        document2.setCustomerId(customerId);
        document2.setDocumentType("PASSPORT");

        documents.add(document1);
        documents.add(document2);

        // Mocking the findByCustomerId operation
        when(documentRepository.findByCustomerId(customerId)).thenReturn(documents);

        List<Document> result = documentService.getByCustomerId(customerId);

        assertEquals(2, result.size());
        assertEquals("ID_CARD", result.get(0).getDocumentType());
        assertEquals("PASSPORT", result.get(1).getDocumentType());
    }

    @Test
    void testGetByCustomerId_NoDocumentsFound() {
        Long customerId = 12345L;

        // Mocking the findByCustomerId operation
        when(documentRepository.findByCustomerId(customerId)).thenReturn(new ArrayList<>());

        List<Document> result = documentService.getByCustomerId(customerId);

        assertEquals(0, result.size());
    }

    @Test
    void testGetByDocumentId() {
        Long documentId = 1L;
        Document document = new Document();
        document.setDocumentId(documentId);
        document.setCustomerId(12345L);
        document.setDocumentType("ID_CARD");

        // Mocking the findById operation
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(document));

        Document result = documentService.getByDocumentId(documentId);

        assertNotNull(result);
        assertEquals(documentId, result.getDocumentId());
    }

    @Test
    void testGetByDocumentId_NotFound() {
        Long documentId = 1L;

        // Mocking the findById operation
        when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

        Document result = documentService.getByDocumentId(documentId);

        assertNull(result);
    }

    @Test
    void testFindAllDocuments() {
        List<Document> documents = new ArrayList<>();
        Document document1 = new Document();
        document1.setDocumentId(1L);
        document1.setCustomerId(12345L);
        document1.setDocumentType("ID_CARD");
        Document document2 = new Document();
        document2.setDocumentId(2L);
        document2.setCustomerId(67890L);
        document2.setDocumentType("PASSPORT");

        documents.add(document1);
        documents.add(document2);

        // Mocking the findAll operation
        when(documentRepository.findAll()).thenReturn(documents);

        List<Document> result = documentService.findAllDocuments();

        assertEquals(2, result.size());
        assertEquals("ID_CARD", result.get(0).getDocumentType());
        assertEquals("PASSPORT", result.get(1).getDocumentType());
    }

    @Test
    void testFindAllDocuments_EmptyList() {
        // Mocking the findAll operation
        when(documentRepository.findAll()).thenReturn(new ArrayList<>());

        List<Document> result = documentService.findAllDocuments();

        assertEquals(0, result.size());
    }

    @Test
    void testDocumentTypeHandling() {
        Long customerId = 12345L;
        String documentType = "PAN_CARD";

        Document document = new Document();
        document.setCustomerId(customerId);
        document.setDocumentType(documentType);
        document.setUploadDate(LocalDate.now());

        when(documentRepository.save(any(Document.class))).thenReturn(document);

        documentService.saveDocument(customerId, documentType);

        verify(documentRepository, times(1)).save(argThat(doc -> doc.getDocumentType().equals("PAN_CARD")));
    }
}
