package uz.java.backendtask.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String upload(
        MultipartFile file,
        String objectKey,
        String contentType
    );

    void delete(String objectKey);
}
