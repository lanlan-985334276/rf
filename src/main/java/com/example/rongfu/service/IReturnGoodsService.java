package com.example.rongfu.service;

import com.example.rongfu.entity.ReturnGoods;

import java.util.List;

public interface IReturnGoodsService {
    List<ReturnGoods> all(int userId);

    void add(ReturnGoods returnGoods);
}
