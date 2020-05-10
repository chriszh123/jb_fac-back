package com.ruoyi.mry.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.mapper.MryCustomerInvestMapper;
import com.ruoyi.mry.mapper.MryCustomerMapper;
import com.ruoyi.mry.mapper.MryShopMapper;
import com.ruoyi.mry.model.*;
import com.ruoyi.mry.service.MryCustomerInvestService;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("mryCustomerInvestService")
public class MryCustomerInvestServiceImpl implements MryCustomerInvestService {

    @Autowired
    private MryCustomerInvestMapper customerInvestMapper;
    @Autowired
    private MryCustomerMapper customerMapper;
    @Autowired
    private MryShopMapper shopMapper;

    @Override
    public List<MryCustomerInvest> selectCustomerInvests(MryCustomerInvest customerInvest, List<MryShop> shops, Map<Long, MryCustomer> customerMap) {
        MryCustomerInvestExample example = new MryCustomerInvestExample();
        MryCustomerInvestExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (MapUtil.isNotEmpty(customerMap)) {
            criteria.andCustomerIdIn(new ArrayList<>(customerMap.keySet()));
        }
        example.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);
        List<MryCustomerInvest> customerInvests = this.customerInvestMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(customerInvests)) {
            // 所属店面
            Map<Short, MryShop> shopMap = new HashMap<>();
            shops.forEach(item -> shopMap.putIfAbsent(item.getId(), item));
            for (MryCustomerInvest item : customerInvests) {
                item.setShopName(shopMap.get(item.getShopId()).getName());
                // 客户名称
                if (customerMap.containsKey(item.getCustomerId())) {
                    item.setCustomerName(customerMap.get(item.getCustomerId()).getName());
                }
            }
        }

        return customerInvests;
    }

    @Override
    public int insertCustomerInvest(MryCustomerInvest customerInvest) {
        Date nowDate = new Date();
        customerInvest.setIsDeleted(false);
        customerInvest.setCreateTime(nowDate);
        customerInvest.setUpdateTime(nowDate);

        // 新增当前客户对应的积分数、消费次数
        MryCustomer customer = this.customerMapper.selectByPrimaryKey(customerInvest.getCustomerId());
        if (customer != null) {
            Long totalCustomePoints = customer.getTotalCustomePoints();
            totalCustomePoints = totalCustomePoints + customerInvest.getCustomePoints();
            customer.setTotalCustomePoints(totalCustomePoints);

            Long leftPoints = customer.getLeftPoints();
            leftPoints = leftPoints + customerInvest.getCustomePoints();
            customer.setLeftPoints(leftPoints);

            Short totalCustomeTimes = customer.getTotalCustomeTimes();
            Integer totalCustomeTimesI = totalCustomeTimes + customerInvest.getCustomeTimes();
            customer.setTotalCustomeTimes(totalCustomeTimesI.shortValue());

            Short leftTimes = customer.getLeftTimes();
            Integer leftTimesI = leftTimes + customerInvest.getCustomeTimes();
            customer.setLeftTimes(leftTimesI.shortValue());

            customer.setUpdateTime(nowDate);
            customer.setOperatorId(customerInvest.getOperatorId());
            customer.setOperatorName(customerInvest.getOperatorName());
            this.customerMapper.updateByPrimaryKeySelective(customer);
        }

        return this.customerInvestMapper.insertSelective(customerInvest);
    }

    @Override
    public MryCustomerInvest selectCustomerInvest(Long id) {
        MryCustomerInvest customerInvest = this.customerInvestMapper.selectByPrimaryKey(id);
        if (customerInvest != null && customerInvest.getInvestTime() != null) {
            customerInvest.setInvestTimeStr(DateUtil.format(customerInvest.getInvestTime(), DatePattern.NORM_DATE_PATTERN));
        }

        return customerInvest;
    }

    @Override
    public int updateCustomerInvest(MryCustomerInvest customerInvest) {
        Date nowDate = new Date();
        customerInvest.setUpdateTime(nowDate);
        if (StrUtil.isNotBlank(customerInvest.getInvestTimeStr())) {
            customerInvest.setInvestTime(DateUtil.parse(customerInvest.getInvestTimeStr(), DatePattern.NORM_DATE_PATTERN));
        }

        return this.customerInvestMapper.updateByPrimaryKeySelective(customerInvest);
    }

    @Override
    public int deleteCustomerInvestByIds(String ids, SysUser user) {
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
            MryCustomerInvestExample example = new MryCustomerInvestExample();
            example.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsLongs);

            List<MryCustomerInvest> invests = this.customerInvestMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(invests)) {
                for (MryCustomerInvest invest : invests) {
                    // 减少当前客户对应的积分数、消费次数
                    MryCustomer customer = this.customerMapper.selectByPrimaryKey(invest.getCustomerId());
                    if (customer != null) {
                        // 积分
                        Long totalCustomePoints = customer.getLeftPoints();
                        totalCustomePoints = totalCustomePoints - invest.getCustomePoints();
                        customer.setTotalCustomePoints(totalCustomePoints);
                        // 消费次数
                        Short totalCustomeTimes = customer.getTotalCustomeTimes();
                        Integer totalCustomeTimesI = totalCustomeTimes - invest.getCustomeTimes();
                        customer.setTotalCustomeTimes(totalCustomeTimesI.shortValue());

                        customer.setUpdateTime(nowDate);
                        customer.setOperatorId(user.getUserId());
                        customer.setOperatorName(user.getUserName());
                        this.customerMapper.updateByPrimaryKeySelective(customer);
                    }
                }
            }

            MryCustomerInvest update = new MryCustomerInvest();
            update.setIsDeleted(true);
            update.setUpdateTime(nowDate);
            if (user != null) {
                update.setOperatorId(user.getUserId());
                update.setOperatorName(user.getUserName());
            }
            return this.customerInvestMapper.updateByExampleSelective(update, example);
        }

        return 0;
    }
}
