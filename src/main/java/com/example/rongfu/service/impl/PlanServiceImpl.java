package com.example.rongfu.service.impl;

import com.example.rongfu.entity.Plan;
import com.example.rongfu.entity.User;
import com.example.rongfu.mapper.PlanMapper;
import com.example.rongfu.mapper.UserMapper;
import com.example.rongfu.service.IPlanService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanServiceImpl implements IPlanService {

    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(Plan plan) {
        String progress = "";
        progress = "0";
        for (int i = 1; i < plan.getStaffs().split("-").length; i++) {
            progress += "-0";
        }
        plan.setProgress(progress);
        if (planMapper.insert(plan) != 1)
            throw new FailedException("添加失败，未知插入错误，请联系管理员！");
    }

    @Override
    public void delete(int planId) {
        if (planMapper.delete(planId) != 1)
            throw new FailedException("删除失败，未知删除错误，请联系管理员");
    }

    @Override
    public void update(Plan plan) {
        if (planMapper.update(plan) != 1)
            throw new FailedException("修改失败，未知更新错误，请联系管理员");
    }

    @Override
    public List<Plan> findAll(int userId) {
        List<Plan> plans = planMapper.findAll(userId);
        for (int i = 0; i < plans.size(); i++) {
            List<User> users = new ArrayList<>();
            String[] userIds = plans.get(i).getStaffs().split("-");
            for (int j = 0; j < userIds.length; j++) {
                try {
                    users.add(userMapper.findByUserId(Integer.valueOf(userIds[j])));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new FailedException("未知错误，请联系管理员！");
                }
            }
            String[] progressArr = plans.get(i).getProgress().split("-");
            int progress = 0;
            for (int j = 0; j < progressArr.length; j++) {
                progress += Integer.valueOf(progressArr[i]);
            }
            plans.get(i).setUserList(users);
            plans.get(i).setProgressNum(progress / progressArr.length * 100);
        }
        return plans;
    }

    @Override
    public List<Plan> findTodayPlan(int userId) {
        List<Plan> list = findByDate(userId, DateUtils.getToadyDateTimeMillis(), DateUtils.getTomorrowDateTimeMills());
        return list;
    }

    @Override
    public List<Plan> findByDate(int userId, long startTime, long endTime) {
        List<Plan> plans = planMapper.findByDate(userId, new Timestamp(startTime), new Timestamp(endTime));
        for (int i = 0; i < plans.size(); i++) {
            List<User> users = new ArrayList<>();
            String[] userIds = plans.get(i).getStaffs().split("-");
            for (int j = 0; j < userIds.length; j++) {
                try {
                    users.add(userMapper.findByUserId(Integer.valueOf(userIds[j])));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new FailedException("未知错误，请联系管理员！");
                }
            }
            String[] progressArr = plans.get(i).getProgress().split("-");
            int progress = 0;
            for (int j = 0; j < progressArr.length; j++) {
                progress += Integer.valueOf(progressArr[i]);
                users.get(i).setState(progress);
            }
            plans.get(i).setUserList(users);
            plans.get(i).setProgressNum(progress / progressArr.length * 100);
        }
        return plans;
    }

    @Override
    public List<Plan> findTodayNotFinished(int userId) {
        List<Plan> list = findTodayPlan(userId);
        for (int i = 0; i < list.size(); ) {
            Plan plan = list.get(i);
            if (!plan.getProgress().contains("0")) {
                list.remove(i);
            } else {
                i++;
            }
        }
        return list;
    }

    @Override
    public List<Plan> findTodayFinished(int userId) {
        List<Plan> list = findTodayPlan(userId);
        for (int i = 0; i < list.size(); ) {
            Plan plan = list.get(i);
            if (plan.getProgress().contains("0")) {
                list.remove(i);
            } else {
                i++;
            }
        }
        return list;
    }

    @Override
    public List<Plan> findByDateState(int userId, long startTime, long endTime, int state) {
        //state 0:未完成的 1：完成的
        List<Plan> list = findByDate(userId, startTime, endTime);
        for (int i = 0; i < list.size(); ) {
            Plan plan = list.get(i);
            if (plan.getProgress().contains("0")) {
                //计划未完成
                if (state != 0) list.remove(i);
                else i++;
            } else {
                if (state != 0) i++;
                else list.remove(i);
            }
        }
        return list;
    }
}
