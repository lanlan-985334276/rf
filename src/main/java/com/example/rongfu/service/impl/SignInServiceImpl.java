package com.example.rongfu.service.impl;

import com.example.rongfu.entity.*;
import com.example.rongfu.mapper.AdminMapper;
import com.example.rongfu.mapper.EnterpriseMapper;
import com.example.rongfu.mapper.SignInMapper;
import com.example.rongfu.mapper.StaffMapper;
import com.example.rongfu.service.ISignInService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SignInServiceImpl implements ISignInService {

    @Autowired
    private SignInMapper signInMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public List<User> ToadySignIn(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        if (enterprise == null) {
            Admin admin = adminMapper.findByUserId(userId);
            if (admin != null) enterprise = enterpriseMapper.findByEpId(admin.getEpId());
            else throw new FailedException("未知错误，请重新登录！");
        }
        List<User> list = new ArrayList<>();
        try {
            Timestamp startTime = new Timestamp(DateUtils.getToadyDateTimeMillis());
            Timestamp endTime = new Timestamp(DateUtils.getTomorrowDateTimeMills());
            list.addAll(signInMapper.findSignInByEpidAndTime(enterprise.getEpId(), startTime, endTime));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        return list;
    }

    @Override
    public List<User> ToadyNotSignIn(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        if (enterprise == null) {
            Admin admin = adminMapper.findByUserId(userId);
            if (admin != null) enterprise = enterpriseMapper.findByEpId(admin.getEpId());
            else throw new FailedException("未知错误，请重新登录！");
        }
        List<User> list = new ArrayList<>();
        try {
            Timestamp startTime = new Timestamp(DateUtils.getDailyStartTime(System.currentTimeMillis(), null));
            Timestamp endTime = new Timestamp(DateUtils.getDailyEndTime(System.currentTimeMillis(), null));
            System.out.println(startTime + " " + endTime);
            list.addAll(signInMapper.findNotSignInByEpidAndTime(enterprise.getEpId(), startTime, endTime));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        System.out.println(list);
        return list;
    }

    @Override
    public void SignIn(SignIn signIn) {
        signIn.setDate(new Timestamp(System.currentTimeMillis()));
        Timestamp startTime = new Timestamp(DateUtils.getDailyStartTime(System.currentTimeMillis(), null));
        Timestamp endTime = new Timestamp(DateUtils.getDailyEndTime(System.currentTimeMillis(), null));
        List<SignIn> signIns = signInMapper.findByEpidAndTime(signIn.getEpid(), startTime, endTime);
        for (SignIn s : signIns) {
            System.out.println(s.getStaffId()+" "+signIn.getStaffId());
            if (s.getStaffId() == signIn.getStaffId()) {
                System.out.println(signIn);
                signIn.setSiId(s.getSiId());
                if (signInMapper.update(signIn) != 1) throw new FailedException("签到失败！");
                return;
            }
        }
    }

    @Override
    public List<User> findAll(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        if (enterprise == null) {
            Admin admin = adminMapper.findByUserId(userId);
            if (admin != null) enterprise = enterpriseMapper.findByEpId(admin.getEpId());
            else throw new FailedException("未知错误，请重新登录！");
        }
        return signInMapper.findAllByEpId(enterprise.getEpId());
    }
}
