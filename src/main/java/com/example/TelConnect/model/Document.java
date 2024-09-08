package com.example.TelConnect.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DocumentId;

    @Column
    private Long customerId;

    @Column
    private LocalDate uploadDate;

    @Column
    private String DocumentType;

    public Long getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(Long documentId) {
        DocumentId = documentId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(String documentType) {
        this.DocumentType = documentType;
    }


    @Override
    public String toString() {
        return "Document{" +
                "DocumentId=" + DocumentId +
                ", customerId='" + customerId + '\'' +
                ", uploadDate=" + uploadDate +
                '}';
    }
}

