package com.example.rongfu.controller;

import com.example.rongfu.entity.Product;
import com.example.rongfu.service.IProductService;
import com.example.rongfu.service.IUploadImgService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.DateUtils;
import com.example.rongfu.util.FileUtils;
import com.example.rongfu.util.JsonResult;
import com.example.rongfu.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    JsonResult<List<Product>> findAll(@RequestBody String userStr, HttpSession session) {
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = JsonUtils.json2User(userStr).getUserId();
        List<Product> list = productService.findAll(userId);
        return new JsonResult<>(OK, list);
    }

    @RequestMapping("/add")
    JsonResult<Product> add(@RequestBody String productStr, HttpSession session) {
        System.out.println(productStr);
        Product product=JsonUtils.json2Product(productStr);
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = product.getUserId();
        product.setUserId(userId);
        try {
            product.setAddTime(DateUtils.getDate(product.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        return new JsonResult<>(OK, productService.add(product));
    }

    @RequestMapping("/update")
    JsonResult<Product> update(@RequestBody String productStr, HttpSession session) {
        System.out.println(productStr);
        Product product=JsonUtils.json2Product(productStr);
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = product.getUserId();
        product.setUserId(userId);
        try {
            product.setAddTime(DateUtils.getDate(product.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        return new JsonResult<>(OK, productService.update(product));
    }

    @RequestMapping("/delete")
    JsonResult<Void> delete(@RequestBody String productStr, HttpSession session) {
        Product product=JsonUtils.json2Product(productStr);
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = product.getUserId();
        product.setUserId(userId);
        productService.delete(product);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/allCount")
    JsonResult<List<Product>> findAllCount(@RequestBody String userStr, HttpSession session) {
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = JsonUtils.json2User(userStr).getUserId();
        List<Product> list = productService.findAllCount(userId);
        return new JsonResult<>(OK, list);
    }
    @RequestMapping("/findById")
    JsonResult<Product> findById(@RequestBody String productStr) {
        Product product=JsonUtils.json2Product(productStr);
        return new JsonResult<>(OK, productService.findById(product));
    }
}
