package com.example.rongfu.controller;

import com.example.rongfu.entity.LogEntity;
import com.example.rongfu.service.ILogService;
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
@RequestMapping("/log")
public class LogController extends BaseController {

    @Autowired
    private ILogService logService;

    @RequestMapping("/all")
    public JsonResult<List<LogEntity>> all(@RequestBody String logStr, HttpSession session) {
        LogEntity logEntity = JsonUtils.json2LogEntity(logStr);
        int userId = 0;
        if (session != null && session.getAttribute("userId") != null)
            userId = Integer.valueOf(session.getAttribute("userId").toString());
        else userId = logEntity.getUserId();
        return new JsonResult<>(OK, logService.all(userId));
    }

    @RequestMapping("/add")
    public JsonResult<Void> add(@RequestBody String logStr, HttpSession session) {
        LogEntity logEntity = JsonUtils.json2LogEntity(logStr);
        if (session != null && session.getAttribute("userId") != null)
            logEntity.setUserId(Integer.valueOf(session.getAttribute("userId").toString()));
        if (logEntity.getAddTime() == null) {
            try {
                logEntity.setAddTime(DateUtils.getDate(logEntity.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                throw new FailedException("未知错误，请联系管理员！");
            }
        }
        logService.add(logEntity);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/delete")
    public JsonResult<Void> delete(@RequestBody String logStr, HttpSession session) {
        LogEntity logEntity = JsonUtils.json2LogEntity(logStr);
        if (session != null)
            logEntity.setUserId(Integer.valueOf(session.getAttribute("userId").toString()));
        logService.delete(logEntity);
        return new JsonResult<>(OK);
    }
}
