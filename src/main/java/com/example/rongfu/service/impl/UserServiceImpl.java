package com.example.rongfu.service.impl;

import com.example.rongfu.entity.*;
import com.example.rongfu.mapper.*;
import com.example.rongfu.service.IUserService;
import com.example.rongfu.service.ex.*;
import com.example.rongfu.util.SendMessageUtils;
import com.example.rongfu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private StaffMapper staffMapper;

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
        Enterprise enterprise = new Enterprise();
        enterprise.setEpName(eqName);
        enterprise.setUserId(result.getUserId());
        enterprise.setTime(new Timestamp(new Date().getTime()));
        System.out.println(vCode);
        codeMapper.delete(vCode.getVcId());
        result.setUserName(username);
        result.setPassword(password);
        //调用userMapper的insert方法进行插入
        Integer rows = userMapper.insert(result);
        //若影响行数不为1，则抛出InsertException
        if (rows != 1) {
            throw new InsertException("注册失败，未知插入错误！请联系管理员!");
        }
        rows = enterpriseMapper.insert(enterprise);
        if (rows != 1) {
            userMapper.delete(result.getUserId());
            throw new FailedException("注册失败，未知插入错误！请联系管理员!");
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

        if (enterprise != null) {
            result.setEnterprise(true);
        } else if (admin != null) {
            result.setAdmin(true);
        } else
            throw new AdminNotFoundException("没有权限，请联系您的企业管理员！");
        //返回登录者的用户信息
        result.setLastLoginTime(new Timestamp(new Date().getTime()));
        if (userMapper.updateLastLoginTime(result) != 1)
            throw new FailedException("登录失败，未知错误，请联系管理员！");
        return result;
    }


    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendCode(String username) {
        String code = Integer.toString((int) ((Math.random() * 9 + 1) * 100000));
        String content = "欢迎您使用RongFu平台，您的验证码为：" + code;
        if (StringUtils.isEmail(username)) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(from); // 邮件发送者
                message.setTo(username); // 邮件接受者
                message.setSubject("【RongFu】验证码"); // 主题
                message.setText(content); // 内容
                mailSender.send(message);
            } catch (MailException e) {
                throw new FailedException("发送失败，请重试");
            }
        } else if (StringUtils.isPhone(username)) {
            Integer resultCode = SendMessageUtils.send("lanlan985334276",
                    "d41d8cd98f00b204e980",
                    username, content);
            System.out.println("IMessageService:resultCode=" + resultCode);
            switch (resultCode) {
                case -4:
                    throw new FailedException("手机号格式不正确");
                case -41:
                    throw new FailedException("手机号码为空！");
                default:
                    break;
            }

        } else
            throw new FailedException("格式不正确！");
        long time = new Date().getTime();
        VerificationCode vCode = new VerificationCode();
        vCode.setVerificationCode(code);
        vCode.setUsername(username);
        vCode.setStartTime(new Timestamp(time));
        vCode.setEndTime(new Timestamp(time + 5 * 60 * 1000));
        if (codeMapper.insert(vCode) != 1) {
            throw new FailedException("发送验证码失败，未知插入错误，请联系管理员！");
        }
    }

    @Override
    public List<User> queryUserToAdd(String username) {
        List<User> users = userMapper.findNotInEnterprise("%" + username + "%");
        return users;
    }

    @Override
    public void addStaff(int mUserId, int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(mUserId);
        if (enterprise == null)
            throw new FailedException("未知错误，请尝试重新登录！");
        Staff staff = new Staff();
        staff.setUserId(mUserId);
        staff.setEpId(enterprise.getEpId());
        staff.setAddTime(new Timestamp(new Date().getTime()));
        if (staffMapper.insert(staff) != 1)
            throw new FailedException("添加失败，未知插入错误！");
    }
}
