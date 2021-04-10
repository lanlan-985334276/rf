package com.example.rongfu.controller;

import com.example.rongfu.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController extends BaseController {
    @RequestMapping("/index")
    public JsonResult<Void> index() {
        return new JsonResult<>(OK, "这是导航页");
    }
}
