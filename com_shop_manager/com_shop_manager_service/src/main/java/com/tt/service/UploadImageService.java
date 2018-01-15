package com.tt.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadImageService {

    Map uploadImages(MultipartFile multipartFile);
}
