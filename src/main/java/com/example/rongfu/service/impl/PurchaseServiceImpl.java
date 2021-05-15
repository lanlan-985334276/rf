package com.example.rongfu.service.impl;

import com.example.rongfu.entity.*;
import com.example.rongfu.mapper.*;
import com.example.rongfu.service.IAdminService;
import com.example.rongfu.service.IPurchaseService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PurchaseServiceImpl implements IPurchaseService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<Purchase> all(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else throw new FailedException("未知错误，请联系管理员！");
        List<Purchase> purchases = purchaseMapper.findAll(epid);
        return purchases;
    }

    @Override
    public void add(Purchase purchase) {
        int userId = purchase.getUserId();
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else throw new FailedException("未知错误，请联系管理员！");
        purchase.setEpId(epid);
        purchase.setpName(productMapper.findByProductId(purchase.getProductId()).getpName());
        purchase.setCname(customerMapper.findById(purchase.getCustomerId()).getcName());
        if (purchaseMapper.insert(purchase) != 1) throw new FailedException("未知插入错误，请联系管理员！");
        productMapper.updateCount(purchase.getCount() + productMapper.findByProductId(purchase.getProductId()).getCount(), purchase.getProductId());
    }
}
