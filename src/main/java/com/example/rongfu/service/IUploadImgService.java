package com.example.rongfu.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadImgService {
    void upload(MultipartFile fil);
}
