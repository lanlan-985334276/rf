package com.example.rongfu.controller;

import com.example.rongfu.entity.Product;
import com.example.rongfu.service.IUploadImgService;
import com.example.rongfu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/upload")
public class UploadImgController extends BaseController{
    @Autowired
    private IUploadImgService uploadImgService;
    @Resource
    private HttpServletRequest request;

    @RequestMapping("/img")
    JsonResult<Void> img(String path, HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());

        return new JsonResult<>(OK);
    }
}
