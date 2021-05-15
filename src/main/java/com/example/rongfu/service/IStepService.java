package com.example.rongfu.service;

import com.example.rongfu.entity.Step;

import java.util.List;

public interface IStepService {
    List<Step> all(int userId);

    Step findByUserId(int userId);

    void add(Step step);

    void update(Step step);
}
