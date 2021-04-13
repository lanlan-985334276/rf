package com.example.rongfu;

import com.example.rongfu.entity.User;
import com.example.rongfu.mapper.UserMapper;
import com.example.rongfu.service.ICategoryService;
import com.example.rongfu.service.ISignInService;
import com.example.rongfu.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.SQLException;

@SpringBootTest
class RongfuApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ISignInService signInService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;


    @Test
    void contextLoads() {
    }

    @Test
    void textConnction() throws SQLException {
        User user = userMapper.findByUserName("11");
        System.out.println(user == null);
    }

    @Test
    void sendMessage() {

    }
    @Test
    void sendEmail(){

    }
    @Test
    void addStaff(){
        User user=userMapper.findByUserId(2);
        System.out.println(user);
        userService.addStaff(1,2);
    }
    @Test
    void deleteStaff(){
        userService.deleteStaff(2);
    }
    @Test
    void find(){
        System.out.println(signInService.ToadySignIn(1));
        System.out.println(signInService.ToadyNotSignIn(1));

    }
    @Test
    void findCategory(){
        System.out.println(categoryService.findAll(1));
    }
}
