package com.example.TelConnect.service;
import com.example.TelConnect.model.CustomerAadhar;
import com.example.TelConnect.repository.CustomerAadharRepository;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OcrService {

    @Autowired
    private Tesseract tesseract;
    CustomerAadharRepository customerAadharRepository;

    //Extracts Test from document
    public String recognizeText(InputStream inputStream) throws IOException {
        List<BufferedImage> images = convertPdfToImages(inputStream);
        StringBuilder result = new StringBuilder();

        for (BufferedImage image : images) {
            try {
                result.append(tesseract.doOCR(image)).append("\n");
            } catch (TesseractException e) {
                e.printStackTrace();
                return "failed";
            }
        }
        return result.toString().trim();
    }

    //Convert pdf to image format
    public List<BufferedImage> convertPdfToImages(InputStream inputStream) throws IOException {
        List<BufferedImage> images = new ArrayList<>();
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            int pageCount = document.getNumberOfPages();

            for (int page = 0; page < pageCount; page++) {
                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300); // Render at 300 DPI
                images.add(bufferedImage);
            }
        }
        return images;
    }

    //Extract Aadhaar number from document
    public List<String> extractAadhaarNumbers(String text) {
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

    //Find aadhar record from database
    public List<CustomerAadhar> findVerifiedPersons(List<String> aadhaarNumbers) {
        return customerAadharRepository.findByIdVerificationIn(aadhaarNumbers);
    }

    //Final method to verify aadhar number from document and DB
    public String generateResponse(List<CustomerAadhar> verifiedPersons) {
        if (verifiedPersons.isEmpty()) {
            return "No verified customers found.";
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