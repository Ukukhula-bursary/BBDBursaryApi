package com.ukukhula.bursaryapi.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;

@Service
public class BlobStorageService {
    // @Autowired
    private final BlobServiceClient blobServiceClient;

    public BlobStorageService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    public void downloadBlob(String containerName, String blobName, String destinationDirectory) {
        try {
            // Create the destination directory if it doesn't exist
            Path directoryPath = Paths.get(destinationDirectory, containerName);
            Files.createDirectories(directoryPath);

            // Construct the destination file path
            String destinationFilePath = Paths.get(directoryPath.toString(), blobName).toString();

            // Download the blob's content stream and write it to the destination file
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            try (OutputStream outputStream = new FileOutputStream(destinationFilePath)) {
                blobClient.downloadStream(outputStream);
            }

            System.out.println("Blob downloaded successfully.");
        } catch (Exception e) {
            System.err.println("Failed to download blob: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String uploadBlob(String containerName, String blobName, byte[] data, String contentType) throws IOException {

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        containerClient.createIfNotExists();
  
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        try (InputStream dataStream = new ByteArrayInputStream(data)) {
            blobClient.upload(dataStream, data.length, true);
        }

        return blobClient.getBlobUrl();
    }
}
