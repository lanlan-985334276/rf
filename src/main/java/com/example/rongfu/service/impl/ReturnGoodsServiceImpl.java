package com.example.rongfu.service.impl;

import com.example.rongfu.entity.*;
import com.example.rongfu.mapper.*;
import com.example.rongfu.service.IReturnGoodsService;
import com.example.rongfu.service.ex.FailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReturnGoodsServiceImpl implements IReturnGoodsService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private ReturnGoodMapper returnGoodMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<ReturnGoods> all(int userId) {
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else throw new FailedException("未知错误，请联系管理员！");
        List<ReturnGoods> returnGoods = returnGoodMapper.findAll(epid);
        return returnGoods;
    }

    @Override
    public void add(ReturnGoods returnGoods) {
        int userId = returnGoods.getUserId();
        Enterprise enterprise = enterpriseMapper.findByUserId(userId);
        Admin admin = adminMapper.findByUserId(userId);
        int epid = 0;
        if (enterprise != null) {
            epid = enterprise.getEpId();
        } else if (admin != null) {
            epid = admin.getEpId();
        } else throw new FailedException("未知错误，请联系管理员！");
        returnGoods.setEpId(epid);
        returnGoods.setpName(productMapper.findByProductId(returnGoods.getProductId()).getpName());
        returnGoods.setCname(customerMapper.findById(returnGoods.getCustomerId()).getcName());
        if (returnGoodMapper.insert(returnGoods) != 1) throw new FailedException("未知插入错误，请联系管理员！");
        productMapper.updateCount(productMapper.findByProductId(returnGoods.getProductId()).getCount() - returnGoods.getCount(), returnGoods.getProductId());
    }
}
