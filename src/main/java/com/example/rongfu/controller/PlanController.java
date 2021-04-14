package com.example.rongfu.controller;

import com.example.rongfu.entity.Plan;
import com.example.rongfu.entity.User;
import com.example.rongfu.service.IPlanService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.DateUtils;
import com.example.rongfu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
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
    public JsonResult<Void> add(Plan plan, String time, HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        if (userId == 0)
            throw new FailedException("未知错误！");
        try {
            plan.setAddTime(DateUtils.getDate(time));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        plan.setUserId(userId);
        planService.add(plan);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/delete")
    public JsonResult<Void> delete(int planId) {
        planService.delete(planId);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/all")
    public JsonResult<List<Plan>> all(HttpSession session) {
        int userId = Integer.valueOf(session.getAttribute("userId").toString());
        if (userId == 0)
            throw new FailedException("未知错误！");
        return new JsonResult<>(OK, planService.findAll(userId));
    }

    @RequestMapping("/today")
    public JsonResult<List<Plan>> today(HttpSession session) {
        int userid = Integer.valueOf(session.getAttribute("userId").toString());
        if (userid == 0)
            throw new FailedException("未知错误！");
        return new JsonResult<>(OK, planService.findTodayPlan(userid));
    }
}
