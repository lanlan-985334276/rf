package com.example.rongfu.controller;

import com.example.rongfu.entity.LogEntity;
import com.example.rongfu.entity.Step;
import com.example.rongfu.service.IStepService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.DateUtils;
import com.example.rongfu.util.JsonResult;
import com.example.rongfu.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/step")
public class StepController extends BaseController {

    @Autowired
    private IStepService stepService;

    @RequestMapping("/all")
    public JsonResult<List<Step>> all(@RequestBody String stepStr) {
        Step step = JsonUtils.json2Step(stepStr);
        return new JsonResult<>(OK, stepService.all(step.getUserId()));
    }

    @RequestMapping("/add")
    public JsonResult<Void> add(@RequestBody String stepStr) {
        Step step = JsonUtils.json2Step(stepStr);
        stepService.add(step);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/findByUserId")
    public JsonResult<Void> findByUserId(@RequestBody String stepStr) {
        Step step = JsonUtils.json2Step(stepStr);
        stepService.add(step);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/update")
    public JsonResult<Void> update(@RequestBody String stepStr) {
        Step step = JsonUtils.json2Step(stepStr);
        stepService.update(step);
        return new JsonResult<>(OK);
    }
}
