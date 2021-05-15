package com.example.rongfu.service;

import com.example.rongfu.entity.Purchase;

import java.util.List;

public interface IPurchaseService {
    List<Purchase> all(int userId);

    void add(Purchase purchase);
}
