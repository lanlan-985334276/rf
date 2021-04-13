package com.example.rongfu.service.impl;

import com.example.rongfu.service.IUploadImgService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class UploadImgServiceIml implements IUploadImgService {
    @Override
    public String fileUpload(MultipartFile file, String CKEditorFuncNum) {
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        //设置文件上传路径
        String filePath = "/uploadImg/";
        try {
            org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath + fileName));
            FileUtils.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedException("上传失败，未知错误，请联系管理员！");
        }
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\">");
        sb.append("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" + filePath + fileName
                + "','')");
        sb.append("</script>");
        return sb.toString();
    }

    @Override
    public String upload(MultipartFile file) {
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        //设置文件上传路径
        String filePath = "D://EndIDea/upload/";
        try {
//            org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath + fileName));
            FileUtils.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedException("上传失败，未知错误，请联系管理员！");
        }
        return "/uploadImg/" + fileName;
    }
}
