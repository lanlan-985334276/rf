package com.example.rongfu.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadImgService {
    String fileUpload(MultipartFile file, String CKEditorFuncNum);
    String upload(MultipartFile file);

}
