package com.ruoyi.mry.service.impl;

import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.mapper.MryShopCostMapper;
import com.ruoyi.mry.mapper.MryShopMapper;
import com.ruoyi.mry.model.MryShop;
import com.ruoyi.mry.model.MryShopCost;
import com.ruoyi.mry.model.MryShopCostExample;
import com.ruoyi.mry.service.MryShopCostService;
import com.ruoyi.mry.util.MryDecimalUtils;
import com.ruoyi.mry.util.MryTimeUtils;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("mryShopCostService")
public class MryShopCostServiceImpl implements MryShopCostService{

    @Autowired
    private MryShopCostMapper shopCostMapper;
    @Autowired
    private MryShopMapper shopMapper;

    @Override
    public List<MryShopCost> selectShopCosts(MryShopCost shopCost) {
        final MryShopCostExample example = new MryShopCostExample();
        final MryShopCostExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (StringUtils.isNotBlank(shopCost.getShopName()) && StringUtils.isNotBlank(shopCost.getShopName().trim())) {
            criteria.andShopNameLike("%" + shopCost.getShopName().trim() + "%%");
        }
        if (StringUtils.isNotBlank(shopCost.getCostTimeStart())) {
            String costTimeStart = shopCost.getCostTimeStart() + " " + "00:00:00";
            final Date costTimeStartD = MryTimeUtils.parseTime(costTimeStart, MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS);
            criteria.andCostTimeGreaterThanOrEqualTo(costTimeStartD);
        }
        if (StringUtils.isNotBlank(shopCost.getCostTimeEnd())) {
            String costTimeEnd = shopCost.getCostTimeEnd() + " " + "23:59:59";
            final Date costTimeEndD = MryTimeUtils.parseTime(costTimeEnd, MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS);
            criteria.andCostTimeLessThanOrEqualTo(costTimeEndD);
        }
        example.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);

        final List<MryShopCost> shopCosts = this.shopCostMapper.selectByExample(example);
        // 统计查出来的总和:放在第一条记录里的 totalAmount 字段里
        if (CollectionUtils.isNotEmpty(shopCosts)) {
            BigDecimal totalAmount = MryDecimalUtils.getDefaultDecimal();
            for (MryShopCost cost : shopCosts) {
                totalAmount = MryDecimalUtils.add(totalAmount, cost.getAmount());
            }
            String totalAmountStr = MryDecimalUtils.formatDecimal(totalAmount).toString();
            shopCosts.get(0).setTotalAmount(totalAmountStr);
        }

        return shopCosts;
    }

    @Override
    public int insertShopCost(MryShopCost shopCost) {
        final Date nowDate = new Date();

        MryShop shop = this.shopMapper.selectByPrimaryKey(shopCost.getShopId());
        shopCost.setShopName(shop.getName());

        shopCost.setCreateTime(nowDate);
        shopCost.setUpdateTime(nowDate);
        shopCost.setIsDeleted(false);

        return this.shopCostMapper.insertSelective(shopCost);
    }

    @Override
    public MryShopCost selectShopCostById(Long id) {

        MryShopCost shopCost = this.shopCostMapper.selectByPrimaryKey(id);

        if (shopCost.getCostTime() != null) {
            shopCost.setCostTimeStr(MryTimeUtils.date2Str(shopCost.getCostTime(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }

        return shopCost;
    }

    @Override
    public int updateShopCost(MryShopCost shopCost) {
        final Date nowDate = new Date();
        shopCost.setUpdateTime(nowDate);

        if (StringUtils.isNotBlank(shopCost.getCostTimeStr())) {
            shopCost.setCostTime(MryTimeUtils.parseTime(shopCost.getCostTimeStr(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }

        return this.shopCostMapper.updateByPrimaryKeySelective(shopCost);
    }

    @Override
    public int deleteShopCostByIds(String ids, SysUser user) {
        if (com.ruoyi.common.utils.StringUtils.isBlank(ids)) {
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
            final MryShopCostExample example = new MryShopCostExample();
            example.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsLongs);
            final MryShopCost update = new MryShopCost();
            update.setIsDeleted(true);
            update.setUpdateTime(new Date());
            if (user != null) {
                update.setOperatorId(user.getUserId());
                update.setOperatorName(user.getUserName());
            }
            return this.shopCostMapper.updateByExampleSelective(update, example);
        }
        return 0;
    }
}
