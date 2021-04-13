package com.example.rongfu.util;

import com.example.rongfu.service.ex.FailedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static String uploadImg(MultipartFile file) {
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        //设置文件上传路径
        String filePath = "/uploadImg/";
        try {
            org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedException("上传失败，未知错误，请联系管理员！");
        }
        return filePath+fileName;
    }
}
