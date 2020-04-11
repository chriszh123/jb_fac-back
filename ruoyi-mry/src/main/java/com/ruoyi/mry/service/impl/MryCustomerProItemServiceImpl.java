/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryCustomerProItemService
 */
package com.ruoyi.mry.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.enums.CustomeType;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public int insertCustomerProItem(MryCustomerProItem customerProItem) {
        Date nowDate = new Date();
        customerProItem.setIsDeleted(false);
        customerProItem.setCreateTime(nowDate);
        customerProItem.setUpdateTime(nowDate);

        // 减少当前客户对应的积分数、消费次数
        MryCustomer customer = this.customerMapper.selectByPrimaryKey(customerProItem.getCustomerId());
        if (customer != null) {
            if (customerProItem.getCustomeType().equals(CustomeType.POINTS.getValue())) {
                Long leftPoints = customer.getLeftPoints();
                if (leftPoints.equals(0L)) {
                    leftPoints = customer.getTotalCustomePoints();
                }
                leftPoints = leftPoints - customerProItem.getCustomePoints();
                customer.setLeftPoints(leftPoints);
            } else if (customerProItem.getCustomeType().equals(CustomeType.TIMES.getValue())) {
                Short leftTimes = customer.getLeftTimes();
                if (leftTimes.equals(Short.valueOf("0"))) {
                    leftTimes = customer.getTotalCustomeTimes();
                }
                Integer leftTimesI = leftTimes - 1;
                customer.setLeftTimes(leftTimesI.shortValue());
            }

            customer.setUpdateTime(nowDate);
            customer.setOperatorId(customerProItem.getOperatorId());
            customer.setOperatorName(customerProItem.getOperatorName());
            this.customerMapper.updateByPrimaryKeySelective(customer);
        }

        // 客户消费一次需要同步更新当前客人对应当前消费卡中，当前消费项目对应得消费次数
        final MryCustomerCardExample customerCardExample = new MryCustomerCardExample();
        customerCardExample.createCriteria().andIsDeletedEqualTo(false).andShopIdEqualTo(customerProItem.getShopId())
                .andCustomerIdEqualTo(customerProItem.getCustomerId()).andIdEqualTo(customerProItem.getCardId());
        final List<MryCustomerCard>  customerCards = this.customerCardMapper.selectByExample(customerCardExample);
        if (CollUtil.isNotEmpty(customerCards)) {
            final MryCustomerCard customerCard = customerCards.get(0);
            // [服务项目id - 总次数 - 当前消费次数]
            final String proInfo = customerCard.getInitProIds();
            String[] proInfoArr = proInfo.split(StrUtil.COMMA);
            StringBuilder proInfoStr = new StringBuilder();
            for (String proInfoItem : proInfoArr) {
                String[] proInfoItemArr = proInfoItem.split("-");
                if (StrUtil.equals(proInfoItemArr[0], customerProItem.getProId().toString())) {
                    int counsumeCount = Convert.toInt(proInfoItemArr[2], 0) + 1;
                    String newProInfoItem = proInfoItemArr[0] + "-" + proInfoItemArr[1] + "-" + counsumeCount;
                    proInfoStr.append(newProInfoItem).append(StrUtil.COMMA);
                } else {
                    proInfoStr.append(proInfoItem).append(StrUtil.COMMA);
                }
            }
            if (StrUtil.isNotBlank(proInfoStr.toString())) {
                proInfoStr = proInfoStr.deleteCharAt(proInfoStr.toString().length() - 1);
            }
            customerCard.setInitProIds(proInfoStr.toString());
            customerCard.setUpdateTime(nowDate);
            customerCard.setOperatorId(customerProItem.getOperatorId());
            customerCard.setOperatorName(customerProItem.getOperatorName());
            int rows = this.customerCardMapper.updateByPrimaryKeySelective(customerCard);
            if (rows > 0) {
                log.info(StrUtil.format("================[更新用户办卡中选择的服务项目当前消费次数] success, customerCard:{}", customerCard));
            } else {
                log.info(StrUtil.format("================[更新用户办卡中选择的服务项目当前消费次数] fail, customerCard:{}", customerCard));
            }
        }


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
        Date nowDate = new Date();
        if (CollectionUtils.isNotEmpty(idsLongs)) {
            MryCustomerProItemExample example = new MryCustomerProItemExample();
            example.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsLongs);
            List<MryCustomerProItem> customerProItems = this.customerProItemMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(customerProItems)) {
                for (MryCustomerProItem customerProItem : customerProItems) {
                    // 增加当前客户对应的积分数、消费次数
                    MryCustomer customer = this.customerMapper.selectByPrimaryKey(customerProItem.getCustomerId());
                    if (customer != null) {
                        if (customerProItem.getCustomeType().equals(CustomeType.POINTS.getValue())) {
                            Long leftPoints = customer.getLeftPoints();
                            if (leftPoints.equals(0L)) {
                                leftPoints = customer.getTotalCustomePoints();
                            }
                            leftPoints = leftPoints + customerProItem.getCustomePoints();
                            customer.setLeftPoints(leftPoints);
                        } else if (customerProItem.getCustomeType().equals(CustomeType.TIMES.getValue())) {
                            Short leftTimes = customer.getLeftTimes();
                            if (leftTimes.equals(Short.valueOf("0"))) {
                                leftTimes = customer.getTotalCustomeTimes();
                            }
                            Integer leftTimesI = leftTimes + 1;
                            customer.setLeftTimes(leftTimesI.shortValue());
                        }

                        customer.setUpdateTime(nowDate);
                        customer.setOperatorId(customerProItem.getOperatorId());
                        customer.setOperatorName(customerProItem.getOperatorName());
                        this.customerMapper.updateByPrimaryKeySelective(customer);
                    }
                }
            }

            MryCustomerProItem update = new MryCustomerProItem();
            update.setIsDeleted(true);
            update.setUpdateTime(nowDate);
            if (user != null) {
                update.setOperatorId(user.getUserId());
                update.setOperatorName(user.getUserName());
            }
            return this.customerProItemMapper.updateByExampleSelective(update, example);
        }

        return 0;
    }
}
