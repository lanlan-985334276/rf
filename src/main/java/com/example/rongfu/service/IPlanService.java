package com.example.rongfu.service;

import com.example.rongfu.entity.Plan;

import java.util.List;

public interface IPlanService {

    void add(Plan plan);

    void delete(int planId);

    void update(Plan plan);

    List<Plan> findAll(int userId);

    List<Plan> findAllNotFinished(int userId);

    void updateState(Plan plan);

    List<Plan> findTodayPlan(int userId);

    List<Plan> findByDate(int userId, long startTime, long endTime);

    List<Plan> findTodayNotFinished(int userId);

    List<Plan> findTodayFinished(int userId);

    List<Plan> findByDateState(int userId, long startTime, long endTime, int state);
}
