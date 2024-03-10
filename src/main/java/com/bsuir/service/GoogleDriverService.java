package com.bsuir.service;

import com.bsuir.dto.ImageLocation;
import org.springframework.web.multipart.MultipartFile;

public interface GoogleDriverService {
    void uploadUserIcon(MultipartFile file, String username);

    void uploadPropertyImage(MultipartFile file, Long propertyId);

    ImageLocation uploadArticleImage(MultipartFile file);
}