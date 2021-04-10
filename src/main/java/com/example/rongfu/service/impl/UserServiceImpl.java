package com.example.rongfu.service.impl;

import com.example.rongfu.entity.Admin;
import com.example.rongfu.entity.Enterprise;
import com.example.rongfu.entity.User;
import com.example.rongfu.entity.VerificationCode;
import com.example.rongfu.mapper.AdminMapper;
import com.example.rongfu.mapper.EnterpriseMapper;
import com.example.rongfu.mapper.UserMapper;
import com.example.rongfu.mapper.VerificationCodeMapper;
import com.example.rongfu.service.IUserService;
import com.example.rongfu.service.ex.*;
import com.example.rongfu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service注解，在Springboot环境加载时，实现类的一个对象交给Spring框架容器进行管理
@Service
public class UserServiceImpl implements IUserService {
    @Autowired //取出Spring框架容器中的对象（UserMapper实现类的对象）赋给下面的成员变量
    private UserMapper userMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private VerificationCodeMapper codeMapper;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void reg(String username, String password, String code, String eqName) {
        User result = null;
        if (StringUtils.isPhone(username)) {
            result = userMapper.findByPhone(username);
            if (result != null)
                throw new PhoneDuplicateException("注册失败，手机号已注册！");
            result = new User();
            result.setPhone(username);
        } else if (StringUtils.isEmail(username)) {
            result = userMapper.findByEmail(username);
            if (result != null)
                throw new EmailDuplicateException("注册失败，邮箱已注册！");
            result = new User();
            result.setEmail(username);
        } else {
            throw new UserNameErrorException("注册失败，格式错误！");
        }
        //判断验证码是否正确
        VerificationCode vCode = codeMapper.findByUsername(username);
        if (vCode == null || !vCode.getVerificationCode().equals(code))
            throw new VerificationCodeErrorException("验证码错误！");
        //判断企业是否已注册
        if (enterpriseMapper.findByName(eqName) != null)
            throw new UserNameErrorException("注册失败，企业已注册！");
        codeMapper.delete(vCode.getVcId());
        result.setUserName(username);
        result.setPassword(password);
        //调用userMapper的insert方法进行插入
        Integer rows = userMapper.insert(result);
        //若影响行数不为1，则抛出InsertException
        if (rows != 1) {
            throw new InsertException("注册失败，未知插入错误！请联系管理员");
        }
    }

    @Override
    public User login(String username, String password) {
        //调用userMapper的findByUsername按用户名进行查询
        User result;
        if (StringUtils.isPhone(username))
            result = userMapper.findByPhone(username);
        else if (StringUtils.isEmail(username))
            result = userMapper.findByEmail(username);
        else result = userMapper.findByUserName(username);
        //若未找到，抛出UserNotFoundException
        if (result == null) {
            throw new UserNotFoundException("用户不存在");
        }
        //比较密码是否匹配
        //若不匹配，抛出PasswordNotMatchException
        if (!password.equals(result.getPassword())) {
            throw new PasswordNotMatchException("密码错误");
        }
        Enterprise enterprise = enterpriseMapper.findByUserId(result.getUserId());
        Admin admin = adminMapper.findByUserId(result.getUserId());
        if (enterprise == null || admin == null)
            throw new AdminNotFoundException("没有权限，请联系您的企业管理员！");
        else if (enterprise != null) {
            result.setEnterprise(true);
        } else {
            result.setAdmin(true);
        }
        //返回登录者的用户信息
        User user = new User();
        user.setUserId(result.getUserId());
        user.setUserName(result.getUserName());
        return user;
    }
}
