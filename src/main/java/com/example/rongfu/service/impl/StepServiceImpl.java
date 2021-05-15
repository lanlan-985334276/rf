package com.example.rongfu.service.impl;

import com.example.rongfu.entity.*;
import com.example.rongfu.mapper.*;
import com.example.rongfu.service.IStepService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StepServiceImpl implements IStepService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private StepMapper stepMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public List<Step> all(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        Staff staff=staffMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        }else if (staff!=null){
            epid=staff.getEpId();
        }
        List<Step> steps = stepMapper.findAll(epid);
        return steps;
    }

    @Override
    public void add(Step step) {
        int userId = step.getUserId();
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        Staff staff=staffMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        }else if (staff!=null){
            epid=staff.getEpId();
        }
        step.setEpId(epid);
        step.setUserName(userMapper.findByUserId(step.getUserId()).getUserName());
        if (stepMapper.insert(step) != 1) throw new FailedException("未知插入错误，请联系管理员！");
    }

    @Override
    public Step findByUserId(int userId) {
        return stepMapper.findByUserId(userId);
    }

    @Override
    public void update(Step step) {
        if (stepMapper.update(step) != 1) throw new FailedException("未知更新错误，请联系管理员！");
    }
}
