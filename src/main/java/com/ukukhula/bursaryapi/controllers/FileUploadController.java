// package com.ukukhula.bursaryapi.controllers;

// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;

// import java.io.File;
// import java.io.IOException;

// @RestController
// @RequestMapping("/upload")
// public class FileUploadController {

//     @PostMapping("/userDocuments")
//     public String uploadUserDocument(@RequestParam("file") MultipartFile file) {
//         if (file.isEmpty()) {
//             return "File is empty";
//         }

//         // Define the directory to save the uploaded files
//         String uploadDir = "path/to/your/upload/directory";

//         // Create the directory if it doesn't exist
//         File directory = new File(uploadDir);
//         if (!directory.exists()) {
//             directory.mkdirs();
//         }

//         try {
//             // Save the file to the server
//             String filePath = uploadDir + "/" + file.getOriginalFilename();
//             file.transferTo(new File(filePath));
//             return "File uploaded successfully";
//         } catch (IOException e) {
//             e.printStackTrace();
//             return "Failed to upload file";
//         }
//     }
// }
