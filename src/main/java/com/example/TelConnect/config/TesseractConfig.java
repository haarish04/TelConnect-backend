package com.example.TelConnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import net.sourceforge.tess4j.Tesseract;

@Configuration
public class TesseractConfig {
    @Bean
    Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Users/e031975/Downloads/TelConnect/src/main/resources/testdata"); //files of the example : https://github.com/tesseract-ocr/tessdata
        return tesseract;
    }
}
