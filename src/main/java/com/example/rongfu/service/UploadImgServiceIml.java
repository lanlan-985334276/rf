package com.example.rongfu.service;

import ch.qos.logback.core.util.FileUtil;
import org.springframework.stereotype.Service;

@Service
public class UploadImgServiceIml implements IUploadImgService{
    @Override
    public void upload(String path, int userId) {
        String fileName = file.getOriginalFilename();
        //设置文件上传路径
        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
            return "上传成功";
        } catch (Exception e) {
            return "上传失败";
        }
    }
}
