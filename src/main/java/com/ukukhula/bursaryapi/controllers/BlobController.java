package com.ukukhula.bursaryapi.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ukukhula.bursaryapi.services.BlobStorageService;


//TODO
@RestController
@RequestMapping("blob")
public class BlobController {

    private final BlobStorageService blobStorageService;

    @Autowired
    public BlobController(BlobStorageService blobStorageService) {
        this.blobStorageService = blobStorageService;
    }
        @PostMapping("/uploadJpg")
    public ResponseEntity<String> uploadJpg(@RequestParam("file") MultipartFile file) {
        try {
            byte[] data = file.getBytes();
            blobStorageService.uploadBlob("testcontainer", file.getOriginalFilename(), data, "image/jpeg");
            return ResponseEntity.ok("JPG file uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload JPG file.");
        }
    }
    @PostMapping(path="/uploadPdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPdf(@RequestParam("file")MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Uploaded file is empty.");
        }
        try {
            byte[] data = multipartFile.getBytes();
           // String blobName = "file.pdf"; // Use filename as blob name
            blobStorageService.uploadBlob("studentsdocs", multipartFile.getOriginalFilename(), data, "application/pdf");


            return ResponseEntity.ok("PDF file uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload PDF file.");
        }
    }

    @GetMapping("/downloadBlob/{containerName}/{blobName}")
    public ResponseEntity<byte[]> downloadBlob(@PathVariable String containerName, @PathVariable String blobName) {
        try {
            String destinationDirectory = "/tmp"; // Base directory for downloaded files
            blobStorageService.downloadBlob(containerName, blobName, destinationDirectory);
    
            // Read the downloaded file into a byte array
            String destinationFilePath = Paths.get(destinationDirectory, containerName, blobName).toString();
            byte[] fileContent = Files.readAllBytes(Paths.get(destinationFilePath));
    
            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("filename", blobName);
    
            // Return the file content as the response
            return ResponseEntity.ok().headers(headers).body(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
