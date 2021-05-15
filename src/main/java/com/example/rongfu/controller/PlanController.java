package com.example.rongfu.controller;

import com.example.rongfu.entity.Plan;
import com.example.rongfu.entity.User;
import com.example.rongfu.service.IPlanService;
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
@RequestMapping("/plan")
public class PlanController extends BaseController {

    @Autowired
    private IPlanService planService;


    @RequestMapping("/add")
    public JsonResult<Void> add(@RequestBody String planStr, HttpSession session) {
        Plan plan = JsonUtils.json2Plan(planStr);
        if (session != null)
            plan.setUserId(Integer.valueOf(session.getAttribute("userId").toString()));
        try {
            plan.setAddTime(DateUtils.getDate(plan.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        planService.add(plan);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/delete")
    public JsonResult<Void> delete(@RequestBody String planStr) {
        int planId=JsonUtils.json2Plan(planStr).getPlanId();
        planService.delete(planId);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/all")
    public JsonResult<List<Plan>> all(@RequestBody String userStr, HttpSession session) {
        User user = new User();
        if (session != null)
            user.setUserId(Integer.valueOf(session.getAttribute("userId").toString()));
        else user = JsonUtils.json2User(userStr);
        return new JsonResult<>(OK, planService.findAll(user.getUserId()));
    }

    @RequestMapping("/today")
    public JsonResult<List<Plan>> today(HttpSession session) {
        int userid = Integer.valueOf(session.getAttribute("userId").toString());
        if (userid == 0)
            throw new FailedException("未知错误！");
        return new JsonResult<>(OK, planService.findTodayPlan(userid));
    }

    @RequestMapping("/allNotFinished")
    public JsonResult<List<Plan>> allNotFinished(@RequestBody String userStr) {
        User user = JsonUtils.json2User(userStr);
        System.out.println(user.getUserId());
        return new JsonResult<>(OK, planService.findAllNotFinished(user.getUserId()));
    }

    @RequestMapping("/updateState")
    public JsonResult<Void> updateState(@RequestBody String planStr) {
        Plan plan = JsonUtils.json2Plan(planStr);
        planService.updateState(plan);
        return new JsonResult<>(OK);
    }
}
