package com.example.rongfu.service;

import com.example.rongfu.entity.User;

import java.util.List;

public interface IAdminService {
    List<User> all(int userId);

    List<User> other(int userId);

    void add(int mUserId,int userId);

    void delete(int adminId);
}
