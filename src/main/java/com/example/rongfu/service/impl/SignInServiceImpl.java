package com.example.rongfu.service.impl;

import com.example.rongfu.entity.Enterprise;
import com.example.rongfu.entity.User;
import com.example.rongfu.mapper.EnterpriseMapper;
import com.example.rongfu.mapper.SignInMapper;
import com.example.rongfu.service.ISignInService;
import com.example.rongfu.service.ex.FailedException;
import com.example.rongfu.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SignInServiceImpl implements ISignInService {

    @Autowired
    private SignInMapper signInMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public List<User> ToadySignIn(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        if (enterprise == null)
            throw new FailedException("未知错误，请重新登录！");
        List<User> list = new ArrayList<>();
        try {
            Timestamp startTime = new Timestamp(DateUtils.getToadyDateTimeMillis());
            Timestamp endTime = new Timestamp(DateUtils.getTomorrowDateTimeMills());
            list.addAll(signInMapper.findTodaySignIn(enterprise.getEpId(), startTime, endTime));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        System.out.println(list);
        return list;
    }

    @Override
    public List<User> ToadyNotSignIn(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        if (enterprise == null)
            throw new FailedException("未知错误，请重新登录！");
        List<User> list = new ArrayList<>();
        try {
            Timestamp startTime = new Timestamp(DateUtils.getToadyDateTimeMillis());
            Timestamp endTime = new Timestamp(DateUtils.getTomorrowDateTimeMills());
            list.addAll(signInMapper.findToadyNotSignIn(enterprise.getEpId(), startTime, endTime));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedException("未知错误，请联系管理员！");
        }
        System.out.println(list);
        return list;
    }

    @Override
    public void SignIn(int staffId) {

    }

    @Override
    public List<User> findAll(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        if (enterprise == null)
            throw new FailedException("未知错误，请重新登录！");
        return signInMapper.findAllByEpId(enterprise.getEpId());
    }
}
