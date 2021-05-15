package com.example.rongfu.service;

import com.example.rongfu.entity.Delivery;
import com.example.rongfu.entity.Purchase;

import java.util.List;

public interface IDeliveryService {
    List<Delivery> all(int userId);

    void add(Delivery delivery);
}
