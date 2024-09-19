package com.example.TelConnect.service;
import com.example.TelConnect.model.CustomerAadhar;
import com.example.TelConnect.repository.CustomerAadharRepository;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class OcrService {

    @Autowired
    private CustomerAadharRepository customerAadharRepository;

    private final String tesseractPath = "C:/Users/e031975/Downloads/tesseract.exe"; // Path to Tesseract executable

    public String recognizeText(InputStream fileStream) throws IOException {

        List<BufferedImage> images = extractImagesFromPdf(fileStream);


        for (BufferedImage image : images) {
            String extractedText = performOcr(image);
            String verifiedCustomerName = verifyNameInText(extractedText);

            if (verifiedCustomerName != null) {
                System.out.println("Name found in text: " + verifiedCustomerName);
                System.out.println("Name Verification Status: Verified");
                return "verified"; // Indicate verification success
            }
        }

        System.out.println("Name Verification Status: Not Verified");
        return "not_verified";
    }

    public List<BufferedImage> extractImagesFromPdf(InputStream pdfStream) throws IOException {
        List<BufferedImage> images = new ArrayList<>();
        try (PDDocument document = PDDocument.load(pdfStream)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                PDPage page = document.getPage(pageIndex);
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 300); // Render image at 300 DPI
                images.add(image);
            }
        }
        return images;
    }

    public String performOcr(BufferedImage image) throws IOException {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            baos.flush();
            InputStream imageStream = new ByteArrayInputStream(baos.toByteArray());


            String[] command = {
                    "cmd.exe", "/c", tesseractPath, "stdin", "stdout"
            };


            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);


            Process process = processBuilder.start();


            try (OutputStream processOutputStream = process.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = imageStream.read(buffer)) != -1) {
                    processOutputStream.write(buffer, 0, bytesRead);
                }
                processOutputStream.flush();
            }


            StringBuilder result = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            }


            try {
                process.waitFor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Process interrupted", e);
            }

            return result.toString();
        }
    }

    public String verifyNameInText(String text) {

        List<CustomerAadhar> customers = customerAadharRepository.findAll();


        for (CustomerAadhar customer : customers) {
            String name = customer.getName();


            if (text.contains(name)) {

                return name;
            }
        }

        return null;
    }
}