/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryCustomerProItemService
 */
package com.ruoyi.mry.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.mapper.*;
import com.ruoyi.mry.model.*;
import com.ruoyi.mry.service.MryCustomerProItemService;
import com.ruoyi.mry.util.MryTimeUtils;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * MryCustomerProItemService
 * @author zhangguifeng
 * @create 2020-01-17 17:33
 **/
@Slf4j
@Service("mryCustomerProService")
public class MryCustomerProItemServiceImpl implements MryCustomerProItemService {

    @Autowired
    private MryCustomerProItemMapper customerProItemMapper;
    @Autowired
    private MryServiceProMapper serviceProMapper;
    @Autowired
    private MryCustomerCardMapper customerCardMapper;
    @Autowired
    private MryCustomerMapper customerMapper;
    @Autowired
    private MryShopMapper shopMapper;
    @Autowired
    private MryShopCardMapper shopCardMapper;

    @Override
    public List<MryCustomerProItem> selectCustomerProItems(MryCustomerProItem customerProItem) {
        MryCustomerProItemExample proItemExample = new MryCustomerProItemExample();
        MryCustomerProItemExample.Criteria criteria = proItemExample.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        MryCustomerExample customerExample = new MryCustomerExample();
        MryCustomerExample.Criteria customerCri = customerExample.createCriteria();
        customerCri.andIsDeletedEqualTo(false);
        if (customerProItem.getShopId() != null) {
            customerCri.andShopIdEqualTo(customerProItem.getShopId());
        }
        if (StringUtils.isNotBlank(customerProItem.getCustomerName()) && StringUtils.isNotBlank(customerProItem.getCustomerName().trim())) {
            customerCri.andNameLike("%" + customerProItem.getCustomerName() + "%");
        }
        List<MryCustomer> customers = this.customerMapper.selectByExample(customerExample);
        Map<Long, MryCustomer> customerMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(customers)) {
            if (StringUtils.isNotBlank(customerProItem.getCustomerName()) && StringUtils.isNotBlank(customerProItem.getCustomerName().trim())) {
                List<Long> customerIds = customers.stream().map(MryCustomer::getId).collect(Collectors.toList());
                criteria.andCustomerIdIn(customerIds);
            }

            customers.forEach(item -> {
                customerMap.putIfAbsent(item.getId(), item);
            });
        } else {
            return new ArrayList<>();
        }

        proItemExample.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);
        List<MryCustomerProItem> proItems = this.customerProItemMapper.selectByExample(proItemExample);
        if (CollectionUtils.isNotEmpty(proItems)) {
            // 所属店面
            List<Short> shopIds = proItems.stream().map(MryCustomerProItem::getShopId).collect(Collectors.toList());
            MryShopExample shopExample = new MryShopExample();
            shopExample.createCriteria().andIsDeletedEqualTo(false).andIdIn(shopIds);
            List<MryShop> shops = this.shopMapper.selectByExample(shopExample);
            Map<Short, MryShop> shopMap = new HashMap<>();
            shops.forEach(item -> shopMap.putIfAbsent(item.getId(), item));
            Set<Short> serviceProIds = new HashSet<>();
            for (MryCustomerProItem item : proItems) {
                item.setShopName(shopMap.get(item.getShopId()).getName());
                // 客户名称
                if (customerMap.containsKey(item.getCustomerId())) {
                    item.setCustomerName(customerMap.get(item.getCustomerId()).getName());
                }
                serviceProIds.add(item.getProId());
            }

            MryServiceProExample serviceProExample = new MryServiceProExample();
            serviceProExample.createCriteria().andIsDeletedEqualTo(false).andIdIn(new ArrayList<>(serviceProIds));
            List<MryServicePro> servicePros = this.serviceProMapper.selectByExample(serviceProExample);
            if (CollectionUtils.isNotEmpty(servicePros)) {
                Map<Short, MryServicePro> serviceProMap = new HashMap<>();
                servicePros.forEach(item -> serviceProMap.putIfAbsent(item.getId(), item));
                for (MryCustomerProItem item : proItems) {
                    if (serviceProMap.containsKey(item.getProId())) {
                        item.setProName(serviceProMap.get(item.getProId()).getName());
                    }
                }
            }
        }

        return proItems;
    }

    @Override
    public int insertCustomerProItem(MryCustomerProItem customerProItem) {
        Date nowDate = new Date();
        customerProItem.setIsDeleted(false);
        customerProItem.setCreateTime(nowDate);
        customerProItem.setUpdateTime(nowDate);

        return this.customerProItemMapper.insertSelective(customerProItem);
    }

    @Override
    public MryCustomerProItem selectCustomerProItemById(Long id) {
        MryCustomerProItem proItem = this.customerProItemMapper.selectByPrimaryKey(id);

        if (proItem.getServiceStart() != null) {
            proItem.setServiceStartStr(MryTimeUtils.date2Str(proItem.getServiceStart(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (proItem.getServiceEnd() != null) {
            proItem.setServiceEndStr(MryTimeUtils.date2Str(proItem.getServiceEnd(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }

        return proItem;
    }

    @Override
    public int updateCustomerProItem(MryCustomerProItem customerProItem) {
        Date nowDate = new Date();
        customerProItem.setUpdateTime(nowDate);

        if (StringUtils.isNotBlank(customerProItem.getServiceStartStr())) {
            customerProItem.setServiceStart(MryTimeUtils.parseTime(customerProItem.getServiceStartStr(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (StringUtils.isNotBlank(customerProItem.getServiceEndStr())) {
            customerProItem.setServiceEnd(MryTimeUtils.parseTime(customerProItem.getServiceEndStr(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }

        return this.customerProItemMapper.updateByPrimaryKeySelective(customerProItem);
    }

    @Override
    public int deleteCustomerProItemByIds(String ids, SysUser user) {
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
            MryCustomerProItemExample example = new MryCustomerProItemExample();
            example.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsLongs);
            MryCustomerProItem update = new MryCustomerProItem();
            update.setIsDeleted(true);
            update.setUpdateTime(new Date());
            if (user != null) {
                update.setOperatorId(user.getUserId());
                update.setOperatorName(user.getUserName());
            }
            return this.customerProItemMapper.updateByExampleSelective(update, example);
        }

        return 0;
    }
}
