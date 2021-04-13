package com.example.rongfu.controller;

import com.example.rongfu.entity.ProductCategory;
import com.example.rongfu.service.ICategoryService;
import com.example.rongfu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping("/all")
    JsonResult<List<ProductCategory>> allSignInLog(HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        List<ProductCategory> list = categoryService.findAll(userId);
        System.out.println(list);
        return new JsonResult<>(OK, list);
    }

    @RequestMapping("/add")
    JsonResult<Void> add(String pcName, HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        ProductCategory category = new ProductCategory();
        category.setPcName(pcName);
        category.setUserId(userId);
        categoryService.add(category);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/delete")
    JsonResult<Void> delete(int pcId, HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        ProductCategory category = new ProductCategory();
        category.setPcId(pcId);
        category.setUserId(userId);
        categoryService.delete(category);
        return new JsonResult<>(OK);
    }
    @RequestMapping("/update")
    JsonResult<Void> update(ProductCategory category, HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        category.setUserId(userId);
        System.out.println(category);
        categoryService.update(category);
        return new JsonResult<>(OK);
    }
}
