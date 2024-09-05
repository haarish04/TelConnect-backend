package com.example.TelConnect.controller;

import com.example.TelConnect.model.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TelConnect.service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService){
        this.documentService= documentService;
    }


    @PostMapping("/save")
    public ResponseEntity<String> saveDocument(@RequestParam String customerId){
        documentService.saveDocument(customerId);
        return ResponseEntity.ok("Document entry successfull");
    }

    @GetMapping("/get/{customerId}")
    public ResponseEntity<?> getDocument(@PathVariable String customerId){
        List<Document> documents = documentService.getByCustomerId(customerId);
        if(documents!=null)
            return ResponseEntity.ok(documents);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User has not submitted any documents");
    }

//    @DeleteMapping("delete/{customerId}")
//    public ResponseEntity<String> deleteDocument(@PathVariable String customerId){
//        if(documentService.deleteDocument(customerId))
//            return ResponseEntity.ok("Document Deleted successfully");
//
//        else
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document does not exist");
//
//    }
}
