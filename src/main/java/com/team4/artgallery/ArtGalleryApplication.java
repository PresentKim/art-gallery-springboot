package com.team4.artgallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ArtGalleryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtGalleryApplication.class, args);
    }

}
