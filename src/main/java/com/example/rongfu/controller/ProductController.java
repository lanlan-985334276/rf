package com.example.rongfu.controller;

import com.example.rongfu.entity.Product;
import com.example.rongfu.service.IProductService;
import com.example.rongfu.service.IUploadImgService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.DateUtils;
import com.example.rongfu.util.FileUtils;
import com.example.rongfu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Autowired
    private IProductService productService;
    @Autowired
    private IUploadImgService uploadImgService;

    @RequestMapping("/all")
    JsonResult<List<Product>> allSignInLog(HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        List<Product> list = productService.findAll(userId);
        return new JsonResult<>(OK, list);
    }

    @RequestMapping("/add")
    JsonResult<Product> add(Product product, String time, HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        product.setUserId(userId);
        try {
            product.setAddTime(DateUtils.getDate(time));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        return new JsonResult<>(OK, productService.add(product));
    }

    @RequestMapping("/update")
    JsonResult<Product> update(Product product,String time, HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        product.setUserId(userId);
        try {
            product.setAddTime(DateUtils.getDate(time));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        return new JsonResult<>(OK, productService.update(product));
    }

    @RequestMapping("/delete")
    JsonResult<Void> delete(Product product, HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        product.setUserId(userId);
        productService.delete(product);
        return new JsonResult<>(OK);
    }
}
