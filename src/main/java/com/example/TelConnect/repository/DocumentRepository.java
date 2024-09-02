package com.example.TelConnect.repository;
import com.example.TelConnect.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCustomerId(String customerId);
}
