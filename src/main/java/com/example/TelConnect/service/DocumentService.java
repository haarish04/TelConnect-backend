package com.example.TelConnect.service;

import com.example.TelConnect.model.Document;
import com.example.TelConnect.repository.DocumentRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository){
        this.documentRepository = documentRepository;
    }

    public void saveDocument(String customerId){

        Document document= new Document();
        document.setCustomerId(customerId);
        document.setUploadDate(LocalDate.now());
        document.setDocumentType(document.getDocumentType());

        documentRepository.save(document);
    }

    public List<Document> getByCustomerId(String customerId ){
        List<Document> documents= documentRepository.findByCustomerId(customerId);
        return documents.stream()
                .map(this::convertEntity)
                .collect(Collectors.toList());
    }

    public Document getByDocumentId(Long documentId){
        return documentRepository.findById(documentId).orElse(null);
    }
//
//    public Document getByDocumentType(String documentType){
//        return documentRepository.findByDocumentType(documentType);
//
//    }
    public List<Document> findAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        return documents.stream()
                .map(this::convertEntity)
                .collect(Collectors.toList());
    }

//    public boolean deleteDocument(String customerId,String documentType){
//        if(documentRepository.findByCustomerId(customerId)!= null) {
//            List<Document> documents=documentRepository.findByCustomerId(customerId);
//              for(doc:documents)
//                  if(doc.getDocumentType == documentType)
//                      customerRepository.delete(doc)
//
//            return true;
//        }
//
//        else
//            return false;
//    }

    private Document convertEntity(Document document) {
        document.setDocumentId(document.getDocumentId());
        return document;
    }
}
