/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryCustomerCardService
 */
package com.ruoyi.mry.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.mapper.*;
import com.ruoyi.mry.model.*;
import com.ruoyi.mry.service.MryCustomerCardService;
import com.ruoyi.mry.util.MryTimeUtils;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * MryCustomerCardService
 * @author zhangguifeng
 * @create 2020-01-17 17:29
 **/
@Slf4j
@Service("mryCustomerCardService")
public class MryCustomerCardServiceImpl implements MryCustomerCardService {

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

    @Autowired
    private MryStaffMapper staffMapper;

    @Override
    public List<MryCustomerCard> selectCustomerCards(MryCustomerCard customerCard, Map<Long, MryCustomer> customerMap, List<MryShop> shops, List<MryServicePro> servicePros) {
        if (MapUtil.isEmpty(customerMap) || CollUtil.isEmpty(shops)) {
            return CollUtil.newArrayList();
        }
        final MryCustomerCardExample example = new MryCustomerCardExample();
        final MryCustomerCardExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false).andCustomerIdIn(CollUtil.newArrayList(customerMap.keySet()));
        example.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);
        final List<MryCustomerCard> customerCards = this.customerCardMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(customerCards)) {
            // 所属店面
            final Map<Short, MryShop> shopMap = new HashMap<>();
            shops.forEach(item -> shopMap.putIfAbsent(item.getId(), item));
            final Set<Short> serviceProIds = new HashSet<>();
            for (MryCustomerCard item : customerCards) {
                // 剩余积分
                if (item.getLeftPoints().equals(0L)) {
                    item.setLeftPoints(item.getTotalPoints());
                }
                // 剩余次数
                if (item.getLeftTimes().equals(Short.valueOf("0"))) {
                    item.setLeftTimes(item.getTotalTimes());
                }
                item.setShopName(shopMap.get(item.getShopId()).getName());
                // 客户名称
                if (customerMap.containsKey(item.getCustomerId())) {
                    item.setCustomerName(customerMap.get(item.getCustomerId()).getName());
                }
                // 客户初始化服务项目id
                if (StringUtils.isNotBlank(item.getInitProIds())) {
                    List<String> initProIds = Arrays.asList(item.getInitProIds().split(","));
                    initProIds.forEach(proInfo -> {
                        // proId + "-" + serviceTotalCount + "-" + consumeTotalCount;
                        String proId = proInfo.split("-")[0];
                        serviceProIds.add(Short.valueOf(proId));
                    });
                }
            }

            // 客户初始化服务项目名称
            if (CollectionUtils.isNotEmpty(serviceProIds)) {
                if (CollectionUtils.isNotEmpty(servicePros)) {
                    Map<Short, MryServicePro> serviceProMap = new HashMap<>();
                    servicePros.forEach(item -> serviceProMap.putIfAbsent(item.getId(), item));
                    for (MryCustomerCard item : customerCards) {
                        if (StringUtils.isNotBlank(item.getInitProIds())) {
                            StringBuilder initProIdsSB = new StringBuilder();
                            List<String> initProIds = Arrays.asList(item.getInitProIds().split(StrUtil.COMMA));
                            for (String proInfo : initProIds) {
                                // proId + "-" + serviceTotalCount + "-" + consumeTotalCount;
                                String proId = proInfo.split("-")[0];
                                if (serviceProMap.containsKey(Short.valueOf(proId))) {
                                    initProIdsSB.append(serviceProMap.get(Short.valueOf(proId)).getName()).append(StrUtil.COMMA);
                                }
                            }
                            if (StringUtils.isNotBlank(initProIdsSB.toString())) {
                                initProIdsSB = initProIdsSB.deleteCharAt(initProIdsSB.length() - 1);
                                item.setInitProNames(initProIdsSB.toString());
                            }
                        }
                    }
                }
            }
        }

        return customerCards;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertMryCustomerCard(MryCustomerCard customerCard) {
        Date nowDate = new Date();
        customerCard.setIsDeleted(false);
        customerCard.setCreateTime(nowDate);
        customerCard.setUpdateTime(nowDate);
        customerCard.setShopStaffId(customerCard.getOperatorId());
        customerCard.setShopStaffName(customerCard.getOperatorName());

        // 新增当前客户对应的积分数、消费次数
        MryCustomer customer = this.customerMapper.selectByPrimaryKey(customerCard.getCustomerId());
        if (customer != null) {
            Long totalCustomePoints = customer.getTotalCustomePoints();
            totalCustomePoints = totalCustomePoints + (customerCard.getTotalPoints() != null ? customerCard.getTotalPoints() : 0L);
            customer.setTotalCustomePoints(totalCustomePoints);

            Short totalCustomeTimes = customer.getTotalCustomeTimes();
            Integer totalCustomeTimesI = totalCustomeTimes + (customerCard.getTotalTimes() != null ? customerCard.getTotalTimes() : 0);
            customer.setTotalCustomeTimes(totalCustomeTimesI.shortValue());

            customer.setUpdateTime(nowDate);
            customer.setOperatorId(customerCard.getOperatorId());
            customer.setOperatorName(customerCard.getOperatorName());
            this.customerMapper.updateByPrimaryKeySelective(customer);
        }

        return this.customerCardMapper.insertSelective(customerCard);
    }

    @Override
    public MryCustomerCard selectCustomerCardById(Long id) {
        MryCustomerCard customerCard = this.customerCardMapper.selectByPrimaryKey(id);

        if (customerCard.getServiceStart() != null) {
            customerCard.setServiceStartStr(MryTimeUtils.date2Str(customerCard.getServiceStart(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (customerCard.getServiceEnd() != null) {
            customerCard.setServiceEndStr(MryTimeUtils.date2Str(customerCard.getServiceEnd(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        return customerCard;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMryCustomerCard(MryCustomerCard customerCard) {
        Date nowDate = new Date();
        customerCard.setUpdateTime(nowDate);

        if (StringUtils.isNotBlank(customerCard.getServiceStartStr())) {
            customerCard.setServiceStart(MryTimeUtils.parseTime(customerCard.getServiceStartStr(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (StringUtils.isNotBlank(customerCard.getServiceEndStr())) {
            customerCard.setServiceEnd(MryTimeUtils.parseTime(customerCard.getServiceEndStr(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }

        return this.customerCardMapper.updateByPrimaryKeySelective(customerCard);
    }

    @Override
    public int deleteMryCustomerCardByIds(String ids, SysUser user) {
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
        Date nowDate = new Date();
        if (CollectionUtils.isNotEmpty(idsLongs)) {
            MryCustomerCardExample example = new MryCustomerCardExample();
            example.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsLongs);

            List<MryCustomerCard> customerCards = this.customerCardMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(customerCards)) {
                for (MryCustomerCard card : customerCards) {
                    // 减少当前客户对应的积分数、消费次数
                    MryCustomer customer = this.customerMapper.selectByPrimaryKey(card.getCustomerId());
                    if (customer != null) {
                        Long totalCustomePoints = customer.getTotalCustomePoints();
                        totalCustomePoints = totalCustomePoints - card.getTotalPoints();
                        customer.setTotalCustomePoints(totalCustomePoints);

                        Short totalCustomeTimes = customer.getTotalCustomeTimes();
                        Integer totalCustomeTimesI = totalCustomeTimes - card.getTotalTimes();
                        customer.setTotalCustomeTimes(totalCustomeTimesI.shortValue());

                        customer.setUpdateTime(nowDate);
                        customer.setOperatorId(user.getUserId());
                        customer.setOperatorName(user.getUserName());
                        this.customerMapper.updateByPrimaryKeySelective(customer);
                    }
                }
            }

            MryCustomerCard update = new MryCustomerCard();
            update.setIsDeleted(true);
            update.setUpdateTime(nowDate);
            if (user != null) {
                update.setOperatorId(user.getUserId());
                update.setOperatorName(user.getUserName());
            }
            return this.customerCardMapper.updateByExampleSelective(update, example);
        }
        return 0;
    }

    @Override
    public List<MryCustomer> getCustomersByShopId(MryCustomerCard customerCard) {
        MryCustomerExample customerExample = new MryCustomerExample();
        MryCustomerExample.Criteria customerCri = customerExample.createCriteria();
        customerCri.andIsDeletedEqualTo(false).andShopIdEqualTo(customerCard.getShopId());
        customerExample.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);

        List<MryCustomer> customers = this.customerMapper.selectByExample(customerExample);

        return customers;
    }

    @Override
    public List<MryCustomerCard> getCustomerCardsByShopCustomer(MryCustomerCard customerCard) {
        final MryCustomerCardExample customerCardExample = new MryCustomerCardExample();
        customerCardExample.createCriteria().andIsDeletedEqualTo(false).andShopIdEqualTo(customerCard.getShopId())
                .andCustomerIdEqualTo(customerCard.getCustomerId());
        customerCardExample.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);
        final List<MryCustomerCard> customerCards = this.customerCardMapper.selectByExample(customerCardExample);

        return customerCards;
    }

    @Override
    public List<MryServicePro> getServiceProsByShopId(MryCustomerCard customerCard) {
        MryServiceProExample example = new MryServiceProExample();
        example.createCriteria().andIsDeletedEqualTo(false).andShopIdEqualTo(customerCard.getShopId());
        example.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);
        List<MryServicePro> servicePros = this.serviceProMapper.selectByExample(example);

        return servicePros;
    }

    @Override
    public List<MryStaff> getStaffsByShopId(MryCustomerCard customerCard) {
        MryStaffExample example = new MryStaffExample();
        example.createCriteria().andIsDeletedEqualTo(false).andShopIdEqualTo(customerCard.getShopId());
        example.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);

        List<MryStaff> staffList = this.staffMapper.selectByExample(example);

        return staffList;
    }

    @Override
    public List<MryShopCard> getShopCardsByShopId(MryCustomerCard customerCard) {
        final MryShopCardExample cardExample = new MryShopCardExample();
        cardExample.createCriteria().andIsDeletedEqualTo(false).andShopIdEqualTo(customerCard.getShopId());
        cardExample.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);

        final List<MryShopCard> shopCards = this.shopCardMapper.selectByExample(cardExample);
        return shopCards;
    }

    @Override
    public Map<Long, MryCustomer> listCustomers(MryCustomerCard customerCard) {
        final MryCustomerExample customerExample = new MryCustomerExample();
        MryCustomerExample.Criteria customerCri = customerExample.createCriteria();
        customerCri.andIsDeletedEqualTo(false);
        if (customerCard.getShopId() != null) {
            customerCri.andShopIdEqualTo(customerCard.getShopId());
        }
        if (StringUtils.isNotBlank(customerCard.getCustomerName()) && StringUtils.isNotBlank(customerCard.getCustomerName().trim())) {
            customerCri.andNameLike("%" + customerCard.getCustomerName() + "%");
        }
        final List<MryCustomer> customers = this.customerMapper.selectByExample(customerExample);
        final Map<Long, MryCustomer> customerMap = MapUtil.newHashMap();
        if (CollectionUtils.isNotEmpty(customers)) {
            customers.forEach(item -> {
                customerMap.putIfAbsent(item.getId(), item);
            });
        }

        return customerMap;
    }
}
