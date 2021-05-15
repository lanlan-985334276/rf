package com.example.rongfu.service.impl;

import com.example.rongfu.entity.Admin;
import com.example.rongfu.entity.Delivery;
import com.example.rongfu.entity.Enterprise;
import com.example.rongfu.entity.Purchase;
import com.example.rongfu.mapper.*;
import com.example.rongfu.service.IDeliveryService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeliveryServiceImpl implements IDeliveryService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private DeliveryMapper deliveryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<Delivery> all(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else throw new FailedException("未知错误，请联系管理员！");
        List<Delivery> purchases = deliveryMapper.findAll(epid);
        return purchases;
    }

    @Override
    public void add(Delivery delivery) {
        int userId = delivery.getUserId();
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else throw new FailedException("未知错误，请联系管理员！");
        delivery.setEpId(epid);
        delivery.setpName(productMapper.findByProductId(delivery.getProductId()).getpName());
        delivery.setCname(customerMapper.findById(delivery.getCustomerId()).getcName());
        if (deliveryMapper.insert(delivery) != 1) throw new FailedException("未知插入错误，请联系管理员！");
        productMapper.updateCount(productMapper.findByProductId(delivery.getProductId()).getCount() - delivery.getCount(), delivery.getProductId());
    }
}
