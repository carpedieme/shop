package com.tt.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadImageService {

    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    Map uploadImages(MultipartFile multipartFile);
}
