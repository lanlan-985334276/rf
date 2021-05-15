package com.example.rongfu.service.impl;

import com.example.rongfu.entity.Admin;
import com.example.rongfu.entity.Enterprise;
import com.example.rongfu.entity.LogEntity;
import com.example.rongfu.entity.Staff;
import com.example.rongfu.mapper.*;
import com.example.rongfu.service.ILogService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements ILogService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public List<LogEntity> all(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        Staff staff = staffMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else if (staff != null) epid = staff.getEpId();
        List<LogEntity> logEntities = logMapper.findAll(epid);
        return logEntities;
    }

    @Override
    public void add(LogEntity logEntity) {
        int userId = logEntity.getUserId();
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        Staff staff = staffMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else if (staff != null) epid = staff.getEpId();
        logEntity.setEpId(epid);
        logEntity.setUserName(userMapper.findByUserId(logEntity.getUserId()).getUserName());
        if (logMapper.insert(logEntity) != 1) throw new FailedException("未知插入错误，请联系管理员！");
    }

    @Override
    public void delete(LogEntity logEntity) {
        if (logMapper.delete(logEntity.getLogId()) != 1) throw new FailedException("未知删除错误，请联系管理员！");
    }
}
