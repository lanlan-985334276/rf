package com.example.rongfu.service;

import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadImgServiceIml implements IUploadImgService {
    @Override
    public void upload(MultipartFile file) {
        String fileName = Math.random()+file.getOriginalFilename();
        //设置文件上传路径
        String filePath = "/uploadImg/";
        try {
            FileUtils.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedException("上传失败，未知错误，请联系管理员！");
        }
    }
}
