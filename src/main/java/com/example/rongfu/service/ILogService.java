package com.example.rongfu.service;

import com.example.rongfu.entity.Delivery;
import com.example.rongfu.entity.LogEntity;

import java.util.List;

public interface ILogService {
    List<LogEntity> all(int userId);

    void add(LogEntity delivery);
    void delete(LogEntity logEntity);
}
