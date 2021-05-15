package com.example.rongfu.controller;

import com.example.rongfu.entity.ProductCategory;
import com.example.rongfu.service.ICategoryService;
import com.example.rongfu.util.JsonResult;
import com.example.rongfu.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
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
    JsonResult<List<ProductCategory>> allSignInLog(@RequestBody String userStr, HttpSession session) {
        int userId = 0;
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = JsonUtils.json2User(userStr).getUserId();
        List<ProductCategory> list = categoryService.findAll(userId);
        System.out.println(list);
        return new JsonResult<>(OK, list);
    }

    @RequestMapping("/add")
    JsonResult<Void> add(@RequestBody String categoryStr, HttpSession session) {
        int userId = 0;
        ProductCategory category = JsonUtils.json2Category(categoryStr);
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else
            userId = category.getUserId();
        category.setUserId(userId);
        categoryService.add(category);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/delete")
    JsonResult<Void> delete(@RequestBody String categoryStr, HttpSession session) {
        int userId = 0;
        ProductCategory category = JsonUtils.json2Category(categoryStr);
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else
            userId = category.getUserId();
        category.setUserId(userId);
        categoryService.delete(category);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/update")
    JsonResult<Void> update(@RequestBody String categoryStr, HttpSession session) {
        int userId = 0;
        ProductCategory category = JsonUtils.json2Category(categoryStr);
        if (session != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else
            userId = category.getUserId();
        category.setUserId(userId);
        System.out.println(category);
        categoryService.update(category);
        return new JsonResult<>(OK);
    }
}
