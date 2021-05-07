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

    @Autowired
    private SignInMapper signInMapper;

    @Override
    public void regEp(User user) {
        User result = reg(user);
        //判断企业是否已注册
        if (enterpriseMapper.findByName(user.getEqName()) != null)
            throw new UserNameErrorException("注册失败，企业已注册！");
        Enterprise enterprise = new Enterprise();
        enterprise.setEpName(user.getEqName());
        enterprise.setUserId(result.getUserId());
        enterprise.setTime(new Timestamp(new Date().getTime()));
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
        List<VerificationCode> vCode = codeMapper.findByUsername(user.getUserName());
        if (vCode.size() > 0)
            codeMapper.delete(vCode.get(vCode.size() - 1).getVcId());
    }

    @Override
    public User reg(User user) {
        User result = null;
        if (StringUtils.isPhone(user.getUserName())) {
            result = userMapper.findByPhone(user.getUserName());
            if (result != null)
                throw new PhoneDuplicateException("注册失败，手机号已注册！");
            result = new User();
            result.setPhone(user.getUserName());
        } else if (StringUtils.isEmail(user.getUserName())) {
            result = userMapper.findByEmail(user.getUserName());
            if (result != null)
                throw new EmailDuplicateException("注册失败，邮箱已注册！");
            result = new User();
            result.setEmail(user.getUserName());
        } else {
            throw new UserNameErrorException("注册失败，格式错误！");
        }
        //判断验证码是否正确
        List<VerificationCode> vCode = codeMapper.findByUsername(user.getUserName());

        if (vCode.size() == 0 || !vCode.get(vCode.size() - 1).getVerificationCode().equals(user.getCode()))
            throw new VerificationCodeErrorException("验证码错误！");
        result.setUserName(user.getUserName());
        result.setPassword(user.getPassword());
        return result;
    }

    @Override
    public User login(User user) {
        //调用userMapper的findByUsername按用户名进行查询
        User result;
        if (StringUtils.isPhone(user.getUserName()))
            result = userMapper.findByPhone(user.getUserName());
        else if (StringUtils.isEmail(user.getUserName()))
            result = userMapper.findByEmail(user.getUserName());
        else result = userMapper.findByUserName(user.getUserName());
        //若未找到，抛出UserNotFoundException
        if (result == null) {
            throw new UserNotFoundException("用户不存在");
        }
        //比较密码是否匹配
        //若不匹配，抛出PasswordNotMatchException
        if (!user.getPassword().equals(result.getPassword())) {
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
        codeMapper.deleteByUserName(user.getUserName());
        return result;
    }

    @Override
    public User loginByCode(String userName, String code) {
        User result;
        if (StringUtils.isPhone(userName))
            result = userMapper.findByPhone(userName);
        else if (StringUtils.isEmail(userName))
            result = userMapper.findByEmail(userName);
        else result = userMapper.findByUserName(userName);
        //若未找到，抛出UserNotFoundException
        if (result == null) {
            throw new UserNotFoundException("用户不存在");
        }
        List<VerificationCode> vCode = codeMapper.findByUsername(userName);
        if (vCode.size() == 0 || !vCode.get(vCode.size() - 1).getVerificationCode().equals(code))
            throw new VerificationCodeErrorException("验证码错误！");
        codeMapper.deleteByUserName(userName);
        return result;
    }

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String sendCode(String username) {
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
        if (codeMapper.findByUsername(username).size() > 0) {
            codeMapper.deleteByUserName(username);
        }
        if (codeMapper.insert(vCode) != 1) {
            throw new FailedException("发送验证码失败，未知插入错误，请联系管理员！");
        }
        return code;
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
        staff.setUserId(userId);
        staff.setEpId(enterprise.getEpId());
        staff.setAddTime(new Timestamp(new Date().getTime()));
        System.out.println(staff);
        if (staffMapper.insert(staff) != 1)
            throw new FailedException("添加失败，未知插入错误！");
    }

    @Override
    public void deleteStaff(int userId) {
        System.out.println(userId);
        signInMapper.delete(staffMapper.findByUserId(userId).getStaffId());
        adminMapper.delete(adminMapper.findByUserId(userId).getAdminId());
        if (staffMapper.delete(userId) != 1)
            throw new FailedException("删除失败，未知删除错误，请联系管理员！");
    }

    @Override
    public List<User> queryStaff(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        if (enterprise == null)
            throw new FailedException("未知错误，请重新登录！");
        return staffMapper.findByEnterpriseId(enterprise.getEpId());
    }

    @Override
    public List<User> queryStaff2(int userId, String username) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        if (enterprise == null)
            throw new FailedException("未知错误，请重新登录！");
        return staffMapper.findByEnterpriseId2(enterprise.getEpId(), "%" + username + "%");
    }

    @Override
    public User findByUserId(int userId) {
        return userMapper.findByUserId(userId);
    }

    @Override
    public User findByUserName(String userName) {
        User user = userMapper.findByUserName(userName);
        if (user == null) throw new FailedException("用户不存在！");
        return user;
    }

    @Override
    public User equalsCode(User user) {

        return null;
    }

    @Override
    public User loginApp(User user) {
        //调用userMapper的findByUsername按用户名进行查询
        User result;
        if (StringUtils.isPhone(user.getUserName()))
            result = userMapper.findByPhone(user.getUserName());
        else if (StringUtils.isEmail(user.getUserName()))
            result = userMapper.findByEmail(user.getUserName());
        else result = userMapper.findByUserName(user.getUserName());
        //若未找到，抛出UserNotFoundException
        if (result == null) {
            throw new UserNotFoundException("用户不存在");
        }
        //比较密码是否匹配
        //若不匹配，抛出PasswordNotMatchException
        if (!user.getPassword().equals(result.getPassword())) {
            throw new PasswordNotMatchException("密码错误");
        }
        Enterprise enterprise = enterpriseMapper.findByUserId(result.getUserId());
        Admin admin = adminMapper.findByUserId(result.getUserId());

        if (enterprise != null) {
            result.setEnterprise(true);
        } else if (admin != null) {
            result.setAdmin(true);
        }
        //返回登录者的用户信息
        result.setLastLoginTime(new Timestamp(new Date().getTime()));
        if (userMapper.updateLastLoginTime(result) != 1)
            throw new FailedException("登录失败，未知错误，请联系管理员！");
        codeMapper.deleteByUserName(user.getUserName());
        return result;
    }

    @Override
    public User regOther(User user) {
        user = reg(user);
        Integer rows = userMapper.insert(user);
        //若影响行数不为1，则抛出InsertException
        if (rows != 1) {
            throw new InsertException("注册失败，未知插入错误！请联系管理员!");
        }
        List<VerificationCode> vCode = codeMapper.findByUsername(user.getUserName());
        if (vCode.size() > 0)
            codeMapper.delete(vCode.get(vCode.size() - 1).getVcId());
        return user;
    }
}
