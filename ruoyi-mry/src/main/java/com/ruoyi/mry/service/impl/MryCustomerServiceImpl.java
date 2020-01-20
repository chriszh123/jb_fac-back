/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryCustomer
 */
package com.ruoyi.mry.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.mapper.MryCustomerMapper;
import com.ruoyi.mry.mapper.MryShopMapper;
import com.ruoyi.mry.model.MryCustomer;
import com.ruoyi.mry.model.MryCustomerExample;
import com.ruoyi.mry.model.MryServicePro;
import com.ruoyi.mry.model.MryServiceProExample;
import com.ruoyi.mry.model.MryShop;
import com.ruoyi.mry.service.MryCustomerService;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MryCustomer
 * @author zhangguifeng
 * @create 2020-01-17 17:31
 **/
@Slf4j
@Service("mryCustomerService")
public class MryCustomerServiceImpl implements MryCustomerService {

    @Autowired
    private MryShopMapper shopMapper;

    @Autowired
    private MryCustomerMapper customerMapper;

    @Override
    public List<MryCustomer> selectCustomers(MryCustomer customer) {
        MryCustomerExample example = new MryCustomerExample();
        MryCustomerExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (StringUtils.isNotBlank(customer.getName()) && StringUtils.isNotBlank(customer.getName().trim())) {
            criteria.andNameLike("%" + customer.getName().trim() + "%");
        }
        List<MryCustomer> customers = this.customerMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(customers)) {
            Map<Short, MryShop> shopMap = new HashMap<>();
            for (MryCustomer item : customers) {
                if (!shopMap.containsKey(item.getShopId())) {
                    MryShop shop = this.shopMapper.selectByPrimaryKey(item.getShopId());
                    shopMap.put(item.getShopId(), shop);
                }
                item.setShopName(shopMap.get(item.getShopId()).getName());
            }
        }

        return customers;
    }

    @Override
    public int insertCustomer(MryCustomer customer) {
        Date nowDate = new Date();
        customer.setIsDeleted(false);
        customer.setCreateTime(nowDate);
        customer.setUpdateTime(nowDate);

        return this.customerMapper.insertSelective(customer);
    }

    @Override
    public MryCustomer selectCustomerById(Long id) {
        MryCustomer customer = this.customerMapper.selectByPrimaryKey(id);

        return customer;
    }

    @Override
    public int updateCustomer(MryCustomer customer) {
        Date nowDate = new Date();
        customer.setUpdateTime(nowDate);

        return this.customerMapper.updateByPrimaryKeySelective(customer);
    }

    @Override
    public int deleteCustomersByIds(String ids, SysUser user) {
        if (StringUtils.isBlank(ids)) {
            throw new MryException("主键id不能为空");
        }
        List<String> idsList = Arrays.asList(ids.split(","));
        List<Long> idsLongs = new ArrayList<>();
        for (String id : idsList) {
            if (StringUtils.isBlank(id)) {
                continue;
            }
            idsLongs.add(Long.valueOf(id));
        }
        if (CollectionUtils.isNotEmpty(idsLongs)) {
            MryCustomerExample example = new MryCustomerExample();
            example.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsLongs);
            MryCustomer update = new MryCustomer();
            update.setIsDeleted(true);
            update.setUpdateTime(new Date());
            if (user != null) {
                update.setOperatorId(user.getUserId());
                update.setOperatorName(user.getUserName());
            }

            return this.customerMapper.updateByExampleSelective(update, example);
        }

        return 0;
    }
}
