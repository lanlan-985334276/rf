package com.example.rongfu.service.impl;

import com.example.rongfu.entity.*;
import com.example.rongfu.mapper.AdminMapper;
import com.example.rongfu.mapper.EnterpriseMapper;
import com.example.rongfu.mapper.PlanMapper;
import com.example.rongfu.mapper.UserMapper;
import com.example.rongfu.service.IPlanService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlanServiceImpl implements IPlanService {

    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public void add(Plan plan) {
        plan.setProgress("0");
        Enterprise enterprise = enterpriseMapper.findByUserId(plan.getUserId());
        Admin admin = adminMapper.findByUserId(plan.getUserId());
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else throw new FailedException("未知错误，请联系管理员");
        plan.setEpId(epid);
        if (planMapper.findByTitle(plan.getTitle(), plan.getEpId()) != null)
            throw new FailedException("计划标题已占用！");
        if (planMapper.insert(plan) != 1)
            throw new FailedException("添加失败，未知插入错误，请联系管理员！");
        List<PlanItem> items = new ArrayList<>();
        try {
            if (plan.getStaffs().indexOf("-") > 0) {
                String[] userIds = plan.getStaffs().split("-");
                for (int i = 1; i < userIds.length; i++) {
                    PlanItem item = new PlanItem();
                    item.setPlanId(planMapper.findByTitle(plan.getTitle(), plan.getEpId()).getPlanId());
                    item.setUserId(Integer.valueOf(userIds[i]));
                    item.setState(0);
                    planMapper.insertItem(item);
                }
            } else {
                PlanItem item = new PlanItem();
                item.setPlanId(planMapper.findByTitle(plan.getTitle(), plan.getEpId()).getPlanId());
                item.setUserId(Integer.valueOf(plan.getStaffs()));
                item.setState(0);
                planMapper.insertItem(item);
            }
        } catch (Exception e) {
            throw new FailedException("添加失败，未知插入错误，请联系管理员！");
        }
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
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else throw new FailedException("未知错误，请联系管理员");
        List<Plan> plans = planMapper.findAll(epid);
        for (int i = 0; i < plans.size(); i++) {
            Plan plan = plans.get(i);
            List<PlanItem> items = planMapper.findItem(plan.getPlanId());
            plan.setPlanItems(items);
        }
        return plans;
    }

    @Override
    public List<Plan> findTodayPlan(int userId) {
        List<Plan> list = findByDate(userId,
                DateUtils.getDailyStartTime(System.currentTimeMillis(), null),
                DateUtils.getDailyEndTime(System.currentTimeMillis(), null));
        return list;
    }

    @Override
    public List<Plan> findByDate(int userId, long startTime, long endTime) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else throw new FailedException("未知错误，请联系管理员");
        List<Plan> plans = planMapper.findByDate(epid, new Timestamp(startTime), new Timestamp(endTime));
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

    @Override
    public List<Plan> findAllNotFinished(int userId) {
        List<Plan> plans = null;
        try {
            List<PlanItem> items = planMapper.findByUserId(userId);
            plans = new ArrayList<>();
            for (PlanItem item : items) {
                Plan plan = planMapper.findByPlanId(item.getPlanId());
                plans.add(plan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plans;
    }

    @Override
    public void updateState(Plan plan) {
        System.out.println(plan);
        PlanItem planItem = planMapper.findByUidAndPiId(plan);
        System.out.println(planItem);
        if (planMapper.updateItem(planItem.getPiId()) != 1) throw new FailedException("提交失败！");
        int sum = planMapper.findCount(plan.getPlanId());
        int progress = (int) ((sum - planMapper.findNotCount(plan.getPlanId())) * 1d / (sum * 1d) * 100);
        plan.setProgress(Integer.toString(progress));
        if (planMapper.updateProgress(plan) != 1) throw new FailedException("提交失败！");
    }
}
