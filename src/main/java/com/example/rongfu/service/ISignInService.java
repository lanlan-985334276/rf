package com.example.rongfu.service;

import com.example.rongfu.entity.SignIn;
import com.example.rongfu.entity.User;

import java.util.List;

public interface ISignInService {


    List<User> ToadySignIn(int userId);

    List<User> ToadyNotSignIn(int userId);

    void SignIn(SignIn signIn);

    List<User> findAll(int userId);
}
